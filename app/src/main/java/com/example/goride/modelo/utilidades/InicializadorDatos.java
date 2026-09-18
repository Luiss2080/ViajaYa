package com.example.goride.modelo.utilidades;

import android.content.Context;

import com.example.goride.modelo.entidades.Conductor;
import com.example.goride.modelo.entidades.Rol;
import com.example.goride.modelo.entidades.Servicio;
import com.example.goride.modelo.entidades.Usuario;
import com.example.goride.modelo.entidades.Viaje;
import com.example.goride.modelo.repositorio.RepositorioConductor;
import com.example.goride.modelo.repositorio.RepositorioRol;
import com.example.goride.modelo.repositorio.RepositorioServicio;
import com.example.goride.modelo.repositorio.RepositorioUsuario;
import com.example.goride.modelo.repositorio.RepositorioViaje;

public class InicializadorDatos {

    private Context contexto;
    private RepositorioRol repositorioRol;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioConductor repositorioConductor;
    private RepositorioServicio repositorioServicio;
    private RepositorioViaje repositorioViaje;

    public InicializadorDatos(Context contexto) {
        this.contexto = contexto;
        this.repositorioRol = new RepositorioRol(contexto);
        this.repositorioUsuario = new RepositorioUsuario(contexto);
        this.repositorioConductor = new RepositorioConductor(contexto);
        this.repositorioServicio = new RepositorioServicio(contexto);
        this.repositorioViaje = new RepositorioViaje(contexto);
    }

    public void inicializarDatosPrueba() {
        if (repositorioRol.obtenerTodos().size() > 0) {
            return;
        }

        inicializarRoles();
        inicializarUsuarios();
        inicializarServicios();
        inicializarConductores();
        inicializarViajes();
    }

    private void inicializarRoles() {
        repositorioRol.crear(new Rol("Administrador", "Usuario con acceso total al sistema", "Activo"));
        repositorioRol.crear(new Rol("Cliente", "Usuario que solicita viajes", "Activo"));
        repositorioRol.crear(new Rol("Conductor", "Usuario que ofrece servicios de transporte", "Activo"));
    }

    private void inicializarUsuarios() {
        repositorioUsuario.crear(new Usuario("admin", HashContrasena.generar("1234"), "Administrador del Sistema",
            "admin@goride.com", "3001234567", 1, "Activo", UtilidadesFecha.obtenerFechaActual()));
        repositorioUsuario.crear(new Usuario("juan_perez", HashContrasena.generar("5678"), "Juan Pérez García",
            "juan.perez@correo.com", "3101234567", 2, "Activo", UtilidadesFecha.obtenerFechaActual()));
        repositorioUsuario.crear(new Usuario("maria_lopez", HashContrasena.generar("5678"), "María López Ramírez",
            "maria.lopez@correo.com", "3201234567", 2, "Activo", UtilidadesFecha.obtenerFechaActual()));
        repositorioUsuario.crear(new Usuario("carlos_driver", HashContrasena.generar("9999"), "Carlos Rodríguez Mora",
            "carlos.rodriguez@correo.com", "3151234567", 3, "Activo", UtilidadesFecha.obtenerFechaActual()));
        repositorioUsuario.crear(new Usuario("ana_driver", HashContrasena.generar("9999"), "Ana Martínez Soto",
            "ana.martinez@correo.com", "3181234567", 3, "Activo", UtilidadesFecha.obtenerFechaActual()));
    }

    private void inicializarServicios() {
        repositorioServicio.crear(new Servicio("GoRide Económico",
            "Servicio de transporte básico a bajo costo", 5000.0, 1500.0, "Activo"));
        repositorioServicio.crear(new Servicio("GoRide Confort",
            "Servicio de transporte con mayor comodidad", 8000.0, 2000.0, "Activo"));
        repositorioServicio.crear(new Servicio("GoRide Premium",
            "Servicio de transporte de lujo con vehículos exclusivos", 15000.0, 3500.0, "Activo"));
        repositorioServicio.crear(new Servicio("GoRide Compartido",
            "Servicio de transporte compartido con otros pasajeros", 3000.0, 1000.0, "Activo"));
    }

    private void inicializarConductores() {
        repositorioConductor.crear(new Conductor(4, "LIC123456", "ABC123",
            "Chevrolet Spark 2020", "Blanco", 2020, "Activo"));
        repositorioConductor.crear(new Conductor(5, "LIC789012", "XYZ789",
            "Mazda 3 2021", "Gris", 2021, "Activo"));
    }

    private void inicializarViajes() {
        repositorioViaje.crear(new Viaje(2, 1, 1, "Calle 10 # 20-30, Centro",
            "Carrera 50 # 80-90, Norte", "15/01/2026", "08:30", "Completado", 12500.0, 5.0));
        repositorioViaje.crear(new Viaje(3, 2, 2, "Avenida 68 # 45-12",
            "Calle 127 # 15-20", "15/01/2026", "14:00", "Completado", 18000.0, 5.0));
        repositorioViaje.crear(new Viaje(2, 1, 1, "Centro Comercial Plaza",
            "Universidad Nacional", "15/01/2026", "16:45", "En Curso", 11000.0, 4.0));
        repositorioViaje.crear(new Viaje(3, 2, 3, "Aeropuerto El Dorado",
            "Hotel Zona T", "15/01/2026", "18:00", "Pendiente", 26500.0, 3.5));
    }
}

