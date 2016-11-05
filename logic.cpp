// Example program
#include <iostream>
#include <string>
#include <stdio.h>

class Symbol {
    Symbol operator !();
};

void fact(char * expr) {}
void query(char * expr) {}
void rule(char * expr) {}

#define print(x) std::cout << x << "!\n";

#define SHOW(a) std::cout << #a << std::endl;

#define F(e...) fact(#e);
#define Q(e...) query(#e);
#define R(e...) rule(#e);


int main()
{
    F(father, a, b)
    F(father, b, c)
    R(grandfather(X,Y) :- father(X,Z) and father(Z,Y))
    Q(father, a, b)
    return 0;
}
