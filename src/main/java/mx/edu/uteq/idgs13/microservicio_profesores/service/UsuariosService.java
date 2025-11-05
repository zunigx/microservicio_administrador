package mx.edu.uteq.idgs13.microservicio_profesores.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uteq.idgs13.microservicio_profesores.dto.UsuarioViewDto;
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

    // Actualizar usuario
    public UsuarioViewDto updateUsuario(Long id, UsuariosEntity usuarioActualizado) throws Exception {
        UsuariosEntity usuario = usuariosRepository.findById(id)
            .orElseThrow(() -> new Exception("Usuario no encontrado con id: " + id));
        
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setActivo(usuarioActualizado.isActivo());
        
        // Actualizar campos específicos según el tipo
        if (usuario.getTipoUsuario() == TipoUsuario.PROFESOR) {
            if (usuarioActualizado.getDivisiones() != null) {
                usuario.setDivisiones(usuarioActualizado.getDivisiones());
            }
        } else if (usuario.getTipoUsuario() == TipoUsuario.ALUMNO) {
            usuario.setMatricula(usuarioActualizado.getMatricula());
            usuario.setCarrera(usuarioActualizado.getCarrera());
            usuario.setSemestre(usuarioActualizado.getSemestre());
        } else if (usuario.getTipoUsuario() == TipoUsuario.COORDINADOR) {
            usuario.setAreaCoordinacion(usuarioActualizado.getAreaCoordinacion());
            usuario.setNivelAcceso(usuarioActualizado.getNivelAcceso());
        }
        
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