package app;

import domain.Tablero;

import java.util.concurrent.TimeUnit;

public class Main {

        public static void main(String[] args) {


        String nombreFichero = "juego.txt";


            try {
            Tablero tablero = new Tablero();
            System.out.println("SIMULACIÓN CON TABLERO LEÍDO");

            tablero.leerEstadoActual(nombreFichero);
            System.out.println(tablero);
            for (int i = 0; i <= 5; i++) {
                TimeUnit.SECONDS.sleep(1);
                tablero.transitarAlEstadoSiguiente();
                System.out.println(tablero);
            }
            System.out.println("SIMULACIÓN CON TABLERO GENERADO MEDIANTE MONTECARLO");
            tablero.generarEstadoActualPorMontecarlo();
            System.out.println(tablero);
            for (int i = 0; i <= 15; i++) {
                TimeUnit.SECONDS.sleep(1);
                tablero.transitarAlEstadoSiguiente();
                System.out.println(tablero);
            }
        }catch(
        InterruptedException e)

        {
            System.out.println(e);
        }






        }

}
