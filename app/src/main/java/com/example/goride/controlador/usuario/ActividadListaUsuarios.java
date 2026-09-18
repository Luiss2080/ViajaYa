package com.example.goride.controlador.usuario;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goride.R;
import com.example.goride.modelo.entidades.Usuario;
import com.example.goride.modelo.repositorio.RepositorioRol;
import com.example.goride.modelo.repositorio.RepositorioUsuario;
import com.example.goride.modelo.utilidades.GestorSesion;
import com.example.goride.modelo.utilidades.PoliticaAcceso;
import com.example.goride.vista.adaptadores.usuario.AdaptadorUsuario;

import java.util.List;

/**
 * Actividad para listar usuarios
 */
public class ActividadListaUsuarios extends AppCompatActivity implements AdaptadorUsuario.EventosUsuario {

    private RecyclerView listaUsuarios;
    private RepositorioUsuario repositorioUsuario;
    private GestorSesion gestorSesion;
    private String rolActual;
    private boolean autorizado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestorSesion = new GestorSesion(this);
        rolActual = new RepositorioRol(this).obtenerNombrePorId(gestorSesion.obtenerIdRol());
        if (!gestorSesion.haySesionActiva() || !PoliticaAcceso.puedeGestionarUsuarios(rolActual)) {
            Toast.makeText(this, "No tienes permiso para gestionar usuarios", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        autorizado = true;
        setContentView(R.layout.activity_lista_usuarios);

        repositorioUsuario = new RepositorioUsuario(this);

        listaUsuarios = findViewById(R.id.listaUsuarios);
        listaUsuarios.setLayoutManager(new LinearLayoutManager(this));

        Button botonCrear = findViewById(R.id.botonCrear);
        botonCrear.setOnClickListener(v -> abrirFormularioCrear());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autorizado) {
            cargarUsuarios();
        }
    }

    /**
     * Carga la lista de usuarios
     */
    private void cargarUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.obtenerTodos();
        AdaptadorUsuario adaptador = new AdaptadorUsuario(usuarios, this);
        listaUsuarios.setAdapter(adaptador);
    }

    /**
     * Abre el formulario para crear usuario
     */
    private void abrirFormularioCrear() {
        Intent intent = new Intent(this, ActividadFormularioUsuario.class);
        startActivity(intent);
    }

    /**
     * Abre el formulario para editar usuario
     */
    @Override
    public void alEditarUsuario(Usuario usuario) {
        Intent intent = new Intent(this, ActividadFormularioUsuario.class);
        intent.putExtra("idUsuario", usuario.getIdUsuario());
        startActivity(intent);
    }

    /**
     * Elimina un usuario
     */
    @Override
    public void alEliminarUsuario(Usuario usuario) {
        if (!PoliticaAcceso.puedeEliminarUsuario(rolActual, gestorSesion.obtenerIdUsuario(), usuario.getIdUsuario())) {
            mostrarMensaje("No puedes eliminar tu propia cuenta");
            return;
        }
        new AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage(getString(R.string.mensaje_confirmacion_eliminar))
            .setPositiveButton(getString(R.string.si), (dialog, which) -> {
                try {
                    repositorioUsuario.eliminar(usuario);
                    mostrarMensaje(getString(R.string.mensaje_exito_eliminar));
                } catch (SQLiteConstraintException e) {
                    // Tiene conductor o viajes asociados (clave foránea RESTRICT)
                    mostrarMensaje("No se puede eliminar: el usuario tiene registros asociados");
                }
                cargarUsuarios();
            })
            .setNegativeButton(getString(R.string.no), null)
            .show();
    }

    /**
     * Muestra un mensaje Toast
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}

