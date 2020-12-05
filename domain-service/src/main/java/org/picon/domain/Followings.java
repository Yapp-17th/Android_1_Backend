package org.picon.domain;

import org.picon.DomainApplication;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
        List<Member> members = follows.stream()
                .map(follow -> follow.followMember)
                .map(DomainApplication::initializeAndUnproxy)
                .collect(Collectors.toList());
        return members;
    }

    protected boolean isAlreadyFollowingMember(Member followingMember) {
        return getFollowingMembers().contains(followingMember);
    }
}
