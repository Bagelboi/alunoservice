package org.danieldemarchipb.alunoservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TB_ALUNO")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String senha;
    private Rank rank;
    @ElementCollection
    private Set<Long> projetos_id = new HashSet<>();

    public AlunoDTO toDto() {
        return new AlunoDTO(id, nome, rank, projetos_id);
    }

    public Aluno(Long id, String _nome, String _senha, Rank _rank) {
        this();
        this.id = id;
        this.nome = _nome;
        this.senha = _senha;
        this.rank  = _rank;
    }
}
