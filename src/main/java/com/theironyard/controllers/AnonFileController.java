package com.theironyard.controllers;

import com.theironyard.entities.AnonFile;
import com.theironyard.services.AnonFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by earlbozarth on 11/18/15.
 */

@RestController
public class AnonFileController {

    @Autowired
    AnonFileRepository files;


    @RequestMapping("/files")
    public List<AnonFile> getFiles(){
        return (List<AnonFile>) files.findAll();
    }//end of getFiles method


    @RequestMapping("/upload")
    public void upload(MultipartFile file, HttpServletResponse response) throws IOException {
        //Create file with name__original file name___then save to public folder
        File f = File.createTempFile("file", file.getOriginalFilename(), new File("public"));
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());

        AnonFile anonFile = new AnonFile();
        anonFile.originalName = file.getOriginalFilename();
        anonFile.name = f.getName();
        files.save(anonFile);

        response.sendRedirect("/");
    }//End of upload method

}//End of Controller
