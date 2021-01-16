package org.picon.domain;

import lombok.NoArgsConstructor;
import org.picon.DomainApplication;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Followers {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "followMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Follow> follows = new ArrayList<>();

    protected List<Member> getFollowerMembers() {
        List<Member> members = follows.stream()
                .map(e -> e.member)
//                .map(DomainApplication::initializeAndUnproxy)
                .collect(Collectors.toList());
        return members;
    }
}
