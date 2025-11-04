package mx.edu.uteq.idgs13.microservicio_profesores.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uteq.idgs13.microservicio_profesores.dto.ProfesoresDto;
import mx.edu.uteq.idgs13.microservicio_profesores.dto.ProfesoresToViewListDto;
import mx.edu.uteq.idgs13.microservicio_profesores.dto.ProfesoresDivisionesDto;
import mx.edu.uteq.idgs13.microservicio_profesores.entity.ProfesoresEntity;
import mx.edu.uteq.idgs13.microservicio_profesores.entity.ProfesoresDivisiones;
import mx.edu.uteq.idgs13.microservicio_profesores.repository.ProfesoresRepository;

@Service
public class ProfesoresService {

    @Autowired
    private ProfesoresRepository profesoresRepository;

    public List<ProfesoresDto> getAllProfesores() {
        List<ProfesoresEntity> profesores = profesoresRepository.findAll();
        List<ProfesoresDto> profesoresDtos = new ArrayList<>();
        for (ProfesoresEntity profesor : profesores) {
            ProfesoresDto dto = new ProfesoresDto();
            dto.setId(profesor.getId());
            dto.setNombre(profesor.getNombre());
            dto.setEmail(profesor.getEmail());
            dto.setActivo(profesor.isActivo());
            profesoresDtos.add(dto);
        }
        return profesoresDtos;
    }

    public List<ProfesoresToViewListDto> findAll() {
        List<ProfesoresEntity> profesores = profesoresRepository.findAll();
        List<ProfesoresToViewListDto> resultado = new ArrayList<>();
        for (ProfesoresEntity profesor : profesores) {
            ProfesoresToViewListDto dto = new ProfesoresToViewListDto();
            dto.setProfesorId(profesor.getId());
            dto.setNombre(profesor.getNombre());
            dto.setEmail(profesor.getEmail());
            if (profesor.getDivisiones() != null) {
                List<Long> divisiones = new ArrayList<>();
                for (ProfesoresDivisiones div : profesor.getDivisiones()) {
                    divisiones.add(div.getDivisionId());
                }
                dto.setDivisiones(divisiones);
            } else {
                dto.setDivisiones(new ArrayList<>());
            }
            resultado.add(dto);
        }
        return resultado;
    }

    public ProfesoresToViewListDto addProfesor(ProfesoresToViewListDto profesorDto) {
        ProfesoresEntity profesor = new ProfesoresEntity();
        profesor.setNombre(profesorDto.getNombre());
        profesor.setEmail(profesorDto.getEmail());
        profesor.setActivo(true);
        List<ProfesoresDivisiones> divisiones = new ArrayList<>();
        if (profesorDto.getDivisiones() != null) {
            for (Long divId : profesorDto.getDivisiones()) {
                ProfesoresDivisiones div = new ProfesoresDivisiones();
                div.setDivisionId(divId);
                div.setActivo(true);
                divisiones.add(div);
            }
        }
        profesor.setDivisiones(divisiones);
        ProfesoresEntity savedProfesor = profesoresRepository.save(profesor);
        ProfesoresToViewListDto savedDto = new ProfesoresToViewListDto();
        savedDto.setProfesorId(savedProfesor.getId());
        savedDto.setNombre(savedProfesor.getNombre());
        savedDto.setEmail(savedProfesor.getEmail());
        List<Long> savedDivisiones = new ArrayList<>();
        for (ProfesoresDivisiones div : savedProfesor.getDivisiones()) {
            savedDivisiones.add(div.getDivisionId());
        }
        savedDto.setDivisiones(savedDivisiones);
        return savedDto;
    }

    public ProfesoresEntity findById(Long id) throws Exception {
        return profesoresRepository.findById(id)
            .orElseThrow(() -> new Exception("Profesor no encontrado con id: " + id));
    }

    public ProfesoresDivisionesDto updateDivisionAsociada(Long divisionAsociadaId, ProfesoresDivisionesDto divisionDto) {
        Optional<ProfesoresEntity> profesorOptional = profesoresRepository.findAll().stream()
                .filter(profesor -> profesor.getDivisiones().stream()
                        .anyMatch(div -> div.getId().equals(divisionAsociadaId)))
                .findFirst();

        if (profesorOptional.isPresent()) {
            ProfesoresEntity profesor = profesorOptional.get();
            ProfesoresDivisiones divisionToUpdate = profesor.getDivisiones().stream()
                    .filter(div -> div.getId().equals(divisionAsociadaId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("División asociada no encontrada"));

            divisionToUpdate.setDivisionId(divisionDto.getDivisionId());
            profesoresRepository.save(profesor);

            ProfesoresDivisionesDto updatedDto = new ProfesoresDivisionesDto();
            updatedDto.setId(divisionToUpdate.getId());
            updatedDto.setDivisionId(divisionToUpdate.getDivisionId());
            return updatedDto;
        } else {
            throw new RuntimeException("Profesor no encontrado para la división asociada");
        }
    }

    public ProfesoresEntity updateProfesorStatus(Long id, boolean activo) {
        ProfesoresEntity profesor = profesoresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        profesor.setActivo(activo);

        if (profesor.getDivisiones() != null) {
            for (ProfesoresDivisiones division : profesor.getDivisiones()) {
                division.setActivo(activo);
            }
        }
        return profesoresRepository.save(profesor);
    }

    public void deleteProfesor(Long id) {
        if (!profesoresRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el profesor con id: " + id);
        }
        profesoresRepository.deleteById(id);
    }

    public ProfesoresEntity updateProfesor(Long id, String nombre, String email, boolean activo) throws Exception {
        ProfesoresEntity profesor = profesoresRepository.findById(id)
                .orElseThrow(() -> new Exception("Profesor no encontrado con id: " + id));
        profesor.setNombre(nombre);
        profesor.setEmail(email);
        profesor.setActivo(activo);
        if (profesor.getDivisiones() != null) {
            for (ProfesoresDivisiones division : profesor.getDivisiones()) {
                division.setActivo(activo);
            }
        }
        return profesoresRepository.save(profesor);
    }
}