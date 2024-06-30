package com.rest.template.service;

import com.rest.template.model.Demo;
import com.rest.template.repository.DemoRepository;
import com.rest.template.utils.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

@Service
@AllArgsConstructor
public class DemoService {

    private final DemoRepository demoRepository;

    public List<Demo> getAllImage() throws IOException,
            DataFormatException {
        List<Demo> demoList = demoRepository.findAll();
        List<Demo> finalDemo = new ArrayList<>();
        for(Demo demo: demoList){
            demo.setImageData(ImageUtils.decompressImage(demo.getImageData()));
            finalDemo.add(demo);
        }
        return finalDemo;
    }

    public String uploadImage(MultipartFile imageFile) throws IOException {
        Demo imageToSave = Demo.builder().
                imageData(ImageUtils.compressImage(imageFile.getBytes())).
                name(imageFile.getName()).
                build();
        demoRepository.save(imageToSave);
        return "File uploaded successfully : " + imageFile.getOriginalFilename();
    }
}
