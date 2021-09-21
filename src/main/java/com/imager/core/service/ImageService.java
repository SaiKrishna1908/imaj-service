package com.imager.core.service;


import com.imager.core.model.ImageModel;

public interface ImageService {

  ImageModel resizeImage(Byte[] data, Integer height, Integer width,
      String name,String imageType) throws Exception;
  ImageModel findById(Long id);
}
