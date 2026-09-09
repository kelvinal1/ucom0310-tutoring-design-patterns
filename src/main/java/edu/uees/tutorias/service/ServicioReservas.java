package edu.uees.tutorias.service;

import edu.uees.tutorias.builder.ReservaTutoriaBuilder;
import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ReservaTutoria;
import edu.uees.tutorias.domain.TipoTutoria;
import edu.uees.tutorias.notification.Notificador;
import edu.uees.tutorias.observer.EventoReserva;
import edu.uees.tutorias.observer.ObservadorNotificacionReserva;
import edu.uees.tutorias.observer.ObservadorReserva;
import edu.uees.tutorias.observer.TipoEventoReserva;
import edu.uees.tutorias.repository.RepositorioReservas;
import edu.uees.tutorias.strategy.PoliticaCancelacion;
import edu.uees.tutorias.strategy.RegistroPoliticasCancelacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ServicioReservas {

    private final RepositorioReservas repositorio;
    private final RegistroPoliticasCancelacion politicasCancelacion;
    private final List<ObservadorReserva> observadores = new ArrayList<>();

    public ServicioReservas(RepositorioReservas repositorio) {
        this(repositorio, new RegistroPoliticasCancelacion());
    }

    // Constructor compatible con Ae1/Ae2.
    public ServicioReservas(
            RepositorioReservas repositorio,
            Notificador notificador
    ) {
        this(repositorio, new RegistroPoliticasCancelacion());
        agregarObservador(new ObservadorNotificacionReserva(notificador));
    }

    public ServicioReservas(
            RepositorioReservas repositorio,
            RegistroPoliticasCancelacion politicasCancelacion
    ) {
        this.repositorio = Objects.requireNonNull(repositorio, "El repositorio es obligatorio");
        this.politicasCancelacion = Objects.requireNonNull(
                politicasCancelacion,
                "El registro de políticas es obligatorio"
        );
    }

    public void agregarObservador(ObservadorReserva observador) {
        Objects.requireNonNull(observador, "El observador es obligatorio");

        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void eliminarObservador(ObservadorReserva observador) {
        observadores.remove(observador);
    }

    public ReservaTutoria crearReserva(
            Estudiante estudiante,
            Docente docente,
            String horarioId
    ) {
        return crearReserva(estudiante, docente, horarioId, TipoTutoria.NORMAL);
    }

    public ReservaTutoria crearReserva(
            Estudiante estudiante,
            Docente docente,
            String horarioId,
            TipoTutoria tipoTutoria
    ) {
        if (estudiante == null || docente == null) {
            throw new IllegalArgumentException("El estudiante y el docente son obligatorios");
        }

        if (!docente.estaHorarioDisponible(horarioId)) {
            throw new IllegalStateException("Ese horario ya no está disponible");
        }

        HorarioDisponible horario = docente.obtenerHorario(horarioId);

        ReservaTutoria reserva = new ReservaTutoriaBuilder()
                .conId(UUID.randomUUID().toString())
                .paraEstudiante(estudiante)
                .conDocente(docente)
                .enHorario(horario)
                .conTipoTutoria(tipoTutoria)
                .construir();

        repositorio.guardar(reserva);
        publicar(reserva, TipoEventoReserva.CREADA);

        return reserva;
    }

    public void confirmarReserva(String reservaId) {
        ReservaTutoria reserva = obtenerReserva(reservaId);

        reserva.confirmar();
        repositorio.guardar(reserva);
        publicar(reserva, TipoEventoReserva.CONFIRMADA);
    }

    public void cancelarReserva(String reservaId) {
        ReservaTutoria reserva = obtenerReserva(reservaId);

        PoliticaCancelacion politica = politicasCancelacion.obtener(reserva.getTipoTutoria());
        politica.validarCancelacion(reserva);

        reserva.cancelar();
        repositorio.guardar(reserva);
        publicar(reserva, TipoEventoReserva.CANCELADA);
    }

    public void reprogramarReserva(String reservaId, String nuevoHorarioId) {
        ReservaTutoria reserva = obtenerReserva(reservaId);
        Docente docente = reserva.getDocente();

        if (!docente.estaHorarioDisponible(nuevoHorarioId)) {
            throw new IllegalStateException("El nuevo horario no está disponible");
        }

        HorarioDisponible nuevoHorario = docente.obtenerHorario(nuevoHorarioId);

        reserva.reprogramar(nuevoHorario);
        repositorio.guardar(reserva);
        publicar(reserva, TipoEventoReserva.REPROGRAMADA);
    }

    private void publicar(ReservaTutoria reserva, TipoEventoReserva tipo) {
        EventoReserva evento = new EventoReserva(reserva, tipo);
        List.copyOf(observadores).forEach(observador -> observador.actualizar(evento));
    }

    private ReservaTutoria obtenerReserva(String reservaId) {
        return repositorio.buscarPorId(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la reserva"));
    }
}
