package com.imager.core.controller;

import com.imager.core.api.model.ResizeRequest;
import com.imager.core.api.model.ResizeResponse;
import com.imager.core.utils.ImageUtils;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.opencv.core.Mat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ImageController {

  private final ImageUtils imageUtils;

  @PostMapping
  public ResponseEntity<ResizeResponse> uploadImage(@RequestParam("width") Integer resizeWidth
      , @RequestParam("file") MultipartFile file, @RequestParam("height") Integer resizeHeight) throws
      Exception {

    byte[] data = file.getBytes();

    ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
    BufferedImage image = ImageIO.read(inputStream);

    Mat mat = imageUtils.imageToMat((BufferedImage) image);

    Image resizedImage = imageUtils
        .resize(mat, resizeHeight,resizeWidth);

//    for testing
//    imageUtils.saveImage(resizedImage,"/home/krishna/Desktop/resize.jpg","JPG");

    ResizeResponse response = new ResizeResponse();
    response.setBase64(imageUtils.toBase64(resizedImage));
    response.setFileName(file.getOriginalFilename());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
