package sample;


import java.util.ArrayList;

public class Objeto {
    int [][] estado;
    Objeto padre;
    ArrayList<Objeto> hijos= new ArrayList<>();

    public Objeto(int[][] estado) {
        this.estado = estado;
        this.hijos=null;
        this.padre=null;
    }

    public int[][] getMatriz() {
        return estado;
    }

    public void setEstado(int[][] estado) {
        this.estado = estado;
    }

    public Objeto getPadre() {
        return padre;
    }

    public void setPadre(Objeto padre) {
        this.padre = padre;
    }

    public ArrayList<Objeto> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Objeto> hijos) {
        this.hijos = hijos;
        if(hijos!=null){
            for(Objeto hijo:hijos){
                hijo.padre=this;
            }
        }

    }
}
