package mx.edu.uteq.idgs13.microservicio_profesores.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.edu.uteq.idgs13.microservicio_profesores.dto.UsuarioViewDto;
import mx.edu.uteq.idgs13.microservicio_profesores.dto.UsuarioEditDto;
import mx.edu.uteq.idgs13.microservicio_profesores.entity.UsuariosEntity;
import mx.edu.uteq.idgs13.microservicio_profesores.service.UsuariosService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    /**
     * GET /api/usuarios
     * Obtiene todos los usuarios del sistema
     */
    @GetMapping
    public ResponseEntity<List<UsuarioViewDto>> getAllUsuarios() {
        List<UsuarioViewDto> usuarios = usuariosService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * GET /api/usuarios/coordinadores
     * Obtiene solo usuarios de tipo COORDINADOR
     */
    @GetMapping("/coordinadores")
    public ResponseEntity<List<UsuarioViewDto>> getCoordinadores() {
        List<UsuarioViewDto> coordinadores = usuariosService.getCoordinadores();
        return ResponseEntity.ok(coordinadores);
    }

    /**
     * GET /api/usuarios/profesores
     * Obtiene solo usuarios de tipo PROFESOR
     */
    @GetMapping("/profesores")
    public ResponseEntity<List<UsuarioViewDto>> getProfesores() {
        List<UsuarioViewDto> profesores = usuariosService.getProfesores();
        return ResponseEntity.ok(profesores);
    }

    /**
     * GET /api/usuarios/alumnos
     * Obtiene solo usuarios de tipo ALUMNO
     */
    @GetMapping("/alumnos")
    public ResponseEntity<List<UsuarioViewDto>> getAlumnos() {
        List<UsuarioViewDto> alumnos = usuariosService.getAlumnos();
        return ResponseEntity.ok(alumnos);
    }

    /**
     * GET /api/usuarios/{id}
     * Obtiene un usuario específico por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        try {
            UsuarioViewDto usuario = usuariosService.getUsuarioById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: " + e.getMessage());
        }
    }

    /**
     * POST /api/usuarios
     * Crea un nuevo usuario
     */
    @PostMapping
    public ResponseEntity<UsuarioViewDto> createUsuario(@RequestBody UsuariosEntity usuario) {
        UsuarioViewDto created = usuariosService.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/usuarios/{id}
     * Actualiza un usuario existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(
            @PathVariable Long id, 
            @RequestBody UsuarioEditDto usuarioEditDto) {
        if (!id.equals(usuarioEditDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            UsuarioViewDto updated = usuariosService.editarUsuario(usuarioEditDto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: " + e.getMessage());
        }
    }

    /**
     * DELETE /api/usuarios/{id}
     * Elimina un usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            usuariosService.deleteUsuario(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente con id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: " + e.getMessage());
        }
    }

    /**
     * PATCH /api/usuarios/{id}/status
     * Activa o desactiva un usuario
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateUsuarioStatus(
            @PathVariable Long id, 
            @RequestParam boolean activo) {
        try {
            UsuarioViewDto updated = usuariosService.updateUsuarioStatus(id, activo);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: " + e.getMessage());
        }
    }

    /**
     * PATCH /api/usuarios/{id}/habilitar
     * Marca al usuario como activo
     */
    @PatchMapping("/{id}/habilitar")
    public ResponseEntity<?> habilitarUsuario(@PathVariable Long id) {
        try {
            UsuarioViewDto updated = usuariosService.updateUsuarioStatus(id, true);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: " + e.getMessage());
        }
    }

    /**
     * PATCH /api/usuarios/{id}/deshabilitar
     * Marca al usuario como inactivo
     */
    @PatchMapping("/{id}/deshabilitar")
    public ResponseEntity<?> deshabilitarUsuario(@PathVariable Long id) {
        try {
            UsuarioViewDto updated = usuariosService.updateUsuarioStatus(id, false);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: " + e.getMessage());
        }
    }
}