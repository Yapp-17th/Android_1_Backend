package org.picon.global.security;

import lombok.RequiredArgsConstructor;
import org.picon.auth.entity.Member;
import org.picon.auth.exception.MemberNotFoundException;
import org.picon.auth.repository.MemberRepository;
import org.picon.global.config.CacheKey;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Cacheable(value = CacheKey.MEMBER, key = "#identity", cacheManager = "cacheManager")
    public UserDetails loadUserByUsername(String identity) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername is Running");
        Member member = memberRepository.findByIdentity(identity).orElseThrow(MemberNotFoundException::new);

        return new User(member.getIdentity(), member.getPassword(), makeGrantedAuthority(member.getRole()));
    }

    private List<? extends GrantedAuthority> makeGrantedAuthority(String role) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_" + role));
        return list;
    }
}
