The POC is for resumeable file upload.

Using TUS api we can break a file in the client end itself using Tus client and 
upload it with multiple chunks using multiple http request.

I am using JAVA tus client, but javascript,react.js client also available for web development.

JavaTusClient.java: Standalone java class acts as a http Tus client.

TusFileUpload.java: Springboot Application acts as a server.

API Link: https://tus.io/


