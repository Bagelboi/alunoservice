package org.danieldemarchipb.alunoservice.controller;

import org.danieldemarchipb.alunoservice.model.Aluno;
import org.danieldemarchipb.alunoservice.model.AlunoDTO;
import org.danieldemarchipb.alunoservice.model.LoginDTO;
import org.danieldemarchipb.alunoservice.model.Rank;
import org.danieldemarchipb.alunoservice.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAllAlunos() {
        List<AlunoDTO> alunos = new ArrayList<>();
        alunoService.getAll().forEach( a -> alunos.add(a.toDto()) );
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<AlunoDTO> getAlunoById(@PathVariable String nome) {
        return alunoService.getByNome(nome)
                .map( a -> ResponseEntity.ok( a.toDto()) )
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AlunoDTO> getAlunoById(@PathVariable Long id) {
        return alunoService.getById(id)
                .map( a -> ResponseEntity.ok( a.toDto()) )
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by_proj/{id}")
    public ResponseEntity<AlunoDTO> getAlunoByProjId(@PathVariable Long id) {
        return alunoService.getByProjetoID(id)
                .map( a -> ResponseEntity.ok( a.toDto()) )
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criarAluno(@RequestBody Aluno a) {
        a.setRank(Rank.USER);
        a = alunoService.save(a);
        return ResponseEntity.ok(a.toDto());
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody String novaSenha) {
        if (alunoService.atualizarSenha(id, novaSenha)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/projetos/{projId}")
    public ResponseEntity<Void> adicionarProjeto(@PathVariable Long id, @PathVariable Long projId) {
        if (alunoService.adicionarProjeto(id, projId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/projetos/{projId}")
    public ResponseEntity<Void> apagarProjeto(@PathVariable Long projId) {
        if (alunoService.apagarProjeto(projId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/projetos")
    public ResponseEntity<Void> apagarTodosProjetos(@PathVariable Long id) {
        if (alunoService.apagarTodosProjetos(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AlunoDTO> login(@RequestBody LoginDTO login) {
        Optional<Aluno> aluno = alunoService.getByNome(login.nome());
        if (aluno.isPresent() && alunoService.getHash(login.senha()).equals( aluno.get().getSenha() )) {
            return aluno.map( a -> ResponseEntity.ok( a.toDto())).get();
        }
        return ResponseEntity.badRequest().build();
    }
}
