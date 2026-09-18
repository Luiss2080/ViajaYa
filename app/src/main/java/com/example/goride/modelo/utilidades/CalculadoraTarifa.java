package com.example.goride.modelo.utilidades;

import com.example.goride.modelo.entidades.Servicio;

public class CalculadoraTarifa {

    public static double calcularTarifa(Servicio servicio, double distanciaKm) {
        // NaN e infinito no cumplen "< 0" y producirían una tarifa NaN/Infinity
        if (servicio == null || Double.isNaN(distanciaKm) || Double.isInfinite(distanciaKm) || distanciaKm < 0) {
            return 0.0;
        }
        double precioBase = servicio.getPrecioBase();
        double precioPorKm = servicio.getPrecioPorKilometro();
        // Un servicio mal configurado (precios negativos) nunca debe dar una tarifa negativa
        double tarifa = Math.max(0.0, precioBase) + distanciaKm * Math.max(0.0, precioPorKm);
        return Double.isFinite(tarifa) ? tarifa : 0.0;
    }

    public static double calcularTarifaRedondeada(Servicio servicio, double distanciaKm) {
        double tarifa = calcularTarifa(servicio, distanciaKm);
        return Math.round(tarifa * 100.0) / 100.0;
    }

    public static String formatearTarifa(double tarifa) {
        return String.format(java.util.Locale.US, "$%.2f", tarifa);
    }
}

