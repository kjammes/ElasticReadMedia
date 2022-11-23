package com.jayesh.elasticsearch.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.jayesh.elasticsearch.iservices.IPostsService;
import com.jayesh.elasticsearch.models.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Post> getPostById(Long id) {
        List<Post> posts = new ArrayList<>();
        try {
            Query byId = MatchQuery.of(m -> m
                    .field("id")
                    .query(id)
            )._toQuery();

            SearchResponse<Post> response = client.search(s -> s
                            .index("posts")
                            .query(q -> q
                                    .bool(b -> b.must(byId))
                            ),
                    Post.class
            );
            List<Hit<Post>> hits = response.hits().hits();
            hits.forEach(hit -> posts.add(hit.source()));
        } catch (IOException ioException) {
            LOG.error("IOException occurred");
            LOG.error(ioException.getLocalizedMessage());
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            LOG.error(e.toString());
        }
        return posts;
    }
}
