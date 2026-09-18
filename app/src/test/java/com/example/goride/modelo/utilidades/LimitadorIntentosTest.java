package com.example.goride.modelo.utilidades;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LimitadorIntentosTest {

    @Test
    public void bloqueaTrasElMaximoDeFallosYDesbloqueaConElTiempo() {
        LimitadorIntentos l = new LimitadorIntentos(3, 1000);
        assertFalse(l.registrarFallo("admin", 0));
        assertFalse(l.registrarFallo("admin", 10));
        assertFalse(l.estaBloqueado("admin", 20));
        assertTrue(l.registrarFallo("admin", 20));
        assertTrue(l.estaBloqueado("admin", 500));
        assertEquals(520, l.msRestantes("admin", 500));
        assertFalse(l.estaBloqueado("admin", 1020));
    }

    @Test
    public void despuesDelBloqueoElContadorEmpiezaDeNuevo() {
        LimitadorIntentos l = new LimitadorIntentos(2, 100);
        l.registrarFallo("a", 0);
        assertTrue(l.registrarFallo("a", 1));
        assertFalse(l.estaBloqueado("a", 200));
        assertFalse(l.registrarFallo("a", 200));
    }

    @Test
    public void exitoReiniciaElContador() {
        LimitadorIntentos l = new LimitadorIntentos(3, 1000);
        l.registrarFallo("admin", 0);
        l.registrarFallo("admin", 1);
        l.registrarExito("admin");
        assertFalse(l.registrarFallo("admin", 2));
        assertFalse(l.registrarFallo("admin", 3));
    }

    @Test
    public void usuariosIndependientesYSinDistinguirMayusculas() {
        LimitadorIntentos l = new LimitadorIntentos(1, 1000);
        assertTrue(l.registrarFallo("Admin", 0));
        assertTrue(l.estaBloqueado("admin ", 1));
        assertFalse(l.estaBloqueado("otro", 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rechazaParametrosInvalidos() {
        new LimitadorIntentos(0, 1000);
    }
}
