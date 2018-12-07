package sample;


import java.util.ArrayList;

public class Objeto {
    int [][] estado;// este nos permitira tener el estado en el que se encuentra el tablero del juego en un momento determinado
    Objeto padre;//Para mantener un control de que nodo  salen los siguiente movimientos
    ArrayList<Objeto> hijos= new ArrayList<>();//guarda los siguientes movimientos posibles

    public Objeto(int[][] estado) {
        //generamos un nodo principal
        this.estado = estado;
        this.hijos=null;
        this.padre=null;
    }

    public int[][] getEstado() {
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
                hijo.padre=this;// con esta linea asignamos a cada hijo su padre que es proporcionado por el llamado
            }
        }

    }
}
