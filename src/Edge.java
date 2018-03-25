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

    public int getVertex(){
        return u;
    }

    public int getOtherVertex(int vertex){
        if (vertex == u){
            return v;
        }
        else{
            return u;
        }
    }

    public int getWeight(){
        return weight;
    }

    public int compareTo(Edge e)
    {
        return this.weight-e.weight;
    }

    public String toString(){
        return String.format("(%d, %d, %d)", u, v, weight);
    }
}