// Example program
#include <iostream>
#include <string>

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
  
  fact("father %s %s", "A", "B");
}