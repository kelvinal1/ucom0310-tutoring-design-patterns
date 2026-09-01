package edu.uees.tutorias.notification;

import edu.uees.tutorias.domain.Usuario;

public class NotificadorSms implements Notificador {

    @Override
    public void notificar(Usuario usuario, String mensaje) {
        System.out.println(
                "SMS para " + usuario.getNombre() + ": " + mensaje
        );
    }
}
