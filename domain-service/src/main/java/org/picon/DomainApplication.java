package org.picon;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.picon.domain.Member;
import org.picon.domain.Post;
import org.picon.dto.MemberDto;
import org.picon.dto.PostDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaAuditing
public class DomainApplication {
    public static void main(String args[]) {
        SpringApplication.run(DomainApplication.class, args);
    }

    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Configuration configuration = modelMapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        modelMapper.createTypeMap(Post.class, PostDto.class)
                .addMapping(e -> e.getMember().getProfileImageUrl(), PostDto::setProfileImageUrl)
                .addMapping(e -> e.getCreateDate(), PostDto::setCreatedDate);
        modelMapper.createTypeMap(Member.class, MemberDto.class)
                .addMapping(Member::getCreateDate, MemberDto::setCreatedDate);

        return modelMapper;
    }
}
