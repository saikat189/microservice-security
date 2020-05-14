package com.practice.auth.client;


import io.tus.java.client.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
                int actualProgress = 0;
                do {
                    long totalBytes = tusUpload.getSize();
                    double bytesUploaded = 0;
                    bytesUploaded += tusUploader.getOffset();
                    BigDecimal progress = new BigDecimal((bytesUploaded / (double)totalBytes) * 100);
                    if(progress.intValue()>actualProgress) {
                        actualProgress = progress.intValue();
                        System.out.println("Upload at " + actualProgress + "%");
                    }
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
