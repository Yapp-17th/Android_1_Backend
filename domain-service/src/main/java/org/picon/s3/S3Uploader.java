package org.picon.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3 amazonS3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    public String bucket;


    public List<String> uploadImages(MultipartFile[] multipartFiles, String dirName) {
        List<String> ImagesUrls = new ArrayList<>();
        Stream<MultipartFile> multipartFileStream = Arrays.stream(multipartFiles);
        multipartFileStream.forEach(multipartFile -> {
            try {
                String fileName = dirName + "/" + UUID.randomUUID().toString();
                String imageUrl = putS3(multipartFile, fileName);
                ImagesUrls.add(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return ImagesUrls;
    }

    public String uploadImage(MultipartFile multipartFile, String dirName) throws IOException {
        String fielName = dirName+"/" +UUID.randomUUID().toString();
        String profileUrl = putS3(multipartFile, fielName);
        return profileUrl;
    }

    private String putS3(MultipartFile multipartFile, String fileName) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentType(MediaType.IMAGE_PNG_VALUE);
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentDisposition(fileName);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket,fileName, multipartFile.getInputStream(),objectMetadata);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3Client.putObject(putObjectRequest);

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

}