import java.io.*;
import java.util.*;

public class KruskalsMSTKnappSection1 {
    private int vertices;
    private int max_edges;
    private Writer writer;
    private PriorityQueue<Edge> edge_queue;
    private LinkedList<Edge> mst;

    KruskalsMSTKnappSection1(String file) {
        findMST(new File(file));
    }

    private void createOutputFile(String fileName) {
        try {
            writer = new PrintWriter(fileName);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void write(String s) {
        try {
            writer.write(s);
            writer.flush();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeHeap(File dataFile) {
        edge_queue = new PriorityQueue<>();
        try {
            Scanner in = new Scanner(dataFile);
            while (in.findInLine("c ") != null) {
                in.nextLine();
            }
            vertices = in.nextInt();
            max_edges = vertices - 1;
            while(in.hasNextLine()) {
                edge_queue.offer(new Edge(in.nextInt(), in.nextInt(), in.nextInt()));
            }
        }
        catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    }

    private void findMST(File dataFile) {
        createOutputFile("mst_output.txt");
        initializeHeap(dataFile);
        WeightedQuickUnionPathCompression uf = new WeightedQuickUnionPathCompression(vertices);
        mst = new LinkedList<>();
        int totalWeight = 0;
        while (mst.size() < max_edges) {
            Edge e = edge_queue.remove();
            int first_vertex = e.getVertex();
            int other_vertex = e.getOtherVertex(first_vertex);
            if ( uf.union(first_vertex, other_vertex)) {
                mst.add(e);
                totalWeight += e.getWeight();
                write(e.toString());
            }
        }
        write(String.format("%d", totalWeight));
    }

    public String toString() {
        return mst.toString();
    }
}