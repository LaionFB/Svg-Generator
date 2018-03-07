# Svg generator

This app reads a .txt file that represents an automata as described in the book "The Algorithmic Beauty of Plants" by Przemysław Prusinkiewicz, Aristid Lindenmayer and generates a .svg file based on it.

The input file must follow the following pattern:

```
n=<number-of-derivations>, d=<angle-increment>, a=<initial-angle>
<initiator>
<generator>
<generator>
<generator>
...
```
_* initial angle is optional._

for example
```
n=2, d=60, a=60
F--F--F
F=F+F--F+F
```
There are some .txt files for testing, but you can make your own and replace the path to the file in the code.