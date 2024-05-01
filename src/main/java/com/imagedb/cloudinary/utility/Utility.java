package com.imagedb.cloudinary.utility;

import com.imagedb.cloudinary.response.BaseResponse;
import com.imagedb.cloudinary.response.Payload;
import com.imagedb.cloudinary.response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class Utility {

    private static final Logger logger = LoggerFactory.getLogger(Utility.class);

    public static BaseResponse getBaseResponse(HttpStatus httpStatus, Object buzResponse) {
        logger.info("Inside getBaseResponse method");

        if (null == buzResponse)
            buzResponse = Collections.emptyMap();

        return BaseResponse.builder()
                .payload(new Payload<>(buzResponse))
                .status(
                        Status.builder()
                                .statusCode(httpStatus.value())
                                .statusValue(httpStatus.name()).build())
                .build();
    }

    /**
     * The function `getConfig` reads properties from a file specified by the `configPath` parameter and returns them as a
     * `Properties` object.
     *
     * @param configPath The `configPath` parameter in the `getConfig` method is a string that represents the file path to
     * the configuration file from which the properties are to be loaded. This method reads the properties from the
     * specified configuration file and returns them as a `Properties` object.
     * @return The method `getConfig` returns a `Properties` object containing the configuration properties loaded from the
     * file specified by the `configPath` parameter.
     */
    public static Properties getConfig(String configPath) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configPath));
        } catch (IOException e) {
            logger.error("Exception occurred while getting cloudinary config with probable cause - ", e);
        }
        return properties;
    }

}
