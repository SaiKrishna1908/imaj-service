package com.imager.core.api.model;

import java.io.File;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ResizeResponse {

    private Long id;
    private String fileName;
}
