package org.danieldemarchipb.alunoservice.model;

import java.util.HashSet;
import java.util.Set;

public record AlunoDTO(Long id, String nome, Rank rank, Set<Long> projetos_id) {
}
