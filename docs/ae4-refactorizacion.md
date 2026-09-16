# Ae4 - Kata de refactorización: antes y después

## Objetivo

En Ae4 trabajé sobre el mismo sistema de gestión de tutorías que ya venía evolucionando en las actividades anteriores. En esta etapa no agregué funcionalidades ni reglas nuevas. El objetivo fue mejorar la estructura interna y comprobar que el comportamiento se mantenga.

La línea base de Ae4 quedó registrada en el commit:

```text
3cfab9a chore: registrar linea base de ae4
```

Antes de cambiar el código se ejecutó:

```bash
mvn clean compile
mvn clean test
java -cp target\classes edu.uees.tutorias.app.Main
```

La línea base tenía 16 pruebas exitosas, sin fallos ni errores.

## Matriz de Code Smells

| Código / ubicación | Smell | Evidencia | Impacto |
|---|---|---|---|
| `ServicioReservas` | Código duplicado | Los casos crear, confirmar, cancelar y reprogramar repetían guardar la reserva y publicar el evento. | Aumentaba la cantidad de líneas que había que tocar si cambiaba el cierre de una operación. |
| `ReservaTutoria` | Validación repetida | `confirmar()` y `reprogramar()` comprobaban por separado si la reserva estaba cancelada. | La misma regla estaba escrita en más de un lugar. |
| `ObservadorNotificacionReserva` | Condicionales paralelos | Existían dos `switch` con los mismos cuatro tipos de evento, uno para estudiante y otro para docente. | Era fácil modificar un flujo y olvidar el otro. |
| `ReservaTutoriaBuilder` | Método con varias responsabilidades | `validarConfiguracion()` revisaba modalidad, tipo, duración y datos propios de cada modalidad. | La intención de cada validación era menos clara y el método crecía conforme aparecían controles. |

## Plan de refactorización

| Prioridad | Problema | Refactorización | Justificación |
|---|---|---|---|
| 1 | Guardado y publicación repetidos | Extract Method + Rename Method | Centralizar el cierre de cada operación sin cambiar el orden de ejecución. |
| 2 | Regla de reserva cancelada repetida | Extract Method | Mantener una sola validación para operaciones que requieren una reserva activa. |
| 3 | Dos `switch` paralelos | Simplify Conditional + Extract Method | Mantener un solo flujo por tipo de evento y conservar los mismos mensajes. |
| 4 | Validación de configuración agrupada | Extract Method | Separar cada regla de validación y hacer más evidente su intención. |

## Refactorización 1 - ServicioReservas

### Antes

Cada operación repetía:

```java
repositorio.guardar(reserva);
publicar(reserva, TipoEventoReserva.CONFIRMADA);
```

### Después

Se centralizó el cierre de la operación:

```java
private void guardarYNotificar(
        ReservaTutoria reserva,
        TipoEventoReserva tipoEvento
) {
    repositorio.guardar(reserva);
    notificarObservadores(reserva, tipoEvento);
}
```

También se cambió el nombre privado `publicar` por `notificarObservadores`, porque expresa mejor lo que realmente hace el método.

## Refactorización 2 - ReservaTutoria

### Antes

`confirmar()` y `reprogramar()` tenían la misma condición:

```java
if (estado == EstadoReserva.CANCELADA) {
    throw new IllegalStateException(...);
}
```

### Después

La regla se dejó en un solo método:

```java
private void validarReservaActiva(String mensaje) {
    if (estado == EstadoReserva.CANCELADA) {
        throw new IllegalStateException(mensaje);
    }
}
```

Se conservaron exactamente los mensajes originales de error.

## Refactorización 3 - ObservadorNotificacionReserva

### Antes

Había un `switch` para obtener el mensaje del estudiante y otro `switch` para el mensaje del docente.

### Después

Se dejó un único `switch` por evento. Cada caso envía los dos mensajes que ya existían:

```java
case CONFIRMADA -> notificarUsuarios(
        reserva,
        "Tu tutoría ya quedó confirmada.",
        "La tutoría ya quedó confirmada."
);
```

No se cambiaron textos ni receptores.

## Refactorización 4 - ReservaTutoriaBuilder

### Antes

`validarConfiguracion()` concentraba varias reglas distintas.

### Después

La validación se dividió en métodos con una intención concreta:

```text
validarModalidad()
validarTipoTutoria()
validarDuracion()
validarDatosSegunModalidad()
```

También se extrajo `validarTextoOpcional()` para evitar repetir la misma comprobación de texto vacío.

## Comportamiento que debe preservarse

| Caso | Antes | Después esperado |
|---|---|---|
| Crear reserva | Estado `PENDIENTE` y evento `CREADA` | Igual |
| Confirmar reserva | Estado `CONFIRMADA` y evento `CONFIRMADA` | Igual |
| Reprogramar reserva | Nuevo horario, estado `PENDIENTE` y evento `REPROGRAMADA` | Igual |
| Cancelar reserva | Estado `CANCELADA`, horario liberado y evento `CANCELADA` | Igual |
| Pruebas | 16 pruebas, 0 fallos, 0 errores | Las mismas 16 pruebas deben seguir pasando |

## Verificación después de cada cambio

El ciclo utilizado para cada refactorización es:

```text
refactorizar
→ mvn clean compile
→ mvn clean test
→ ejecutar Main
→ comparar con la línea base
→ commit
```

Comandos:

```bash
mvn clean compile
mvn clean test
java -cp target\classes edu.uees.tutorias.app.Main
```

## Resultado técnico esperado

La versión final debe producir los mismos mensajes y transiciones que la línea base. La mejora está en que el código tiene menos duplicación, métodos con una intención más clara y validaciones separadas por responsabilidad.

## Uso de inteligencia artificial

Utilicé inteligencia artificial como apoyo para revisar posibles Code Smells, contrastar alternativas de refactorización y mejorar la redacción de la documentación. Las decisiones aplicadas al proyecto, el código final y las verificaciones fueron revisadas y comprendidas antes de incorporarlas.
