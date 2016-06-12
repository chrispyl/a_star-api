#### **An api for the A\* algorithm**

#### **How to use**

In order to use the api, an A_star object has to be created
```java
A_star astar = new A_star(map, xSize, ySize, start, destination, cantPass, maxScale);
```
then findPath() is used to execute the algorithm 
```java
astar.findPath();
```
and getPath() to take the result
```java
path = astar.getPath();
```
#### **Demo**

In the demo provided, in the Ui class, someone can change the given grid with different weights and see how the algorithm reacts. The weights are
```
road: 10
grass: 20
dirt: 30
water: 80
lava: 90
```
The lower the weight the easier it is to go through it. The input to cantPass parameter in the constructor is set to 90 so lava is not acessible.
