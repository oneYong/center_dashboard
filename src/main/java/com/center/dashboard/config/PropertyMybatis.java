package com.center.dashboard.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by WYKIM on 2017-06-19.
 */
@Component
@Data
@ConfigurationProperties(locations={"application-datasource.yml"},prefix="Mybatis")
public class PropertyMybatis {
    private String configPath;
}
