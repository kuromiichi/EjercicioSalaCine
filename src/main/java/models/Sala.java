package models;

import enums.Estados;

public class Sala {
    private String nombreSala;
    private String nombrePelicula;
    private final int numFilas;
    private final int numColumnas;
    private Estados[][] estadoSala;

    public Sala(String nombreSala, String nombrePelicula, int numFilas, int numColumnas) {
        this.nombreSala = nombreSala;
        this.nombrePelicula = nombrePelicula;
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.estadoSala = inicializarSala(numFilas, numColumnas);
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    private Estados[][] inicializarSala(int numFilas, int numColumnas) {
        Estados[][] sala = new Estados[numFilas][numColumnas];
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                sala[i][j] = Estados.LIBRE;
            }
        }
        return sala;
    }

    public void cambiarEstadoButaca(String butaca, Estados estado) {}

    private int[] obtenerButaca(String codigoButaca) {
        return new int[0];
    }

    public void imprimirEstadoSala() {}

    public void imprimirInformeSala() {}
}
