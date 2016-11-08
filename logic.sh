#!/bin/bash


mkdir root

#return variable
return=""

function in_tree {
    return=$(find "$2" -name "$1")
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
    in_tree ${args[0]} $return
    if [ "$return" =  "" ]; then
	mkdir $func_path/${args[0]}
	mkdir $func_path/${args[0]}/${args[1]}
    else
	node_path=$return
	in_tree ${args[1]} $return
	if [ "$return" =  "" ]; then
	    mkdir $node_path/${args[1]}
	fi
    fi
}

function q {
    echo ""
}

function r {
    echo ""
}



f father a b
f father b c
f father n m
f father n i
f father n v
f father m o
f father o p
f father b c
f father b z
f cats a b
f cats b l
# I think those break
# f father z a
# f father y c 

tree root
rm -r root
