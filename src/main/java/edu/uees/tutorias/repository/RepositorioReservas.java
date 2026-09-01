package edu.uees.tutorias.repository;

import edu.uees.tutorias.domain.ReservaTutoria;

import java.util.List;
import java.util.Optional;

public interface RepositorioReservas {

    void guardar(ReservaTutoria reserva);

    Optional<ReservaTutoria> buscarPorId(String id);

    List<ReservaTutoria> buscarTodas();
}
