package com.example.goride.modelo.utilidades;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Almacenamiento seguro de contraseñas con PBKDF2 y sal aleatoria por usuario.
 *
 * Formato almacenado: {@code pbkdf2$<iteraciones>$<salHex>$<hashHex>}.
 * Se usa PBKDF2WithHmacSHA1 porque es el único PBKDF2 disponible desde API 24
 * (PBKDF2WithHmacSHA256 exige API 26). Esta clase no depende de Android, por lo
 * que se puede probar en la JVM.
 */
public final class HashContrasena {

    private static final String PREFIJO = "pbkdf2";
    private static final String ALGORITMO = "PBKDF2WithHmacSHA1";
    private static final int ITERACIONES = 60000;
    private static final int BYTES_SAL = 16;
    private static final int BITS_HASH = 160;
    private static final SecureRandom ALEATORIO = new SecureRandom();

    private HashContrasena() {
    }

    /** Genera el valor a guardar en base de datos para una contraseña en claro. */
    public static String generar(String contrasena) {
        if (contrasena == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }
        byte[] sal = new byte[BYTES_SAL];
        ALEATORIO.nextBytes(sal);
        byte[] hash = derivar(contrasena, sal, ITERACIONES);
        return PREFIJO + "$" + ITERACIONES + "$" + aHex(sal) + "$" + aHex(hash);
    }

    /** Indica si el valor almacenado ya tiene el formato de hash (no es texto plano heredado). */
    public static boolean esHash(String almacenado) {
        return analizar(almacenado) != null;
    }

    /**
     * Verifica una contraseña contra el valor almacenado. Un valor con formato inválido
     * (incluido texto plano heredado) nunca verifica: use {@link #esHash} para migrarlo.
     */
    public static boolean verificar(String contrasena, String almacenado) {
        if (contrasena == null) {
            return false;
        }
        String[] partes = analizar(almacenado);
        if (partes == null) {
            return false;
        }
        int iteraciones = Integer.parseInt(partes[1]);
        byte[] sal = deHex(partes[2]);
        byte[] esperado = deHex(partes[3]);
        byte[] calculado = derivar(contrasena, sal, iteraciones);
        return MessageDigest.isEqual(esperado, calculado);
    }

    private static String[] analizar(String almacenado) {
        if (almacenado == null) {
            return null;
        }
        String[] partes = almacenado.split("\\$", -1);
        if (partes.length != 4 || !PREFIJO.equals(partes[0])) {
            return null;
        }
        try {
            int iteraciones = Integer.parseInt(partes[1]);
            if (iteraciones <= 0 || partes[2].isEmpty() || partes[3].isEmpty()
                    || partes[2].length() % 2 != 0 || partes[3].length() % 2 != 0
                    || !partes[2].matches("[0-9a-f]+") || !partes[3].matches("[0-9a-f]+")) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return partes;
    }

    private static byte[] derivar(String contrasena, byte[] sal, int iteraciones) {
        PBEKeySpec spec = new PBEKeySpec(contrasena.toCharArray(), sal, iteraciones, BITS_HASH);
        try {
            return SecretKeyFactory.getInstance(ALGORITMO).generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("PBKDF2 no disponible en esta plataforma", e);
        } finally {
            spec.clearPassword();
        }
    }

    private static String aHex(byte[] datos) {
        StringBuilder sb = new StringBuilder(datos.length * 2);
        for (byte b : datos) {
            sb.append(Character.forDigit((b >> 4) & 0xF, 16)).append(Character.forDigit(b & 0xF, 16));
        }
        return sb.toString();
    }

    private static byte[] deHex(String hex) {
        byte[] salida = new byte[hex.length() / 2];
        for (int i = 0; i < salida.length; i++) {
            salida[i] = (byte) ((Character.digit(hex.charAt(2 * i), 16) << 4)
                    | Character.digit(hex.charAt(2 * i + 1), 16));
        }
        return salida;
    }
}
