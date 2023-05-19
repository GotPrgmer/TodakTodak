package com.ssafy.todaktodak.global.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageClient {
    String uploadFile(MultipartFile file) throws IOException;

    void deleteFile(String fileUrl) throws IOException;

}
