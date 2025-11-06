package mx.edu.uteq.idgs13.microservicio_profesores.dto;

import lombok.Data;
import mx.edu.uteq.idgs13.microservicio_profesores.entity.TipoUsuario;

@Data
public class UsuarioEditDto {
    private Long id;
    private String nombre;
    private String email;
    private TipoUsuario tipoUsuario;
    private boolean activo;
    private String matricula;
    private String carrera;
    private Integer semestre;
    private String areaCoordinacion;
    private String nivelAcceso;
}