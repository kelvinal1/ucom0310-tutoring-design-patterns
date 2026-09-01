package edu.uees.tutorias.notification;

import edu.uees.tutorias.domain.Usuario;

// Esta es la variante adicional para demostrar que el Factory Method se puede extender.
public class NotificadorWhatsApp implements Notificador {

    @Override
    public void notificar(Usuario usuario, String mensaje) {
        System.out.println(
                "WhatsApp para " + usuario.getNombre() + ": " + mensaje
        );
    }
}
