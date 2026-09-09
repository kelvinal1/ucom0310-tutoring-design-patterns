package edu.uees.tutorias.strategy;

import edu.uees.tutorias.domain.ReservaTutoria;

public class PoliticaCancelacionGrupal implements PoliticaCancelacion {

    @Override
    public void validarCancelacion(ReservaTutoria reserva) {
        throw new IllegalStateException(
                "La tutoría grupal requiere gestión coordinada y no se cancela desde este flujo"
        );
    }

    @Override
    public String descripcion() {
        return "La cancelación grupal se deriva a un flujo coordinado.";
    }
}
