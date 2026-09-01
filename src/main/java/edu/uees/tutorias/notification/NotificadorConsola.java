package edu.uees.tutorias.notification;

import edu.uees.tutorias.domain.Usuario;

public class NotificadorConsola implements Notificador {

    @Override
    public void notificar(Usuario usuario, String mensaje) {
        System.out.println(
                "Aviso para " + usuario.getNombre()
                        + " <" + usuario.getCorreo() + ">: "
                        + mensaje
        );
    }
}
