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

    fun father( fatherName: String?, childName: String? ) : Boolean {
        return find(root, fatherName, childName, "father");
    }

    fun grandfather( fatherName: String?, childName: String? ) : Boolean {
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

    private fun find( currentRoot: Node, fatherName: String?, childName: String?, state : String ): Boolean {
        var parent : Node = Node(ArrayList<Node>(), "", ArrayList<Node>());
        var child : Node = Node(ArrayList<Node>(), "", ArrayList<Node>());

        var safeState = "father";
        if(state == "grandfather") { safeState = state; }

        if( fatherName == null && childName != null ) {
            for (godElem in currentRoot.getChildren()) {
                if( "grandfather" == safeState ){
                    for (childElem in godElem.getChildren()) {
                        if (childElem.getName() == childName) {
                            println("[FIND] For grandchild "+ childName +", found a grandfather: " + currentRoot);
                            return true;
                        }
                    }
                    var result = find(godElem, fatherName, childName, state);
                    if (result == true) {
                        return true;
                    }
                } else {
                    if (godElem.getName() == childName) {
                        println("[FIND] Found element: " + godElem);
                        return true;
                    }

                    var result = find(godElem, fatherName, childName, state);
                    if (result == true) {
                        return true;
                    }
                }
            }
            return false;

        } else if ( fatherName != null && childName == null ) {
            for (godElem in currentRoot.getChildren()) {
                if( "grandfather" == safeState ){
                    if (currentRoot.getName() == fatherName) {
                        println("[FIND] Found element: " + currentRoot);
                        for (rootChild in currentRoot.getChildren()) {
                            for ( rootGrandChild in rootChild.getChildren() ) {
                                println("[FIND] The grandchild is: " + rootGrandChild);
                                return true;
                            }
                        }
                    }

                    for (childElem in godElem.getChildren()) {
                        if (childElem.getName() == fatherName) {
                            println("[FIND] Found element: " + currentRoot);
                            for (rootChild in currentRoot.getChildren()) {
                                for ( rootGrandChild in rootChild.getChildren() ) {
                                    println("[FIND] The grandchild is: " + rootGrandChild);
                                    return true;
                                }
                            }
                        }
                    }
                    var result = find(godElem, fatherName, childName, state);
                    if (result == true) {
                        return true;
                    }
                } else {
                    if (currentRoot.getName() == fatherName) {
                        println(currentRoot);
                        return true;
                    }


                    if (godElem.getName() == fatherName) {
                        println("[FIND] Found element: " + godElem);
                        return true;
                    }

                    var result = find(godElem, fatherName, childName, state);
                    if (result == true) {
                        return true;
                    }
                }
            }
            return false;
        } else if ( fatherName == null && childName == null ) {
            var preventOverflow = ArrayList<Node>();
            preventOverflow.add( currentRoot );
            exposeStructure(currentRoot, preventOverflow );
            return false;
        }

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
                    child = grandfatherSearch( parentElem, childName!!);
                } else {
                    child = fatherSearch( parentElem, childName!!);
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

fun traceNodeElem( masterNode: Node, candidateFatherNode: Node ): Node? {
    for (masterChildElem in masterNode.getChildren()) {
        if ( masterChildElem.getName() == candidateFatherNode.getName() ) {
            //println("Before 1" + masterChildElem.getName());
            return masterChildElem;
        }

        var tracingResult = traceNodeElem(masterChildElem, candidateFatherNode);
        if ( tracingResult != null ) {
            //println("Before 2" + masterChildElem.getName());
            return tracingResult;
        }
    }
    return null
}

fun fact( masterNode: Node, fatherName: String, childName: String ) : Boolean {
    println("[FACT] : fatherName: " + fatherName + ", childName: " + childName);
    var candidateFatherNode = Node( ArrayList<Node>(), fatherName, ArrayList<Node>() );
    var candidateChildNode = Node( ArrayList<Node>(), childName, ArrayList<Node>() );

    var father = traceNodeElem(masterNode, candidateFatherNode);
    var child = traceNodeElem(masterNode, candidateChildNode);

    if(masterNode.getName() == childName) {
        return false;
    }

    if ( child == null ) {
        if ( father != null ) {
            //println("1");
            candidateChildNode.addParent(father);
            father.addChild(candidateChildNode);
        } else if (fatherName == masterNode.getName()) {
            //println("2");
            candidateChildNode.addParent(masterNode);
            masterNode.addChild(candidateChildNode);
        } else {
            //println("3");
            candidateChildNode.addParent(candidateFatherNode);
            candidateFatherNode.addChild( candidateChildNode );
            candidateFatherNode.addParent(masterNode);
            masterNode.addChild( candidateFatherNode );
        }
    } else {
        if ( father == null ) {
            if ( candidateFatherNode.getName() == masterNode.getName() ) {
                //println("5.1 " + candidateChildNode.getName());
                masterNode.addChild(child);
                child.addParent(masterNode);
            } else {
                //println("5.2 " + candidateChildNode.getName());
                candidateFatherNode.addParent(masterNode);
                child.addParent(candidateFatherNode);
                candidateFatherNode.addChild(child);
                masterNode.addChild(candidateFatherNode);
            }
        } else {
            //println("4 " + child.getName());
            father.addChild(child);
            child.addParent(father);
        }
    }
    return true;
}

fun rule( masterNode: Node, fatherName: String, childName: String ): Boolean {
    println("[RULE] : fatherName: " + fatherName + ", childName: " + childName);
    var candidateFatherNode = Node( ArrayList<Node>(), fatherName, ArrayList<Node>() );
    var candidateChildNode = Node( ArrayList<Node>(), childName, ArrayList<Node>() );

    var father = traceNodeElem(masterNode, candidateFatherNode);
    var child = traceNodeElem(masterNode, candidateChildNode);

    if(masterNode.getName() == childName) {
        return false;
    }

    if(masterNode.getName() == fatherName) {
        father = masterNode;

        if ( child != null ){
            father.addChild( child );
            child.addParent( father );
            return true;
        }
    }

    if ( father != null && child != null ) {
        father.addChild( child );
        child.addParent( father );
        return true;
    }

    return false
}

fun exposeStructure( masterNode : Node, preventOverflow: ArrayList<Node>)  {

    if( masterNode.getParents().size == 0 ) {
        println( "[EXPOSESTRUCTURE] *MASTER* : " + masterNode.toString());
    }

    for (child in masterNode.getChildren()){
        println( "[EXPOSESTRUCTURE] *CHILD*  : " + child.toString());

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
            println("[EXPOSESTRUCTURE] *UUPS!*  : Pointer to existing element '" + child.getName() + "' ... [die]" );
            //loop - die
        }
    }
}

fun main(args: Array<String>) {
    var masterNode = Node(ArrayList<Node>(), "A", ArrayList<Node>() );

    println("-------------------");
    println("[MAIN] Fact is " + fact(masterNode, "A", "B") );
    println("[MAIN] Fact is " + fact(masterNode, "B", "C") );
    println("[MAIN] Fact is " + fact(masterNode, "B", "D") );
    println("[MAIN] Fact is " + fact(masterNode, "D", "E") );
    println("[MAIN] Fact is " + fact(masterNode, "Q", "B") );
    println("[MAIN] Fact is " + fact(masterNode, "A", "F") );
    println("[MAIN] Fact is " + fact(masterNode, "G", "H") );
    println("[MAIN] Fact is " + fact(masterNode, "A", "D") );
    println("[MAIN] Fact is " + fact(masterNode, "F", "E") );
    println("[MAIN] Fact is " + fact(masterNode, "F", "H") );
    println("[MAIN] Fact is " + fact(masterNode, "Q", "A") );
    println("[MAIN] Fact is " + fact(masterNode, "H", "I") );
    println("[MAIN] Fact is " + fact(masterNode, "I", "J") );

    println("-------------------");
    println("[MAIN] Rule is " + rule(masterNode, "A", "C") );
    println("[MAIN] Rule is " + rule(masterNode, "B", "E") );
    println("[MAIN] Rule is " + rule(masterNode, "A", "E") );
    println("[MAIN] Rule is " + rule(masterNode, "Q", "A") );

    println("-------------------");
    var preventOverflow = ArrayList<Node>();
    preventOverflow.add( masterNode );
    exposeStructure(masterNode, preventOverflow );

    var queryObj = Query(masterNode);
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father("A", "C"));
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather("A", "E") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father("A", "H") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather("B", "E") );

    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father(null, "B") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father(null, "H") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father(null, "QP") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father(null, null) );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father("A", null) );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father("B", null) );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.father("QP", null) );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather(null, "B") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather(null, "C") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather(null, "J") );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather("A", null) );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather("B", null) );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather("F", null) );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather("QP", null) );
    println("-------------------");
    println("[MAIN] Result is: " + queryObj.grandfather(null, null) );
}
