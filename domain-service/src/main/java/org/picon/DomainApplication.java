package org.picon;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.picon.domain.Post;
import org.picon.dto.PostDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class DomainApplication {
    public static void main(String args[]){
        SpringApplication.run(DomainApplication.class,args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Configuration configuration = modelMapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        modelMapper.createTypeMap(Post.class, PostDto.class)
                .addMapping(e -> e.getMember().getProfileImageUrl(), PostDto::setProfileImageUrl);
        return modelMapper;
    }
}
