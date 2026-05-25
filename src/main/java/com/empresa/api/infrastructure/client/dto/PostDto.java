package com.empresa.api.infrastructure.client.dto;

import com.empresa.api.domain.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long   id;
    private Long   userId;
    private String title;
    private String body;

    public Post toDomain() {
        return Post.builder()
                .id(id)
                .userId(userId)
                .title(title)
                .body(body)
                .build();
    }
}
