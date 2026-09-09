package edu.uees.tutorias.app;

import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ReservaTutoria;
import edu.uees.tutorias.domain.TipoTutoria;
import edu.uees.tutorias.factory.CreadorNotificadorCorreo;
import edu.uees.tutorias.notification.Notificador;
import edu.uees.tutorias.observer.ObservadorAuditoriaReserva;
import edu.uees.tutorias.observer.ObservadorNotificacionReserva;
import edu.uees.tutorias.repository.RepositorioReservasEnMemoria;
import edu.uees.tutorias.service.ServicioReservas;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Estudiante estudiante = new Estudiante(
                "EST-001",
                "Kevin Aguilar",
                "kevin@uees.edu.ec"
        );

        Docente docente = new Docente(
                "DOC-001",
                "Docente UEES",
                "docente@uees.edu.ec"
        );

        docente.agregarHorario(new HorarioDisponible(
                "HOR-001",
                LocalDateTime.of(2026, 9, 10, 18, 0),
                LocalDateTime.of(2026, 9, 10, 19, 0)
        ));

        docente.agregarHorario(new HorarioDisponible(
                "HOR-002",
                LocalDateTime.of(2026, 9, 11, 18, 0),
                LocalDateTime.of(2026, 9, 11, 19, 0)
        ));

        RepositorioReservasEnMemoria repositorio = new RepositorioReservasEnMemoria();
        ServicioReservas servicio = new ServicioReservas(repositorio);

        // Factory Method de Ae2 se mantiene.
        Notificador notificadorCorreo =
                new CreadorNotificadorCorreo().crearNotificador();

        // Observer: varios componentes reaccionan al mismo cambio de reserva.
        servicio.agregarObservador(new ObservadorNotificacionReserva(notificadorCorreo));
        servicio.agregarObservador(new ObservadorAuditoriaReserva());

        // Strategy: la política de cancelación depende del tipo de tutoría.
        ReservaTutoria reserva = servicio.crearReserva(
                estudiante,
                docente,
                "HOR-001",
                TipoTutoria.NORMAL
        );

        servicio.confirmarReserva(reserva.getId());
        servicio.reprogramarReserva(reserva.getId(), "HOR-002");
        servicio.cancelarReserva(reserva.getId());

        System.out.println();
        System.out.println("Estado final: " + reserva.getEstado());
        System.out.println("Tipo de tutoría: " + reserva.getTipoTutoria());
    }
}
