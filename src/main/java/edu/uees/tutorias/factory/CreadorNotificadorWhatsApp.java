package edu.uees.tutorias.factory;

import edu.uees.tutorias.notification.Notificador;
import edu.uees.tutorias.notification.NotificadorWhatsApp;

public class CreadorNotificadorWhatsApp extends CreadorNotificador {

    @Override
    public Notificador crearNotificador() {
        return new NotificadorWhatsApp();
    }
}
