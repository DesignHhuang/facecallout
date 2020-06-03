package com.face.callout.controller;

import com.face.callout.common.restmodel.RestResult;
import com.face.callout.common.restmodel.RestResultBuilder;
import com.face.callout.entity.User;
import com.face.callout.repository.UserRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.util.Iterator;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    final
    private UserRepository userRepository;

    private static final ModelMapper INSTANCE = new ModelMapper();

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //获取当前用户信息
    @GetMapping(value = "/currentUser")
    @SuppressWarnings("unchecked")
    public RestResult currentUser() {
        User user = getCurrentUser();
        return RestResultBuilder.builder().success(user).build();
    }

    //更新用户信息
    @PostMapping(value = "/update")
    @SuppressWarnings("unchecked")
    public RestResult update(@RequestBody @Valid JSONObject data) {
        User user = userRepository.findOne(data.getLong("id"));
        INSTANCE.map(data, user);
        userRepository.save(user);
        return RestResultBuilder.builder().success(user).build();
    }

    //处理上传图片   by huangxiaomin
    @PostMapping(value = "/uploadImage")
    @SuppressWarnings("unchecked")
    public RestResult uploadImage(MultipartHttpServletRequest request) {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        JSONArray files = new JSONArray();
        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            if (files.size() >= 10)
                files.remove(0);
            JSONObject obj = new JSONObject();
            obj.put("fileName", mpf.getOriginalFilename());
            obj.put("fileSize", mpf.getSize() / 1024 + " Kb");
            obj.put("fileType", mpf.getContentType());
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            files.add(obj);
        }
        return RestResultBuilder.builder().success(files).build();
    }
}
