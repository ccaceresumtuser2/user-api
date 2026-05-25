package com.empresa.api.application.port.in;

import com.empresa.api.domain.model.Post;

import java.util.List;

public interface PostPortIn {
    List<Post> findAll();
    Post findById(Long id);
}
