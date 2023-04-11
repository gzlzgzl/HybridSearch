This folder contains the source code of the NSW (SW) algorithm that supports hybrid search with pre-query filtering, post-query filtering, and concurrent filtering implementations.

The filtering criteria is specified by the passesFilter() method of Point.java. In our experiments on GloVe-50d, this criteria is fixed to be "length is even".  In practice, it can be anything related to the data point.

To determine the filtering mode, it suffices to change a few lines:

For pre-query filtering, uncomment line 40 and 42 in Main.java.
For post-query filtering, uncomment line 63-68 in Main.java. In addition, modify k in line 12 to k/p where p is the probability of passesFiltering() being true for a randomly selected point. In line 71, modify pq to pq2.
For concurrent filtering, uncomment line 117, 119, 139, and 141 in NSWGraph.java.

Only one filtering mode should be adopted, so one component from the above should be uncommented and the other two should be commented.
