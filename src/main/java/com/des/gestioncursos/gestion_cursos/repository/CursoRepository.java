package com.des.gestioncursos.gestion_cursos.repository;

import com.des.gestioncursos.gestion_cursos.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Como lo vamos a inyectar colocamos la anotacion
@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

}
