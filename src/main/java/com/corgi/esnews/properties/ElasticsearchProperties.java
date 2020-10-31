package com.corgi.esnews.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "elasticsearch")
@ConstructorBinding     // todo - 불변 클래스로 만듦
public class ElasticsearchProperties {

    private String host;
    private String port;
    private String scheme;
}
