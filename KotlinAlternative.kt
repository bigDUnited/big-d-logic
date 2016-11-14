import java.util.*

//? = allow nulls

class Node constructor(parentNode: Node?, nodeName: String, childenNode: ArrayList<Node>?) {
    private var parent : Node? = parentNode;
    private var name : String = nodeName
    private var children : ArrayList<Node>? = childenNode

    fun getName(): String {
       return name.toString();
    }

    fun addChild(element: Node?): Boolean {
        return children!!.add( element!! );
    }

    fun getChildren(): ArrayList<Node> {
        return children!!;
    }
}

fun builder( a : Node ) {
    var b = Node(a, "2", ArrayList<Node>() );
    a.addChild( b );
    var c = Node(a, "3", ArrayList<Node>() );
    a.addChild( c );

    var d = Node(b, "4", ArrayList<Node>() );
    b.addChild( d );
    var e = Node(b, "5", ArrayList<Node>() );
    b.addChild( e );
    var f = Node(b, "6", ArrayList<Node>() );
    b.addChild( f );

        var i = Node(e, "9", ArrayList<Node>() );
        e.addChild(i);
        var j = Node(e, "10", ArrayList<Node>() );
        e.addChild(j);
        var k = Node(e, "11", ArrayList<Node>() );
        e.addChild(k);


    var g = Node(c, "7", ArrayList<Node>() );
    c.addChild( g );
    var h = Node(c, "8", ArrayList<Node>() );
    c.addChild( h );

        var l = Node(g, "12", ArrayList<Node>() );
        g.addChild(l);
        var m = Node(g, "13", ArrayList<Node>() );
        g.addChild(m);


}

fun grandfather( a : Node, aName: String, bName: String ): Boolean {
    return father(a, aName, bName, "grandfather");
}

fun father( a : Node, aName: String, bName: String, state : String ): Boolean {
    var god : Node = a;
    var parent : Node = Node(null, "", null);
    var child : Node = Node(null, "", null);

    println("Hi! Req.Parent: " + aName + " & req.Child: " + bName);

    if( god.getName() == aName ) {
        parent = god;

        println("You are first element (god!) - EASY!");

    } else {
        println("Go deep, current parent : " + a.getName());

        for (godElem in god.getChildren()) {
            println("Hi! Plausable Parent: " + godElem.getName() + " & req.Child: " + bName );

            var result = father(godElem, aName, bName, state);
            if (result == true ) return result;
        }
    }
    println("Checking : " + parent.getName() + " atm ...");

    if ( parent.getName() != "" ) {
        for (parentElem in parent.getChildren()) {
            if( "grandfather" == state ){
                for (grandchildElem in parentElem.getChildren()) {
                    if (grandchildElem.getName() == bName) {
                        child = grandchildElem;

                        println("I AM the CORRECT >>> Grandchild!!! : " + grandchildElem.getName());
                        break;
                    } else {

                        println("I am NOT the correct >>> Grandchild : " + grandchildElem.getName());
                    }

                }
            } else {
                if (parentElem.getName() == bName) {
                    child = parentElem;

                    println("I AM the CORRECT child!!! : " + parentElem.getName());
                    break;
                } else {

                    println("I am NOT the correct child : " + parentElem.getName());
                }
            }
        }
    }

    if ( (child.getName() != "") && (parent.getName() != "") ) {

        println("SUCCESS : parentName: " + parent.getName() + " & " + child.getName() );
        return true;
    }

    println("Did not find proper child, sorry!");
    return false;

}

fun traceFather( masterNode: Node, candidateFatherNode: Node ): Node? {
    for (masterChildElem in masterNode.getChildren()) {
        println("Candidate: " + candidateFatherNode.getName() + " vs Curr.Child : " + masterChildElem.getName()) ;
        if ( masterChildElem.getName() == candidateFatherNode.getName() ) {
            return masterChildElem;
        }
        println("Jump in");
        var tracingResult = traceFather(masterChildElem, candidateFatherNode);
        if ( tracingResult != null ) return tracingResult
    }
    return null
}

fun fact( masterNode: Node, fatherName: String, childName: String ) {
    var candidateFatherNode = Node( null, fatherName, ArrayList<Node>() );
    var candidateChildNode = Node( null, childName, ArrayList<Node>() );
    println("fatherName: " + fatherName) ;
    var father = traceFather(masterNode, candidateFatherNode);
    var child = traceFather(masterNode, candidateChildNode);

    if ( child == null ) {
        if ( father != null ) {
            println("I am existing");
            father.addChild(candidateChildNode);
        } else if (fatherName == masterNode.getName()) {
            println("I am the father of all");
            masterNode.addChild(candidateChildNode);
        } else {
            println("Yeahh, not existing");
            candidateFatherNode.addChild( candidateChildNode );
            masterNode.addChild( candidateFatherNode )
        }
    } else {
        print("Child is existing : " + child.getName() + "\n");
        if ( father != null ) {
            father.addChild(child);
        }
    }
}

fun exposeStructure(trace: String, masterNode : Node) {
    var preventOverflow = ArrayList<Node>();
    preventOverflow.add( masterNode );

    for (child in masterNode.getChildren()){
        println( "I am father '" + masterNode.getName() + "' and my child is '" + trace + " " + child.getName() + "'" );
        exposeStructure(trace + ">", child);

//        for (previousChild in preventOverflow) {
//            if ( previousChild.getName() != child.getName() ){
//                println("Not equal");
//                exposeStructure(trace + ">", child);
//            } else {
//                println("EQUAL UPS! Parent " + previousChild.getName() + " Child " + child.getName());
//            }
//        }
    }
}

fun main(args: Array<String>) {
    var masterNode = Node(null, "A", ArrayList<Node>() );
    fact(masterNode, "A", "B");
    fact(masterNode, "A", "C");
    fact(masterNode, "C", "D");
    fact(masterNode, "B", "E");
    //fact(masterNode, "E", "B");
//    fact(masterNode, "B", "F");
//    fact(masterNode, "B", "G");
//    fact(masterNode, "F", "H");
//    fact(masterNode, "F", "I");
//    fact(masterNode, "F", "J");
//    fact(masterNode, "G", "K");
//    fact(masterNode, "C", "MAREK");
    println("");
    exposeStructure(">", masterNode );

    //val a = Node(null, "1", ArrayList<Node>() );
    //builder( a );

    //println( "Result is " + father(a, "5", "11", "father") );
    //println( "Result is " +  grandfather(a, "2", "11") );

}

//MAP
// 1 > [2 > [ 4, 5 > [ 9, 10, 11 ], 6], 3 > [ 7 > [ 12, 13 ], 8 ]
