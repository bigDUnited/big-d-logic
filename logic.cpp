// Example program
#include <iostream>
#include <string>
#include <stdio.h>
#include <string.h>
#include <vector>
using namespace std;

class Symbol
{
    Symbol operator !();
};

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
};

class Tree {
private:
    Node & _root;
public:
    Tree(Node & root):_root(root) {}

    Node & get_functor(char * functor) {
	for(auto & child: _root.get_children()) {
	    if (strcmp(child.get_functor(), functor) == 0) {
		return child;
	    }
	}
	vector<Node> children = vector<Node>();
	Node n = Node("", functor, children);
	_root.get_children().push_back(n);
	return n;
    }

    void add_fact(char * functor, Node & node) {
	Node & functor_node = get_functor(functor);
	
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
	// // functor, a, b
	// unsigned int expr_len = strlen(expr);
	// char functor[255];
	// char arg[255];
	// vector<Node>& children;
	// Node node;
	// for (int i = 0; i < expr_len; i++) {
	    
	// }
    }
    
};


void fact(char * expr, Tree* t) 
{
    // Fact f = parse_fact(expr);
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
    Tree t = Tree(root);
    F(father, a, b)
    // F(father, b, c)
    // R(grandfather(X,Y) :- father(X,Z) and father(Z,Y))
    // Q(father, a, b)
    return 0;
}
