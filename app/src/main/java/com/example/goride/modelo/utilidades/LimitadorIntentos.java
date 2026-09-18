package com.example.goride.modelo.utilidades;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Bloqueo temporal tras varios intentos de login fallidos por usuario.
 * Es necesario porque la contraseña es un PIN de 4 dígitos (10.000 combinaciones).
 * El estado vive en memoria: se reinicia al cerrar el proceso de la app (limitación conocida).
 */
public class LimitadorIntentos {

    private final int maxIntentos;
    private final long duracionBloqueoMs;
    private final Map<String, Integer> fallos = new HashMap<>();
    private final Map<String, Long> bloqueadoHasta = new HashMap<>();

    public LimitadorIntentos(int maxIntentos, long duracionBloqueoMs) {
        if (maxIntentos < 1 || duracionBloqueoMs < 1) {
            throw new IllegalArgumentException("Parámetros de bloqueo inválidos");
        }
        this.maxIntentos = maxIntentos;
        this.duracionBloqueoMs = duracionBloqueoMs;
    }

    private static String clave(String usuario) {
        return usuario == null ? "" : usuario.trim().toLowerCase(Locale.ROOT);
    }

    /** Milisegundos restantes de bloqueo (0 si puede intentar). */
    public synchronized long msRestantes(String usuario, long ahoraMs) {
        String k = clave(usuario);
        Long hasta = bloqueadoHasta.get(k);
        if (hasta == null) {
            return 0;
        }
        if (ahoraMs >= hasta) {
            bloqueadoHasta.remove(k);
            fallos.remove(k);
            return 0;
        }
        return hasta - ahoraMs;
    }

    public synchronized boolean estaBloqueado(String usuario, long ahoraMs) {
        return msRestantes(usuario, ahoraMs) > 0;
    }

    /** Registra un fallo; devuelve true si este fallo provoca el bloqueo. */
    public synchronized boolean registrarFallo(String usuario, long ahoraMs) {
        String k = clave(usuario);
        int total = fallos.containsKey(k) ? fallos.get(k) + 1 : 1;
        if (total >= maxIntentos) {
            bloqueadoHasta.put(k, ahoraMs + duracionBloqueoMs);
            return true;
        }
        fallos.put(k, total);
        return false;
    }

    public synchronized void registrarExito(String usuario) {
        String k = clave(usuario);
        fallos.remove(k);
        bloqueadoHasta.remove(k);
    }
}
