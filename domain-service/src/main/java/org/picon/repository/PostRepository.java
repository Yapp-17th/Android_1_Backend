package org.picon.repository;

import org.picon.domain.Address;
import org.picon.domain.Coordinate;
import org.picon.domain.Emotion;
import org.picon.domain.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    private List<Post> posts = new ArrayList<>();

    public PostRepository() {
        Post post1 = Post.init(1L,
                new Coordinate(1.1, 1.2),
                new Address("Address", null, null, null),
                Emotion.BLUE_GRAY,
                "memo"
        );
        Post post2 = Post.init(2L,
                new Coordinate(1.1, 1.2),
                new Address("Address", null, null, null),
                Emotion.BLUE_GRAY,
                "memo"
        );
        Post post3 = Post.init(3L,
                new Coordinate(1.1, 1.2),
                new Address("Address", null, null, null),
                Emotion.BLUE_GRAY,
                "memo"
        );
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
    }

    public Post getPostById(Long postId) {
        return posts.stream()
                .filter(e -> e.getId().equals(postId))
                .findFirst()
                .orElse(null);
    }
}
