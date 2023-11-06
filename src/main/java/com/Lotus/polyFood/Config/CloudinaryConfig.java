package com.Lotus.polyFood.Config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Component
public class CloudinaryConfig {
    private final String CLOUD_NAME = "dghlr3bmj";
    private final String API_KEY = "471836221675997";
    private final String API_SECRET = "DEWpNoXTqxBj1jZefRuClp_H3oQ";
    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);

    }
}
