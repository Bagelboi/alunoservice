package org.danieldemarchipb.alunoservice;

import org.danieldemarchipb.alunoservice.model.Aluno;
import org.danieldemarchipb.alunoservice.service.AlunoService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;


@SpringBootTest
@ActiveProfiles("test")
public class AlunoServiceTest {

    @Autowired
    AlunoService service;

    @Test
    public void compareHashes() {
        String text1 = "hello world";
        Assert.isTrue(service.getHash(text1).equals( service.getHash(text1) ), "hash nao e igual");
    }

    @Test
    @Disabled
    public void testAluno() {
        Aluno newal = new Aluno();
        Long projid = 12L;
        newal.setNome("joao");
        newal.setSenha("123");
        newal = service.save(newal);
        newal.getProjetos_id().add(projid);
        Assert.isTrue(service.apagarProjeto(projid), "nao apagou proj");
        Assert.isTrue(service.getById(newal.getId()).get().getProjetos_id().isEmpty(), "nao vazio");
    }
}

