package com.jayesh.elasticsearch.iservices;

import com.jayesh.elasticsearch.models.Post;

import java.util.List;

public interface IPostsService {
    String addSinglePost(Post post);
    List<Post> getPostById(Long id);
}
