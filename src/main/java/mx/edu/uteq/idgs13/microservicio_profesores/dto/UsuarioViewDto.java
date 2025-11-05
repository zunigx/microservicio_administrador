package mx.edu.uteq.idgs13.microservicio_profesores.dto;

import lombok.Data;
import java.util.List;

// DTO genérico para visualizar cualquier tipo de usuario
@Data
public class UsuarioViewDto {
    private Long id;
    private String nombre;
    private String email;
    private String tipoUsuario;
    private boolean activo;
    
    // Campos para profesores
    private List<Long> divisiones;
    
    // Campos para alumnos
    private String matricula;
    private String carrera;
    private Integer semestre;
    
    // Campos para coordinadores
    private String areaCoordinacion;
    private String nivelAcceso;
}

// DTO específico para coordinadores
@Data
class CoordinadorDto {
    private Long id;
    private String nombre;
    private String email;
    private boolean activo;
    private String areaCoordinacion;
    private String nivelAcceso;
}

// DTO específico para profesores (compatible con tu sistema actual)
@Data
class ProfesorViewDto {
    private Long id;
    private String nombre;
    private String email;
    private boolean activo;
    private List<Long> divisiones;
}

// DTO específico para alumnos
@Data
class AlumnoDto {
    private Long id;
    private String nombre;
    private String email;
    private boolean activo;
    private String matricula;
    private String carrera;
    private Integer semestre;
}

// DTO para crear nuevos usuarios
@Data
class UsuarioCreateDto {
    private String nombre;
    private String email;
    private String tipoUsuario;
    private boolean activo;
    
    // Campos opcionales según el tipo
    private List<Long> divisiones;
    private String matricula;
    private String carrera;
    private Integer semestre;
    private String areaCoordinacion;
    private String nivelAcceso;
}