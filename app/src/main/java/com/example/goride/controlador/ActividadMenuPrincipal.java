package com.example.goride.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goride.R;
import com.example.goride.controlador.usuario.ActividadListaUsuarios;
import com.example.goride.modelo.repositorio.RepositorioRol;
import com.example.goride.modelo.utilidades.GestorSesion;
import com.example.goride.modelo.utilidades.PoliticaAcceso;

/**
 * Controlador para el menú principal de la aplicación
 */
public class ActividadMenuPrincipal extends AppCompatActivity {

    private TextView textoBienvenida;
    private Button botonGestionUsuarios;
    private Button botonGestionRoles;
    private Button botonGestionConductores;
    private Button botonGestionViajes;
    private Button botonGestionServicios;
    private Button botonCerrarSesion;

    private GestorSesion gestorSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        gestorSesion = new GestorSesion(this);

        // Verificar sesión
        if (!gestorSesion.haySesionActiva()) {
            volverAlLogin();
            return;
        }

        inicializarVistas();
        configurarEventos();
        mostrarBienvenida();
    }

    /**
     * Inicializa las vistas
     */
    private void inicializarVistas() {
        textoBienvenida = findViewById(R.id.textoBienvenida);
        botonGestionUsuarios = findViewById(R.id.botonGestionUsuarios);
        botonGestionRoles = findViewById(R.id.botonGestionRoles);
        botonGestionConductores = findViewById(R.id.botonGestionConductores);
        botonGestionViajes = findViewById(R.id.botonGestionViajes);
        botonGestionServicios = findViewById(R.id.botonGestionServicios);
        botonCerrarSesion = findViewById(R.id.botonCerrarSesion);
    }

    /**
     * Configura los eventos de los botones
     */
    private void configurarEventos() {
        botonGestionUsuarios.setOnClickListener(v -> abrirGestionUsuarios());
        botonGestionRoles.setOnClickListener(v -> abrirGestionRoles());
        botonGestionConductores.setOnClickListener(v -> abrirGestionConductores());
        botonGestionViajes.setOnClickListener(v -> abrirGestionViajes());
        botonGestionServicios.setOnClickListener(v -> abrirGestionServicios());
        botonCerrarSesion.setOnClickListener(v -> cerrarSesion());
    }

    /**
     * Muestra el mensaje de bienvenida
     */
    private void mostrarBienvenida() {
        String nombreCompleto = gestorSesion.obtenerNombreCompleto();
        String mensaje = getString(R.string.bienvenido) + ", " + nombreCompleto;
        textoBienvenida.setText(mensaje);
    }

    /**
     * Abre la gestión de usuarios
     */
    private void abrirGestionUsuarios() {
        String rol = new RepositorioRol(this).obtenerNombrePorId(gestorSesion.obtenerIdRol());
        if (!PoliticaAcceso.puedeGestionarUsuarios(rol)) {
            Toast.makeText(this, "No tienes permiso para gestionar usuarios", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ActividadListaUsuarios.class);
        startActivity(intent);
    }

    /**
     * Abre la gestión de roles
     */
    private void abrirGestionRoles() {
        // TODO: Implementar
    }

    /**
     * Abre la gestión de conductores
     */
    private void abrirGestionConductores() {
        // TODO: Implementar
    }

    /**
     * Abre la gestión de viajes
     */
    private void abrirGestionViajes() {
        // TODO: Implementar
    }

    /**
     * Abre la gestión de servicios
     */
    private void abrirGestionServicios() {
        // TODO: Implementar
    }

    /**
     * Cierra la sesión del usuario
     */
    private void cerrarSesion() {
        gestorSesion.cerrarSesion();
        Toast.makeText(this, "Sesión cerrada exitosamente", Toast.LENGTH_SHORT).show();
        volverAlLogin();
    }

    /**
     * Vuelve a la pantalla de login
     */
    private void volverAlLogin() {
        Intent intent = new Intent(this, ActividadLogin.class);
        startActivity(intent);
        finish();
    }
}

