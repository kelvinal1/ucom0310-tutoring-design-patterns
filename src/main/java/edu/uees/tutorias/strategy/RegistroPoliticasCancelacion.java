package edu.uees.tutorias.strategy;

import edu.uees.tutorias.domain.TipoTutoria;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class RegistroPoliticasCancelacion {

    private final Map<TipoTutoria, PoliticaCancelacion> politicas =
            new EnumMap<>(TipoTutoria.class);

    public RegistroPoliticasCancelacion() {
        registrar(TipoTutoria.NORMAL, new PoliticaCancelacionNormal());
        registrar(TipoTutoria.PRIORITARIA, new PoliticaCancelacionPrioritaria());
        registrar(TipoTutoria.GRUPAL, new PoliticaCancelacionGrupal());
    }

    public void registrar(TipoTutoria tipo, PoliticaCancelacion politica) {
        politicas.put(
                Objects.requireNonNull(tipo, "El tipo de tutoría es obligatorio"),
                Objects.requireNonNull(politica, "La política es obligatoria")
        );
    }

    public PoliticaCancelacion obtener(TipoTutoria tipo) {
        PoliticaCancelacion politica = politicas.get(tipo);

        if (politica == null) {
            throw new IllegalStateException("No existe política de cancelación para " + tipo);
        }

        return politica;
    }
}
