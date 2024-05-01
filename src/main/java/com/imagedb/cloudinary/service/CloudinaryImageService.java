package com.imagedb.cloudinary.service;

import com.imagedb.cloudinary.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryImageService {

    public BaseResponse upload(MultipartFile file, String fileName);

    BaseResponse getImageByFileName(String fileName);
}
