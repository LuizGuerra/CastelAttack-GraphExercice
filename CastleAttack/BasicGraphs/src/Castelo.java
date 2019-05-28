import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Castelo {

    private int num;
    private int guarnicao;
    private char marcacao;

    public Castelo(int num, int guarnicao, char marcacao) {
        this.guarnicao = guarnicao;
        this.marcacao = marcacao;
        this.num = num;
    }

    public Castelo(){
        this.guarnicao = 0;
        this.marcacao = 0;
        this.num = 0;
    }

    public int getGuarnicao() {
        return guarnicao;
    }

    public void setGuarnicao(int guarnicao) {
        this.guarnicao = guarnicao;
    }

    public char getMarcacao() {
        return marcacao;
    }

    public void setMarcacao(char marcacao) {
        this.marcacao = marcacao;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    class CasteloComparator implements Comparator<Castelo>{

            public int compare(Castelo c1, Castelo c2) {
            if (c1.getGuarnicao() > c2.getGuarnicao())
                return 1;
            else if (c1.getGuarnicao() < c2.getGuarnicao())
                return -1;
            return 0;
        }
    }
}
