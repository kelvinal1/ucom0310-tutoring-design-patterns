package edu.uees.tutorias.notification;

import edu.uees.tutorias.domain.Usuario;

public class NotificadorCorreo implements Notificador {

    @Override
    public void notificar(Usuario usuario, String mensaje) {
        System.out.println(
                "Correo para " + usuario.getCorreo() + ": " + mensaje
        );
    }
}
