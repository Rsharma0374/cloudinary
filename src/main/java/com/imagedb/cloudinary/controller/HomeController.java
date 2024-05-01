/**
 * The HomeController class in the Cloudinary image service project handles requests for uploading and retrieving images
 * using Spring annotations.
 */
package com.imagedb.cloudinary.controller;

import com.imagedb.cloudinary.constants.EndPointReferrer;
import com.imagedb.cloudinary.response.BaseResponse;
import com.imagedb.cloudinary.service.CloudinaryImageService;
import com.imagedb.cloudinary.service.impl.CloudinaryImageServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(EndPointReferrer.CLOUDINARY)
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryImageServiceImpl.class);

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @GetMapping(EndPointReferrer.WELCOME)
    public String welcome() {
        return  "   ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥\n" +
                "  ♥                                                  ♥\n" +
                " ♥                  Welcome to                        ♥\n" +
                " ♥               Cloudinary Service                   ♥\n" +
                "  ♥                                                  ♥\n" +
                "   ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥\n";
    }

    /**
     * This Java function handles uploading an image file to a cloud service using Spring's @PostMapping annotation.
     *
     * @param file The `file` parameter in the `uploadImage` method is of type `MultipartFile` and represents the actual
     * image file that is being uploaded. It contains the binary data of the image file along with its metadata.
     * @param fileName The `fileName` parameter in the `uploadImage` method is a String type parameter that represents the
     * name of the file being uploaded. It is a required parameter as indicated by the `@NotNull` annotation, which ensures
     * that a non-null value is provided for this parameter.
     * @return The method `uploadImage` is returning a `ResponseEntity` object with a generic type of `BaseResponse`. The
     * response entity is created using the `cloudinaryImageService.upload(file, fileName)` method result as the body and
     * `HttpStatus.OK` as the HTTP status.
     */
    @PostMapping(EndPointReferrer.UPLOAD_IMAGE)
    public ResponseEntity<BaseResponse> uploadImage(@RequestParam(value = "file") @NotNull MultipartFile file,
                                                    @RequestParam(value = "fileName") @NotNull String  fileName) {
        logger.debug("{} controller started", EndPointReferrer.UPLOAD_IMAGE);
        return new ResponseEntity<>(cloudinaryImageService.upload(file, fileName), HttpStatus.OK);
    }

    /**
     * This Java function handles a POST request to retrieve an image by its file name using Cloudinary service.
     *
     * Args:
     *   fileName (String): The `fileName` parameter in the `getImage` method is a request parameter that specifies the
     * name of the image file that needs to be retrieved.
     *
     * Returns:
     *   A ResponseEntity object containing a BaseResponse object is being returned. The BaseResponse object is retrieved
     * by calling the getImageByFileName method from the cloudinaryImageService, and the HTTP status code is set to
     * HttpStatus.OK.
     */
    @PostMapping(EndPointReferrer.GET_IMAGE)
    public ResponseEntity<BaseResponse> getImage(@RequestParam(value = "fileName") @NotNull String  fileName) {
        logger.debug("{} controller started", EndPointReferrer.UPLOAD_IMAGE);

        return new ResponseEntity<>(cloudinaryImageService.getImageByFileName(fileName), HttpStatus.OK);
    }
}
