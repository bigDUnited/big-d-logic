#!/bin/bash


mkdir root

#return variable
return=""

function get_case {
    letter=$1
    case ${letter:0:1} in
        [0-9]*)
                return="digit"
                ;;
        [[:upper:]])
                return="upper"
                ;;
        [[:lower:]])
                return="lower"
                ;;
        *)
                return="other"
                ;;
    esac
}


function in_tree {
    if [ -d $1 ]; then
    	return="$1"
    else
    	return=""
    fi    
}

function find_functor {
    if ! ls root/$1 &>/dev/null ; then
	mkdir root/$1
    fi
    return="root/$1"
}


function f {
    functor=$1
    args=(${@:2})
    find_functor $functor
    func_path=$return
    arg_path=$( IFS=$'/'; echo "${args[*]}" )
    in_tree "$func_path/$arg_path"
    if [ "$return" = "" ]; then
	mkdir -p "$func_path/$arg_path"
    fi
}

#search for constant values in trie
function const_q {
    functor=$1
    args=(${@:2})
    arg_path=$( IFS=$'/'; echo "${args[*]}" )
    in_tree "root/$functor/$arg_path"
    if [ "$return" =  "" ]; then
	echo "false"
	return 0
    else
	echo "true"
	return 1
    fi
}


ast=()
#return a chain of operations to be evaluated
function parse_q() {
    context=()
    ast=() #abstract syntax tree
    iast=0 #index of abstract sytax tree
    wfunctor=1 #awaiting functor
    wargs=0 #awaiting arguments
    swap=1 #swap on next iteration
    for var in "$@"
    do
	get_case $var
	if [ "$return" = "lower" ] && [ $wfunctor = 1 ]; then
	    ast+=("functor;$var");
	    wfunctor=0
	    wargs=1
	    swap=`expr $swap + 1`;
	elif [ "$return" = "upper" ] && [ $wfunctor = 0 ] &&
	    [ $wargs = 1 ]; then
	    ast[$iast]+=";$var";
	elif [ "$return" = "lower" ] && [ $wfunctor = 0 ] &&
	    [ $wargs = 1 ]; then
	    ast[$iast]+=";$var";
	elif [ "${var:0:1}" = ":" ] && [ $wfunctor = 0 ] &&
	    [ $wargs = 1 ]; then
	    ast+=("operation;${var:1}")
	    iast=`expr $iast + 2`
	    wfunctor=1
	    wargs=0
	fi
    done
}

#order the parsed input
function order_pile {
    pile=(${@})
    index=0
    end="${#pile[@]}"
    swap=1
    while [ $index -le $end ]
    do
	IFS=';' read -ra token <<< "${pile[$index]}"
	if [ "${token[0]}" = "operation" ] && [ $swap = 1 ]; then
	    tmp="${pile[$index]}"
	    pile[$index]="${pile[$index+1]}"
	    pile[$index+1]="${tmp[@]}"
	    index=`expr $index + 1`
	fi
	index=`expr $index + 1`
    done
    ast=(${pile[@]})
}

function operation_and {
    len="${#return_stack[@]}";
    a="${return_stack[$len-1]}";
    b="${return_stack[$len-2]}";
    if [ "$a" = "true" ] && [ "$b" = "true" ]; then
    	tmp="true"
    else
    	tmp="false"
    fi

    return_stack=("${return_stack[@]:2}")
    return_stack+=($tmp)
}

function operation_or {
    len="${#return_stack[@]}";
    a="${return_stack[$len-1]}";
    b="${return_stack[$len-2]}";
    if [ "$a" = "true" ] || [ "$b" = "true" ]; then
    	tmp="true"
    else
    	tmp="false"
    fi

    return_stack=("${return_stack[@]:2}")
    return_stack+=($tmp)
}

#execute a pile of const queries and operations using a return stack
function pile_q {
    pile=(${@})
    return_stack=()
    for var in "${pile[@]}"
    do
	IFS=';' read -ra token <<< "$var"
	if [ "${token[0]}" = "functor" ]; then
	    if [ "${token[@]:1:1}" = "true" ] ||
		[ "${token[@]:1:1}" = "false" ]; then
		return_stack+=("${token[@]:1:1}")
	    else
		return_stack+=($(const_q "${token[@]:1}"))		
	    fi

	else [ "${token[0]}" = "operation" ];
	    operation_${token[1]}
	fi
    done
    echo "${return_stack[@]}"
}

function q {
    parse_q ${@}
    order_pile ${ast[@]}
    pile_q ${ast[@]}
}

#context where variables are stored
declare -A context
function fetch_variables {
    context=()
    transformed_args=()
    pos=0
    for var in "$@"
    do
    	get_case $var
    	if [ "$return" =  "upper" ]; then
	    context[$var]+="$pos;"
    	    transformed_args+=("*")
    	else
    	    transformed_args+=("$var")
    	fi
	pos=`expr $pos + 1`
    done
    arg_path=$( IFS=$'/'; echo "${transformed_args[*]}" )
    ls root/$arg_path
    echo "${!context[@]}"
    echo "${context[@]}"
}

function r {
    fact=()
    query=()
    stage=0
    for i in "${@}"
    do
	if [ "$i" = ":-" ]; then
	    stage=1
	    continue
        fi

	if [ $stage = 0 ]; then
	    fact+=("$i")
	else
	    query+=("$i")
	fi
    done
    res=$(q "${query[@]}")
    if [ "$res" = "true" ]; then
	f "${fact[@]}"
    fi
}

#q father X Y :and father Y X :and cats X Y
f father a b
f father b a
f cats a b
q father b a :and $(q father a b :or father m b)
r father l p :- father a b :and cats a b
# fetch_variables father A B A M A

#father X Y
#0      x x
#1      x x

#father;
#cats X Y
#0    x
#1      x

#cats;
# f father m n
# f father a b
# f father a b c
# f father b a
# f cats a b c x d
# f cats a c c x d
# #f father a a
# #f father b c


# q father a a
#q father b c

# f father n m
# f father n i
# f father n v
# f father m o
# f father o p
# f father b c
# f father b z
# f cats a b
#f cats b l

tree root
rm -r root
