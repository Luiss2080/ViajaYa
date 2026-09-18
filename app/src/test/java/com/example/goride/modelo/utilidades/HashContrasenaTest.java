package com.example.goride.modelo.utilidades;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HashContrasenaTest {

    @Test
    public void hashNoContieneLaContrasenaEnClaro() {
        String h = HashContrasena.generar("1234");
        assertFalse(h.contains("1234"));
        assertTrue(h.startsWith("pbkdf2$"));
    }

    @Test
    public void verificaContrasenaCorrecta() {
        String h = HashContrasena.generar("4321");
        assertTrue(HashContrasena.verificar("4321", h));
    }

    @Test
    public void rechazaContrasenaIncorrecta() {
        String h = HashContrasena.generar("4321");
        assertFalse(HashContrasena.verificar("4322", h));
        assertFalse(HashContrasena.verificar("", h));
        assertFalse(HashContrasena.verificar(null, h));
    }

    @Test
    public void mismaContrasenaGeneraHashesDistintosPorLaSal() {
        assertNotEquals(HashContrasena.generar("1234"), HashContrasena.generar("1234"));
    }

    @Test
    public void textoPlanoHeredadoNoEsHashNiVerifica() {
        assertFalse(HashContrasena.esHash("1234"));
        assertFalse(HashContrasena.verificar("1234", "1234"));
        assertFalse(HashContrasena.esHash(null));
        assertFalse(HashContrasena.verificar("1234", null));
    }

    @Test
    public void valoresMalFormadosNoLanzanExcepcion() {
        assertFalse(HashContrasena.verificar("1234", "pbkdf2$abc$00$00"));
        assertFalse(HashContrasena.verificar("1234", "pbkdf2$1000$zz$00"));
        assertFalse(HashContrasena.verificar("1234", "pbkdf2$1000$0$00"));
        assertFalse(HashContrasena.verificar("1234", "pbkdf2$0$00$00"));
        assertFalse(HashContrasena.verificar("1234", "otro$1000$00$00"));
    }

    @Test
    public void esHashReconoceElFormatoGenerado() {
        assertTrue(HashContrasena.esHash(HashContrasena.generar("0000")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void generarConNuloFalla() {
        HashContrasena.generar(null);
    }

    @Test
    public void soportaCaracteresNoAscii() {
        String h = HashContrasena.generar("contraseña-ñ");
        assertTrue(HashContrasena.verificar("contraseña-ñ", h));
        assertEquals(4, h.split("\\$").length);
    }
}
