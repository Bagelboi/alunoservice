package org.danieldemarchipb.alunoservice.model;

import java.util.HashSet;

public record AlunoDTO(Long id, String nome, Rank rank, HashSet<Long> projetos_id) {
}
