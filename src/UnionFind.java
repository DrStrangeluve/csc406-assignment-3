import java.util.Arrays;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.abs;

class UnionFind {
    int[] partition;

    UnionFind(int n){
        int partion_size = n + 1;
        this.partition = new int[partion_size];
    }
}

class QuickFind extends UnionFind{

    public QuickFind(int n) {
        super(n);
        for (int i = 1; i <= n; i++) {
            makeSet(i);
        }
    }

    private void makeSet(int x) {
        partition[x] = x;
    }

    public int find(int x) {
        return partition[x];
    }

    public void union(int x, int y) {
        int x_subset = find(x);
        int y_subset = find(y);
        int change_to = min(x_subset, y_subset);
        int to_change = max(x_subset, y_subset);
        for (int i = 1; i < partition.length; i++) {
            if (partition[i] == to_change) {
                partition[i] = change_to;
            }
        }
    }
}

class QuickUnion extends UnionFind{

    public QuickUnion(int n) {
        super(n);
        for (int i = 1; i <= n; i++) {
            makeSet(i);
        }
    }

    private void makeSet(int x) {
        partition[x] = x;
    }

    public int find(int x) {
        while (x != partition[x]) {
            x = partition[x];
        }
        return partition[x];
    }

    public void union(int x, int y) {
        int p = find(x);
        int q = find(y);
        int change_to = min(p, q);
        int to_change = max(p, q);
        partition[to_change] = change_to;
    }
}

class WeightedQuickUnion extends UnionFind{

    public WeightedQuickUnion(int n) {
        super(n);
        makeSet();
    }

    private void makeSet() {
        Arrays.fill(partition, -1);
    }

    public int find(int x) {
        int value = partition[x];
        if (value > 0) {
            return find(value);
        }
        else {
            return x;
        }
    }

    //return as bool
    public void union(int i, int j) {
        int i_parent = find(i);
        int j_parent = find(j);
        if (i_parent == j_parent) {
            return; //parents are the same, thus they are already joined
        }
        int x = partition[i_parent];
        int y = partition[j_parent];
        int change_to = -(abs(x) + abs(y));
        if (abs(x) < abs(y)) {
            partition[j_parent] = change_to;
            partition[i_parent] = j_parent;
        } else {
            partition[i_parent] = change_to;
            partition[j_parent] = i_parent;
        }
    }
}

class WeightedQuickUnionPathCompression extends WeightedQuickUnion{

    public WeightedQuickUnionPathCompression(int n) {
        super(n);
    }

    public int find(int x) {
        int value = partition[x];
        if (value > 0) {
            partition[x] = find(value);
            return partition[x];
        }
        else {
            return x;
        }
    }
}