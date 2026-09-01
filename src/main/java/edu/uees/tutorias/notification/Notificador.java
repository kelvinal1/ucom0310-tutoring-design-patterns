package edu.uees.tutorias.notification;

import edu.uees.tutorias.domain.Usuario;

public interface Notificador {

    void notificar(Usuario usuario, String mensaje);
}
