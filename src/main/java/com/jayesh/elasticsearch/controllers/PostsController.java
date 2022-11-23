package com.jayesh.elasticsearch.controllers;

import com.jayesh.elasticsearch.models.Post;
import com.jayesh.elasticsearch.services.PostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elastic/post")
public class PostsController {
    private final PostsService postsService;
    private final Logger LOG;

    PostsController(PostsService postsService) {
        this.postsService = postsService;
        this.LOG = LoggerFactory.getLogger(PostsService.class);
    }

    @PostMapping("")
    String sampleSavePost() {
        LOG.info("sampleSavePost called");
        return postsService.addSinglePost(new Post(1L, "War of art", "This is some short description"));
    }

    @GetMapping("/{id}")
    List<Post> getPostById(@PathVariable Long id) {
        return postsService.getPostById(id);
    }
}
