package com.example.goride.modelo.utilidades;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidadorDatosTest {

    @Test
    public void textoVacioONuloNoEsValido() {
        assertFalse(ValidadorDatos.esTextoValido(null));
        assertFalse(ValidadorDatos.esTextoValido("   "));
        assertTrue(ValidadorDatos.esTextoValido(" a "));
    }

    @Test
    public void pinDebeTenerExactamenteCuatroDigitos() {
        assertTrue(ValidadorDatos.esPinValido("0000"));
        assertTrue(ValidadorDatos.esPinValido("1234"));
        assertFalse(ValidadorDatos.esPinValido("123"));
        assertFalse(ValidadorDatos.esPinValido("12345"));
        assertFalse(ValidadorDatos.esPinValido("12a4"));
        assertFalse(ValidadorDatos.esPinValido("12 4"));
        assertFalse(ValidadorDatos.esPinValido("admin123"));
        assertFalse(ValidadorDatos.esPinValido(null));
    }

    @Test
    public void nombreDeUsuario() {
        assertTrue(ValidadorDatos.esNombreUsuarioValido("juan_perez"));
        assertFalse(ValidadorDatos.esNombreUsuarioValido("ab"));
        assertFalse(ValidadorDatos.esNombreUsuarioValido("juan perez"));
        assertFalse(ValidadorDatos.esNombreUsuarioValido(null));
    }

    @Test
    public void correo() {
        assertTrue(ValidadorDatos.esCorreoValido("a@b.com"));
        assertTrue(ValidadorDatos.esCorreoValido("juan.perez@correo.com.bo"));
        assertFalse(ValidadorDatos.esCorreoValido("@."));
        assertFalse(ValidadorDatos.esCorreoValido("a@b"));
        assertFalse(ValidadorDatos.esCorreoValido("a b@c.com"));
        assertFalse(ValidadorDatos.esCorreoValido("a@@b.com"));
        assertFalse(ValidadorDatos.esCorreoValido(".@a"));
        assertFalse(ValidadorDatos.esCorreoValido(null));
    }

    @Test
    public void telefono() {
        assertTrue(ValidadorDatos.esTelefonoValido("71234567"));
        assertTrue(ValidadorDatos.esTelefonoValido("3001234567"));
        assertFalse(ValidadorDatos.esTelefonoValido("1234567"));
        assertFalse(ValidadorDatos.esTelefonoValido("7123-4567"));
        assertFalse(ValidadorDatos.esTelefonoValido("1234567890123456"));
        assertFalse(ValidadorDatos.esTelefonoValido(null));
    }
}
