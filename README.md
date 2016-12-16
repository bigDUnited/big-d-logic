# big-d-logic
You guessed it! Its the logic of the Big D! It has no logic!

## Prolog - only used to see how Prolog behaves
To run the Prolog version do the following:
(In case Prolog is not installed on your system):
```sh
$ sudo apt install swi-prolog
```
If Prolog is installed:
```sh
$ prolog -s path/to/prologLogic.pl
```
This will load the Prolog file with facts and rules and then in the Prolog program:
```sh
?- grandfather(a,c).
```
This should return as true. If it does the program works. Try changing the arguments to see that no other condition will return true.


## Kotlin 
Participants: Boyko & Madalina
To install and work Kotlin in the easiest way possible we will need the IntelliJ IDEA IDE

### Solution includes :
Node object (representing the actual element in the tree structure)
  containing : List of Parent Nodes, Name and List of Children Nodes
Query object (representing the traversing and searching through Nodes)
  containing 2 public function - father and grandfather
Fact function for inserting facts
Rule function for inserting rules
Function for exposing the current structure
Several helper functions and the main to start with


The solution is tested and it works.

## C\# 

### Running C\# version
Just run the Program.cs main class.

Participants: Marek & Teodor
Uses lists and dynamic functor names and argument lists, so it can take as many arguments as you want. And it has the capability to evaluate single queries - either supply the missing arguments or return if they are true or false.

## Bash

### Running evil bash version
Install tree:
```sh
sudo apt-get install tree
```

run:
```sh
./logic.sh
```

Participants: Martin
Uses folder creation to generate the tree structures used for evaluation. This is the one that won the bottle of whiskey.

## C

### Building

To build project run:
```sh
make
```
Participants: Martin, Boyko, Marek, Madalina, Teodor
Solution was abandoned.
