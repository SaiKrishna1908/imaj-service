package com.imager.core.service;

import com.imager.core.exceptions.ImageTypeNotSupported;
import com.imager.core.model.ImageModel;
import com.imager.core.repository.ImageRepository;
import com.imager.core.utils.ImageUtils;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;
import org.opencv.core.Mat;
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
	public ImageModel resizeImage(Byte[] data, Integer height, Integer width, String name, String imageType) {

		if (imageType == null) {
			log.debug(String.format("Image type is null"));
			throw new ImageTypeNotSupported("[ImageType null]");
		}
		ByteArrayOutputStream outputStream = null;

		try {
		

			ImageModel imageModel = ImageModel.builder().data(data).fileName(name).build();
			

			ByteArrayInputStream inputStream = new ByteArrayInputStream(ArrayUtils.toPrimitive(data));
			BufferedImage image = ImageIO.read(inputStream);
			Mat mat = imageUtils.imageToMat(image, imageType);
			
			Image resizedImageModel = null;
			
			try {
				resizedImageModel = imageUtils.resize(mat, height, width);
			}
			catch(Exception e) {
				e.getMessage();
				log.debug(String.format("[Image type %s]",imageType));
				throw new ImageTypeNotSupported("ImageType not supported");
			}
			
			
			
			outputStream = new ByteArrayOutputStream();
			ImageIO.write((BufferedImage) resizedImageModel, imageType, outputStream);
			
			ImageModel resizedImage = ImageModel.builder().data(ArrayUtils.toObject(outputStream.toByteArray()))
					.fileName(name).build();
			
			return imageRepository.save(resizedImage);
			
		} catch(Exception e) {
		  e.getMessage();
		}
			
		
		return new ImageModel();
	}

	public ImageModel findById(Long id) {
		return imageRepository.findById(id).get();
	}
}
