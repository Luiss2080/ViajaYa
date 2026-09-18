package com.example.goride.modelo.utilidades;

import static org.junit.Assert.assertEquals;

import com.example.goride.modelo.entidades.Servicio;

import org.junit.Test;

public class CalculadoraTarifaTest {

    private static Servicio servicio(double base, double porKm) {
        return new Servicio("Test", "desc", base, porKm, "Activo");
    }

    @Test
    public void tarifaEsBaseMasKilometros() {
        assertEquals(5000.0 + 5 * 1500.0, CalculadoraTarifa.calcularTarifa(servicio(5000, 1500), 5), 0.0001);
    }

    @Test
    public void distanciaCeroCobraSoloLaBase() {
        assertEquals(3000.0, CalculadoraTarifa.calcularTarifa(servicio(3000, 1000), 0), 0.0001);
    }

    @Test
    public void servicioNuloODistanciaInvalidaDaCero() {
        assertEquals(0.0, CalculadoraTarifa.calcularTarifa(null, 5), 0.0);
        assertEquals(0.0, CalculadoraTarifa.calcularTarifa(servicio(5000, 1500), -1), 0.0);
        assertEquals(0.0, CalculadoraTarifa.calcularTarifa(servicio(5000, 1500), Double.NaN), 0.0);
        assertEquals(0.0, CalculadoraTarifa.calcularTarifa(servicio(5000, 1500), Double.POSITIVE_INFINITY), 0.0);
    }

    @Test
    public void preciosNegativosNuncaGeneranTarifaNegativa() {
        assertEquals(0.0, CalculadoraTarifa.calcularTarifa(servicio(-100, -50), 10), 0.0);
        assertEquals(100.0, CalculadoraTarifa.calcularTarifa(servicio(100, -50), 10), 0.0001);
    }

    @Test
    public void redondeaADosDecimales() {
        assertEquals(10.34, CalculadoraTarifa.calcularTarifaRedondeada(servicio(0, 3.4466), 3), 0.0001);
    }

    @Test
    public void formatoNoDependeDelIdiomaDelDispositivo() {
        java.util.Locale anterior = java.util.Locale.getDefault();
        try {
            java.util.Locale.setDefault(new java.util.Locale("es", "ES"));
            assertEquals("$1234.50", CalculadoraTarifa.formatearTarifa(1234.5));
        } finally {
            java.util.Locale.setDefault(anterior);
        }
    }
}
