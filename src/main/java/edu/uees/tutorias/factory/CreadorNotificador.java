package edu.uees.tutorias.factory;

import edu.uees.tutorias.domain.Usuario;
import edu.uees.tutorias.notification.Notificador;

public abstract class CreadorNotificador {

    // Este es el Factory Method. Cada subclase decide qué producto concreto crear.
    public abstract Notificador crearNotificador();


    public void notificar(Usuario usuario, String mensaje) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        if (mensaje == null || mensaje.isBlank()) {
            throw new IllegalArgumentException("El mensaje es obligatorio");
        }

        Notificador notificador = crearNotificador();
        notificador.notificar(usuario, mensaje);
    }
}
