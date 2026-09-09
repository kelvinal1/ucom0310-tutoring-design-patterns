package edu.uees.tutorias.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObservadorAuditoriaReserva implements ObservadorReserva {

    private final List<String> registros = new ArrayList<>();

    @Override
    public void actualizar(EventoReserva evento) {
        String registro = evento.tipo() + " | reserva=" + evento.reserva().getId();
        registros.add(registro);
        System.out.println("[AUDITORIA] " + registro);
    }

    public List<String> getRegistros() {
        return Collections.unmodifiableList(registros);
    }
}
