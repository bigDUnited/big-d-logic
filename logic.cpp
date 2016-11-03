// Example program
#include <iostream>
#include <string>
#include <stdio.h>

#define print(x) std::cout << x << "!\n";


#define fact(name,args...) {\
    printf(name, args);\
    char * arr[] = {args};\
    printf(arr[0]);\
    }\


//#define rule(x,y)

int main()
{
  print("hey madafaka");
  
  fact("father %s %s \n", "A", "B");
}
