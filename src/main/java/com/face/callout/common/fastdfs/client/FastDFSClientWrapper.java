package com.face.callout.common.fastdfs.client;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Component
public class FastDFSClientWrapper {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 支持的图片类型
     */
    private static final String[] SUPPORT_IMAGE_TYPE = {"JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"};
    private static final List<String> SUPPORT_IMAGE_LIST = Arrays.asList(SUPPORT_IMAGE_TYPE);

    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getAccessUrl(storePath);
    }

    public String upload(InputStream is, long fileSize, String fileExtName) throws IOException {
        StorePath storePath = storageClient.uploadFile(is, fileSize, fileExtName, null);
        return getAccessUrl(storePath);
    }

//    public String uploadFile(File file) throws IOException {
//        StorePath storePath = storageClient.uploadFile(file, file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
//        return getAccessUrl(storePath);
//    }

    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getAccessUrl(storePath);
    }

    public String uploadFile(byte[] content, String fileExtension) {
        ByteArrayInputStream stream = new ByteArrayInputStream(content);
        StorePath storePath = storageClient.uploadFile(stream, content.length, fileExtension, null);
        return getAccessUrl(storePath);
    }

    private String getAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }

    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }
}
