package edu.uees.tutorias.builder;

import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ModalidadTutoria;
import edu.uees.tutorias.domain.ReservaTutoria;

public class ReservaTutoriaBuilder {

    private String id;
    private Estudiante estudiante;
    private Docente docente;
    private HorarioDisponible horario;

    private String asignatura;
    private ModalidadTutoria modalidad = ModalidadTutoria.ONLINE;
    private String enlaceReunion;
    private String ubicacion;
    private String notas;
    private int duracionMinutos = 60;


    public ReservaTutoriaBuilder conId(String id) {
        this.id = id;
        return this;
    }

    public ReservaTutoriaBuilder paraEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        return this;
    }

    public ReservaTutoriaBuilder conDocente(Docente docente) {
        this.docente = docente;
        return this;
    }

    public ReservaTutoriaBuilder enHorario(HorarioDisponible horario) {
        this.horario = horario;
        return this;
    }

    public ReservaTutoriaBuilder paraAsignatura(String asignatura) {
        this.asignatura = asignatura;
        return this;
    }

    public ReservaTutoriaBuilder conModalidad(ModalidadTutoria modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public ReservaTutoriaBuilder conEnlaceReunion(String enlaceReunion) {
        this.enlaceReunion = enlaceReunion;
        return this;
    }

    public ReservaTutoriaBuilder enUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
        return this;
    }

    public ReservaTutoriaBuilder conNotas(String notas) {
        this.notas = notas;
        return this;
    }

    public ReservaTutoriaBuilder conDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
        return this;
    }


    // Antes de construir revisamos los datos obligatorios y algunas reglas simples.
    public ReservaTutoria construir() {
        validarObligatorios();
        validarConfiguracion();

        return new ReservaTutoria(
                id,
                estudiante,
                docente,
                horario,
                asignatura,
                modalidad,
                enlaceReunion,
                ubicacion,
                notas,
                duracionMinutos
        );
    }


    private void validarObligatorios() {
        if (id == null || id.isBlank()) {
            throw new IllegalStateException("El id de la reserva es obligatorio");
        }

        if (estudiante == null) {
            throw new IllegalStateException("El estudiante es obligatorio");
        }

        if (docente == null) {
            throw new IllegalStateException("El docente es obligatorio");
        }

        if (horario == null) {
            throw new IllegalStateException("El horario es obligatorio");
        }
    }

    private void validarConfiguracion() {
        if (modalidad == null) {
            throw new IllegalStateException("La modalidad no puede ser nula");
        }

        if (duracionMinutos <= 0) {
            throw new IllegalStateException("La duración debe ser mayor que cero");
        }

        if (modalidad == ModalidadTutoria.ONLINE
                && enlaceReunion != null
                && enlaceReunion.isBlank()) {
            throw new IllegalStateException("El enlace de reunión no puede estar vacío");
        }

        if (modalidad == ModalidadTutoria.PRESENCIAL
                && ubicacion != null
                && ubicacion.isBlank()) {
            throw new IllegalStateException("La ubicación no puede estar vacía");
        }
    }
}
