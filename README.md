# Sistema de gestión de tutorías - Ae2 Patrones de diseño

Este proyecto continúa el Sistema de gestión de tutorías trabajado en Ae1.

No se reemplazó el modelo anterior. Se conservaron las clases principales del dominio, repositorio y servicio, y sobre esa misma base se aplicaron los patrones **Factory Method** y **Builder** solicitados en Ae2.

## Base reutilizada de Ae1

Se mantienen:

- `Usuario`
- `Estudiante`
- `Docente`
- `HorarioDisponible`
- `EstadoReserva`
- `ReservaTutoria`
- `Notificador`
- `NotificadorConsola`
- `RepositorioReservas`
- `RepositorioReservasEnMemoria`
- `ServicioReservas`

También se conservaron las reglas de confirmación, cancelación y reprogramación.

## Factory Method

### Problema

En Ae1 existía la interfaz `Notificador` y una implementación por consola. El nuevo requisito plantea varios mecanismos de notificación.

Para evitar que la lógica principal tenga que conocer cómo se construye cada variante, se agregó Factory Method.

### Product

`Notificador`

### ConcreteProducts iniciales

- `NotificadorCorreo`
- `NotificadorSms`
- `NotificadorPush`

### Creator

`CreadorNotificador`

### ConcreteCreators

- `CreadorNotificadorCorreo`
- `CreadorNotificadorSms`
- `CreadorNotificadorPush`

### Variante adicional

Se agregó:

- `NotificadorWhatsApp`
- `CreadorNotificadorWhatsApp`

Esta variante permite demostrar que se puede extender el patrón sin modificar los productos y creadores ya existentes.

## Builder

### Problema

En Ae1 `ReservaTutoria` tenía un constructor corto con:

- id
- estudiante
- docente
- horario

En Ae2 la reserva incorpora más configuración. Agregar todos esos parámetros al constructor original lo volvería difícil de leer.

### Campos obligatorios

- id
- estudiante
- docente
- horario

### Campos opcionales

- asignatura
- modalidad
- enlace de reunión
- ubicación
- notas
- duración

### Valores por defecto

- modalidad: `ONLINE`
- duración: `60`
- estado inicial: `PENDIENTE`

`ReservaTutoriaBuilder` utiliza Fluent API y valida los campos obligatorios antes de crear la reserva.

## Comparación

| Criterio | Factory Method | Builder |
|---|---|---|
| Problema que resuelve | Crear diferentes tipos de notificador sin acoplar la creación a clases concretas. | Construir una reserva con varios datos obligatorios y opcionales. |
| Variabilidad principal | Tipo de producto creado. | Configuración del objeto construido. |
| Participantes | Product, ConcreteProducts, Creator y ConcreteCreators. | Producto y Builder. |
| Ventaja principal | Extensibilidad. | Construcción legible y validada. |
| Costo | Aumenta el número de clases. | Agrega un objeto Builder y métodos de configuración. |
| Cuándo usarlo | Cuando existen variantes de creación que pueden seguir creciendo. | Cuando un constructor empieza a tener demasiados parámetros. |
| Cuándo evitarlo | Cuando existe una sola variante estable. | Cuando el objeto es muy simple y su constructor sigue siendo claro. |

## UML

- `docs/factory-method.puml`
- `docs/builder.puml`
- `docs/modelo-clases.puml`

## Compilar

```bash
mvn clean compile
```

## Ejecutar pruebas

```bash
mvn clean test
```

El proyecto conserva las pruebas del flujo de reservas de Ae1 y agrega pruebas específicas para Factory Method y Builder.

## Ejecutar demostración

Windows:

```powershell
java -cp target\classes edu.uees.tutorias.app.Main
```

Linux/macOS:

```bash
java -cp target/classes edu.uees.tutorias.app.Main
```

## Uso de inteligencia artificial

Para esta actividad utilicé inteligencia artificial como apoyo para revisar la estructura, ordenar la documentación y validar ejemplos de implementación.

Revisé, probé y adapté el contenido generado, y puedo explicar y justificar las clases, patrones y decisiones presentadas.
