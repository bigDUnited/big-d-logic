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

#context where variables are stored
context=()
ast=()
#return a chain of operations to be evaluated
function parse_q() {
    context=()
    ast=() #abstract syntax tree
    iast=0 #index of abstract sytax tree
    wfunctor=1 #awaiting functor
    wargs=0 #awaiting arguments
    for var in "$@"
    do
	get_case $var
	if [ "$return" = "lower" ] && [ $wfunctor = 1 ]; then
	    ast+=("functor;$var");
	    wfunctor=0
	    wargs=1
	elif [ "$return" = "upper" ] && [ $wfunctor = 0 ] &&
	    [ $wargs = 1 ]; then
	    case "${context[@]}" in
		*"$var"*) ;;
		*)
		    context+=("$var")
		    ;;
	    esac
	    ast[$iast]+=" $var";
	elif [ "$return" = "lower" ] && [ $wfunctor = 0 ] &&
	    [ $wargs = 1 ]; then
	    ast[$iast]+=" $var";
	elif [ "${var:0:1}" = ":" ] && [ $wfunctor = 0 ] &&
	    [ $wargs = 1 ]; then
	    ast+=("operation;$var")
	    wfunctor=1
	    wargs=0
	    iast=`expr $iast + 2`
	fi
    done

}

#execute a single functor query with variable and value arguments
function single_q {
    functor=($1)
    path="root/"
    slots=()
    index=0
    for var in "${functor[@]}"
    do
	get_case "$var"
	if [ "$return" = "upper" ]; then
	    path+="*/"
	else
	    path+="$var/"
	fi
	index=`expr $index + 1`
    done
    ls "$path"
}

function q {
    parse_q ${@}
    result=""
    for var in "${ast[@]}"
    do
	IFS=';' read -ra token <<< "$var"
	if [ "${token[0]}" = "functor" ]; then
	    single_q "${token[1]}" $context
	else [ "${token[0]}" = "operation" ];
	    echo "?"
	fi
	
    done    
    # functor=$1
    # args=(${@:2})
    # transformed_args=()
    # for i in "${args[@]}"
    # do
    # 	get_case $i
    # 	if [ "$return" =  "upper" ]; then
    # 	    transformed_args+=("*")
    # 	else
    # 	    transformed_args+=($i)
    # 	fi
    # done
    # const_q ${@}
}

function r {
    echo ${@}
}


f father m n
f father a b
f father a b c
#f father b c
q father X Y :and father Y X :or father Y Z a b
#q father b c
# r grandfather X Y :- father X Z :and father Z Y
# f father n m
# f father n i
# f father n v
# f father m o
# f father o p
# f father b c
# f father b z
# f cats a b
#f cats b l
# f father c b

#f father y c 

tree root
rm -r root
