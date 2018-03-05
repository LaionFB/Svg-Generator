# Svg generator

This app reads a .txt file that represents an automata as described in the book "The Algorithmic Beauty of Plants" by Przemysław Prusinkiewicz, Aristid Lindenmayer and generates a .svg file based on it.

The input file must follow the following pattern:

```
n=<number-of-derivations>, d=<angle-increment>
<initiator>
<generator>
<generator>
<generator>
...
```
```
for example

n=3, d=90
F-F-F-F
F=F-FF--F-F
```
There are some .txt files for testing, but you can make your own and replace the path to the file in the code.