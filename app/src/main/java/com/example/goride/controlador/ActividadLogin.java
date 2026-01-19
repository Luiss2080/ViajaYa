package com.example.goride.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goride.R;
import com.example.goride.modelo.entidades.Usuario;
import com.example.goride.modelo.repositorio.RepositorioUsuario;
import com.example.goride.modelo.utilidades.GestorSesion;
import com.example.goride.modelo.utilidades.InicializadorDatos;
import com.example.goride.modelo.utilidades.ValidadorDatos;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Controlador para la pantalla de inicio de sesión moderno
 */
public class ActividadLogin extends AppCompatActivity {

    private TextInputEditText campoUsuario;
    private EditText digitoContrasena1;
    private EditText digitoContrasena2;
    private EditText digitoContrasena3;
    private EditText digitoContrasena4;
    private ImageView iconoMostrarContrasena;
    private Button botonIngresar;
    private boolean contrasenaVisible = false;

    private RepositorioUsuario repositorioUsuario;
    private GestorSesion gestorSesion;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar repositorios
        repositorioUsuario = new RepositorioUsuario(this);
        gestorSesion = new GestorSesion(this);

        // Inicializar datos de prueba
        InicializadorDatos inicializador = new InicializadorDatos(this);
        inicializador.inicializarDatosPrueba();

        // Verificar si ya hay sesión activa
        if (gestorSesion.haySesionActiva()) {
            irAMenuPrincipal();
            return;
        }

        setContentView(R.layout.activity_login);

        inicializarVistas();
        configurarEventos();
    }

    /**
     * Inicializa las vistas
     */
    private void inicializarVistas() {
        campoUsuario = findViewById(R.id.campoUsuario);
        digitoContrasena1 = findViewById(R.id.digitoContrasena1);
        digitoContrasena2 = findViewById(R.id.digitoContrasena2);
        digitoContrasena3 = findViewById(R.id.digitoContrasena3);
        digitoContrasena4 = findViewById(R.id.digitoContrasena4);
        iconoMostrarContrasena = findViewById(R.id.iconoMostrarContrasena);
        botonIngresar = findViewById(R.id.botonIngresar);
    }

    /**
     * Configura los eventos de los componentes
     */
    private void configurarEventos() {
        // Evento del botón simplificado
        botonIngresar.setOnClickListener(v -> {
            // Efecto visual simple
            animarBotonClick(v);
            // Delay mínimo antes de procesar
            handler.postDelayed(this::iniciarSesion, 150);
        });

        // Configurar navegación automática entre cuadros de contraseña
        configurarNavegacionContrasena();

        // Configurar ícono de mostrar/ocultar contraseña
        iconoMostrarContrasena.setOnClickListener(v -> alternarVisibilidadContrasena());

        // Validación en tiempo real para campos
        configurarValidacionTiempoReal();

        // Inicializar estado del botón
        validarCamposYActivarBoton();
    }

    /**
     * Configura la validación en tiempo real de los campos
     */
    private void configurarValidacionTiempoReal() {
        TextWatcher validador = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se requiere implementación para este caso
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No se requiere implementación para este caso
            }

            @Override
            public void afterTextChanged(Editable s) {
                validarCamposYActivarBoton();
            }
        };

        campoUsuario.addTextChangedListener(validador);
        digitoContrasena1.addTextChangedListener(validador);
        digitoContrasena2.addTextChangedListener(validador);
        digitoContrasena3.addTextChangedListener(validador);
        digitoContrasena4.addTextChangedListener(validador);
    }

    /**
     * Configura la navegación automática entre cuadros de contraseña
     */
    private void configurarNavegacionContrasena() {
        // Navegar automáticamente al siguiente cuadro al escribir
        configurarNavegacionDigito(digitoContrasena1, digitoContrasena2, null);
        configurarNavegacionDigito(digitoContrasena2, digitoContrasena3, digitoContrasena1);
        configurarNavegacionDigito(digitoContrasena3, digitoContrasena4, digitoContrasena2);
        configurarNavegacionDigito(digitoContrasena4, null, digitoContrasena3);
    }

    /**
     * Configura la navegación para un dígito específico
     */
    private void configurarNavegacionDigito(EditText actual, EditText siguiente, EditText anterior) {
        actual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1 && siguiente != null) {
                    // Mover al siguiente cuadro
                    siguiente.requestFocus();
                } else if (s.length() == 0 && anterior != null) {
                    // Si se borra, regresar al anterior
                    anterior.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Validar después de cada cambio
                validarCamposYActivarBoton();
            }
        });
    }

    /**
     * Alterna la visibilidad de la contraseña entre números y puntos
     */
    private void alternarVisibilidadContrasena() {
        contrasenaVisible = !contrasenaVisible;

        if (contrasenaVisible) {
            // Mostrar números
            digitoContrasena1.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            digitoContrasena2.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            digitoContrasena3.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            digitoContrasena4.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            iconoMostrarContrasena.setImageResource(R.drawable.ic_eye_show);
        } else {
            // Ocultar con puntos
            digitoContrasena1.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            digitoContrasena2.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            digitoContrasena3.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            digitoContrasena4.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            iconoMostrarContrasena.setImageResource(R.drawable.ic_eye_hide);
        }

        // Restaurar el cursor al final del texto en todos los campos
        digitoContrasena1.setSelection(digitoContrasena1.getText().length());
        digitoContrasena2.setSelection(digitoContrasena2.getText().length());
        digitoContrasena3.setSelection(digitoContrasena3.getText().length());
        digitoContrasena4.setSelection(digitoContrasena4.getText().length());
    }

    /**
     * Valida los campos y activa/desactiva el botón
     */
    private void validarCamposYActivarBoton() {
        String usuario = obtenerTextoSeguro(campoUsuario);
        String contrasena = obtenerContrasenaCompleta();

        boolean camposValidos = !usuario.isEmpty() && contrasena.length() == 4;

        // Habilitar/deshabilitar el botón pero mantener siempre la misma apariencia visual
        botonIngresar.setEnabled(camposValidos);
        // No cambiar la alpha para mantener siempre la misma tonalidad
        botonIngresar.setAlpha(1.0f);
    }

    /**
     * Obtiene la contraseña completa de los 4 cuadros
     */
    private String obtenerContrasenaCompleta() {
        return digitoContrasena1.getText().toString() +
               digitoContrasena2.getText().toString() +
               digitoContrasena3.getText().toString() +
               digitoContrasena4.getText().toString();
    }

    /**
     * Obtiene texto de un campo de forma segura
     */
    private String obtenerTextoSeguro(TextInputEditText campo) {
        if (campo != null && campo.getText() != null) {
            return campo.getText().toString().trim();
        }
        return "";
    }

    /**
     * Procesa el inicio de sesión
     */
    private void iniciarSesion() {
        String nombreUsuario = obtenerTextoSeguro(campoUsuario);
        String contrasena = obtenerContrasenaCompleta();

        // Validar campos vacíos
        if (!ValidadorDatos.esTextoValido(nombreUsuario) || contrasena.length() != 4) {
            mostrarMensaje("Por favor completa todos los campos");
            return;
        }

        // Intentar autenticar
        Usuario usuario = repositorioUsuario.autenticar(nombreUsuario, contrasena);

        if (usuario != null) {
            // Guardar sesión
            gestorSesion.iniciarSesion(
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getNombreCompleto(),
                usuario.getIdRol()
            );

            // Ir al menú principal
            irAMenuPrincipal();
        } else {
            mostrarMensaje("Usuario o contraseña incorrectos");
        }
    }

    /**
     * Navega al menú principal
     */
    private void irAMenuPrincipal() {
        Intent intent = new Intent(this, ActividadMenuPrincipal.class);
        startActivity(intent);
        finish();
    }

    /**
     * Muestra un mensaje Toast
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    /**
     * Animación simple para el click del botón
     */
    private void animarBotonClick(android.view.View boton) {
        // Efecto de escala simple usando ViewPropertyAnimator
        boton.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction(() ->
                    boton.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(100)
                            .start()
                ).start();
    }
}