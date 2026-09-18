package com.example.goride.modelo.utilidades;

/**
 * Reglas de autorización por rol. Se aplican en los controladores al abrir cada
 * pantalla, no solo ocultando botones en la interfaz.
 */
public final class PoliticaAcceso {

    public static final String ROL_ADMINISTRADOR = "Administrador";

    private PoliticaAcceso() {
    }

    public static boolean esAdministrador(String nombreRol) {
        return ROL_ADMINISTRADOR.equals(nombreRol);
    }

    /** La gestión de usuarios es exclusiva del administrador. */
    public static boolean puedeGestionarUsuarios(String nombreRol) {
        return esAdministrador(nombreRol);
    }

    /** Un usuario no puede eliminar su propia cuenta (evita quedarse sin acceso). */
    public static boolean puedeEliminarUsuario(String nombreRolActual, int idUsuarioActual, int idUsuarioObjetivo) {
        return puedeGestionarUsuarios(nombreRolActual) && idUsuarioActual != idUsuarioObjetivo;
    }
}
