package com.example.goride.modelo.repositorio;

import android.content.Context;

import com.example.goride.modelo.basedatos.BaseDatosGoRide;
import com.example.goride.modelo.dao.RolDao;
import com.example.goride.modelo.entidades.Rol;

import java.util.List;

public class RepositorioRol {

    private RolDao rolDao;

    public RepositorioRol(Context contexto) {
        BaseDatosGoRide baseDatos = BaseDatosGoRide.obtenerInstancia(contexto);
        this.rolDao = baseDatos.rolDao();
    }

    public long crear(Rol rol) {
        return rolDao.insertar(rol);
    }

    public void actualizar(Rol rol) {
        rolDao.actualizar(rol);
    }

    public void eliminar(Rol rol) {
        rolDao.eliminar(rol);
    }

    public List<Rol> obtenerTodos() {
        return rolDao.obtenerTodos();
    }

    public Rol obtenerPorId(int idRol) {
        return rolDao.obtenerPorId(idRol);
    }

    public Rol obtenerPorNombre(String nombreRol) {
        return rolDao.obtenerPorNombre(nombreRol);
    }

    public List<Rol> obtenerPorEstado(String estado) {
        return rolDao.obtenerPorEstado(estado);
    }

    public boolean existeNombre(String nombreRol) {
        return rolDao.verificarExistenciaNombre(nombreRol) > 0;
    }

    /** Nombre del rol o null si no existe (para decisiones de autorización). */
    public String obtenerNombrePorId(int idRol) {
        Rol rol = rolDao.obtenerPorId(idRol);
        return rol != null ? rol.getNombreRol() : null;
    }
}
