package ua.ivan909020.admin.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.ivan909020.admin.exceptions.FailedSaveFileException;
import ua.ivan909020.admin.services.PhotoStorageService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PhotoStorageServiceDefault implements PhotoStorageService {

    @Value("${admin.images-upload-path}")
    private String imagesUploadPath;

    @Value("${admin.server-url}")
    private String serverUrl;

    @Override
    public String store(MultipartFile photo) {
        if (photo == null) {
            throw new IllegalArgumentException("Photo should not be NULL");
        }
        String fileName = UUID.randomUUID() + ".jpg";
        String filePath = imagesUploadPath + "/" + fileName;
        try {
            photo.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new FailedSaveFileException("Failed save photo", e);
        }
        return serverUrl + "/images/" + fileName;
    }

}
