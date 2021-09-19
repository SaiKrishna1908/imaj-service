package com.imager.core.controller;


import com.imager.core.api.model.ResizeResponse;
import com.imager.core.model.ImageModel;
import com.imager.core.service.ImageService;
import com.imager.core.utils.ImageUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import javax.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.ArrayUtils;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ImageController {

  private final ImageUtils imageUtils;
  private final ImageService imageService;

  // TODO: pass file type in request
  @PostMapping
  public ResponseEntity<ResizeResponse> uploadImage(@RequestParam("file") MultipartFile file,
      @RequestParam("height") Integer height, @RequestParam("width") Integer width,
      @RequestParam("imageType") String imageType) throws
      Exception {

    
    Byte[] data = ArrayUtils.toObject(file.getBytes());

    ImageModel resizedData = imageService.resizeImage(data,
        height, width, file.getOriginalFilename(), imageType);        

    ResizeResponse response = new ResizeResponse();
    response.setFileName(file.getOriginalFilename());
    response.setId(resizedData.getId());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_OCTET_STREAM)
  public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws Exception{

    ImageModel imageModel= imageService.findById(id);
    byte[] data = ArrayUtils.toPrimitive(imageModel.getData());
    
    return new ResponseEntity<byte[]>(data, HttpStatus.OK);
  }

}
