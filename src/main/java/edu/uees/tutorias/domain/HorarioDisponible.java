package edu.uees.tutorias.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class HorarioDisponible {

    private final String id;
    private final LocalDateTime inicio;
    private final LocalDateTime fin;

    private boolean disponible = true;

    public HorarioDisponible(String id, LocalDateTime inicio, LocalDateTime fin) {
        this.id = Objects.requireNonNull(id, "El identificador del horario es obligatorio");
        this.inicio = Objects.requireNonNull(inicio, "La fecha de inicio es obligatoria");
        this.fin = Objects.requireNonNull(fin, "La fecha de fin es obligatoria");

        if (id.isBlank()) {
            throw new IllegalArgumentException("El identificador del horario es obligatorio");
        }

        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
    }


    // Un horario solo se puede reservar una vez.
    public void reservar() {
        if (!disponible) {
            throw new IllegalStateException("Ese horario ya no está disponible");
        }

        disponible = false;
    }

    public void liberar() {
        disponible = true;
    }


    public String getId() {
        return id;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public boolean estaDisponible() {
        return disponible;
    }
}
