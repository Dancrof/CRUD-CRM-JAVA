package com.api.rest.crm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.crm.entidades.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

}
