package edu.uees.tutorias.factory;

import edu.uees.tutorias.notification.Notificador;
import edu.uees.tutorias.notification.NotificadorCorreo;

public class CreadorNotificadorCorreo extends CreadorNotificador {

    @Override
    public Notificador crearNotificador() {
        return new NotificadorCorreo();
    }
}
