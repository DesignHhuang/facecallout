package com.face.callout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.face.callout.common.fastdfs.client.FastDFSClientWrapper;
import com.face.callout.entity.CallOut;
import com.face.callout.entity.Dataset;
import com.face.callout.entity.Person;
import com.face.callout.repository.DatasetRepository;
import com.face.callout.repository.FaceMarkRepository;
import com.face.callout.repository.PersonRepository;
import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import com.google.common.base.Utf8;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.nio.cs.UTF_32;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppApplicationTests {

    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FaceMarkRepository faceMarkRepository;

    @Autowired
    FastDFSClientWrapper fastDFSClientWrapper;


    @Test
    public void testCount() {
        System.out.println(personRepository.countPeopleByIdBetweenAndIsGrouped(1l, 1000l, true));
    }

    @Test
    public static void sendPost(/*String url, Map<String, String> map*/) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String[] arr = {"oVbJ3v0grmpKkNYMqVHP6dcixOXE", "oVbJ3v7VVWIk5cd8YtiefpMXFir4", "oVbJ3v0hNDSUcGUX1AGVltsmvXhI"};
        HttpPost httppost = new HttpPost("http://wxoa.wxjggl.com:6005/visit/api/weixin/getYongHuList");
        StringEntity entity1 = new StringEntity(arr.toString(), Charset.forName("UTF-8"));
        httppost.setEntity(entity1);
        entity1.setContentType("application/json");

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity);
            System.out.println(result);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addDataset() {
        String[] datasets = new String[]{"msceleb", "yc_data", "casia", "vgg", "vgg2"};
        for (String d : datasets) {
            Dataset dataset = new Dataset();
            dataset.setName(d);
            datasetRepository.save(dataset);
        }
    }
}

/*
    @Test
    public void addYc_data() throws IOException, InterruptedException {
        String yc_dir = "/data/face/yc_data/day_time";
        //String yc_dir = "/Users/zangcf/daytime";
        File[] dirs = new File(yc_dir).listFiles();
        Dataset dataset_yc = datasetRepository.getByName("yc_data");

        ArrayList<YcThread> threads = new ArrayList<>();
        for (File dir : dirs) {
            if (dir.isDirectory()) {
                YcThread thread = new YcThread(dir, dataset_yc);
                thread.start();
                threads.add(thread);

            }
        }

        for (YcThread thread : threads) {
            thread.join();
        }
*/


//        File[] files = new File("/Users/zangcf/image_0301").listFiles();
//        for (File file:files){
//            Person person = new Person();
//            person.setName(file.getName());
//            Person savedPerson = personRepository.save(person);
//
//            File[] images = file.listFiles();
//            for (File img: images){
//                String path = fastDFSClientWrapper.uploadFile(FileUtils.readFileToByteArray(img), "jpg");
//
//                if (img.isFile()){
//                    CallOut callOut = new CallOut();
//                    callOut.setDataset(dataset_yc);
//                    callOut.setPerson(savedPerson);
//                    callOut.setImageUrl(path);
//                    faceMarkRepository.save(callOut);
//                }
//            }
//            System.out.println(file.getName());
//        }

/*
    }

}*/

/*
class YcThread extends Thread {
    private Thread t;
    private File imageDir;
    private Dataset dataset;

    YcThread(File dir, Dataset dset) {
        imageDir = dir;
        dataset = dset;
    }

    public void run() {
        System.out.println("Starting " + imageDir.getName());
        try {
            File[] files = imageDir.listFiles();
            for (File file : files) {
                Person person = new Person();
                person.setName(file.getName());
                Person savedPerson = personRepository.save(person);

                File[] images = file.listFiles();
                for (File img : images) {
                    String path = fastDFSClientWrapper.uploadFile(FileUtils.readFileToByteArray(img), "jpg");

                    if (img.isFile()) {
                        CallOut callOut = new CallOut();
                        callOut.setDataset(dataset);
                        callOut.setPerson(savedPerson);
                        callOut.setImageUrl(path);
                        faceMarkRepository.save(callOut);
                    }
                }
                System.out.println(file.getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(imageDir.getName() + " exiting.");
    }
}

}
*/
