package com.corgi.esnews.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class EsConfiguration {

    private String index;
    private String host;
    private String port;
    private String scheme;
}
