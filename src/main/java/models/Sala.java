package models;

import enums.Estado;

public class Sala {
    private final String nombreSala;
    private final Pelicula pelicula;
    private final Estado[][] estadoSala;
    private final int numFilas;
    private final int numColumnas;
    private double recaudacion = 0.0;

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

    public Estado[][] getEstadoSala() {
        return this.estadoSala;
    }

    public double getRecaudacion() {
        return this.recaudacion;
    }

    public Estado[][] inicializarSala(int numFilas, int numColumnas) {
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
        Estado estadoAnterior = estadoSala[codigoButaca[0]][codigoButaca[1]];
        estadoSala[codigoButaca[0]][codigoButaca[1]] = estado;
        cambiarRecaudacion(estado, estadoAnterior);
    }

    private void cambiarRecaudacion(Estado estado, Estado estadoAnterior) {
        if (estado == estadoAnterior) {
            throw new IllegalArgumentException("No se ha actualizado el estado: ya era " + estado.name());
        }
        double PRECIO_BUTACA = 5.25;
        if (estadoAnterior == Estado.OCUPADA) {
            recaudacion -= PRECIO_BUTACA;
        } else {
            if (estado == Estado.OCUPADA) recaudacion += PRECIO_BUTACA;
        }
    }

    private int[] procesarCodigoButaca(String butaca) throws IllegalArgumentException {
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

    public String getPlanoSala() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder plano = new StringBuilder();
        // Primeras filas (números)
        plano.append("   0");
        for (int i = 2; i <= numColumnas; i++) {
            if (i % 10 == 0) {
                plano.append(" ").append(i / 10);
            } else {
                plano.append("  ");
            }
        }
        plano.append("\n");
        plano.append("  ");
        for (int i = 1; i <= numColumnas; i++) {
            plano.append(" ").append(i % 10);
        }
        plano.append("\n");
        // Resto de filas (letra + butacas)
        for (int i = 0; i < numFilas; i++) {
            // Marco de la tabla
            plano.append("  ");
            for (int j = 0; j < numColumnas; j++) {
                plano.append("|-");
            }
            plano.append("|\n");
            // Fila de butacas
            plano.append(letras.charAt(i)).append(" ");
            for (int j = 0; j < numColumnas; j++) {
                plano.append("|").append(caracterEstado(estadoSala[i][j]));
            }
            plano.append("|\n");
        }
        // Última fila (cerrar la tabla)
        plano.append("  ");
        for (int j = 0; j < numColumnas; j++) {
            plano.append("|-");
        }
        plano.append("|");

        return plano.toString();
    }

    private char caracterEstado(Estado estado) {
        switch (estado) {
            case RESERVADA -> {
                return 'R';
            }
            case OCUPADA -> {
                return 'O';
            }
            default -> {
                return ' ';
            }
        }
    }

    public String getInformeSala() {
        StringBuilder informe = new StringBuilder();
        informe.append("Sala \"").append(this.getNombreSala()).append("\"\n");
        informe.append("Película: \"").append(this.getTituloPelicula()).append("\"\n");
        int[] estadoButacas = getButacas();
        informe.append("Número de butacas ocupadas: ").append(estadoButacas[1]).append("\n");
        informe.append("Número de butacas reservadas: ").append(estadoButacas[2]).append("\n");
        switch (estadoButacas[0]) {
            case 1 -> informe.append("Queda una butaca libre");
            case 0 -> informe.append("No quedan butacas libres");
            default -> informe.append("Quedan ").append(estadoButacas[0]).append(" butacas libres");
        }
        informe.append(String.format("\nRecaudación total de la sala: %.2f", this.recaudacion));

        return informe.toString();
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
