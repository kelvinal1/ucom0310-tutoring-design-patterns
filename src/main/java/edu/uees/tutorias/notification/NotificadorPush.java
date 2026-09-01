package edu.uees.tutorias.notification;

import edu.uees.tutorias.domain.Usuario;

public class NotificadorPush implements Notificador {

    @Override
    public void notificar(Usuario usuario, String mensaje) {
        System.out.println(
                "Push para " + usuario.getNombre() + ": " + mensaje
        );
    }
}
