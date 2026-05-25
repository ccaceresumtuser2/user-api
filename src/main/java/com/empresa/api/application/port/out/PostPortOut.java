package com.empresa.api.application.port.out;

import com.empresa.api.domain.model.Post;

import java.util.List;

public interface PostPortOut {
    List<Post> fetchAll();
    Post fetchById(Long id);
}
