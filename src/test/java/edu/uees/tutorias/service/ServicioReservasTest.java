package edu.uees.tutorias.service;

import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.EstadoReserva;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ReservaTutoria;
import edu.uees.tutorias.notification.Notificador;
import edu.uees.tutorias.repository.RepositorioReservasEnMemoria;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServicioReservasTest {

    @Test
    void deberiaCrearYConfirmarLaReserva() {
        Estudiante estudiante = new Estudiante("S1", "Estudiante", "student@test.com");
        Docente docente = new Docente("T1", "Docente", "teacher@test.com");

        HorarioDisponible horario = new HorarioDisponible(
                "A1",
                LocalDateTime.of(2026, 8, 27, 10, 0),
                LocalDateTime.of(2026, 8, 27, 11, 0)
        );

        docente.agregarHorario(horario);

        Notificador notificador = (usuario, mensaje) -> {
            // En esta prueba no hace falta una implementación real.
        };

        ServicioReservas servicio = new ServicioReservas(
                new RepositorioReservasEnMemoria(),
                notificador
        );

        ReservaTutoria reserva = servicio.crearReserva(estudiante, docente, "A1");

        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
        assertFalse(horario.estaDisponible());

        servicio.confirmarReserva(reserva.getId());

        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
    }

    @Test
    void deberiaLiberarElHorarioCuandoLaReservaSeCancela() {
        Estudiante estudiante = new Estudiante("S1", "Estudiante", "student@test.com");
        Docente docente = new Docente("T1", "Docente", "teacher@test.com");

        HorarioDisponible horario = new HorarioDisponible(
                "A1",
                LocalDateTime.of(2026, 8, 27, 10, 0),
                LocalDateTime.of(2026, 8, 27, 11, 0)
        );

        docente.agregarHorario(horario);

        ServicioReservas servicio = new ServicioReservas(
                new RepositorioReservasEnMemoria(),
                (usuario, mensaje) -> {
                }
        );

        ReservaTutoria reserva = servicio.crearReserva(estudiante, docente, "A1");

        servicio.cancelarReserva(reserva.getId());

        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
        assertTrue(horario.estaDisponible());
    }

    @Test
    void deberiaReprogramarLaReservaYPedirConfirmacionOtraVez() {
        Estudiante estudiante = new Estudiante("S1", "Estudiante", "student@test.com");
        Docente docente = new Docente("T1", "Docente", "teacher@test.com");

        HorarioDisponible primerHorario = new HorarioDisponible(
                "A1",
                LocalDateTime.of(2026, 8, 27, 10, 0),
                LocalDateTime.of(2026, 8, 27, 11, 0)
        );

        HorarioDisponible segundoHorario = new HorarioDisponible(
                "A2",
                LocalDateTime.of(2026, 8, 28, 14, 0),
                LocalDateTime.of(2026, 8, 28, 15, 0)
        );

        docente.agregarHorario(primerHorario);
        docente.agregarHorario(segundoHorario);

        ServicioReservas servicio = new ServicioReservas(
                new RepositorioReservasEnMemoria(),
                (usuario, mensaje) -> {
                }
        );

        ReservaTutoria reserva = servicio.crearReserva(estudiante, docente, "A1");

        servicio.confirmarReserva(reserva.getId());
        servicio.reprogramarReserva(reserva.getId(), "A2");

        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
        assertTrue(primerHorario.estaDisponible());
        assertFalse(segundoHorario.estaDisponible());
        assertEquals("A2", reserva.getHorario().getId());
    }

    @Test
    void noDeberiaConfirmarUnaReservaCancelada() {
        Estudiante estudiante = new Estudiante("S1", "Estudiante", "student@test.com");
        Docente docente = new Docente("T1", "Docente", "teacher@test.com");

        HorarioDisponible horario = new HorarioDisponible(
                "A1",
                LocalDateTime.of(2026, 8, 27, 10, 0),
                LocalDateTime.of(2026, 8, 27, 11, 0)
        );

        docente.agregarHorario(horario);

        ServicioReservas servicio = new ServicioReservas(
                new RepositorioReservasEnMemoria(),
                (usuario, mensaje) -> {
                }
        );

        ReservaTutoria reserva = servicio.crearReserva(estudiante, docente, "A1");
        servicio.cancelarReserva(reserva.getId());

        assertThrows(
                IllegalStateException.class,
                () -> servicio.confirmarReserva(reserva.getId())
        );
    }
}
