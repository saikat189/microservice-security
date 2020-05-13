package com.practice.auth.client;


import io.tus.java.client.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class JavaTusClient {

    public static void main(String[] args) throws IOException, ProtocolException {

        TusClient tusClient = new TusClient();
        tusClient.setUploadCreationURL(URI.create("http://localhost:8082/upload").toURL());
        tusClient.enableResuming(new TusURLMemoryStore());

        TusUpload tusUpload = 
                new TusUpload(new File("/home/f5097959/Desktop/Parikrama/Parikrama_Dec1.pdf"));

        TusExecutor tusExecutor = new TusExecutor() {
            @Override
            protected void makeAttempt() throws ProtocolException, IOException {
                TusUploader tusUploader = tusClient.resumeOrCreateUpload(tusUpload);
                tusUploader.setChunkSize(1024);

                do {
                    long totalBytes = tusUpload.getSize();
                    long bytesUploaded = tusUploader.getOffset();
                    double progress = (double) (bytesUploaded / totalBytes) * 100;

                    System.out.println("Upload at "+progress+"%");
                }
                while (tusUploader.uploadChunk() > -1);

                tusUploader.finish();
            }
        };
        
        boolean success = tusExecutor.makeAttempts();

        if (success) {
            System.out.println("Upload successful");
        }
        else {
            System.out.println("Upload interrupted");
        }
    }
    
    
    
}
