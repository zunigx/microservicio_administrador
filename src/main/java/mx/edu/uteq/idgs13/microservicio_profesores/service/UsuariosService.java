package mx.edu.uteq.idgs13.microservicio_profesores.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uteq.idgs13.microservicio_profesores.dto.UsuarioViewDto;
import mx.edu.uteq.idgs13.microservicio_profesores.dto.UsuarioEditDto;
import mx.edu.uteq.idgs13.microservicio_profesores.entity.UsuariosEntity;
import mx.edu.uteq.idgs13.microservicio_profesores.entity.TipoUsuario;
import mx.edu.uteq.idgs13.microservicio_profesores.entity.ProfesoresDivisiones;
import mx.edu.uteq.idgs13.microservicio_profesores.repository.UsuariosRepository;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    // Obtener todos los usuarios
    public List<UsuarioViewDto> getAllUsuarios() {
        List<UsuariosEntity> usuarios = usuariosRepository.findAll();
        return usuarios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Filtrar usuarios por tipo
    public List<UsuarioViewDto> getUsuariosByTipo(TipoUsuario tipo) {
        List<UsuariosEntity> usuarios = usuariosRepository.findByTipoUsuario(tipo);
        return usuarios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener solo coordinadores
    public List<UsuarioViewDto> getCoordinadores() {
        return getUsuariosByTipo(TipoUsuario.COORDINADOR);
    }

    // Obtener solo profesores
    public List<UsuarioViewDto> getProfesores() {
        return getUsuariosByTipo(TipoUsuario.PROFESOR);
    }

    // Obtener solo alumnos
    public List<UsuarioViewDto> getAlumnos() {
        return getUsuariosByTipo(TipoUsuario.ALUMNO);
    }

    // Editar usuario
    public UsuarioViewDto editarUsuario(UsuarioEditDto usuarioEditDto) {
        UsuariosEntity usuario = usuariosRepository.findById(usuarioEditDto.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Guardar las divisiones existentes si es profesor
            List<ProfesoresDivisiones> divisionesExistentes = null;
            if (usuario.getTipoUsuario() == TipoUsuario.PROFESOR) {
                divisionesExistentes = usuario.getDivisiones();
            }

        usuario.setNombre(usuarioEditDto.getNombre());
        usuario.setEmail(usuarioEditDto.getEmail());
        usuario.setTipoUsuario(usuarioEditDto.getTipoUsuario());
        usuario.setActivo(usuarioEditDto.isActivo());
        
        // Campos específicos según el tipo de usuario
        switch (usuarioEditDto.getTipoUsuario()) {
            case ALUMNO:
                usuario.setMatricula(usuarioEditDto.getMatricula());
                usuario.setCarrera(usuarioEditDto.getCarrera());
                usuario.setSemestre(usuarioEditDto.getSemestre());
                // Limpiar campos no relacionados
                usuario.setAreaCoordinacion(null);
                usuario.setNivelAcceso(null);
                usuario.getDivisiones().clear();  // BIEN
                break;
            case COORDINADOR:
                usuario.setAreaCoordinacion(usuarioEditDto.getAreaCoordinacion());
                usuario.setNivelAcceso(usuarioEditDto.getNivelAcceso());
                // Limpiar campos no relacionados
                usuario.setMatricula(null);
                usuario.setCarrera(null);
                usuario.setSemestre(null);
                usuario.getDivisiones().clear();  // BIEN
                break;
            case PROFESOR:
                // Limpiar los campos específicos de otros tipos de usuario
                usuario.setMatricula(null);
                usuario.setCarrera(null);
                usuario.setSemestre(null);
                usuario.setAreaCoordinacion(null);
                usuario.setNivelAcceso(null);
                    // Mantener las divisiones existentes si ya era profesor
                    if (divisionesExistentes != null) {
                        usuario.setDivisiones(divisionesExistentes);
                    }
                    break;
        }

        usuario = usuariosRepository.save(usuario);
        return convertToDto(usuario);
    }

    // Obtener usuario por ID
    public UsuarioViewDto getUsuarioById(Long id) throws Exception {
        UsuariosEntity usuario = usuariosRepository.findById(id)
            .orElseThrow(() -> new Exception("Usuario no encontrado con id: " + id));
        return convertToDto(usuario);
    }

    // Crear usuario
    public UsuarioViewDto createUsuario(UsuariosEntity usuario) {
        UsuariosEntity saved = usuariosRepository.save(usuario);
        return convertToDto(saved);
    }



    // Eliminar usuario
    public void deleteUsuario(Long id) throws Exception {
        if (!usuariosRepository.existsById(id)) {
            throw new Exception("Usuario no encontrado con id: " + id);
        }
        usuariosRepository.deleteById(id);
    }

    // Actualizar estado del usuario
    public UsuarioViewDto updateUsuarioStatus(Long id, boolean activo) throws Exception {
        UsuariosEntity usuario = usuariosRepository.findById(id)
            .orElseThrow(() -> new Exception("Usuario no encontrado con id: " + id));
        
        usuario.setActivo(activo);
        
        // Si es profesor, actualizar también las divisiones
        if (usuario.getTipoUsuario() == TipoUsuario.PROFESOR && usuario.getDivisiones() != null) {
            for (ProfesoresDivisiones division : usuario.getDivisiones()) {
                division.setActivo(activo);
            }
        }
        
        UsuariosEntity saved = usuariosRepository.save(usuario);
        return convertToDto(saved);
    }

    // Convertir entidad a DTO
    private UsuarioViewDto convertToDto(UsuariosEntity usuario) {
        UsuarioViewDto dto = new UsuarioViewDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTipoUsuario(usuario.getTipoUsuario().toString());
        dto.setActivo(usuario.isActivo());
        
        // Agregar campos específicos según el tipo
        switch (usuario.getTipoUsuario()) {
            case PROFESOR:
                if (usuario.getDivisiones() != null) {
                    List<Long> divisiones = usuario.getDivisiones().stream()
                        .map(ProfesoresDivisiones::getDivisionId)
                        .collect(Collectors.toList());
                    dto.setDivisiones(divisiones);
                }
                break;
                
            case ALUMNO:
                dto.setMatricula(usuario.getMatricula());
                dto.setCarrera(usuario.getCarrera());
                dto.setSemestre(usuario.getSemestre());
                break;
                
            case COORDINADOR:
                dto.setAreaCoordinacion(usuario.getAreaCoordinacion());
                dto.setNivelAcceso(usuario.getNivelAcceso());
                break;
        }
        
        return dto;
    }
}