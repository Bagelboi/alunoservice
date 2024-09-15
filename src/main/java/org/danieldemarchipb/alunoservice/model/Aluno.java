package org.danieldemarchipb.alunoservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
    private HashSet<Long> projetos_id;

    public Aluno(String _nome, String _senha, Rank _rank) {
        this();
        this.nome = _nome;
        this.senha = _senha;
        this.rank  = _rank;
    }
}
