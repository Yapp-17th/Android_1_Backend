package org.picon.domain;

import lombok.*;
import org.picon.config.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "MEMBERS")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
public class Member extends BaseEntity {

    @Id @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String identity;
    private String nickName;
    private String password;
    private String role;
    private String profileImageUrl;

    /**
     * 내가 팔로우하는 사람들
     */
    private Followings followings;
    /**
     * 나를 팔로우하는 사람들
     */
    private Followers followers;

    public void following(Member followingMember) {
        if (followings.isAlreadyFollowingMember(followingMember)) {
            throw new IllegalStateException("이미 팔로잉한 멤버입니다.");
        }
        followings.following(this, followingMember);
    }

    public List<Member> getFollowingMembers() {
        return followings.getFollowingMembers();
    }

    public List<Member> getFollowerMembers() {
        return followers.getFollowerMembers();
    }

    public Boolean isFollowing(Member targetMember) {
        return followings.isAlreadyFollowingMember(targetMember);
    }

    public void unfollowing(Member followingMember) {
        if (!followings.isAlreadyFollowingMember(followingMember)) {
            throw new IllegalStateException("팔로잉하지 않은 멤버를 언팔로우 할 수 없습니다.");
        }
        followings.unfollowing(this, followingMember);
    }
}
