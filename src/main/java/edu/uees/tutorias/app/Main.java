package edu.uees.tutorias.app;

import edu.uees.tutorias.builder.ReservaTutoriaBuilder;
import edu.uees.tutorias.domain.Docente;
import edu.uees.tutorias.domain.Estudiante;
import edu.uees.tutorias.domain.HorarioDisponible;
import edu.uees.tutorias.domain.ModalidadTutoria;
import edu.uees.tutorias.domain.ReservaTutoria;
import edu.uees.tutorias.factory.CreadorNotificador;
import edu.uees.tutorias.factory.CreadorNotificadorCorreo;
import edu.uees.tutorias.factory.CreadorNotificadorPush;
import edu.uees.tutorias.factory.CreadorNotificadorSms;
import edu.uees.tutorias.factory.CreadorNotificadorWhatsApp;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        Estudiante estudiante = new Estudiante(
                "ST-001",
                "Kevin Aguilar",
                "student@uees.edu.ec"
        );

        Docente docente = new Docente(
                "TE-001",
                "Jaime Sayago",
                "teacher@uees.edu.ec"
        );


        System.out.println("=== FACTORY METHOD ===");

        CreadorNotificador[] creadores = {
                new CreadorNotificadorCorreo(),
                new CreadorNotificadorSms(),
                new CreadorNotificadorPush(),
                new CreadorNotificadorWhatsApp()
        };

        for (CreadorNotificador creador : creadores) {
            creador.notificar(estudiante, "Tu tutoría fue confirmada.");
        }


        System.out.println();
        System.out.println("=== BUILDER ===");

        HorarioDisponible horarioBasico = new HorarioDisponible(
                "SLOT-001",
                LocalDateTime.of(2026, 9, 2, 10, 0),
                LocalDateTime.of(2026, 9, 2, 11, 0)
        );

        ReservaTutoria reservaBasica = new ReservaTutoriaBuilder()
                .conId("R-001")
                .paraEstudiante(estudiante)
                .conDocente(docente)
                .enHorario(horarioBasico)
                .construir();

        System.out.println(
                "Reserva básica: " + reservaBasica.getId()
                        + " | modalidad=" + reservaBasica.getModalidad()
                        + " | duración=" + reservaBasica.getDuracionMinutos()
                        + " | estado=" + reservaBasica.getEstado()
        );


        HorarioDisponible horarioCompleto = new HorarioDisponible(
                "SLOT-002",
                LocalDateTime.of(2026, 9, 3, 15, 0),
                LocalDateTime.of(2026, 9, 3, 16, 30)
        );

        ReservaTutoria reservaCompleta = new ReservaTutoriaBuilder()
                .conId("R-002")
                .paraEstudiante(estudiante)
                .conDocente(docente)
                .enHorario(horarioCompleto)
                .paraAsignatura("Diseño de Software")
                .conModalidad(ModalidadTutoria.ONLINE)
                .conEnlaceReunion("https://meet.example.com/tutoria-002")
                .conNotas("Revisar Factory Method y Builder")
                .conDuracionMinutos(90)
                .construir();

        System.out.println(
                "Reserva completa: " + reservaCompleta.getId()
                        + " | asignatura=" + reservaCompleta.getAsignatura()
                        + " | modalidad=" + reservaCompleta.getModalidad()
                        + " | duración=" + reservaCompleta.getDuracionMinutos()
                        + " | estado=" + reservaCompleta.getEstado()
        );
    }
}
