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

import com.api.rest.crm.entidades.Materia;
import com.api.rest.crm.repositories.MateriaRepository;

@RestController
@RequestMapping("/api/materia")
public class MateriaController {

	@Autowired
	private MateriaRepository materiaRepository;

	//optenemos todas las materias
	@GetMapping
	public ResponseEntity<Page<Materia>> getMaterias(Pageable pageable){
		
		return ResponseEntity.ok(materiaRepository.findAll(pageable));
	}
	
	// creamos una materia
	@PostMapping
	public ResponseEntity<Materia> createMateria(@Valid @RequestBody Materia materia) {

		Materia materi = materiaRepository.save(materia);

		URI ruta = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(materi.getId())
				.toUri();

		return ResponseEntity.created(ruta).body(materi);
	}

	// editanos una materia
	@PutMapping("/{id}")
	public ResponseEntity<Materia> updateMateria(@PathVariable Integer id, @Valid @RequestBody Materia materia) {

		Optional<Materia> materi = materiaRepository.findById(id);

		if (!materi.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		materia.setId(materi.get().getId());
		materiaRepository.save(materia);

		return ResponseEntity.noContent().build();
	}

	// eliminamos una materia
	@DeleteMapping("/{id}")
	public ResponseEntity<Materia> deleteMateria(@PathVariable Integer id) {

		Optional<Materia> materi = materiaRepository.findById(id);

		if (!materi.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		materiaRepository.delete(materi.get());
		
		return ResponseEntity.noContent().build();
	}
	
	//optenes una materia por su id
	@GetMapping("/{id}")
	public ResponseEntity<Materia> getMateriaById(@PathVariable Integer id){
		
		Optional<Materia> materi = materiaRepository.findById(id);

		if (!materi.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(materi.get());
	}
}
