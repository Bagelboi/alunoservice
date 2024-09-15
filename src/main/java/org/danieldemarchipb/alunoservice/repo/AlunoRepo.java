package org.danieldemarchipb.alunoservice.repo;

import org.danieldemarchipb.alunoservice.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepo extends JpaRepository<Aluno, Long> {
}
