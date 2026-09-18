package com.example.goride.modelo.utilidades;

/**
 * Utilidad para validar datos de entrada
 */
public class ValidadorDatos {

    /**
     * Valida que un texto no esté vacío
     */
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /**
     * Valida que un nombre de usuario sea válido (mínimo 3 caracteres, sin espacios)
     */
    public static boolean esNombreUsuarioValido(String nombreUsuario) {
        return esTextoValido(nombreUsuario)
                && nombreUsuario.length() >= 3
                && !nombreUsuario.matches(".*\\s.*");
    }

    /**
     * Valida el PIN de acceso: exactamente 4 dígitos, que es lo que admite la pantalla de login.
     */
    public static boolean esPinValido(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    /**
     * Valida que un correo tenga forma usuario@dominio.tld (sin espacios ni varias arrobas)
     */
    public static boolean esCorreoValido(String correo) {
        if (!esTextoValido(correo)) return false;
        return correo.matches("[^@\\s]+@[^@\\s.]+(\\.[^@\\s.]+)+");
    }

    /**
     * Valida que un teléfono tenga solo dígitos, entre 8 y 15
     */
    public static boolean esTelefonoValido(String telefono) {
        if (!esTextoValido(telefono)) return false;
        return telefono.matches("\\d{8,15}");
    }
}
