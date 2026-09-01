package edu.uees.tutorias.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Docente extends Usuario {

    private final List<HorarioDisponible> horariosDisponibles = new ArrayList<>();

    public Docente(String id, String nombre, String correo) {
        super(id, nombre, correo);
    }


    // El docente administra sus propios horarios disponibles.
    public void agregarHorario(HorarioDisponible horario) {
        if (horario == null) {
            throw new IllegalArgumentException("El horario disponible es obligatorio");
        }

        boolean duplicado = horariosDisponibles.stream()
                .anyMatch(actual -> actual.getId().equals(horario.getId()));

        if (duplicado) {
            throw new IllegalArgumentException("Ese horario disponible ya existe");
        }

        horariosDisponibles.add(horario);
    }

    public void eliminarHorario(String horarioId) {
        HorarioDisponible horario = buscarHorario(horarioId);

        if (!horario.estaDisponible()) {
            throw new IllegalStateException("No se puede eliminar un horario que ya está reservado");
        }

        horariosDisponibles.remove(horario);
    }

    public boolean estaHorarioDisponible(String horarioId) {
        return horariosDisponibles.stream()
                .anyMatch(horario -> horario.getId().equals(horarioId) && horario.estaDisponible());
    }

    public HorarioDisponible obtenerHorario(String horarioId) {
        return buscarHorario(horarioId);
    }

    public List<HorarioDisponible> obtenerHorariosDisponibles() {
        return Collections.unmodifiableList(horariosDisponibles);
    }


    private HorarioDisponible buscarHorario(String horarioId) {
        return horariosDisponibles.stream()
                .filter(horario -> horario.getId().equals(horarioId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el horario disponible"));
    }
}
