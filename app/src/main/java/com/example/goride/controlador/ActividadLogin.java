package com.example.goride.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

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
    private TextView mensajeDinamico;
    private CardView cardMensajeDinamico;
    private boolean contrasenaVisible = false;

    private RepositorioUsuario repositorioUsuario;
    private GestorSesion gestorSesion;
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Estados de mensaje
    private static final int MENSAJE_BIENVENIDA = 0;
    private static final int MENSAJE_COMPLETAR = 1;
    private static final int MENSAJE_ERROR = 2;
    private static final int MENSAJE_EXITO = 3;

    private int intentosFallidos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar colores de barras del sistema para consistencia visual
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
            getWindow().setNavigationBarColor(0xFFB91C3C); // Color rojo consistente
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(0); // No light status bar
            }
        }


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
        mensajeDinamico = findViewById(R.id.mensajeDinamico);
        cardMensajeDinamico = findViewById(R.id.cardMensajeDinamico);
    }

    /**
     * Muestra mensajes dinámicos según el estado
     */
    private void mostrarMensajeDinamico(int tipoMensaje, String usuarioIngresado) {
        if (mensajeDinamico == null) return;

        String mensaje;
        int color;

        switch (tipoMensaje) {
            case MENSAJE_BIENVENIDA:
                mensaje = "¡Bienvenido a GoRide! 🚗\nIngresa tus credenciales para continuar";
                color = ContextCompat.getColor(this, android.R.color.darker_gray);
                break;

            case MENSAJE_COMPLETAR:
                mensaje = "Completa todos los campos para continuar";
                color = ContextCompat.getColor(this, android.R.color.holo_orange_dark);
                break;

            case MENSAJE_ERROR:
                intentosFallidos++;
                if (intentosFallidos == 1) {
                    mensaje = "❌ Credenciales incorrectas\nVerifica tu usuario y contraseña";
                } else if (intentosFallidos == 2) {
                    mensaje = "❌ Intento fallido nuevamente\n¿Olvidaste tu contraseña?";
                } else {
                    mensaje = "❌ Múltiples intentos fallidos\nRevisa las credenciales en la documentación";
                }
                color = ContextCompat.getColor(this, android.R.color.holo_red_dark);
                break;

            case MENSAJE_EXITO:
                if (usuarioIngresado != null) {
                    mensaje = "✅ ¡Bienvenido, " + usuarioIngresado + "!\nIngresando al sistema...";
                } else {
                    mensaje = "✅ Acceso autorizado\nIngresando al sistema...";
                }
                color = ContextCompat.getColor(this, android.R.color.holo_green_dark);
                intentosFallidos = 0; // Resetear contador
                break;

            default:
                mensaje = "Ingresa tus credenciales";
                color = ContextCompat.getColor(this, android.R.color.darker_gray);
                break;
        }

        mensajeDinamico.setText(mensaje);
        mensajeDinamico.setTextColor(color);
        mensajeDinamico.setVisibility(View.VISIBLE);

        // Animación suave de aparición
        mensajeDinamico.setAlpha(0f);
        mensajeDinamico.animate()
                .alpha(1f)
                .setDuration(300)
                .start();
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

        // Solo habilitar/deshabilitar funcionalidad, mantener apariencia visual consistente
        botonIngresar.setEnabled(camposValidos);
        botonIngresar.setAlpha(1.0f); // Siempre completamente opaco
        botonIngresar.setVisibility(android.view.View.VISIBLE); // Asegurar que sea visible
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
     * Muestra un mensaje dinámico según el estado de la aplicación
     */
    private void mostrarMensajeDinamico(int tipoMensaje, String nombreCompleto) {
        String mensaje;
        int colorFondo;
        int colorTexto;

        switch (tipoMensaje) {
            case MENSAJE_BIENVENIDA:
                mensaje = "¡Bienvenido a GoRide! 🚗\nIngresa tus credenciales para continuar";
                colorFondo = 0xFFF8F9FA;
                colorTexto = 0xFF6B7280;
                break;

            case MENSAJE_COMPLETAR:
                mensaje = "⚠️ Por favor completa todos los campos\nUsuario y los 4 dígitos de la contraseña";
                colorFondo = 0xFFFEF3C7;
                colorTexto = 0xFF92400E;
                break;

            case MENSAJE_ERROR:
                String mensajeBase = "❌ Usuario o contraseña incorrectos\n";
                if (intentosFallidos == 1) {
                    mensaje = mensajeBase + "Revisa las credenciales en la carpeta docs/CREDENCIALES.md";
                } else if (intentosFallidos >= 2) {
                    mensaje = mensajeBase + "¿Necesitas ayuda? Verifica: admin/1234, conductor/5678, pasajero/9999";
                } else {
                    mensaje = mensajeBase + "Intenta de nuevo";
                }
                colorFondo = 0xFFFEE2E2;
                colorTexto = 0xFFDC2626;
                break;

            case MENSAJE_EXITO:
                mensaje = "✅ ¡Bienvenido " + (nombreCompleto != null ? nombreCompleto : "Usuario") + "!\n" +
                         "Acceso concedido, redirigiendo...";
                colorFondo = 0xFFDCFCE7;
                colorTexto = 0xFF065F46;
                break;

            default:
                return;
        }

        // Configurar el mensaje
        mensajeDinamico.setText(mensaje);
        mensajeDinamico.setTextColor(colorTexto);
        cardMensajeDinamico.setCardBackgroundColor(colorFondo);

        // Mostrar con animación
        cardMensajeDinamico.setVisibility(View.VISIBLE);
        cardMensajeDinamico.setAlpha(0f);
        cardMensajeDinamico.animate()
                .alpha(1f)
                .setDuration(300)
                .start();

        // Auto-ocultar después de cierto tiempo (excepto para éxito que se mantiene)
        if (tipoMensaje != MENSAJE_EXITO) {
            handler.postDelayed(() -> {
                if (cardMensajeDinamico.getVisibility() == View.VISIBLE) {
                    cardMensajeDinamico.animate()
                            .alpha(0f)
                            .setDuration(300)
                            .withEndAction(() -> cardMensajeDinamico.setVisibility(View.GONE))
                            .start();
                }
            }, 4000);
        }
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