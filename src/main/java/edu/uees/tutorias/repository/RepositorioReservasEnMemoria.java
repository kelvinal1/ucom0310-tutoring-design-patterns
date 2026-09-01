package edu.uees.tutorias.repository;

import edu.uees.tutorias.domain.ReservaTutoria;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepositorioReservasEnMemoria implements RepositorioReservas {

    private final Map<String, ReservaTutoria> reservas = new LinkedHashMap<>();


    @Override
    public void guardar(ReservaTutoria reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva es obligatoria");
        }

        reservas.put(reserva.getId(), reserva);
    }

    @Override
    public Optional<ReservaTutoria> buscarPorId(String id) {
        return Optional.ofNullable(reservas.get(id));
    }

    @Override
    public List<ReservaTutoria> buscarTodas() {
        return new ArrayList<>(reservas.values());
    }
}
