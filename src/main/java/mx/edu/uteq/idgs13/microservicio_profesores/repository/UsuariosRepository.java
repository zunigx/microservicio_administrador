package mx.edu.uteq.idgs13.microservicio_profesores.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.uteq.idgs13.microservicio_profesores.entity.UsuariosEntity;
import mx.edu.uteq.idgs13.microservicio_profesores.entity.TipoUsuario;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {
    
    // Buscar usuarios por tipo
    List<UsuariosEntity> findByTipoUsuario(TipoUsuario tipoUsuario);
    
    // Buscar usuarios activos
    List<UsuariosEntity> findByActivo(boolean activo);
    
    // Buscar usuarios por tipo y estado activo
    List<UsuariosEntity> findByTipoUsuarioAndActivo(TipoUsuario tipoUsuario, boolean activo);
    
    // Buscar usuario por email
    UsuariosEntity findByEmail(String email);
    
    // Verificar si existe un email
    boolean existsByEmail(String email);
}