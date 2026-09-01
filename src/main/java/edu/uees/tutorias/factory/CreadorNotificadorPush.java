package edu.uees.tutorias.factory;

import edu.uees.tutorias.notification.Notificador;
import edu.uees.tutorias.notification.NotificadorPush;

public class CreadorNotificadorPush extends CreadorNotificador {

    @Override
    public Notificador crearNotificador() {
        return new NotificadorPush();
    }
}
