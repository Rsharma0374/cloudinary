package com.imagedb.cloudinary.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import org.modelmapper.ModelMapper;

public class CloudinaryConfig {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    public Cloudinary getCloudinary(){

        Map config = new HashMap();
        config.put("cloud_name", "");
        config.put("api_key", "");
        config.put("api_secret", "");
        config.put("secure", true);


        return new Cloudinary(config);
    }
}
