package com.imager.core.service;

import com.imager.core.exceptions.ImageIOException;
import com.imager.core.exceptions.ImageTypeNotSupported;
import com.imager.core.model.ImageModel;
import com.imager.core.repository.ImageRepository;
import com.imager.core.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;

import org.apache.commons.lang3.ArrayUtils;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

	private final ImageUtils imageUtils;
	private final ImageRepository imageRepository;

	/*
	 * TODO : compress image before saving
	 * 
	 * saves the image
	 * Create's Mat object from bytes
	 * Resize Image
	 * Convert Image to bytes
	 */
	
	@Override
	public ImageModel resizeImage(Byte[] data, Integer height, Integer width,
	    String name, String imageType)  {

		if (imageType == null) {
			log.debug("Image type is null");
			throw new ImageTypeNotSupported("[ImageType null]");
		}
				
		BufferedImage image = streamToImage(data);		
		
		MarvinImage marvinImage = new MarvinImage(image);
		Scale scale = new Scale();
		scale.load();
		scale.setAttribute("newWidth", width);
    scale.setAttribute("newHeight", height);
    
    scale.process(marvinImage.clone(), marvinImage, null, null, false);
		
		byte[] resizedByte = imageToStream(marvinImage.getBufferedImageNoAlpha(),
		                    imageType);
		
				
		ImageModel resizedImageModel = ImageModel.builder()
		                                .data(ArrayUtils.toObject(resizedByte))
		                                .fileName(name).build();
			
		return imageRepository.save(resizedImageModel);			
	}

	public ImageModel findById(Long id) {
		return imageRepository.findById(id).get();
	}
	
	/*
	 * Utility function to change stream to BufferedImage data
	 */
	private BufferedImage streamToImage(Byte stream[]) {
	  try {
	    ByteArrayInputStream inputStream = 
	        new ByteArrayInputStream(ArrayUtils.toPrimitive(stream));	    
	    return ImageIO.read(inputStream);	    	    	    
	  }
	  catch (Exception e) {
	    log.debug("ImageIO Error");
	    throw new ImageIOException("ImageIO Exception");
	  }
	}
	
	/*
	 * Utility function to change BufferedImage to byte[]
	 */
	private byte[] imageToStream(BufferedImage image, String imageType) {
	  try {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ImageIO.write(image, imageType, out);
	    return out.toByteArray();
	  }
	  catch (Exception e) {
	    log.debug("ImageIO Error");
	    throw new ImageIOException("ImageIO Exception");
	  }
	}
}
