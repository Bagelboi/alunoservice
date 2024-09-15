package org.danieldemarchipb.alunoservice.service;

import jakarta.annotation.PostConstruct;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.hc.client5.http.utils.Base64;
import org.danieldemarchipb.alunoservice.client.ProjectClient;
import org.danieldemarchipb.alunoservice.client.ProjectDTO;
import org.danieldemarchipb.alunoservice.model.Aluno;
import org.danieldemarchipb.alunoservice.model.Rank;
import org.danieldemarchipb.alunoservice.repo.AlunoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepo repo;
    @Autowired
    private Environment env;
    @Autowired
    private ProjectClient projectClient;

    public List<Aluno> getAll() {
        return repo.findAll();
    }

    public Optional<Aluno> getById(Long id) {
        return repo.findById(id);
    }

    public Optional<Aluno> getByNome(String nome) {
        return getAll().stream().filter( a -> a.getNome().equals(nome.trim()) ).findFirst();
    }

    public Optional<Aluno> getByProjetoID(Long id) {
        return getAll().stream().filter( a -> a.getProjetos_id().contains(id) ).findFirst();
    }
    public Aluno save(Aluno aluno) {
        aluno.setNome(aluno.getNome().trim());
        aluno.setProjetos_id(new HashSet<>());
        aluno.setSenha(getHash(aluno.getSenha().trim()));
        return repo.save(aluno);
    }
    public boolean atualizarSenha(Long id, String senha_nova) {
        getById(id).ifPresent( a -> {
            a.setSenha( getHash(senha_nova) );
            save(a);
        } );
        return getById(id).isPresent();
    }
    public boolean adicionarProjeto(Long id, Long proj_id) {
        if (!getByProjetoID(proj_id).isEmpty())
            return false;
        Optional<ProjectDTO> proj = projectClient.getProjectById(proj_id);
        getById(id).ifPresent( a -> {
            if (!proj.isEmpty()) {
                a.getProjetos_id().add(proj_id);
                save(a);
            }
        });
        return getById(id).isPresent() && proj.isPresent();
    }
    public boolean apagarProjeto(Long proj_id) {
        Optional<Aluno> aluno = getByProjetoID(proj_id);
        aluno.ifPresent( a -> {
           a.getProjetos_id().remove(proj_id);
           save(a);
        });
        return aluno.isPresent();
    }
    public boolean apagarTodosProjetos(Long id) {
        Optional<Aluno> aluno = getById(id);
        aluno.ifPresent( a -> {
            a.getProjetos_id().clear();
            save(a);
        });
        return aluno.isPresent();
    }
    public String getHash(String text) {
        byte[] hmac = HmacUtils.hmacSha256(text, env.getProperty("aluno.enc.key"));
        return Base64.encodeBase64String(hmac);
    }

    @PostConstruct
    public void criarUsuarios() {
        save(new Aluno("Daniel", "123456", Rank.ADMIN));
    }

}
