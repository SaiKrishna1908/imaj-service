package com.imager.core.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

@Component
public class ImageUtils {

  // Save an Image to file system
   public void saveImage(Image image, String path, String type) {

    int w = image.getWidth(null);
    int h = image.getHeight(null);

     BufferedImage bufferedImage =(BufferedImage) image;
     System.out.println("width is "+w+" height is "+h);
    try{
      ImageIO.write(bufferedImage,type, new File(path));
    }
    catch (Exception e){
      System.out.println("Error Saving the image");
    }
  }

  // Convert Image to base64 very slow to communicate with this technique
  public String toBase64(Image image, String imageType) throws Exception{

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    try{

      ImageIO.write((BufferedImage) image,imageType, outputStream);

    }
    catch (Exception e){
      System.out.println("Error While converting image to base64");
    }

    return Base64.getEncoder().encodeToString(outputStream.toByteArray());
  }

}
