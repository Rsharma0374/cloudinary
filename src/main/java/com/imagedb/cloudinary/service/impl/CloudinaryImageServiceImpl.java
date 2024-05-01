package com.imagedb.cloudinary.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.imagedb.cloudinary.response.BaseResponse;
import com.imagedb.cloudinary.response.Error;
import com.imagedb.cloudinary.service.CloudinaryImageService;
import com.imagedb.cloudinary.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;
import java.net.URL;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryImageServiceImpl.class);

    @Autowired
    private Cloudinary cloudinary;

    /**
     * The `upload` method uploads a file to Cloudinary with error handling and returns a response indicating success or
     * failure.
     *
     * @param file The `file` parameter in the `upload` method is of type `MultipartFile`, which is a representation of an
     * uploaded file received in a multipart request. It contains the contents of the file as well as additional
     * information such as the file name, content type, and more. In this method,
     * @param fileName The `fileName` parameter in the `upload` method is a `String` variable that represents the public ID
     * of the file being uploaded to Cloudinary. It is used as a unique identifier for the uploaded file in the Cloudinary
     * storage.
     * @return The method `upload` returns a `BaseResponse` object.
     */
    @Override
    public BaseResponse upload(MultipartFile file, String fileName) {
        BaseResponse baseResponse = null;
        try {
            Map params = ObjectUtils.asMap(
                    "public_id", fileName,
                    "overwrite", false,
                    "resource_type", "image"
            );
            this.cloudinary.uploader().upload(file.getBytes(), params);
            baseResponse = Utility.getBaseResponse(HttpStatus.OK, "successfully uploaded");

        } catch (Exception e) {
            logger.error("Exception occurred while uploading image in cloudinary with probable cause -", e);
            Error error = new Error();
            error.setMessage(e.getMessage());
            baseResponse = Utility.getBaseResponse(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singleton(error));
        }
        return baseResponse;
    }

    /**
     * This Java function retrieves an image from Cloudinary, processes it, and returns a BaseResponse object containing
     * the image data.
     *
     * @param fileName The `getImageByFileName` method you provided seems to be retrieving an image from Cloudinary based
     * on the given `fileName`. The `fileName` parameter is likely the name of the image file you want to retrieve from
     * Cloudinary.
     * @return The method `getImageByFileName` returns a `BaseResponse` object where base64 og image is present.
     */
    @Override
    public BaseResponse getImageByFileName(String fileName) {
        BaseResponse baseResponse = null;
        try {
            String image = "";
            String imageUrl = cloudinary.url().transformation(new Transformation().width(200).height(200).crop("fill")).generate(fileName);
            Map<String, String> imageDetails = cloudinary.uploader().upload(imageUrl, ObjectUtils.asMap("resource_type", "image"));
            String imageUrlFromCloudinary = imageDetails.get("secure_url");
            byte[] imageBytes = IOUtils.toByteArray(new URL(imageUrlFromCloudinary));
            image =  Base64.getEncoder().encodeToString(imageBytes);

            baseResponse = Utility.getBaseResponse(HttpStatus.OK, image);

        } catch (Exception e) {
            logger.error("Exception occurred while get image from cloudinary with probable cause -", e);
            Error error = new Error();
            error.setMessage(e.getMessage());
            baseResponse = Utility.getBaseResponse(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singleton(error));
        }
        return baseResponse;
    }
}
