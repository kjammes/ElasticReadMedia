package com.jayesh.elasticsearch.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
    private Long id;
    private String articleTitle;
    private String shortDescription;
    // Will add more fields later
}
