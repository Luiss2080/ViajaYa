package com.example.goride.modelo.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.goride.modelo.entidades.Usuario;
import java.util.List;
@Dao
public interface UsuarioDao {
    @Insert
    long insertar(Usuario usuario);
    @Update
    void actualizar(Usuario usuario);
    @Delete
    void eliminar(Usuario usuario);
    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerTodos();
    @Query("SELECT * FROM usuarios WHERE id_usuario = :idUsuario")
    Usuario obtenerPorId(int idUsuario);
    @Query("SELECT * FROM usuarios WHERE nombre_usuario = :nombreUsuario")
    Usuario obtenerPorNombreUsuario(String nombreUsuario);
    @Query("SELECT * FROM usuarios WHERE correo_electronico = :correo")
    Usuario obtenerPorCorreo(String correo);
    @Query("SELECT * FROM usuarios WHERE id_rol = :idRol")
    List<Usuario> obtenerPorRol(int idRol);
    @Query("SELECT * FROM usuarios WHERE estado = :estado")
    List<Usuario> obtenerPorEstado(String estado);
    @Query("SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = :nombreUsuario")
    int verificarExistenciaUsuario(String nombreUsuario);
    @Query("SELECT COUNT(*) FROM usuarios WHERE correo_electronico = :correo")
    int verificarExistenciaCorreo(String correo);
}