package org.picon.domain;

import java.util.List;
import java.util.Objects;

public class PostImages {
    private final List<PostImage> postImages;

    public PostImages(List<PostImage> postImages) {
        this.postImages = postImages;
    }

    @Override public String toString() {
        return "PostImages{" +
                "postImages=" + postImages +
                '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostImages that = (PostImages) o;
        return postImages.equals(that.postImages);
    }

    @Override public int hashCode() {
        return Objects.hash(postImages);
    }
}

