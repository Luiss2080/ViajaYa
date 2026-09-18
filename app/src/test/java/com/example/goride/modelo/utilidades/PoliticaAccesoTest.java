package com.example.goride.modelo.utilidades;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PoliticaAccesoTest {

    @Test
    public void soloElAdministradorGestionaUsuarios() {
        assertTrue(PoliticaAcceso.puedeGestionarUsuarios("Administrador"));
        assertFalse(PoliticaAcceso.puedeGestionarUsuarios("Cliente"));
        assertFalse(PoliticaAcceso.puedeGestionarUsuarios("Conductor"));
        assertFalse(PoliticaAcceso.puedeGestionarUsuarios(null));
        assertFalse(PoliticaAcceso.puedeGestionarUsuarios("administrador"));
    }

    @Test
    public void nadieSePuedeEliminarASiMismo() {
        assertTrue(PoliticaAcceso.puedeEliminarUsuario("Administrador", 1, 2));
        assertFalse(PoliticaAcceso.puedeEliminarUsuario("Administrador", 1, 1));
        assertFalse(PoliticaAcceso.puedeEliminarUsuario("Cliente", 2, 3));
    }
}
