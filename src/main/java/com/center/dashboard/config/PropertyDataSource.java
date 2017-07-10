package com.center.dashboard.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by WYKIM on 2017-06-19.
 */
@Component
@Data
@ConfigurationProperties(locations={"application-datasource.yml"},prefix="DataSource")
public class PropertyDataSource {
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
}
