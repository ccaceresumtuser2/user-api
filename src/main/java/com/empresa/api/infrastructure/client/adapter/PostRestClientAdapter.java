package com.empresa.api.infrastructure.client.adapter;

import com.empresa.api.application.port.out.PostPortOut;
import com.empresa.api.domain.model.Post;
import com.empresa.api.infrastructure.client.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PostRestClientAdapter implements PostPortOut {

    private final RestClient jsonPlaceholderClient;

    @Override
    public List<Post> fetchAll() {
        log.info("Consultando todos los posts en JSONPlaceholder");
        PostDto[] response = jsonPlaceholderClient.get()
                .uri("/posts")
                .retrieve()
                .body(PostDto[].class);
        List<Post> posts = Arrays.stream(response).map(PostDto::toDomain).toList();
        log.info("fetchAll - {} posts obtenidos", posts.size());
        return posts;
    }

    @Override
    public Post fetchById(Long id) {
        log.info("Consultando post id={} en JSONPlaceholder", id);
        PostDto response = jsonPlaceholderClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .body(PostDto.class);
        log.info("fetchById - post encontrado: id={}", id);
        return response.toDomain();
    }
}
