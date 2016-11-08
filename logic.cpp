// Example program
#include <iostream>
#include <string>
#include <stdio.h>
#include <string.h>
#include <vector>
#include <sstream>
using namespace std;

class Symbol
{
    Symbol operator !();
};

class NoNodeException{};

class Node 
{
private:
    char * _value;
    char * _functor;
    vector<Node> _children;
public:
    Node(char * value, char * functor, vector<Node>& children)
	:_value(value),
	 _functor(functor),
	 _children(children)
    {}

    vector<Node>& get_children() {
	return _children;
    }

    char * get_value() const {
	return _value;
    }

    char * get_functor() const {
	return _functor;
    }

    Node* find_node(char* value) {
	for(auto & child: _children) {
	    if (strcmp(child.get_value(), value) == 0) {
		return &child;
	    }
            child.find_node(value);
	}
	return NULL;
    }
};

class Tree {
private:
    Node & _root;
public:
    Tree(Node & root):_root(root) {}

    vector<Node>& get_functors() {
       return _root.get_children();
    }

    Node & get_functor(char * functor) {
	for(auto & child: _root.get_children()) {
	    if (strcmp(child.get_functor(), functor) == 0) {
		return child;
	    }
	}
	vector<Node> children = vector<Node>();
	Node n = Node("", functor, children);
	_root.get_children().push_back(n);
	return _root.get_children().back();
    }

    Node* find_node(Node& functor_node, char* value) {
	Node* n = functor_node.find_node(value);
	if (n == NULL) {
	    vector<Node> children = vector<Node>();
	    Node new_node = Node(value, "", children);
	    functor_node.get_children().push_back(new_node);
	    return &functor_node.get_children().back();
	} else {
	    return n;
	}
    }

    void add_fact(char * functor, Node & node) {
	Node & functor_node = get_functor(functor);
	// find if in tree
	Node * target = find_node(functor_node, node.get_value());
	if (strcmp(target->get_value(), "") == 0) {
	    functor_node.get_children().push_back(node);
	} else {
	    char * val = node.get_children()[0].get_value();
	    Node* existing_child = target->find_node(val);
	    if (strcmp(existing_child->get_value(), "") == 0) {
		target->get_children().push_back(node.get_children()[0]);
	    }
	}
    }
};

class Parser {
private:
    Tree & _t;
public:
    Parser(Tree & t)
	:_t(t)
    {}

    void parse_fact(char * expr) 
    {
	//functor, a, b
	unsigned int expr_len = strlen(expr);
	unsigned char stage = 0;
	unsigned char v = 0;
	char functor[255];
	char arg0[255];
	char arg1[255];
	vector<Node> children = vector<Node>();
	Node* func_node;
	Node* node;
	for (int i = 0; i < expr_len; i++) {
	    if (expr[i] == ',') {
		if (stage == 0) {
		    functor[v] = '\0';
		    func_node = &_t.get_functor(functor);
		} else if (stage == 1) {
		    arg0[v] = '\0';
		    node = _t.find_node(*func_node, arg0);
		}
		stage++;
		v = 0;
		continue;
	    }
	    if (stage == 0){
		functor[v] = expr[i];
	    } else if (stage == 1){
                arg0[v] = expr[i];
	    } else if (stage == 2){
		arg1[v] = expr[i];
	    }
	    v++;
	}
	arg1[v] = '\0';
	Node child_node = Node(arg1, "", children);
	node->get_children().push_back(child_node);
    }
    
};


void fact(char * expr, Tree* t) 
{
    Parser p = Parser(*t);
    p.parse_fact(expr);
}

void query(char * expr, Tree* t) {}
void rule(char * expr, Tree* t) {}

#define print(x) cout << x << "!\n";
#define SHOW(a) cout << #a << endl;

#define F(e...) fact(#e, &t);
#define Q(e...) query(#e, &t);
#define R(e...) rule(#e, &t);


int main()
{
    vector<Node> hello = vector<Node>();
    Node root = Node("", "", hello);
    Node* node;
    Node* functor;
    Tree t = Tree(root);
    // t.get_functor("faggot");
    // node = t.find_node(
    F(father,a,b);
    // F(father, b, c)
    // R(grandfather(X,Y) :- father(X,Z) and father(Z,Y))
    // Q(father, a, b)
    return 0;
}
