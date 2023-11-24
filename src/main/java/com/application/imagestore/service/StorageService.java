package com.application.imagestore.service;

import com.application.imagestore.entity.ImageData;
import com.application.imagestore.repository.StorageRepository;
import com.application.imagestore.utills.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;

    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = storageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build()
        );
        if(imageData != null){
          return "file upload success :"+file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dpImageData = storageRepository.findByName(fileName);
        byte[] image = ImageUtils.decompressImage(dpImageData.get().getImageData());
        return image;
    }

}
