package edu.uees.tutorias.builder;

import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.EstadoReserva;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ModalidadTutoria;
import edu.uees.tutorias.domain.ReservaTutoria;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservaTutoriaBuilderTest {

    @Test
    void deberiaConstruirReservaConValoresPorDefecto() {
        Estudiante estudiante = new Estudiante("S1", "Estudiante", "student@test.com");
        Docente docente = new Docente("T1", "Docente", "teacher@test.com");
        HorarioDisponible horario = horario("A1");

        ReservaTutoria reserva = new ReservaTutoriaBuilder()
                .conId("R-001")
                .paraEstudiante(estudiante)
                .conDocente(docente)
                .enHorario(horario)
                .construir();

        assertEquals(ModalidadTutoria.ONLINE, reserva.getModalidad());
        assertEquals(60, reserva.getDuracionMinutos());
        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
    }

    @Test
    void deberiaConstruirReservaConCamposOpcionales() {
        Estudiante estudiante = new Estudiante("S1", "Estudiante", "student@test.com");
        Docente docente = new Docente("T1", "Docente", "teacher@test.com");

        ReservaTutoria reserva = new ReservaTutoriaBuilder()
                .conId("R-002")
                .paraEstudiante(estudiante)
                .conDocente(docente)
                .enHorario(horario("A2"))
                .paraAsignatura("Diseño de Software")
                .conModalidad(ModalidadTutoria.PRESENCIAL)
                .enUbicacion("Aula 204")
                .conNotas("Revisar patrones creacionales")
                .conDuracionMinutos(90)
                .construir();

        assertEquals("Diseño de Software", reserva.getAsignatura());
        assertEquals(ModalidadTutoria.PRESENCIAL, reserva.getModalidad());
        assertEquals("Aula 204", reserva.getUbicacion());
        assertEquals(90, reserva.getDuracionMinutos());
    }

    @Test
    void deberiaFallarCuandoFaltaUnCampoObligatorio() {
        ReservaTutoriaBuilder builder = new ReservaTutoriaBuilder()
                .conId("R-003");

        assertThrows(IllegalStateException.class, builder::construir);
    }

    @Test
    void deberiaFallarCuandoLaDuracionNoEsValida() {
        Estudiante estudiante = new Estudiante("S1", "Estudiante", "student@test.com");
        Docente docente = new Docente("T1", "Docente", "teacher@test.com");

        ReservaTutoriaBuilder builder = new ReservaTutoriaBuilder()
                .conId("R-004")
                .paraEstudiante(estudiante)
                .conDocente(docente)
                .enHorario(horario("A4"))
                .conDuracionMinutos(0);

        assertThrows(IllegalStateException.class, builder::construir);
    }


    private HorarioDisponible horario(String id) {
        return new HorarioDisponible(
                id,
                LocalDateTime.of(2026, 9, 5, 10, 0),
                LocalDateTime.of(2026, 9, 5, 11, 0)
        );
    }
}
