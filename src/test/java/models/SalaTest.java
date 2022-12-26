package models;

import enums.Estado;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaTest {
    static Pelicula peliculaTest = new Pelicula("pelicula", "2022", "director", "ACCION");
    static Sala salaTest = new Sala("test", peliculaTest, 10, 12);

    @BeforeAll
    static void setup() {
        salaTest = new Sala("test", peliculaTest, 10, 12);
    }
    @Test
    void cambiarEstadoButaca() {
        salaTest.cambiarEstadoButaca("A2", Estado.RESERVADA);
        salaTest.cambiarEstadoButaca("A3", Estado.OCUPADA);
        assertAll(
                () -> assertEquals(Estado.LIBRE, salaTest.getEstadoSala()[0][0]),
                () -> assertEquals(Estado.RESERVADA, salaTest.getEstadoSala()[0][1]),
                () -> assertEquals(Estado.OCUPADA, salaTest.getEstadoSala()[0][2])
        );
    }

    @Test
    void imprimirEstadoSala() {
    }

    @Test
    void imprimirInformeSala() {
    }
}