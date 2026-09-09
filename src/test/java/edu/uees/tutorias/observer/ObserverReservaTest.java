package edu.uees.tutorias.observer;

import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ReservaTutoria;
import edu.uees.tutorias.repository.RepositorioReservasEnMemoria;
import edu.uees.tutorias.service.ServicioReservas;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObserverReservaTest {

    @Test
    void auditoriaRecibeLosCambiosDeLaReserva() {
        Estudiante estudiante =
                new Estudiante("E1", "Estudiante", "estudiante@uees.edu.ec");
        Docente docente =
                new Docente("D1", "Docente", "docente@uees.edu.ec");

        docente.agregarHorario(
                new HorarioDisponible("H1", LocalDateTime.of(2026, 9, 10, 10, 0), LocalDateTime.of(2026, 9, 10, 11, 0))
        );

        ServicioReservas servicio =
                new ServicioReservas(new RepositorioReservasEnMemoria());

        ObservadorAuditoriaReserva auditoria = new ObservadorAuditoriaReserva();
        servicio.agregarObservador(auditoria);

        ReservaTutoria reserva = servicio.crearReserva(estudiante, docente, "H1");
        servicio.confirmarReserva(reserva.getId());
        servicio.cancelarReserva(reserva.getId());

        assertEquals(3, auditoria.getRegistros().size());
        assertEquals("CREADA | reserva=" + reserva.getId(), auditoria.getRegistros().get(0));
        assertEquals("CONFIRMADA | reserva=" + reserva.getId(), auditoria.getRegistros().get(1));
        assertEquals("CANCELADA | reserva=" + reserva.getId(), auditoria.getRegistros().get(2));
    }
}
