package com.theironyard.controllers;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
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
import java.util.ArrayList;
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
    public void upload(MultipartFile file, HttpServletResponse response, boolean isPermanent, String comment) throws IOException {
        //Create file with name__original file name___then save to public folder
        File f = File.createTempFile("file", file.getOriginalFilename(), new File("public"));
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());

        AnonFile anonFile = new AnonFile();
        anonFile.originalName = file.getOriginalFilename();
        anonFile.name = f.getName();
        anonFile.isPermanent = isPermanent;
        anonFile.comment = comment;
        files.save(anonFile);

//        List<AnonFile> filesList = (List<AnonFile>) files.findAll();
        ArrayList<AnonFile> filteredList = (ArrayList<AnonFile>) files.findByIsPermanentOrderByIdAsc(false);
//        for(AnonFile af : filesList){
//            if(!af.isPermanent){
//                filteredList.add(af);
//            }
//        }
        //After 5 files delete the oldest file (found at index 0)
        if(filteredList.size() > 5){
            AnonFile af = filteredList.get(0);
            files.delete(af);
        }

        response.sendRedirect("/");
    }//End of upload method

}//End of Controller
