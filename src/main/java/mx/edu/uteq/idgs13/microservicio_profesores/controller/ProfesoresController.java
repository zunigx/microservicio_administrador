package mx.edu.uteq.idgs13.microservicio_profesores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import mx.edu.uteq.idgs13.microservicio_profesores.dto.ProfesoresStatusDto;
import mx.edu.uteq.idgs13.microservicio_profesores.dto.ProfesoresToViewListDto;
import mx.edu.uteq.idgs13.microservicio_profesores.dto.ProfesoresDivisionesDto;
import mx.edu.uteq.idgs13.microservicio_profesores.dto.ProfesoresEditDto;
import mx.edu.uteq.idgs13.microservicio_profesores.entity.ProfesoresEntity;
import mx.edu.uteq.idgs13.microservicio_profesores.repository.ProfesoresRepository;
import mx.edu.uteq.idgs13.microservicio_profesores.service.ProfesoresService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/profesores")
public class ProfesoresController {
    @Autowired
    private ProfesoresService profesoresService;

    @Autowired
    public ProfesoresRepository profesoresRepository;

    @GetMapping
    public List<ProfesoresToViewListDto> getAllProfesores() {
        return profesoresService.findAll();
    }

    @GetMapping("/all")
    public List<ProfesoresEntity> getAll() {
        return profesoresRepository.findAll();
    }

    // Endpoint para crear un nuevo profesor
    @PostMapping
    public ResponseEntity<ProfesoresToViewListDto> createProfesor(@RequestBody ProfesoresToViewListDto profesorDto) {
        ProfesoresToViewListDto savedProfesor = profesoresService.addProfesor(profesorDto);
        return new ResponseEntity<>(savedProfesor, HttpStatus.CREATED);
    }

    // Endpoint para editar una división asociada
    @PutMapping("/divisiones/{divisionId}")
    public ResponseEntity<ProfesoresDivisionesDto> updateDivisionAsociada(@PathVariable Long divisionId,
            @RequestBody ProfesoresDivisionesDto divisionDto) {
        ProfesoresDivisionesDto updatedDivision = profesoresService.updateDivisionAsociada(divisionId, divisionDto);
        return new ResponseEntity<>(updatedDivision, HttpStatus.OK);
    }

    // Endpoint para activar o desactivar un profesor
    @PutMapping("/{id}/updateStatus")
    public ProfesoresEntity updateProfesorStatus(@PathVariable Long id, @RequestBody ProfesoresStatusDto statusDto) {
        return profesoresService.updateProfesorStatus(id, statusDto.isActivo());
    }

    @GetMapping("/{id}")
    public ProfesoresEntity getProfesorById(@PathVariable Long id) throws Exception {
        return profesoresService.findById(id);
    }

    @PutMapping("/{id}")
    public ProfesoresEntity editProfesor(@PathVariable Long id, @RequestBody ProfesoresEditDto dto) throws Exception {
        return profesoresService.updateProfesor(id, dto.getNombre(), dto.getEmail(), dto.isActivo());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfesor(@PathVariable Long id) {
        try {
            profesoresService.deleteProfesor(id);
            return ResponseEntity.ok("Profesor eliminado físicamente con id: " + id);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}