/**
 Copyright [2021] [Javier Linares Castrillón]
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package domain;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
/**
 * @author Javier Linares Castrillón
 */

/**
 * Esta clase es la que define a un tablero y aplica el algoritmo del juego de la vida.
 */
public class Tablero {
    private static int DIMENSION = 30;
    private int[][] estadoActual = new int[DIMENSION][DIMENSION], estadoSiguiente = new int[DIMENSION][DIMENSION];
    private Random ran = new Random(System.nanoTime());

    /**
     * Este método lee un fichero e introduce los datos de este en una matriz.
     * @param name nombre del fichero.
     */

    //Este metodo nos lee una matriz de un fichero.
    public void leerEstadoActual(String name){
        try {
            File f  = new File(name);
            f.createNewFile();
            Scanner sc = new Scanner(f);
            for(int i = 0; i < DIMENSION; i++)
                for(int j = 0; j < DIMENSION; j++)
                    estadoActual[i][j] = sc.nextInt();

        }catch(IOException ex)	{
            System.err.println(ex);
        }
    }


    /**
     *  Genera un estado inicial aleatorio. Para cada celda
     * genera un número aleatorio en el intervalo [0, 1). Si * el número es menor que 0,5, entonces la celda está
     * inicialmente viva. En caso contrario, está muerta.
    */
    public void generarEstadoActualPorMontecarlo(){
        for(int i = 0; i < DIMENSION; i++)
            for(int j = 0; j < DIMENSION; j++)
                estadoActual[i][j] = ran.nextInt(2);
    }


    /**
     * Este método transita de un estado a otro utilizando las reglas del juego de la vida. Al terminar, pasa los valores
     * de estadoSiguiente a estadoActual.
     */
    public void transitarAlEstadoSiguiente(){

        //Comienzo pasando todos los valores de estadoSiguiente a estadoActual.
        for(int a = 0; a < DIMENSION; a++)
            for(int b = 0; b < DIMENSION; b++)
                estadoSiguiente[a][b] = estadoActual[a][b];

        //Si 1 tiene 2 o más 1 cerca, se mantiende vivo ---------------> Sobrevivir
        //Si 0 tiene 3 o más 1 cerca, se vuelve 1 ---------------------> Nacer

        //Recorro toda la matriz y voy aplicando los predicados Sobrevivir y Nacer.
        for(int i = 0; i < DIMENSION; i++){
            for(int j = 0; j < DIMENSION; j++) {
                if (estadoActual[i][j] == 1)
                    if (!sobrevivir(estadoActual, i, j)) estadoSiguiente[i][j] = 0;
                if (estadoActual[i][j] == 0)
                    if (nacer(estadoActual, i, j)) estadoSiguiente[i][j] = 1;
            }
        }


        for(int a = 0; a < DIMENSION; a++)
            for(int b = 0; b < DIMENSION; b++)
                estadoActual[a][b] = estadoSiguiente[a][b];
    }

    /**
     * Este método comprueba si un elemento (i,j) cumple los requisitos para sobrevivir o no.
     * * @param i posicion abcisas
     * @param j posicion ordenadas
     * @return true si el elemento sobrevive : false.
     */
    private boolean sobrevivir(int[][] estado, int i, int j){
        int contador = 0;

            for(int x = i - 1; x <= (i+1); x++)
                for(int y = j + 1; y >= (j-1); y--)
                    try{
                        if(estado[x][y] == 1) contador++;
                     }catch (ArrayIndexOutOfBoundsException ai){ }

            return (contador == 3 || contador == 4) ?  true : false; //"contador" tiene un numero valor más (3|4 en vez de 2|3) porque
                                                                     //estoy contando también el 1 que estoy mirando. Siempre habrá uno de más.
    }

    /**
     * Este método comprueba si un elemento (i,j) cumple los requisitos para nacer o no.
     * @param i posición abcisas
     * @param j posición ordenadas
     * @return true si nace : false.
     */
    private boolean nacer(int[][] estado, int i, int j){
        int contador = 0;
            for(int x = i - 1; x <= (i+1); x++)
                for(int y = j + 1; y >= (j-1); y--)
                    try{
                        if(estado[x][y] == 1) contador++;
                    }catch (ArrayIndexOutOfBoundsException ai){}

        return (contador == 3) ? true : false;
    }
    /**
     * Devuelve, en modo texto, el estado actual.
     * @return el estado actual.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < DIMENSION; i++)
            for(int j = 0; j < DIMENSION; j++){
               if(j == 29) sb.append(estadoActual[i][j] + "\n");
               else sb.append(estadoActual[i][j] + " ");
            }
        return sb.toString();
    }

}
