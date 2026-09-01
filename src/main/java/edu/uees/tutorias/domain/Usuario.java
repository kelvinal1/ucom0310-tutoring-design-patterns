package edu.uees.tutorias.domain;

import java.util.Objects;

public abstract class Usuario {

    private final String id;
    private final String nombre;
    private final String correo;

    protected Usuario(String id, String nombre, String correo) {
        this.id = requireText(id, "El identificador del usuario es obligatorio");
        this.nombre = requireText(nombre, "El nombre del usuario es obligatorio");
        this.correo = requireText(correo, "El correo del usuario es obligatorio");
    }


    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }


    private String requireText(String value, String message) {
        Objects.requireNonNull(value, message);

        if (value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }
}
