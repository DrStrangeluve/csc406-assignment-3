import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class KruskalsMSTKnappSection1 {
    private int vertices;
    private int max_edges;
    private LinkedList<Edge> mst;

    KruskalsMSTKnappSection1(String file) {
        System.out.println(findMST(new File(file)));
    }

    private PriorityQueue<Edge> initializeHeap(File dataFile) {
        PriorityQueue<Edge> local_queue = new PriorityQueue<>();
        try {
            Scanner in = new Scanner(dataFile);
            while (in.findInLine("c ") != null) {
                in.nextLine();
            }
            vertices = in.nextInt();
            max_edges = vertices - 1;
            while(in.hasNextLine()) {
                local_queue.offer(new Edge(in.nextInt(), in.nextInt(), in.nextInt()));
            }
        }
        catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        return local_queue;
    }

    private String findMST(File dataFile) {
        PriorityQueue<Edge> edge_queue = initializeHeap(dataFile);
        WeightedQuickUnionPathCompression uf = new WeightedQuickUnionPathCompression(vertices);
        mst = new LinkedList<>();
        while (mst.size() < max_edges){
            Edge e = edge_queue.remove();
            int first_vertex = e.getVertex();
            int other_vertex = e.getOtherVertex(first_vertex);
            if (uf.find(first_vertex) != uf.find(other_vertex)){
                mst.add(e);
                uf.union(first_vertex, other_vertex);
            }
        }
        return toString();
    }

    public String toString() {
        return mst.toString();
    }
}