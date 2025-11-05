package mx.edu.uteq.idgs13.microservicio_profesores.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class UsuariosEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;
    
    private boolean activo;

    // ===== CAMPOS PARA PROFESORES =====
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<ProfesoresDivisiones> divisiones;

    // ===== CAMPOS PARA ALUMNOS =====
    @Column(name = "matricula")
    private String matricula;
    
    @Column(name = "carrera")
    private String carrera;
    
    @Column(name = "semestre")
    private Integer semestre;

    // ===== CAMPOS PARA COORDINADORES =====
    @Column(name = "area_coordinacion")
    private String areaCoordinacion;
    
    @Column(name = "nivel_acceso")
    private String nivelAcceso;

    public UsuariosEntity() {
    }

    public UsuariosEntity(String nombre, String email, TipoUsuario tipoUsuario, boolean activo) {
        this.nombre = nombre;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.activo = activo;
    }
}