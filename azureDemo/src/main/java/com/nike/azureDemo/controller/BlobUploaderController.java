package com.nike.azureDemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nike.azureDemo.service.BlobUploaderService;

@RestController
public class BlobUploaderController {

    @Autowired BlobUploaderService blobUploaderService;

    @PostMapping(value = "/uploadFileToContainer")
    public String uploadBlobToContainer(@RequestBody String json){
        System.out.println("inside BlobUploaderController");
        if (json !=null) {
            blobUploaderService.uploadBlobFileToContainer(json);
            System.out.println("Jason :"+json);
            return "Blob uploaded successfully...";
        } else {
            return "Blob uploaded Failed...";
        }
    }
}
