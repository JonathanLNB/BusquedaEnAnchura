package sample;


import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import javax.swing.*;
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
        Integer contadorM=0;//solo nos ayudara a saber en cuantas iteraciones logro resolverlo
        ArrayList<Objeto> movimientos=new ArrayList<>();
        vistos=new ArrayList<>();
        ArrayList<Objeto> hijos;
        Objeto hijo;
        Objeto revisar;
        int arriba,abajo,izquierda,derecha;
        int[][] movPrueba,movUsado;
        int[] posicionBlanco;
        movimientos.add(inicial);//añadimos el movimiento inicial pues sera el primero en ser revisado
        while (movimientos.size()!=0){//recorrera mientras nos queden movimientos por revisar
            hijos=new ArrayList<>();
            contadorM++;
            revisar=movimientos.remove(0);//obtiene el primero de los movimientos que esten disponibles a revisar
            movPrueba=revisar.getEstado();
            posicionBlanco=obtenerPosicion(movPrueba);//aprendemos en que lugar esta 1
            vistos.add(revisar);

            //movimiento arriba
            if(posicionBlanco[0]!=0){
                hijo=new Objeto(copiarMatriz(movPrueba));
                arriba=hijo.getEstado()[posicionBlanco[0]-1][posicionBlanco[1]];//obtenemos el dato que esta arriba del 0
                if(arriba==-1){
                    return revisar;
                }
                if(arriba!=3){
                    //si lo que esta no es una pared entonces:
                    hijo.getEstado()[posicionBlanco[0]][posicionBlanco[1]]=0;//colocamos el dato en la posicion del 0
                    hijo.getEstado()[posicionBlanco[0]-1][posicionBlanco[1]]=1;//colocamos el 0 en la posicion donde estaba el dato
                    movUsado=hijo.getEstado();
                    if(!visitados(movUsado))//verificamos que ese movimiento no sea un movimiento que ya hayamos usado
                        movimientos.add(hijo);
                    hijos.add(hijo);
                }
            }
            //movimiento abajo
            if(posicionBlanco[0]!=longitud-1){
                hijo=new Objeto(copiarMatriz(movPrueba));
                abajo=hijo.getEstado()[posicionBlanco[0]+1][posicionBlanco[1]];//obtienes el  el dato que existe debajo del 0
                if(abajo==-1){
                    return revisar;
                }
                if(abajo!=3){
                    hijo.getEstado()[posicionBlanco[0]][posicionBlanco[1]]=0;//colocas el dato en la posicion del 0
                    hijo.getEstado()[posicionBlanco[0]+1][posicionBlanco[1]]=1;//colocas el 0 en la posicion del dato
                    movUsado=hijo.getEstado();//obtenemos la matriz del hio
                    if(!visitados(movUsado))
                        movimientos.add(hijo);//si no la hemos usado entonces si lo agregamos a los movimientos posibles
                    hijos.add(hijo);
                }
            }
            //movimiento izquierda
            if(posicionBlanco[1]!=0){
                hijo=new Objeto(copiarMatriz(movPrueba));
                izquierda=hijo.getEstado()[posicionBlanco[0]][posicionBlanco[1]-1];//obtienes el dato que existe a la izquierda del 0
                if(izquierda==-1){
                    return revisar;
                }
                if(izquierda!=3){
                    hijo.getEstado()[posicionBlanco[0]][posicionBlanco[1]]=0;//colocas el dato en la posicion del 0
                    hijo.getEstado()[posicionBlanco[0]][posicionBlanco[1]-1]=1;//colocar el 0 en la posicion del dato anterior
                    movUsado=hijo.getEstado();
                    if(!visitados(movUsado))
                        movimientos.add(hijo);//si no ha sido usado entonces lo agregamos a movimientos posibles
                    hijos.add(hijo);
                }
            }
            //movimiento derecha
            if(posicionBlanco[1]!=longitud-1){
                hijo=new Objeto(copiarMatriz(movPrueba));
                derecha=hijo.getEstado()[posicionBlanco[0]][posicionBlanco[1]+1];//obtienes el dato a la derecha del 0
                if(derecha==-1){
                    return revisar;
                }
                if(derecha!=3){
                    hijo.getEstado()[posicionBlanco[0]][posicionBlanco[1]]=0;//colocamos el dato en la posicion donde se encuentra el 0
                    hijo.getEstado()[posicionBlanco[0]][posicionBlanco[1]+1]=1;//colocamos 0 en la posicon dondeestaba el dato
                    movUsado=hijo.getEstado();
                    if(!visitados(movUsado))
                        movimientos.add(hijo);//si no lo hemos usado entonces lo agregamos a movimientos posibles
                    hijos.add(hijo);
                }
            }
            //quiza un if en caso que hijos venga vacio
            revisar.setHijos(hijos);
        }
        return null;
    }
    public boolean solucion(int[][] movimiento,int[][] solucion){
        if(Arrays.deepEquals(movimiento,solucion))//indica si  las matrices son iguales
            return true;
        return false;
    }

    public int[] obtenerPosicion(int[][] estado){
        int[]posicion=new int[2];// como es una matriz necesitamos guardar i,j por eso el arreglo es de 2 espacios
        int i,j;
        for(i=0;i<estado.length;i++){
            for(j=0;j<estado.length;j++){
                if(estado[i][j]==1){
                    posicion[0]=i;
                    posicion[1]=j;
                }
            }
        }
        return posicion;//lugar donde esta el 0
    }

    public int [][] copiarMatriz(int[][] matriz){
        /*Es necesario generar*/
        int [][] copia=new int[matriz.length][matriz.length];
        int i,j;
        for(i=0;i<copia.length;i++){
            for(j=0;j<copia.length;j++){
                copia[i][j]=matriz[i][j];
            }
        }

        return copia;
    }

    public boolean visitados(int[][]movimiento){
        for(Objeto v:vistos){//buscamos el movimiento ya existe en nuestros movimientos vistos
            if(Arrays.deepEquals(v.getEstado(),movimiento))
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