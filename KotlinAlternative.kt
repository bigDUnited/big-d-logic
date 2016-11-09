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
    var god : Node = a;
    var parent : Node = Node(null, "", null);
    var grandchild : Node = Node(null, "", null);

    println("Hi! Req.Parent: " + aName + " & req.Child: " + bName);

    if( god.getName() == aName ) {
        parent = god;
        println("You are first element (god!) - EASY!");
    } else {
        println("Go deep, current parent : " + a.getName());

        for (godElem in god.getChildren()) {
            println("Hi! Plausable Parent: " + godElem.getName() + " & req.Child: " + bName );

            var result = father(godElem, aName, bName);
            if (result == true ) return result;
        }
    }
    println("Checking : " + parent.getName() + " atm ...");

    if ( parent.getName() != "" ) {
        for (parentElem in parent.getChildren()) {
            println("> Child : " + parentElem.getName());
            for (grandchildElem in parentElem.getChildren()) {
                if (grandchildElem.getName() == bName) {
                    grandchild = grandchildElem;

                    println("I AM the CORRECT >>> Grandchild!!! : " + grandchildElem.getName());
                    break;
                } else {

                    println("I am NOT the correct >>> Grandchild : " + grandchildElem.getName());
                }

            }
        }
    }

    if ( (grandchild.getName() != "") && (parent.getName() != "") ) {

        println("SUCCESS : parentName: " + parent.getName() + " & " + grandchild.getName() );
        return true;
    }

    println("Did not find proper child, sorry!");
    return false;
}

fun father( a : Node, aName: String, bName: String ): Boolean {
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

            var result = father(godElem, aName, bName);
            if (result == true ) return result;
        }
    }
    println("Checking : " + parent.getName() + " atm ...");

    if ( parent.getName() != "" ) {
        for (parentElem in parent.getChildren()) {
            if (parentElem.getName() == bName) {
                child = parentElem;

                println("I AM the CORRECT child!!! : " + parentElem.getName());
                break;
            } else {

                println("I am NOT the correct child : " + parentElem.getName());
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

fun main(args: Array<String>) {
    val a = Node(null, "1", ArrayList<Node>() );
    builder( a );

    //for (parentElem in a.getChildren()) {println(parentElem.getName())}
    //println( "Result is " + father(a, "1", "2") );
    //println( "Result is " + father(a, "7", "13") );
    println( "Result is " +  grandfather(a, "5", "11") );

}

//MAP
// 1 > [2 > [ 4, 5 > [ 9, 10, 11 ], 6], 3 > [ 7 > [ 12, 13 ], 8 ]