package com.imager.core.service;

import com.imager.core.model.ImageModel;
import com.imager.core.repository.ImageRepository;
import com.imager.core.utils.ImageUtils;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService{

  private final ImageUtils imageUtils;
  private final ImageRepository imageRepository;
  @Override
  public ImageModel resizeImage(Byte[] data, Integer height, Integer width, String name)  {

    ByteArrayOutputStream outputStream = null;

    // TODO : compress image before saving
    try {

      //save the image
      ImageModel imageModel = imageRepository.save(ImageModel.builder().
          data(data).fileName(name).build());

      ByteArrayInputStream inputStream = new ByteArrayInputStream(ArrayUtils.toPrimitive(data));

      BufferedImage image = ImageIO.read(inputStream);

      // Create Mat object from bytes
      Mat mat = imageUtils.imageToMat(image);

      // Resize Image
      Image resizedImageModel = imageUtils.resize(mat, height,width);

      // Convert Image to bytes
      outputStream = new ByteArrayOutputStream();
      ImageIO.write((BufferedImage) resizedImageModel, "JPG", outputStream);



      ImageModel resizedImage = ImageModel.builder()
          .data(ArrayUtils.toObject(outputStream.toByteArray()))
          .fileName(name).id(imageModel.getId()).build();

      return resizedImage;
    }
    catch (Exception e){
      System.out.println("Error resizing image cannot read or write image data");
      e.printStackTrace();
    }
    return new ImageModel();
  }

  public ImageModel findById(Long id){
    return imageRepository.findById(id).get();
  }
}
