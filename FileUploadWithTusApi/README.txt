The POC is for resumable file upload.

Using TUS api we can break a file in the client end itself using Tus client and 
upload it with multiple chunks using multiple http request.

I am using JAVA tus client, but javascript,react.js client also available for web development.

JavaTusClient.java: Standalone java class acts as a http Tus client.

TusFileUpload.java: Springboot Application acts as a server.

API Link: https://tus.io/

How It Works?

In the first step, client sends a POST request with the file's upload length(size) to the server. 
The server creates a new file and responds with the file's location.

Now,Patch request is used to write bytes to the file at offset Upload-Offset. 
Each patch request should contain a Upload-Offset field indicating the current offset of the file data being uploaded.

Then HEAD request to get the current file offset
The patch request above was completed successfully without any network problems and the file was uploaded completely.
But if there was a network issue while the file was being uploaded and the upload failed in the middle. 
The client should not upload the entire file again but rather start uploading the file from the failed byte. 
This is where the HEAD request helps.


