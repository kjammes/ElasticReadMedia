package com.jayesh.elasticsearch.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.jayesh.elasticsearch.iservices.IPostsService;
import com.jayesh.elasticsearch.models.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostsService implements IPostsService {

    private final ElasticsearchClient client;
    private final Logger LOG;

    PostsService(ElasticsearchClient client) {
        this.client = client;
        this.LOG = LoggerFactory.getLogger(PostsService.class);
    }

    @Override
    public String addSinglePost(Post post) {
        String returnValue;
        try {
            IndexResponse response = client.index(i -> i
                    .index("posts")
                    .id(post.getId().toString())
                    .document(post)
            );
            returnValue = "Indexed with version " + response.version();
            LOG.info(returnValue);
        } catch (Exception e) {
            LOG.error("Failed to save Post object to index posts");
            LOG.error(e.getLocalizedMessage());
            returnValue = e.getLocalizedMessage();
        }
        return returnValue;
    }
}
