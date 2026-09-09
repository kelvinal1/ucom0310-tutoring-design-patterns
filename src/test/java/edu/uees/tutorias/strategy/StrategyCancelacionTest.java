package edu.uees.tutorias.strategy;

import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ReservaTutoria;
import edu.uees.tutorias.domain.TipoTutoria;
import edu.uees.tutorias.repository.RepositorioReservasEnMemoria;
import edu.uees.tutorias.service.ServicioReservas;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StrategyCancelacionTest {

    @Test
    void unaTutoriaNormalPuedeCancelarse() {
        Fixture fixture = new Fixture();
        ReservaTutoria reserva = fixture.servicio.crearReserva(
                fixture.estudiante,
                fixture.docente,
                "H1",
                TipoTutoria.NORMAL
        );

        assertDoesNotThrow(() -> fixture.servicio.cancelarReserva(reserva.getId()));
    }

    @Test
    void unaTutoriaPrioritariaConfirmadaNoPuedeCancelarse() {
        Fixture fixture = new Fixture();
        ReservaTutoria reserva = fixture.servicio.crearReserva(
                fixture.estudiante,
                fixture.docente,
                "H1",
                TipoTutoria.PRIORITARIA
        );

        fixture.servicio.confirmarReserva(reserva.getId());

        assertThrows(
                IllegalStateException.class,
                () -> fixture.servicio.cancelarReserva(reserva.getId())
        );
    }

    @Test
    void unaTutoriaGrupalNoSeCancelaDesdeEsteFlujo() {
        Fixture fixture = new Fixture();
        ReservaTutoria reserva = fixture.servicio.crearReserva(
                fixture.estudiante,
                fixture.docente,
                "H1",
                TipoTutoria.GRUPAL
        );

        assertThrows(
                IllegalStateException.class,
                () -> fixture.servicio.cancelarReserva(reserva.getId())
        );
    }

    private static class Fixture {
        private final Estudiante estudiante =
                new Estudiante("E1", "Estudiante", "estudiante@uees.edu.ec");
        private final Docente docente =
                new Docente("D1", "Docente", "docente@uees.edu.ec");
        private final ServicioReservas servicio =
                new ServicioReservas(new RepositorioReservasEnMemoria());

        private Fixture() {
            docente.agregarHorario(
                    new HorarioDisponible("H1", LocalDateTime.of(2026, 9, 10, 10, 0), LocalDateTime.of(2026, 9, 10, 11, 0))
            );
        }
    }
}
