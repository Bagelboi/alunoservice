package org.danieldemarchipb.alunoservice.client;

import org.danieldemarchipb.alunoservice.client.ProjectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient("PROJETO-SERVICE")
public interface ProjectClient {
    @GetMapping("/project/{id}")
    Optional<ProjectDTO> getProjectById(@PathVariable Long id);

    @GetMapping("/project/")
    List<ProjectDTO> getProjects();
}
