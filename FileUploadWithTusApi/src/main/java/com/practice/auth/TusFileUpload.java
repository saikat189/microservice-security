package com.practice.auth;

import me.desair.tus.server.TusFileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class TusFileUpload {
    
    @Value("${app.tus-upload-directory}")
    private String tusUploadDirectory;
    
    public static void main(String[] args) {
        SpringApplication.run(TusFileUpload.class, args);
    }

    @Bean
    public TusFileUploadService tusFileUploadService() {
        return new TusFileUploadService().withStoragePath(tusUploadDirectory)
                .withUploadURI("/upload");
    }
}