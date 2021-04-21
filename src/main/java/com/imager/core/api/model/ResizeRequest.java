package com.imager.core.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ResizeRequest {

  @JsonProperty("width")
  private Integer resizeWidth;

  @JsonProperty("heigth")
  private Integer resizeHeight;

  @JsonProperty("type")
  private String fileType;

}
