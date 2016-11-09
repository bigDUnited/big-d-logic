#!/bin/bash


mkdir root

#return variable
return=""
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

function q {
    functor=$1
    args=(${@:2})
    arg_path=$( IFS=$'/'; echo "${args[*]}" )
    in_tree "root/$functor/$arg_path"
    if [ "$return" =  "" ]; then
	echo "false"
    else
	echo "true"
    fi
}

function r {
    echo ${@}
}


f father m n
f father a b
f father a b c
# f father b c
q father a b
q father b c
r grandfather X Y :- father X Z :and father Z Y

# f father n m
# f father n i
# f father n v
# f father m o
# f father o p
# f father b c
# f father b z
# f cats a b
#f cats b l
# I think those break
# f father c b

#f father y c 

tree root
rm -r root
