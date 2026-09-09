package edu.uees.tutorias.strategy;

import edu.uees.tutorias.domain.ReservaTutoria;

/**
 * Strategy: cada implementación encapsula una regla de cancelación.
 */
public interface PoliticaCancelacion {
    void validarCancelacion(ReservaTutoria reserva);
    String descripcion();
}
