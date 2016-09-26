# Prim-s-Minimum-spanning-tree-algorithm

Given an undirected graph and a cost for each edge of the graph we compute the minimum cost tree that spans all vertices by using a greedy approach.

Runs in O(m*n) time - though better implementations could get a better running time (of m*logn) by using heaps (priority queues) to minimize the number of searches for the minimum edge

# Input

The input consists of a file that has on its first line  the number of nodes followed by the number of edges. The next lines are of the form: [vertex1] [vertex2] [cost of edge between vertex1 and vertex2]. All values are separated by one space.

# Output

The output should consist of a single number (positiove or negative) representing the sum of edge costs of the minimum cost tree spanning all vertices (so this tree has no cycles and is connected).

# Implementation Notes

The point of this algorithm is to grow the MST by one edge at a time and the set X by one node at each iteration. Being a Greedy Algorithm, it will choose the cheapest edge from the unvisited set of verices (V \ X).

I did NOT assume that edge costs are positive, nor that they are distinct.

Coming: the implementation of a heap-based version that will efficiently reduce the running time to O(m logn) by maintaining relevant edges in a heap (with keys = edge costs).
