package org.picon.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Followings {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Follow> follows = new ArrayList<>();

    protected void following(Member member, Member followMember) {
        Follow follow = Follow.of(member, followMember);
        follows.add(follow);
    }

    public List<Member> getFollowingMembers() {
        return follows.stream()
                .map(e -> e.followMember)
                .collect(Collectors.toList());
    }

    protected boolean isAlreadyFollowingMember(Member followingMember) {
        return getFollowingMembers().contains(followingMember);
    }
}
