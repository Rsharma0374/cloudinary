/**
 * The CloudinaryConfig class in Java sets up and provides a Cloudinary object with configuration properties loaded from a
 * file.
 */
package com.imagedb.cloudinary.config;

import com.cloudinary.Cloudinary;
import com.imagedb.cloudinary.utility.Utility;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    private static final String CLOUD_NAME = "cloud_name";
    private static final String API_KEY = "api_key";
    private static final String API_SECRET = "api_secret";
    private static final String SECURE = "secure";
    private static final String CLOUDINARY_CONFIG_FILE = "/opt/configs/cloudinary.properties";


    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    /**
     * The function creates and returns a Cloudinary object with configuration properties loaded from a file.
     *
     * @return An instance of the Cloudinary class with the configuration parameters set based on the values retrieved from
     * the cloudinaryConfig properties file.
     */
    @Bean
    public Cloudinary getCloudinary(){

        Properties cloudinaryConfig = Utility.getConfig(CLOUDINARY_CONFIG_FILE);

        Map config = new HashMap();
        config.put(CLOUD_NAME, cloudinaryConfig.getProperty(CLOUD_NAME.toUpperCase()));
        config.put(API_KEY, cloudinaryConfig.getProperty(API_KEY.toUpperCase()));
        config.put(API_SECRET, cloudinaryConfig.getProperty(API_SECRET.toUpperCase()));
        config.put(SECURE, true);

        return new Cloudinary(config);
    }

}
