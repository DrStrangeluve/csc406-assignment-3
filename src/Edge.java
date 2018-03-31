class Edge implements Comparable<Edge>
{
    private int u;
    private int v;
    private int weight;

    Edge(int u, int v, int weight){
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    int getVertex(){
        return u;
    }

    int getOtherVertex(int vertex){
        if (vertex == u){
            return v;
        }
        else{
            return u;
        }
    }

    int getWeight(){
        return weight;
    }

    public int compareTo(Edge e)
    {
        return this.weight-e.weight;
    }

    public String toString(){
        return String.format("%d %d %d%n", u, v, weight);
    }
}