package edu.uees.tutorias.factory;

import edu.uees.tutorias.notification.NotificadorCorreo;
import edu.uees.tutorias.notification.NotificadorPush;
import edu.uees.tutorias.notification.NotificadorSms;
import edu.uees.tutorias.notification.NotificadorWhatsApp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class FactoryMethodTest {

    @Test
    void deberiaCrearNotificadorCorreo() {
        assertInstanceOf(
                NotificadorCorreo.class,
                new CreadorNotificadorCorreo().crearNotificador()
        );
    }

    @Test
    void deberiaCrearNotificadorSms() {
        assertInstanceOf(
                NotificadorSms.class,
                new CreadorNotificadorSms().crearNotificador()
        );
    }

    @Test
    void deberiaCrearNotificadorPush() {
        assertInstanceOf(
                NotificadorPush.class,
                new CreadorNotificadorPush().crearNotificador()
        );
    }

    @Test
    void deberiaCrearVarianteAdicionalWhatsApp() {
        assertInstanceOf(
                NotificadorWhatsApp.class,
                new CreadorNotificadorWhatsApp().crearNotificador()
        );
    }
}
