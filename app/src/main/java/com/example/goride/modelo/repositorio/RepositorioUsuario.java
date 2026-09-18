package com.example.goride.modelo.repositorio;
import android.content.Context;
import com.example.goride.modelo.basedatos.BaseDatosGoRide;
import com.example.goride.modelo.dao.UsuarioDao;
import com.example.goride.modelo.entidades.Usuario;
import java.util.List;
public class RepositorioUsuario {
    private final UsuarioDao usuarioDao;
    public RepositorioUsuario(Context contexto) {
        BaseDatosGoRide baseDatos = BaseDatosGoRide.obtenerInstancia(contexto);
        this.usuarioDao = baseDatos.usuarioDao();
    }
    public long crear(Usuario usuario) {
        return usuarioDao.insertar(usuario);
    }
    public void actualizar(Usuario usuario) {
        usuarioDao.actualizar(usuario);
    }
    public void eliminar(Usuario usuario) {
        usuarioDao.eliminar(usuario);
    }
    public List<Usuario> obtenerTodos() {
        return usuarioDao.obtenerTodos();
    }
    public Usuario obtenerPorId(int idUsuario) {
        return usuarioDao.obtenerPorId(idUsuario);
    }
    public Usuario obtenerPorNombreUsuario(String nombreUsuario) {
        return usuarioDao.obtenerPorNombreUsuario(nombreUsuario);
    }
    public Usuario obtenerPorCorreo(String correo) {
        return usuarioDao.obtenerPorCorreo(correo);
    }
    public List<Usuario> obtenerPorRol(int idRol) {
        return usuarioDao.obtenerPorRol(idRol);
    }
    public List<Usuario> obtenerPorEstado(String estado) {
        return usuarioDao.obtenerPorEstado(estado);
    }
    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioDao.verificarExistenciaUsuario(nombreUsuario) > 0;
    }
    public boolean existeCorreo(String correo) {
        return usuarioDao.verificarExistenciaCorreo(correo) > 0;
    }
}