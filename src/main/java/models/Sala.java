package models;

import enums.Estado;

public class Sala {
    private final String nombreSala;
    private final Pelicula pelicula;
    private final Estado[][] estadoSala;
    private final int numFilas;
    private final int numColumnas;
    private double recaudacion = 0.0;
    private final double PRECIO_BUTACA = 5.25;

    public Sala(String nombreSala, Pelicula pelicula, int numFilas, int numColumnas) {
        this.nombreSala = nombreSala;
        this.pelicula = pelicula;
        this.estadoSala = inicializarSala(numFilas, numColumnas);
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
    }

    public String getNombreSala() {
        return this.nombreSala;
    }

    public String getTituloPelicula() {
        return this.pelicula.getTitulo();
    }

    private Estado[][] inicializarSala(int numFilas, int numColumnas) {
        // Las filas están limitadas a 26 por el número de letras que se pueden asignar
        if (numFilas > 26) throw new IllegalArgumentException("Se ha superado el número máximo de filas (26)");
        Estado[][] sala = new Estado[numFilas][numColumnas];
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                sala[i][j] = Estado.LIBRE;
            }
        }
        return sala;
    }

    public void cambiarEstadoButaca(String butaca, Estado estado) {
        int[] codigoButaca = procesarCodigoButaca(butaca);
        estadoSala[codigoButaca[0]][codigoButaca[1]] = estado;
        if (estado == Estado.OCUPADA) {
            recaudacion += PRECIO_BUTACA;
        } else if (estado == Estado.LIBRE) {
            recaudacion -= PRECIO_BUTACA;
        }
    }

    private int[] procesarCodigoButaca(String butaca) {
        String codigoRegex = "[A-Z]\\d{1,2}";
        if (!butaca.matches(codigoRegex))
            throw new IllegalArgumentException("El código de butaca no es válido: formato incorrecto");
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int fila = letras.indexOf(butaca.charAt(0));
        if (fila > this.numFilas || fila < 0)
            throw new IllegalArgumentException("El código de butaca no es válido: la fila no existe");
        int columna = Integer.parseInt(butaca.substring(1)) - 1;
        if (columna > this.numColumnas || columna < 0)
            throw new IllegalArgumentException("El código de butaca no es válido: la columna no existe");
        return new int[]{fila, columna};
    }

    public void imprimirEstadoSala() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Primeras filas (números)
        System.out.print("   0");
        for (int i = 2; i <= numColumnas; i++) {
            if (i % 10 == 0) {
                System.out.print(" " + i / 10);
            } else {
                System.out.print("  ");
            }
        }
        System.out.println();
        System.out.print("  ");
        for (int i = 1; i <= numColumnas; i++) {
            System.out.print(" " + i % 10);
        }
        System.out.println();
        // Resto de filas (letra + butacas)
        for (int i = 0; i < numFilas; i++) {
            // Marco de la tabla
            System.out.print("  ");
            for (int j = 0; j < numColumnas; j++) {
                System.out.print("|-");
            }
            System.out.println("|");
            // Fila de butacas
            System.out.print(letras.charAt(i) + " ");
            for (int j = 0; j < numColumnas; j++) {
                System.out.print("|" + caracterEstado(estadoSala[i][j]));
            }
            System.out.println("|");
        }
        // Última fila (cerrar la tabla)
        System.out.print("  ");
        for (int j = 0; j < numColumnas; j++) {
            System.out.print("|-");
        }
        System.out.println("|");
    }

    private char caracterEstado(Estado estado) {
        switch (estado) {
            case LIBRE -> {
                return ' ';
            }
            case RESERVADA -> {
                return 'R';
            }
            case OCUPADA -> {
                return 'O';
            }
        }
        return ' ';
    }

    public void imprimirInformeSala() {
        System.out.println("Sala \"" + this.nombreSala + "\"");
        System.out.println("Película: \"" + this.getTituloPelicula() + "\"");
        int[] estadoButacas = getButacas();
        System.out.println("Número de butacas ocupadas: " + estadoButacas[1]);
        System.out.println("Número de butacas reservadas: " + estadoButacas[2]);
        switch (estadoButacas[0]) {
            case 1 -> System.out.println("Queda una butaca libre");
            case 0 -> System.out.println("No quedan butacas libres");
            default -> System.out.println("Quedan " + estadoButacas[0] + " butacas libres");
        }
        System.out.printf("Recaudación total de la sala: %.2f%n", this.recaudacion);
    }

    private int[] getButacas() {
        int sumaLibres = 0;
        int sumaOcupadas = 0;
        int sumaReservadas = 0;
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                switch (estadoSala[i][j]) {
                    case LIBRE -> sumaLibres++;
                    case OCUPADA -> sumaOcupadas++;
                    case RESERVADA -> sumaReservadas++;
                }
            }
        }
        return new int[]{sumaLibres, sumaOcupadas, sumaReservadas};
    }
}
