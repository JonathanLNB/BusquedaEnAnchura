package sample;


import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;

public class Simulador {
    Canvas cCanvas;
    ArrayList<Objeto> vistos;
    int longitud;

    public Simulador(Canvas cCanvas, int longitud) {
        this.cCanvas = cCanvas;
        this.longitud = longitud;
    }
    public Objeto resolver(Objeto inicial){
        ArrayList<Objeto> movimientos=new ArrayList<>();
        vistos=new ArrayList<>();
        ArrayList<Objeto> siguientes;
        Objeto siguiente;
        Objeto aux;
        int arriba;
        int abajo;
        int izquierda;
        int derecha;
        int[][] auxIntentos, visitado;
        int[] marioElForaneo;
        movimientos.add(inicial);
        while (movimientos.size()!=0){
            siguientes=new ArrayList<>();
            aux=movimientos.remove(0);
            auxIntentos=aux.getMatriz();
            marioElForaneo=obtenerPosicion(auxIntentos);
            vistos.add(aux);
            
            //Arriba :3
            if(marioElForaneo[0]!=0){
                siguiente=new Objeto(generar(auxIntentos));
                arriba=siguiente.getMatriz()[marioElForaneo[0]-1][marioElForaneo[1]];
                if(arriba==-1){
                    return aux;
                }
                if(arriba!=3){
                    siguiente.getMatriz()[marioElForaneo[0]][marioElForaneo[1]]=0;
                    siguiente.getMatriz()[marioElForaneo[0]-1][marioElForaneo[1]]=1;
                    visitado=siguiente.getMatriz();
                    if(!usados(visitado))
                        movimientos.add(siguiente);
                    siguientes.add(siguiente);
                }
            }
            
            //Abajo :3
            if(marioElForaneo[0]!=longitud-1){
                siguiente=new Objeto(generar(auxIntentos));
                abajo=siguiente.getMatriz()[marioElForaneo[0]+1][marioElForaneo[1]];
                if(abajo==-1){
                    return aux;
                }
                if(abajo!=3){
                    siguiente.getMatriz()[marioElForaneo[0]][marioElForaneo[1]]=0;
                    siguiente.getMatriz()[marioElForaneo[0]+1][marioElForaneo[1]]=1;
                    visitado=siguiente.getMatriz();
                    if(!usados(visitado))
                        movimientos.add(siguiente);
                    siguientes.add(siguiente);
                }
            }
            //Izquierda :3
            if(marioElForaneo[1]!=0){
                siguiente=new Objeto(generar(auxIntentos));
                izquierda=siguiente.getMatriz()[marioElForaneo[0]][marioElForaneo[1]-1];
                if(izquierda==-1){
                    return aux;
                }
                if(izquierda!=3){
                    siguiente.getMatriz()[marioElForaneo[0]][marioElForaneo[1]]=0;
                    siguiente.getMatriz()[marioElForaneo[0]][marioElForaneo[1]-1]=1;
                    visitado=siguiente.getMatriz();
                    if(!usados(visitado))
                        movimientos.add(siguiente);
                    siguientes.add(siguiente);
                }
            }
            
            //Derecha
            if(marioElForaneo[1]!=longitud-1){
                siguiente=new Objeto(generar(auxIntentos));
                derecha=siguiente.getMatriz()[marioElForaneo[0]][marioElForaneo[1]+1];
                if(derecha==-1){
                    return aux;
                }
                if(derecha!=3){
                    siguiente.getMatriz()[marioElForaneo[0]][marioElForaneo[1]]=0;
                    siguiente.getMatriz()[marioElForaneo[0]][marioElForaneo[1]+1]=1;
                    visitado=siguiente.getMatriz();
                    if(!usados(visitado))
                        movimientos.add(siguiente);
                    siguientes.add(siguiente);
                }
            }
            aux.setHijos(siguientes);
        }
        return null;
    }
    public int[] obtenerPosicion(int[][] estado){
        int[]posicion=new int[2];
        int i,j;
        for(i=0;i<estado.length;i++){
            for(j=0;j<estado.length;j++){
                if(estado[i][j]==1){
                    posicion[0]=i;
                    posicion[1]=j;
                }
            }
        }
        return posicion;
    }

    public int [][] generar(int[][] matriz){
        int [][] copia=new int[matriz.length][matriz.length];
        int i,j;
        for(i=0;i<copia.length;i++){
            for(j=0;j<copia.length;j++){
                copia[i][j]=matriz[i][j];
            }
        }

        return copia;
    }

    public boolean usados(int[][]movimiento){
        for(Objeto v:vistos){//buscamos el movimiento ya existe en nuestros movimientos vistos
            if(Arrays.deepEquals(v.getMatriz(),movimiento))
                return true;
        }
        return false;
    }

    public void imprimeMovimiento(int[][]movimiento){
        ArrayList<String> mov = new ArrayList<>();
        String aux = "";
        int i,j;
        for(i=0;i<movimiento.length;i++){
            for(j=0;j<movimiento.length;j++){
                aux+=movimiento[i][j]+" ";
            }
            mov.add(aux);
            aux = "";
        }
        dibujarCanvas(mov);
    }

    private void dibujarCanvas(ArrayList<String> mov) {
        String [] datos;
        Image suelo = new Image("Imagenes/sSuelo.png");
        Image pasto = new Image("Imagenes/sArbusto.png");
        Image personaje = new Image("Imagenes/abajo.gif");
        Image tumba = new Image("Imagenes/tumba.png");
        for(int i = mov.size()-1; i >= 0; i--){
            datos = mov.get(i).split(" ");
            for(int e = 0; e < datos.length; e++){
                cCanvas.getGraphicsContext2D().drawImage(suelo, i*32, e*32);
                if(datos[e].equals("3"))
                    cCanvas.getGraphicsContext2D().drawImage(pasto, i*32, e*32);
                if(datos[e].equals("1"))
                    cCanvas.getGraphicsContext2D().drawImage(personaje, i*32, e*32);
                if(datos[e].equals("-1"))
                    cCanvas.getGraphicsContext2D().drawImage(tumba, i*32, e*32);

            }
        }
    }
}