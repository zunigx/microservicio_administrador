package mx.edu.uteq.idgs13.microservicio_profesores.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.edu.uteq.idgs13.microservicio_profesores.entity.ProfesoresDivisiones;
import mx.edu.uteq.idgs13.microservicio_profesores.service.ProfesoresDivisionesService;

@RestController
@RequestMapping("/api/profesores-divisiones")
public class ProfesoresDivisionesController {
    @Autowired
    private ProfesoresDivisionesService profesoresDivisionesService;

    @GetMapping
    public List<ProfesoresDivisiones> getAll() {
        return profesoresDivisionesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<ProfesoresDivisiones> division = profesoresDivisionesService.findById(id);
        return division.isPresent() ? ResponseEntity.ok(division.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/profesor/{profesorId}")
    public ResponseEntity<?> create(@PathVariable Long profesorId, @RequestBody ProfesoresDivisiones division) {
        try {
            ProfesoresDivisiones creada = profesoresDivisionesService.saveWithProfesor(profesorId, division);
            return ResponseEntity.ok(creada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProfesoresDivisiones division) {
        Optional<ProfesoresDivisiones> existing = profesoresDivisionesService.findById(id);
        if (existing.isPresent()) {
            division.setId(id);
            return ResponseEntity.ok(profesoresDivisionesService.save(division));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<ProfesoresDivisiones> existing = profesoresDivisionesService.findById(id);
        if (existing.isPresent()) {
            profesoresDivisionesService.deleteById(id);
            return ResponseEntity.ok().body("División asociada eliminada correctamente");
        } else {
            return ResponseEntity.status(404).body("División asociada no existe");
        }
    }
}