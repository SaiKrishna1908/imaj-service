package com.imager.core.service;


import com.imager.core.model.ImageModel;

public interface ImageService {

  ImageModel resizeImage(Byte[] data, Integer height, Integer width, String name);
  ImageModel findById(Long id);
}
