package com.practice.auth.controller;

import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@CrossOrigin(exposedHeaders = {"Location","Upload-Offset"})
public class UploadController {
    
    @Autowired
    private TusFileUploadService tusFileUploadService;
    @Value("${app.app-upload-directory}")
    private String uploadDirectoryName;
    @Value("${app.tus-upload-directory}")
    private String tusUploadDirectoryName;
    private Path uploadDirectory;
    private Path tusUploadDirectory;


    @RequestMapping(value = { "/upload", "/upload/**" }, method = { RequestMethod.POST,RequestMethod.GET })
    public void upload(HttpServletRequest request, HttpServletResponse response)throws IOException{

        uploadDirectory = Paths.get(uploadDirectoryName);
        try {
            Files.createDirectories(this.uploadDirectory);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.tusUploadDirectory = Paths.get(tusUploadDirectoryName);
        
        tusFileUploadService.process(request,response);
        String uploadURI = request.getRequestURI();
        UploadInfo uploadInfo = null;

        try {
            uploadInfo = tusFileUploadService.getUploadInfo(uploadURI);
        } catch (TusException e) {
            e.printStackTrace();
        }
        
        if(uploadInfo!=null && !uploadInfo.isUploadInProgress()){
            try(InputStream is=tusFileUploadService.getUploadedBytes(uploadURI)){
                Path output = uploadDirectory.resolve(uploadInfo.getFileName());
                Files.copy(is, output, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException | TusException e){
                e.printStackTrace();
            }

            try {
                this.tusFileUploadService.deleteUpload(uploadURI);
            }
            catch (IOException | TusException e) {
                e.printStackTrace();
            }
        }

    }

    @Scheduled(fixedDelayString = "PT24H")
    private void cleanup() {
        Path locksDir = tusUploadDirectory.resolve("locks");
        if (Files.exists(locksDir)) {
            try {
                this.tusFileUploadService.cleanup();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
