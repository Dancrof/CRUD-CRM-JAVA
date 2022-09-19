package com.api.rest.crm.controllers;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.rest.crm.entidades.Estudiante;
import com.api.rest.crm.entidades.Materia;
import com.api.rest.crm.repositories.EstudianteRepository;
import com.api.rest.crm.repositories.MateriaRepository;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

	@Autowired
	private EstudianteRepository estudianteRepository;

	@Autowired
	private MateriaRepository materiaRepository;

	// optenemos todas las estudiante
	@GetMapping
	public ResponseEntity<Page<Estudiante>> getEstudiantes(Pageable pageable) {

		return ResponseEntity.ok(estudianteRepository.findAll(pageable));
	}

	// creamos una Estudiante
	@PostMapping
	public ResponseEntity<Estudiante> createEstudiante(@Valid @RequestBody Estudiante estudiante) {

		Optional<Materia> materia = materiaRepository.findById(estudiante.getMateria().getId());

		if (!materia.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		estudiante.setMateria(materia.get());
		Estudiante estudi = estudianteRepository.save(estudiante);
		URI ruta = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(estudi.getId())
				.toUri();

		return ResponseEntity.created(ruta).body(estudi);
	}

	// editanos un estudiante
	@PutMapping("/{id}")
	public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Integer id,
			@Valid @RequestBody Estudiante estudiante) {

		Optional<Materia> materia = materiaRepository.findById(estudiante.getMateria().getId());

		if (!materia.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);

		if (!estudianteOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		estudiante.setMateria(materia.get());
		estudiante.setId(estudianteOptional.get().getId());
		estudianteRepository.save(estudiante);

		return ResponseEntity.noContent().build();
	}

	// eliminamos un estudiante
	@DeleteMapping("/{id}")
	public ResponseEntity<Estudiante> deleteEstudiante(@PathVariable Integer id) {

		Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);

		if (!estudianteOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		estudianteRepository.delete(estudianteOptional.get());

		return ResponseEntity.noContent().build();
	}

	// optenes un estudiante por su id
	@GetMapping("/{id}")
	public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Integer id) {

		Optional<Estudiante> estudianteOptional = estudianteRepository.findById(id);

		if (!estudianteOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(estudianteOptional.get());
	}
}
