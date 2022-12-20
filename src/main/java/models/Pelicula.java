package models;

import enums.Genero;

public class Pelicula {
    private final String titulo;
    private int fecha;
    private final String director;
    private Genero genero;

    public Pelicula(String titulo, String fecha, String director, String genero) {
        this.titulo = titulo;
        setFecha(fecha);
        this.director = director;
        setGenero(genero);
    }

    public String getTitulo() {
        return this.titulo;
    }

    public int getFecha() {
        return this.fecha;
    }

    private void setFecha(String fecha) {
        String fechaRegex = "\\d{4}";
        if (!fecha.matches(fechaRegex)) throw new IllegalArgumentException("El año no tiene el formato correcto");
        this.fecha = Integer.parseInt(fecha);
    }

    public String getDirector() {
        return this.director;
    }

    public String getGenero() {
        return this.genero.name();
    }

    private void setGenero(String genero) {
        Genero resultado = null;
        for (Genero g : Genero.values()) {
            if (genero.equals(g.name())) {
                resultado = g;
            }
        }
        if (resultado == null) throw new IllegalArgumentException("El género introducido no es válido");
        this.genero = resultado;
    }
}
