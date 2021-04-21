package com.imager.core.api.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ResizeResponse {

    private String base64;
    private String fileName;

}
