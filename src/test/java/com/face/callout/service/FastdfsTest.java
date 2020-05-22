package com.face.callout.service;

import com.face.callout.common.fastdfs.client.FastDFSClientWrapper;
import com.face.callout.repository.FaceMarkRepository;
import com.github.tobato.fastdfs.conn.FdfsWebServer;
import com.github.tobato.fastdfs.service.DefaultGenerateStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastdfsTest {
    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper;

    @Autowired
    FaceMarkRepository faceMarkRepository;

    @Autowired
    DefaultGenerateStorageClient defaultGenerateStorageClient;

    @Autowired
    FdfsWebServer fdfsWebServer;

    @Test
    public void testFdfsWebServer() {
        System.out.println(fdfsWebServer.getWebServerUrl());
    }

    @Test
    public void testCreate() {
        String url = fastDFSClientWrapper.uploadFile("This is test", "png");
        System.out.println(url);
    }

}
