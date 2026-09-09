# Sistema de gestión de tutorías - Ae3 Incremento 1

Este repositorio continúa el Sistema de gestión de tutorías trabajado en Ae1 y Ae2.  
Ae3 no crea un proyecto nuevo: evoluciona la misma base e incorpora **Strategy** y **Observer** porque existen dos puntos de variación concretos.

## 1. Estado inicial recuperado

De Ae1 se conservan las entidades y reglas principales:

- `Usuario`
- `Estudiante`
- `Docente`
- `HorarioDisponible`
- `ReservaTutoria`
- `RepositorioReservas`
- `ServicioReservas`

De Ae2 se mantienen:

- **Factory Method** para crear distintas variantes de `Notificador`.
- **Builder** para construir `ReservaTutoria` con datos obligatorios y opcionales.

## 2. Revisión de patrones de Ae2

| Patrón | Problema que resuelve | ¿Se mantiene? | Justificación |
|---|---|---:|---|
| Factory Method | La creación de correo, SMS, push y WhatsApp variaba y podía seguir creciendo. | Sí | Evita dispersar la creación concreta de notificadores. |
| Builder | `ReservaTutoria` posee varios datos obligatorios y opcionales. | Sí | Mantiene una construcción legible, validada y extensible. |

## 3. Problemas reales identificados en Ae3

### Problema 1: reglas de cancelación variables

La cancelación no tiene por qué seguir la misma regla para todos los tipos de tutoría.  
Una tutoría normal, prioritaria o grupal puede requerir una política diferente.

**Patrón elegido: Strategy.**

Lo estable es el caso de uso `cancelarReserva`.  
Lo variable es la política que decide si la cancelación es válida.

### Problema 2: varios componentes reaccionan a una reserva

Cuando una reserva se crea, confirma, cancela o reprograma, más de un componente puede reaccionar: notificaciones, auditoría, paneles u otras integraciones.

**Patrón elegido: Observer.**

Lo estable es el evento del dominio.  
Lo variable son los receptores que reaccionan al evento.

## 4. Strategy

Contrato:

- `PoliticaCancelacion`

Estrategias:

- `PoliticaCancelacionNormal`
- `PoliticaCancelacionPrioritaria`
- `PoliticaCancelacionGrupal`

Selección:

- `RegistroPoliticasCancelacion`

`ServicioReservas` obtiene la estrategia asociada a `TipoTutoria` y la ejecuta antes de cancelar.

### Beneficio

Se evita llenar el servicio con una cadena creciente de `if/else` para cada política.

### Costo

Se agregan interfaces y clases pequeñas que solo se justifican porque la política realmente cambia.

## 5. Observer

Contrato:

- `ObservadorReserva`

Evento:

- `EventoReserva`
- `TipoEventoReserva`

Observadores actuales:

- `ObservadorNotificacionReserva`
- `ObservadorAuditoriaReserva`

`ServicioReservas` publica los eventos y no necesita conocer el detalle de lo que hace cada observador.

### Beneficio

Un nuevo receptor puede agregarse sin modificar los casos de uso de confirmar, cancelar o reprogramar.

### Costo

El flujo deja de ser totalmente lineal porque existen reacciones desacopladas al evento.

## 6. Relación con SOLID

- **SRP:** las políticas de cancelación, las notificaciones, la auditoría, el repositorio y el servicio tienen responsabilidades separadas.
- **OCP:** nuevas estrategias u observadores pueden incorporarse mediante nuevas implementaciones.
- **LSP:** las implementaciones de `PoliticaCancelacion`, `ObservadorReserva` y `Notificador` respetan sus contratos.
- **ISP:** las interfaces son pequeñas y específicas.
- **DIP:** `ServicioReservas` trabaja con abstracciones como `RepositorioReservas`, `ObservadorReserva` y `PoliticaCancelacion`.

## 7. Cohesión y acoplamiento

La cohesión mejora porque cada clase concentra una responsabilidad concreta.  
El acoplamiento disminuye porque el servicio ya no contiene todas las reacciones ni todas las reglas variables.

## 8. Estructura principal

```text
src/main/java/edu/uees/tutorias/
├── app/
├── builder/
├── domain/
├── factory/
├── notification/
├── observer/
├── repository/
├── service/
└── strategy/
```

## 9. UML

- `docs/uml-incremento1.puml`
- `docs/uml-incremento1.png`

El diagrama incluye las relaciones principales de Factory Method, Builder, Strategy y Observer y mantiene los mismos nombres usados en Java.

## 10. Compilar

```bash
mvn clean compile
```

## 11. Ejecutar pruebas

```bash
mvn clean test
```

## 12. Ejecutar demostración

Windows PowerShell:

```powershell
java -cp target\classes edu.uees.tutorias.app.Main
```

Linux/macOS:

```bash
java -cp target/classes edu.uees.tutorias.app.Main
```

## 13. Verificación esperada

La ejecución demuestra:

1. creación de una reserva;
2. notificación y auditoría mediante Observer;
3. confirmación;
4. reprogramación;
5. cancelación aplicando Strategy;
6. estado final `CANCELADA`.

## 14. Decisión de diseño

No se agregaron más patrones solo por aumentar la cantidad.  
Se mantuvieron Factory Method y Builder porque continúan resolviendo problemas existentes y se incorporaron únicamente Strategy y Observer por los dos puntos de variación identificados en Ae3.

## 15. Uso de inteligencia artificial

Utilicé inteligencia artificial como herramienta de apoyo para revisar la estructura del proyecto, analizar alternativas de diseño, mejorar la redacción de la documentación y revisar ejemplos de implementación.

Las decisiones de diseño, el código, las pruebas y la relación UML-Java fueron revisadas y comprendidas antes de incluirlas en el proyecto.
