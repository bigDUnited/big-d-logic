import java.util.*

class Node constructor(parentsNode: ArrayList<Node>?, nodeName: String, childrenNode: ArrayList<Node>?) {
    private var parents : ArrayList<Node>? = parentsNode;
    private var name : String = nodeName;
    private var children : ArrayList<Node>? = childrenNode;

    fun getName(): String {
       return name.toString();
    }

    fun addChild(element: Node?): Boolean {
        return children!!.add( element!! );
    }

    fun getChildren(): ArrayList<Node> {
        return children!!;
    }

    fun addParent(element: Node?): Boolean {
        return parents!!.add( element!! );
    }

    fun getParents(): ArrayList<Node> {
        return parents!!;
    }

    override fun toString(): String {
        var parentsString = "Parents : [-";

        for (parent in parents!!) {
            parentsString += parent.getName() + "-";
        }
        parentsString += "]";

        var childrenString = "Children : [-";

        for (child in children!!) {
            childrenString += child.getName() + "-";
        }
        childrenString += "]";

        return parentsString + " [*" + this.getName() + "*] " + childrenString;
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
        var child : Node = Node(ArrayList<Node>(), "", ArrayList<Node>());
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
        var child : Node = Node(ArrayList<Node>(), "", ArrayList<Node>());
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
        var parent : Node = Node(ArrayList<Node>(), "", ArrayList<Node>());
        var child : Node = Node(ArrayList<Node>(), "", ArrayList<Node>());

        var safeState = "father";
        if(state == "grandfather") { safeState = state; }

        println("[FIND] Root: "+ currentRoot.getName() +", Father: " + fatherName + ", Child: " + childName);

        if( currentRoot.getName() == fatherName ) {
            parent = currentRoot;
            println("[FIND] Father element is Root! EASY!");
        } else {
            println("[FIND] Traverse from root: " + currentRoot.getName() + " to its children");

            for (godElem in currentRoot.getChildren()) {
                println("[FIND] Plausible root: " + godElem.getName() + ", Child: " + childName );

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
            //println("Before 1" + masterChildElem.getName());
            return masterChildElem;
        }

        var tracingResult = traceFather(masterChildElem, candidateFatherNode);
        if ( tracingResult != null ) {
            //println("Before 2" + masterChildElem.getName());
            return tracingResult;
        }
    }
    return null
}

fun fact( masterNode: Node, fatherName: String, childName: String ) {
    println("fatherName: " + fatherName + ", childName: " + childName);
    var candidateFatherNode = Node( ArrayList<Node>(), fatherName, ArrayList<Node>() );
    var candidateChildNode = Node( ArrayList<Node>(), childName, ArrayList<Node>() );

    var father = traceFather(masterNode, candidateFatherNode);
    var child = traceFather(masterNode, candidateChildNode);

    if ( child == null ) {
        if ( father != null ) {
            println("1");
            candidateChildNode.addParent(father);
            father.addChild(candidateChildNode);
        } else if (fatherName == masterNode.getName()) {
            println("2");
            candidateChildNode.addParent(masterNode);
            masterNode.addChild(candidateChildNode);
        } else {
            println("3");
            candidateChildNode.addParent(candidateFatherNode);
            candidateFatherNode.addChild( candidateChildNode );
            candidateFatherNode.addParent(masterNode);
            masterNode.addChild( candidateFatherNode )
        }
    } else {
        if ( father == null ) {
            if ( candidateFatherNode.getName() == masterNode.getName() ) {
                println("5.1 " + candidateChildNode.getName());
                masterNode.addChild(child);
                child.addParent(masterNode);
            } else {
                println("5.2 " + candidateChildNode.getName());
                candidateFatherNode.addParent(masterNode);
                child.addParent(candidateFatherNode);
                candidateFatherNode.addChild(child);
                masterNode.addChild(candidateFatherNode);
            }
        } else {
            println("4 " + child.getName());
            father.addChild(child);
            child.addParent(father);
        }
    }
}

//fun rule( masterNode: Node, fatherName: String, childName: String ){
//    var candidateFatherNode = Node( ArrayList<Node>(), fatherName, ArrayList<Node>() );
//    var candidateChildNode = Node( ArrayList<Node>(), childName, ArrayList<Node>() );
//
//    var father = traceFather(masterNode, candidateFatherNode);
//    var child = traceFather(masterNode, candidateChildNode);
//}

fun exposeStructure( masterNode : Node, preventOverflow: ArrayList<Node>)  {

    if( masterNode.getParents().size == 0 ) {
        println( "[EXPOSESTRUCTURE] *ActualM* :" + masterNode.toString());
    }

    for (child in masterNode.getChildren()){
        //println( "[EXPOSESTRUCTURE] Father: '" + masterNode.getName() + "', Child: '" + child.getName() + "'" );
        println( "[EXPOSESTRUCTURE] *Actual* :" + child.toString());

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
            println("[EXPOSESTRUCTURE] Pointer to existing element '" + child.getName() + "' ... [die]" );
            //loop - die
        }
    }
}

fun main(args: Array<String>) {
    var masterNode = Node(ArrayList<Node>(), "A", ArrayList<Node>() );

    //Normal rotation
    fact(masterNode, "A", "B");
    fact(masterNode, "B", "C");
    fact(masterNode, "B", "D");
    fact(masterNode, "D", "E");

    fact(masterNode, "QQQ", "B");

    fact(masterNode, "A", "F");
    fact(masterNode, "G", "H");
    fact(masterNode, "A", "D");

    fact(masterNode, "F", "E");


//    fact(masterNode, "E", "F");
//    fact(masterNode, "E", "G");
//
//    fact(masterNode, "A", "H");
//    fact(masterNode, "H", "I");
//    fact(masterNode, "H", "J");
//
//    fact(masterNode, "Z", "ZZ");
//    fact(masterNode, "C", "D");
//    fact(masterNode, "D", "W");
//
//    //Mixup connections
//    fact(masterNode, "E", "A");
//    fact(masterNode, "Q", "D");
//
//    fact(masterNode, "E", "123");

    println("-------------------");
    var preventOverflow = ArrayList<Node>();
    preventOverflow.add( masterNode );
    exposeStructure(masterNode, preventOverflow );

//    var queryObj = Query(masterNode);
//    println("-------------------");
//    println("[MAIN] Result is: " + queryObj.father("E", "123"));
//
//    println( masterNode.toString() );
//    println("-------------------");
//    println("[MAIN] Result is: " + queryObj.father("A", "QPDASDASDAS") );
//    println("-------------------");
//    println("[MAIN] Result is: " + queryObj.grandfather("A", "E") );
//    println("-------------------");
//    println("[MAIN] Result is: " + queryObj.grandfather("A", "QPDASDASDAS") );


    //previous solution
    //val a = Node(null, "1", ArrayList<Node>() );
    //builder( a );

    //println( "Result is " + father(masterNode, "A", "B", "father") );
    //println( "Result is " +  grandfather(masterNode, "A", "E") );

}
