package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.PhotoRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
@AllArgsConstructor
@Slf4j
@Service
public class PhotoServiceBean implements PhotoService{
    private final PhotoRepository  photoRepository;
    private final EmployeeRepository employeeRepository;
    @Override
    public void addPhoto(MultipartFile file, Integer photoId) throws IOException {
        Photo photo = photoRepository.findById(photoId).orElseThrow( () -> new ResourceNotFoundException() );
        photo.setImage(file.getBytes());
        photoRepository.save(photo);
    }

    @Override
    public byte[] getPhoto(Integer photoId) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(() -> new ResourceNotFoundException());
        return photo.getImage();
    }
}
