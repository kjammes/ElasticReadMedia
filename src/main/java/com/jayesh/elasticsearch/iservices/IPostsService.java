package com.jayesh.elasticsearch.iservices;

import com.jayesh.elasticsearch.models.Post;

import java.util.List;
import java.util.Optional;

public interface IPostsService {
    String addSinglePost(Post post);
}
