package org.picon.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;


public class Post {
    @Getter
    private final Long id;

    @Getter
    private final Coordinate coordinate;
    @Getter
    private final Address address;
    @Getter
    private final LocalDate createDate;

    private String content;
    private PostImages postImages;
    private Color color;

    private Post(Long id, Coordinate coordinate, Address address, LocalDate createDate) {
        this.id = id;
        this.coordinate = coordinate;
        this.address = address;
        this.createDate = createDate;
        this.color = Color.BLACK;
    }

    public static Post init(Long id, Coordinate coordinate, Address address) {
        return new Post(id, coordinate, address, LocalDate.now());
    }

    @Override public String toString() {
        return "Post{" +
                "coordinate=" + coordinate +
                ", address=" + address +
                ", createDate=" + createDate +
                ", content='" + content + '\'' +
                ", postImages=" + postImages +
                ", color=" + color +
                '}';
    }

    public void saveImages(PostImage... postImages) {
        this.postImages = new PostImages(new ArrayList<>(Arrays.asList(postImages)));
    }

    protected PostImages getPostImages() {
        return postImages;
    }

    public void changeColor(Color updateColor) {
        this.color = updateColor;
    }

    protected Color getColor() {
        return color;
    }
}