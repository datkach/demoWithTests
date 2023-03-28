package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    void addPhoto(MultipartFile file, Integer photoID) throws IOException;
    byte[] getPhoto(Integer photoId);
}
