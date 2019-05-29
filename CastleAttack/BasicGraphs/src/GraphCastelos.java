import java.io.*;
import java.util.*;
import java.util.Stack;

public class GraphCastelos {

    private static final String NEWLINE = System.getProperty("line.separator");

    private int V;
    private int E;
    private int exercitoDoMal;
    private int exercitoAtualizado;
    private Bag<Castelo>[] adj;
    private ArrayList<Integer>[] listaAdj;
    private ArrayList<Castelo> castelos;
    private int qtdDominados;
    private int ct;

    public ArrayList<Integer>[] getListaAdj() {
        return listaAdj;
    }

    public void setListaAdj(ArrayList<Integer>[] listaAdj) {
        this.listaAdj = listaAdj;
    }

    public int getQtdDominados() {
        return qtdDominados;
    }

    public void setQtdDominados(int qtdDominados) {
        this.qtdDominados = qtdDominados;
    }

    public int getCt() {
        return ct;
    }

    public GraphCastelos(int vert, int edges, int exer) {
        setE(edges);
        setV(vert);
        setExercitoDoMal(exer);
        adj = (Bag<Castelo>[]) new Bag[V];
        listaAdj = (ArrayList<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Castelo>();
            listaAdj[v] = new ArrayList<>();
        }
        this.qtdDominados = 0;
    }

    public GraphCastelos(){
        this.qtdDominados = 0;
    }

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
        listaAdj[a.getNum()].add(b.getNum());
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

    public int getExercitoAtualizado() { return exercitoAtualizado; }

    public void setExercitoAtualizado(int exercitoAtualizado) { this.exercitoAtualizado = exercitoAtualizado; }

    public ArrayList<Castelo> getCastelos() { return castelos; }

    public void setCastelos(ArrayList<Castelo> castelos) { this.castelos = castelos; }

    public void dominaCastelos(){
        Stack<Integer> elem = new Stack<>();
        elem.push(0);
        dominaCastelos(this,elem,0);
    }

    private void dominaCastelos(GraphCastelos gc, Stack<Integer> elem, int castelo){
        if(castelo == 0){ //Primeiro castelo
            int auxC = 0;
            int sizeAdj = gc.getListaAdj()[0].size();
            int numCastelo = Integer.MIN_VALUE;
            Castelo c = new Castelo();
            for(int i = 0; i<sizeAdj; i++){
                if(!(c = gc.castelos.get(gc.getListaAdj()[0].get(i))).getMarcacao()){
                    numCastelo = c.getNum();
                    break;
                }
            }
            if(numCastelo == Integer.MIN_VALUE){
                return;
            }else{
                dominaCastelos(gc,elem,numCastelo);
            }
        }
        else{ //Outros castelos
            if(gc.castelos.get(castelo).getGuarnicao()*2 + 50 < gc.getExercitoAtualizado()){ //Conquista
                gc.setQtdDominados(gc.getQtdDominados() + 1);
                gc.setExercitoAtualizado((gc.getExercitoAtualizado() - 50) - (gc.castelos.get(castelo).getGuarnicao()*2));
                gc.castelos.get(castelo).setMarcacao(true);
                elem.push(castelo);
                dominaCastelos(gc,elem,gc.adj[castelo].iterator().next().getNum());
            }
            else{ //Não conquista
                elem.pop();
                System.out.println("Peeking "+elem.peek());
                System.out.println(gc.castelos.get(elem.peek()).getMarcacao());
                dominaCastelos(gc,elem,elem.peek());
            }
        }
    }

    private void dfsPrivate(GraphCastelos gc, int v) {
        gc.ct++;
        gc.castelos.get(v).setMarcacao(true);
        for (Integer c : gc.listaAdj[v]) {
            Castelo cas = gc.castelos.get(c);
            if (!cas.getMarcacao()) {
                dfsPrivate(gc, cas.getNum());
            }
        }
    }

    public void dfs(){
        dfsPrivate(this,0);
        System.out.println(this.getCt());
    }


    /**
     * Unit tests the {@code Graph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/CastleAttack/CasosTeste/caso30.txt"));
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
                castelos.get(0).setGuarnicao(50);
                castelos.get(0).setMarcacao(false);
                castelos.get(0).setNum(0);
                gc.setExercitoDoMal(exercito);
                gc.setExercitoAtualizado(exercito-50);
                countAux++;
            }
            else if (separado.length == 2){
                if(countAux >= V){ //Adicionou todos os castelos e está colocando as estradas entre eles.
                    int casteloA = Integer.parseInt(separado[0]);
                    int casteloB = Integer.parseInt(separado[1]);
                    gc.addEdge(castelos.get(casteloA),castelos.get(casteloB));
                }
                else{ //Adicionando os castelos do arquivo txt.
                    Castelo c = new Castelo(Integer.parseInt(separado[0]),Integer.parseInt(separado[1]));
                    castelos.add(c.getNum(),c);
                    countAux++;
                }
            }
        }

        gc.setCastelos(castelos);

        System.out.println(gc.toString());

        System.out.println("--------//--------\n");

        Castelo inicial = castelos.get(0);
        Castelo aux = gc.adj[0].iterator().next(); //Primeiro da lista. Aux será utilizado na iteração

        gc.dominaCastelos();

    }


}
