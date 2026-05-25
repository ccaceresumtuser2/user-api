package com.empresa.api.application.usecase;

import com.empresa.api.application.port.in.PostPortIn;
import com.empresa.api.application.port.out.PostPortOut;
import com.empresa.api.domain.model.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PostUseCase implements PostPortIn {

    private final PostPortOut postPortOut;

    @Override
    public List<Post> findAll() {
        log.debug("Ejecutando caso de uso: findAll posts");
        return postPortOut.fetchAll();
    }

    @Override
    public Post findById(Long id) {
        log.debug("Ejecutando caso de uso: findById post id={}", id);
        return postPortOut.fetchById(id);
    }
}
