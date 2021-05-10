package com.imager.core.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

@Component
public class ImageUtils {

  // Returns A AWT image
  public static Mat loadImage(String path) {

    Imgcodecs imgcodecs = new Imgcodecs();
    Mat imageMatrix =  imgcodecs.imread(path);

    return  imageMatrix;
  }

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

  public Image resize(Mat mat, int heigth, int width){
    Mat dst = new Mat();
    Imgproc.resize(mat, dst, new Size(width, heigth));
    return matToImage(dst);
  }


  public Image matToImage(Mat mat){
    return  HighGui.toBufferedImage(mat);
  }

  public Mat imageToMat(BufferedImage image, String imageType) throws  Exception{

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(image, imageType, outputStream);
    outputStream.flush();
    return  Imgcodecs.imdecode(new MatOfByte(outputStream.toByteArray()),
        Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
  }

}
