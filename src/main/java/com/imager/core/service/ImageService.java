package com.imager.core.service;


import com.imager.core.model.ImageModel;

public interface ImageService {

  ImageModel resizeImage(Byte[] data, Integer height, Integer width, String name,String imageType);
  ImageModel findById(Long id);
}
