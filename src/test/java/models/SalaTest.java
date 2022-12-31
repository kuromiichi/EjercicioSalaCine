package models;

import enums.Estado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaTest {
    static Pelicula peliculaTest = new Pelicula("pelicula", "2022", "director", "ACCION");
    static Sala salaTest = new Sala("test", peliculaTest, 10, 12);

    @BeforeEach
    void setup() {
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
    void cambiarRecaudacion() {
        // Libre -> Ocupada
        salaTest.cambiarEstadoButaca("A1", Estado.OCUPADA);
        assertEquals(5.25, salaTest.getRecaudacion());
        salaTest.cambiarEstadoButaca("A2", Estado.OCUPADA);
        assertEquals(10.50, salaTest.getRecaudacion());
        // Libre -> Reservada
        salaTest.cambiarEstadoButaca("A3", Estado.RESERVADA);
        salaTest.cambiarEstadoButaca("A4", Estado.RESERVADA);
        assertEquals(10.50, salaTest.getRecaudacion());
        // Ocupada -> Libre
        salaTest.cambiarEstadoButaca("A1", Estado.LIBRE);
        assertEquals(5.25, salaTest.getRecaudacion());
        // Ocupada -> Reservada
        salaTest.cambiarEstadoButaca("A2", Estado.RESERVADA);
        assertEquals(0.0, salaTest.getRecaudacion());
        // Reservada -> Libre
        salaTest.cambiarEstadoButaca("A3", Estado.LIBRE);
        assertEquals(0.0, salaTest.getRecaudacion());
        // Reservada -> Ocupada
        salaTest.cambiarEstadoButaca("A4", Estado.OCUPADA);
        assertEquals(5.25, salaTest.getRecaudacion());
    }

    @Test
    void cambiarRecaudacionIncorrecto() {
        salaTest.cambiarEstadoButaca("A2", Estado.RESERVADA);
        salaTest.cambiarEstadoButaca("A3", Estado.OCUPADA);
        assertAll(
                // Libre -> Libre
                () -> {
                    IllegalArgumentException e = assertThrows(
                            IllegalArgumentException.class, () -> salaTest.cambiarEstadoButaca("A1", Estado.LIBRE));
                    assertEquals("No se ha actualizado el estado: ya era LIBRE", e.getMessage());
                },
                // Reservada -> Reservada
                () -> {
                    IllegalArgumentException e = assertThrows(
                            IllegalArgumentException.class, () -> salaTest.cambiarEstadoButaca("A2", Estado.RESERVADA));
                    assertEquals("No se ha actualizado el estado: ya era RESERVADA", e.getMessage());
                },
                // Ocupada -> Ocupada
                () -> {
                    IllegalArgumentException e = assertThrows(
                            IllegalArgumentException.class, () -> salaTest.cambiarEstadoButaca("A3", Estado.OCUPADA));
                    assertEquals("No se ha actualizado el estado: ya era OCUPADA", e.getMessage());
                }
        );
    }

    @Test
    void butacaIncorrecta() {
        assertAll(
                () -> {
                    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> salaTest.cambiarEstadoButaca("", Estado.LIBRE));
                    assertEquals("El código de butaca no es válido: formato incorrecto", e.getMessage());
                },
                () -> {
                    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> salaTest.cambiarEstadoButaca("Z1", Estado.LIBRE));
                    assertEquals("El código de butaca no es válido: la fila no existe", e.getMessage());
                },
                () -> {
                    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> salaTest.cambiarEstadoButaca("A27", Estado.LIBRE));
                    assertEquals("El código de butaca no es válido: la columna no existe", e.getMessage());
                }
        );
    }

    @Test
    void getPlanoSala() {
        String planoInicial = """
                   0                 1   \s
                   1 2 3 4 5 6 7 8 9 0 1 2
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                A | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                B | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                C | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                D | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                E | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                F | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                G | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                H | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                I | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                J | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|""";
        String planoCambiado = """
                   0                 1   \s
                   1 2 3 4 5 6 7 8 9 0 1 2
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                A | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                B | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                C | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                D | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                E | | | |O|O|O|O|O| | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                F | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                G | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                H | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                I | | | | | | | |R|R|R|R|R|
                  |-|-|-|-|-|-|-|-|-|-|-|-|
                J | | | | | | | | | | | | |
                  |-|-|-|-|-|-|-|-|-|-|-|-|""";
        assertAll(
                () -> assertEquals(planoInicial, salaTest.getPlanoSala()), () -> {
                    for (int i = 4; i <= 8; i++) {
                        salaTest.cambiarEstadoButaca(String.format("E%d", i), Estado.OCUPADA);
                    }
                    for (int i = 8; i <= 12; i++) {
                        salaTest.cambiarEstadoButaca(String.format("I%d", i), Estado.RESERVADA);
                    }
                    assertEquals(planoCambiado, salaTest.getPlanoSala());
                }
        );
    }

    @Test
    void getInformeSala() {
        String informeInicial = """
                Sala "test"
                Película: "pelicula"
                Número de butacas ocupadas: 0
                Número de butacas reservadas: 0
                Quedan 120 butacas libres
                Recaudación total de la sala: 0.00""";
        String informeCambiado = """
                Sala "test"
                Película: "pelicula"
                Número de butacas ocupadas: 5
                Número de butacas reservadas: 5
                Quedan 110 butacas libres
                Recaudación total de la sala: 26.25""";
        String informeOcupadas = """
                Sala "test"
                Película: "pelicula"
                Número de butacas ocupadas: 120
                Número de butacas reservadas: 0
                No quedan butacas libres
                Recaudación total de la sala: 630.00""";
        String informeUnaLibre = """
                Sala "test"
                Película: "pelicula"
                Número de butacas ocupadas: 119
                Número de butacas reservadas: 0
                Queda una butaca libre
                Recaudación total de la sala: 624.75""";
        assertEquals(informeInicial, salaTest.getInformeSala());
        for (int i = 4; i <= 8; i++) {
            salaTest.cambiarEstadoButaca(String.format("E%d", i), Estado.OCUPADA);
        }
        for (int i = 8; i <= 12; i++) {
            salaTest.cambiarEstadoButaca(String.format("I%d", i), Estado.RESERVADA);
        }
        assertEquals(informeCambiado, salaTest.getInformeSala());
        for (char i = 'A'; i <= 'J'; i++) {
            for (int j = 1; j <= 12; j++) {
                if (salaTest.getEstadoSala()[i - 65][j - 1] != Estado.OCUPADA) {
                    salaTest.cambiarEstadoButaca(String.format("%c%d", i, j), Estado.OCUPADA);
                }
            }
        }
        assertEquals(informeOcupadas, salaTest.getInformeSala());
        salaTest.cambiarEstadoButaca("A1", Estado.LIBRE);
        assertEquals(informeUnaLibre, salaTest.getInformeSala());
    }
}