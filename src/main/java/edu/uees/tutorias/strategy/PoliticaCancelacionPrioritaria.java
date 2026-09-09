package edu.uees.tutorias.strategy;

import edu.uees.tutorias.domain.EstadoReserva;
import edu.uees.tutorias.domain.ReservaTutoria;

public class PoliticaCancelacionPrioritaria implements PoliticaCancelacion {

    @Override
    public void validarCancelacion(ReservaTutoria reserva) {
        if (reserva.getEstado() != EstadoReserva.PENDIENTE) {
            throw new IllegalStateException(
                    "Una tutoría prioritaria solo puede cancelarse mientras está pendiente"
            );
        }
    }

    @Override
    public String descripcion() {
        return "La tutoría prioritaria solo se cancela antes de ser confirmada.";
    }
}
