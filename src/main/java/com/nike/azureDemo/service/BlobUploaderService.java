package com.nike.azureDemo.service;


import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class BlobUploaderService {

    public String uploadBlobFileToContainer(String json){
        System.out.println("inside BlobUploaderService");

        DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder().build();
//VisualStudioCodeCredential defaultCredential = new VisualStudioCodeCredentialBuilder().build();

// Azure SDK client builders accept the credential as a parameter
// TODO: Replace <storage-account-name> with your actual storage account name
String connectStr = "DefaultEndpointsProtocol=https;AccountName=nikepreeti;AccountKey=svqE0aJQhD2mg595t7Nj+3YV0T6ki1x8mcSjZvK2dVge09kpZhE+xA+JRg5QDeAJwWBbwdjC7Gv4+AStxR9x0A==;EndpointSuffix=core.windows.net";
//String connectStr = "DefaultEndpointsProtocol=https;AccountName=nikedemosa;AccountKey=du1MxdU2TONTmdmoflzz0rAyFqjeINyoMkwh4S+cQtF/8VdElhB1550u2DIk5UZdQtAJ8ClBknUb+AStBg3L+g==;EndpointSuffix=core.windows.net";
        // BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
        //         .endpoint("https://nikepreeti.blob.core.windows.net/")
        //         .credential(defaultCredential)
        //         .buildClient();

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();


        // Create a unique name for the container
        String containerName = "quickstartblobs";
        // Create the container and return a container client object
        BlobContainerClient blobContainerClient = blobServiceClient.createBlobContainer(containerName);

        // Create the ./data/ directory and a file for uploading and downloading
        String localPath = "./data/";
        new File(localPath).mkdirs();
        String fileName = "quickstart" + ".txt";

// Get a reference to a blob
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

// Write text to the file
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(localPath + fileName, true);
            writer.write(json);
            writer.close();
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());

// Upload the blob
        blobClient.uploadFromFile(localPath + fileName);

        System.out.println("\nListing blobs...");

// List the blob(s) in the container.
for (BlobItem blobItem : blobContainerClient.listBlobs()) {
    System.out.println("\t" + blobItem.getName());
}

// Download the blob to a local file

// Append the string "DOWNLOAD" before the .txt extension for comparison purposes
String downloadFileName = fileName.replace(".txt", "DOWNLOAD.txt");

System.out.println("\nDownloading blob to\n\t " + localPath + downloadFileName);

// System.out.println("\nPress the Enter key to begin clean up");
// System.console().readLine();

// System.out.println("Deleting blob container...");
// blobContainerClient.delete();

// System.out.println("Deleting the local source and downloaded files...");
// localFile.delete();
// downloadedFile.delete();

// System.out.println("Done");

blobClient.downloadToFile(localPath + downloadFileName);
        return "success";
    }
}
