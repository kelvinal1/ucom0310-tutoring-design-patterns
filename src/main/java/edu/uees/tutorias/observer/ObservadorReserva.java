package edu.uees.tutorias.observer;

/**
 * Observer: componentes interesados reaccionan sin que ServicioReservas
 * conozca el detalle de cada reacción.
 */
public interface ObservadorReserva {
    void actualizar(EventoReserva evento);
}
