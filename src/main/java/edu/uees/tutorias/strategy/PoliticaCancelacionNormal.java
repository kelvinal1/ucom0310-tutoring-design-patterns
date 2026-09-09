package edu.uees.tutorias.strategy;

import edu.uees.tutorias.domain.EstadoReserva;
import edu.uees.tutorias.domain.ReservaTutoria;

public class PoliticaCancelacionNormal implements PoliticaCancelacion {

    @Override
    public void validarCancelacion(ReservaTutoria reserva) {
        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("La reserva ya está cancelada");
        }
    }

    @Override
    public String descripcion() {
        return "Permite cancelar una tutoría normal mientras no esté cancelada.";
    }
}
