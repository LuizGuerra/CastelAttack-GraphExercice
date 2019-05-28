import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class GraphCastelos {

    private static final String NEWLINE = System.getProperty("line.separator");

    private int V;
    private int E;
    private int exercitoDoMal;
    private Bag<Castelo>[] adj;

    public GraphCastelos(int vert, int edges, int exer) {
        setE(edges);
        setV(vert);
        setExercitoDoMal(exer);
        adj = (Bag<Castelo>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Castelo>();
        }
    }

    public GraphCastelos(){}

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(Castelo c) {
        if (c.getNum() < 0 || c.getNum() >= V)
            throw new IllegalArgumentException("vertex " + c.getNum() + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the undirected edge v-w to this graph.
     *
     * @param  a one vertex in the edge
     * @param  b the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(Castelo a, Castelo b) {
        validateVertex(a);
        validateVertex(b);
        E++;
        adj[a.getNum()].add(b);
        adj[b.getNum()].add(a);
    }


    /**
     * Returns the vertices adjacent to vertex {@code v}.
     *
     * @param  c the vertex
     * @return the vertices adjacent to vertex {@code v}, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Castelo> adj(Castelo c) {
        validateVertex(c);
        return adj[c.getNum()];
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  c the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(Castelo c) {
        validateVertex(c);
        return adj[c.getNum()].size();
    }


    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() { //Retorna o numero de edges * 2
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Castelo w : adj[v]) {
                s.append(w.getNum() + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Returns this graph as an input for GraphViz (dot format).
     *
     * @return dot graph representation
     */
    /*public String toDot() {
        // Uses a set of edges to prevent duplicates
        Set<String> edges = new HashSet<>();
        StringBuilder s = new StringBuilder();
        s.append("graph {"+NEWLINE);
        s.append("rankdir = LR;"+NEWLINE);
        s.append("node [shape = circle];"+NEWLINE);
        for (int v = 0; v < V; v++) {
            for (int w : adj[v]) {
                String edge = Math.min(v,w)+"-"+Math.max(v, w);
                if(!edges.contains(edge)) {
                    s.append(v + " -- " + w + ";"+NEWLINE);
                    edges.add(edge);
                }
            }
        }
        s.append("}");
        return s.toString();
    }*/


    public int getV() {
        return V;
    }

    public void setV(int v) {
        V = v;
    }

    public int getE() {
        return E;
    }

    public void setE(int e) {
        E = e;
    }

    public int getExercitoDoMal() {
        return exercitoDoMal;
    }

    public void setExercitoDoMal(int exercitoDoMal) {
        this.exercitoDoMal = exercitoDoMal;
    }


    /**
     * Unit tests the {@code Graph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("/Luiz/Faculdade/CastelAttack-GraphExercice/CastleAttack/CasosTeste/teste.txt"));
        String cur = "";

        int V = 0;
        int E = 0;
        int exercito;
        int countAux = 0;
        GraphCastelos gc = new GraphCastelos();
        ArrayList<Castelo> castelos = new ArrayList<>();
        castelos.add(0,new Castelo());

        while((cur = bf.readLine()) != null){
            String separado[] = cur.split(" ");
            if(separado.length == 3){
                exercito = Integer.parseInt(separado[0]);
                V = Integer.parseInt(separado[1]) + 1;
                E = Integer.parseInt(separado[2]);
                gc = new GraphCastelos(V,E,exercito);
                castelos.get(0).setGuarnicao(exercito);
                castelos.get(0).setMarcacao('B');
                castelos.get(0).setNum(0);
                countAux++;
            }
            else if (separado.length == 2){
                if(countAux >= V){ //Adicionou todos os castelos e está colocando as estradas entre eles.
                    int casteloA = Integer.parseInt(separado[0]);
                    int casteloB = Integer.parseInt(separado[1]);
                    gc.addEdge(castelos.get(casteloA),castelos.get(casteloB));
                }
                else{ //Adicionando os castelos do arquivo txt.
                    Castelo c = new Castelo(Integer.parseInt(separado[0]),Integer.parseInt(separado[1]),'B');
                    castelos.add(c.getNum(),c);
                    countAux++;
                }
            }
        }

        System.out.println(gc.toString());

        System.out.println("--------//--------");

    }


}
