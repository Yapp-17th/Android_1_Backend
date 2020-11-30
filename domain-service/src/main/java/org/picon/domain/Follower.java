package org.picon.domain;

import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Follower {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "followMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Follow> follows = new ArrayList<>();

    public List<Member> getFollowerMembers() {
        return follows.stream()
                .map(e -> e.member)
                .collect(Collectors.toList());
    }
}
