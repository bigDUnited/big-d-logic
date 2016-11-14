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

    fun getParent(): Node {
        return parent!!;
    }
}

class Query( masterNode: Node ) {
    private var root : Node = masterNode;

    fun father( fatherName: String, childName: String ) : Boolean {
        return find(root, fatherName, childName, "father");
    }

    fun grandfather( fatherName: String, childName: String ) : Boolean {
        return find(root, fatherName, childName, "grandfather");
    }

    private fun fatherSearch( parentChild: Node, childName: String ): Node {
        var child : Node = Node(null, "", null);
        if (parentChild.getName() == childName) {
            child = parentChild;
            println("[FATHER] Correct child: " + parentChild.getName());
        } else {
            //useless except for comment
            println("[FATHER] Incorrect child: " + parentChild.getName());
        }
        return child;

    }

    private fun grandfatherSearch( parentChild: Node, childName: String ): Node {
        var child : Node = Node(null, "", null);
        for (grandchildElem in parentChild.getChildren()) {
            if (grandchildElem.getName() == childName) {
                child = grandchildElem;
                println("[GRANDFATHER] Correct child: " + grandchildElem.getName());
                break;
            } else {
                println("[GRANDFATHER] Incorrect child: " + grandchildElem.getName());
            }
        }
        return child;
    }

    private fun find( currentRoot: Node, fatherName: String, childName: String, state : String ): Boolean {
        var parent : Node = Node(null, "", null);
        var child : Node = Node(null, "", null);

        var safeState = "father";
        if(state == "grandfather") { safeState = state; }

        println("[FIND] Root: "+ currentRoot.getName() +", Father: " + fatherName + ", Child: " + childName);

        if( currentRoot.getName() == fatherName ) {
            parent = currentRoot;
            println("[FIND] Father element is Root! EASY!");
        } else {
            println("[FIND] Traverse from root: " + currentRoot.getName() + " to its children");

            for (godElem in currentRoot.getChildren()) {
                println("[FIND] Plausable root: " + godElem.getName() + ", Child: " + childName );

                var result = find(godElem, fatherName, childName, state);
                if (result == true ) return result;
            }
        }
        println("[FIND] Searching in parent: " + parent.getName() + " [...]");

        if ( parent.getName() != "" ) {
            for (parentElem in parent.getChildren()) {
                if( "grandfather" == safeState ){
                    child = grandfatherSearch( parentElem, childName );
                } else {
                    child = fatherSearch( parentElem, childName );
                }
                if (child.getName() != "") { break; }
            }
        }

        if ( (child.getName() != "") && (parent.getName() != "") ) {

            println("[FIND] Success : " + safeState + ": " + parent.getName() + ", Child: " + child.getName() );
            return true;
        }

        println("[FIND] Failure - Did not find proper " + safeState);
        return false;
    }
}

fun traceFather( masterNode: Node, candidateFatherNode: Node ): Node? {
    for (masterChildElem in masterNode.getChildren()) {
        if ( masterChildElem.getName() == candidateFatherNode.getName() ) {
            return masterChildElem;
        }
        var tracingResult = traceFather(masterChildElem, candidateFatherNode);
        if ( tracingResult != null ) return tracingResult
    }
    return null
}

fun fact( masterNode: Node, fatherName: String, childName: String ) {
    var candidateFatherNode = Node( null, fatherName, ArrayList<Node>() );
    var candidateChildNode = Node( null, childName, ArrayList<Node>() );

    var father = traceFather(masterNode, candidateFatherNode);
    var child = traceFather(masterNode, candidateChildNode);

    if ( child == null ) {
        if ( father != null ) {
            father.addChild(candidateChildNode);
        } else if (fatherName == masterNode.getName()) {
            masterNode.addChild(candidateChildNode);
        } else {
            candidateFatherNode.addChild( candidateChildNode );
            masterNode.addChild( candidateFatherNode )
        }
    } else {
        if ( father != null ) {
            father.addChild(child);
        } else {
            masterNode.addChild(candidateFatherNode);
            candidateFatherNode.addChild(candidateChildNode);
        }
    }
}

fun exposeStructure( masterNode : Node, preventOverflow: ArrayList<Node>)  {

    for (child in masterNode.getChildren()){
        println( "I am father '" + masterNode.getName() + "' and my child is '" + child.getName() + "'" );

        var isExisting = false;
        for (goner in preventOverflow){

            if ( child.getName() == goner.getName() ) {
                isExisting = true;
            }
        }
        if ( !isExisting ) {
            preventOverflow.add( child );
            exposeStructure( child, preventOverflow );
        } else {
            println("I am dying : " + child.getName() );
            //loop - die
        }

    }
}

fun main(args: Array<String>) {
    var masterNode = Node(null, "A", ArrayList<Node>() );

    //Normal rotation
    fact(masterNode, "A", "B");
    fact(masterNode, "B", "BB");
    fact(masterNode, "B", "E");
    fact(masterNode, "E", "F");
    fact(masterNode, "E", "G");


    fact(masterNode, "A", "C");
    fact(masterNode, "C", "D");
    fact(masterNode, "D", "W");

    //Mixup connections
    //fact(masterNode, "E", "A");
    //fact(masterNode, "Q", "D");

    //fact(masterNode, "E", "123");

    println("-------------------");
    var preventOverflow = ArrayList<Node>();
    preventOverflow.add( masterNode );
    exposeStructure(masterNode, preventOverflow );

    println("-------------------");
    var queryObj = Query(masterNode);
    println("[MAIN] Result is: " + queryObj.father("A", "B"));
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father("A", "QPDASDASDAS") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather("A", "E") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather("A", "QPDASDASDAS") );


    //previous solution
    //val a = Node(null, "1", ArrayList<Node>() );
    //builder( a );

    //println( "Result is " + father(masterNode, "A", "B", "father") );
    //println( "Result is " +  grandfather(masterNode, "A", "E") );

}
