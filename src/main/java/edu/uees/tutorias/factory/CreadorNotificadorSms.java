package edu.uees.tutorias.factory;

import edu.uees.tutorias.notification.Notificador;
import edu.uees.tutorias.notification.NotificadorSms;

public class CreadorNotificadorSms extends CreadorNotificador {

    @Override
    public Notificador crearNotificador() {
        return new NotificadorSms();
    }
}
