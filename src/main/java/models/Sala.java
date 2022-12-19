package models;

import enums.Estado;

public class Sala {
    private final String nombreSala;
    private Pelicula pelicula;
    private final int numFilas;
    private final int numColumnas;
    private Estado[][] estadoSala;

    public Sala(String nombreSala, Pelicula pelicula, int numFilas, int numColumnas) {
        this.nombreSala = nombreSala;
        this.pelicula = pelicula;
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.estadoSala = inicializarSala(numFilas, numColumnas);
    }

    public String getNombreSala() {
        return this.nombreSala;
    }

    public String getNombrePelicula() {
        return this.pelicula.getNombre();
    }

    private Estado[][] inicializarSala(int numFilas, int numColumnas) {
        Estado[][] sala = new Estado[numFilas][numColumnas];
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                sala[i][j] = Estado.LIBRE;
            }
        }
        return sala;
    }

    public void cambiarEstadoButaca(String butaca, Estado estado) {}

    private int[] obtenerButaca(String codigoButaca) {
        return new int[0];
    }

    public void imprimirEstadoSala() {}

    public void imprimirInformeSala() {}
}
