package com.face.callout.controller;

import com.face.callout.common.restmodel.RestResult;
import com.face.callout.common.restmodel.RestResultBuilder;
import com.face.callout.entity.CallOut;
import com.face.callout.entity.Person;
import com.face.callout.repository.FaceMarkRepository;
import com.face.callout.repository.PersonRepository;
import com.face.callout.service.FaceMarkService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/facemark")
public class FaceMarkController {

    @Autowired
    FaceMarkRepository faceMarkRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FaceMarkService faecMarkService;

    @GetMapping()
    @SuppressWarnings("unchecked")
    public RestResult load() {
        JSONObject json = new JSONObject();
        json.put("test", "nihao");
        return RestResultBuilder.builder().success(json).build();
    }

    //查询全部，已标注，未标注（分页）
    @PostMapping(value = "/imagelists")
    @SuppressWarnings("unchecked")
    public RestResult imagelists(@RequestBody @Valid JSONObject data) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        int page = data.getInt("page");
        int size = data.getInt("size");
        int ismark = data.getInt("ismark");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<CallOut> lists = null;
        switch (ismark) {
            case 0:
                lists = faceMarkRepository.findAll(pageable);
                break;
            case 1:
                lists = faceMarkRepository.findAllByIsMark(true, pageable);
                break;
            case 2:
                lists = faceMarkRepository.findAllByIsMark(false, pageable);
                break;
        }
        return RestResultBuilder.builder().success(lists).build();
    }

    //标注
    @PatchMapping(value = "/{id}")
    @SuppressWarnings("unchecked")
    public RestResult mark(@PathVariable("id") Long id, @RequestBody @Valid JSONObject data) {
        CallOut callOut = faceMarkRepository.findOne(id);
        int sex = data.getInt("sex");
        int age = data.getInt("age");
        int glasses = data.getInt("glasses");
        int sunglasses = data.getInt("sunglasses");
        int hat = data.getInt("hat");
        int masks = data.getInt("masks");
        int country = data.getInt("country");
        callOut.setSex(sex != 0);
        callOut.setAge(age);
        callOut.setGlasses(glasses != 0);
        callOut.setSunGlasses(sunglasses != 0);
        callOut.setHat(hat != 0);
        callOut.setMasks(masks != 0);
        callOut.setCountry(country != 0);
        callOut.setMark(true);
        callOut.setLabeledAt(new Date());
        callOut = faceMarkRepository.save(callOut);
        return RestResultBuilder.builder().success(callOut).build();
    }

    //一键标注
    @PostMapping(value = "/markAll")
    @SuppressWarnings("unchecked")
    public RestResult markAll(@RequestBody @Valid JSONObject data) {
        JSONArray ids = data.getJSONArray("ids");
        int sex = data.getInt("sex");
        int age = data.getInt("age");
        int glasses = data.getInt("glasses");
        int sunglasses = data.getInt("sunglasses");
        int hat = data.getInt("hat");
        int masks = data.getInt("masks");
        int country = data.getInt("country");
        JSONObject result = new JSONObject();
        for(Object id :ids){
            JSONObject  idObj = (JSONObject) id;
            CallOut callOut = faceMarkRepository.findOne(idObj.getLong("id"));
            callOut.setSex(sex != 0);
            callOut.setAge(age);
            callOut.setGlasses(glasses != 0);
            callOut.setSunGlasses(sunglasses != 0);
            callOut.setHat(hat != 0);
            callOut.setMasks(masks != 0);
            callOut.setCountry(country != 0);
            callOut.setMark(true);
            callOut.setLabeledAt(new Date());
            faceMarkRepository.save(callOut);
        }
        result.put("success",1);
        result.put("msg","一键标注成功");
        return RestResultBuilder.builder().success(result).build();
    }

    //详情
    @GetMapping(value = "/{id}")
    @SuppressWarnings("unchecked")
    public RestResult markDetail(@PathVariable("id") Long id) {
        CallOut callOut = faceMarkRepository.findOne(id);
        return RestResultBuilder.builder().success(callOut).build();
    }

    //重新分组
    @PostMapping(value = "/setGroup")
    @SuppressWarnings("unchecked")
    public RestResult markDetail(@RequestBody @Valid JSONObject data) {
        Long id = data.getLong("id");
        Long personId = data.getLong("newgroup");
        CallOut facemark = faceMarkRepository.findOne(id);
        Person person = personRepository.findOne(personId);
        if (person != null) {
            facemark.setPerson(person);
            facemark = faceMarkRepository.save(facemark);
        }
        return RestResultBuilder.builder().success(facemark).build();
    }

    //删除
    @DeleteMapping(value = "/{id}")
    @SuppressWarnings("unchecked")
    public RestResult del(@PathVariable("id") Long id) {
        CallOut callOut = faceMarkRepository.findOne(id);
        faceMarkRepository.delete(callOut);
        return RestResultBuilder.builder().success(callOut).build();
    }

    //按姓名或personid查询(固定条数)
    @PostMapping(value = "/searchOne")
    @SuppressWarnings("unchecked")
    public RestResult searchOne(@RequestBody @Valid JSONObject data) {
        Long key = data.getLong("key");
        Person person = personRepository.findOne(key);
        JSONObject obj = new JSONObject();
        if (person != null) {
            Long total = faceMarkRepository.countAllByPerson(person);
            Long ismarked = faceMarkRepository.countAllByPersonAndIsMark(person, true);
            List<CallOut> lists = faceMarkRepository.findAllByPerson(person);
            JSONArray array = faecMarkService.list2jsonarray(lists);
            obj.put("total", total);
            obj.put("ismarked", ismarked);
            obj.put("id", person.getId());
            obj.put("isGrouped", person.getGrouped());
            obj.put("lists", array);
            obj.put("noone", false);
        } else {
            obj.put("noone", true);
        }
        return RestResultBuilder.builder().success(obj).build();
    }

    //同一个personid的图片(固定条数)
    @PostMapping(value = "/typelists")
    @SuppressWarnings("unchecked")
    public RestResult typelists(@RequestBody @Valid JSONObject data) {
        Long personID = data.getLong("personID");
        Person person = personRepository.findOne(personID);
        List<CallOut> faceMarkLists = faceMarkRepository.findTop7ByPerson(person);
        return RestResultBuilder.builder().success(faceMarkLists).build();
    }

    //已标注比例
    @PostMapping(value = "/ismarked")
    @SuppressWarnings("unchecked")
    public RestResult ismarked(@RequestBody @Valid JSONObject data) {
        Long personID = data.getLong("personID");
        Person person = personRepository.findOne(personID);
        Long total = faceMarkRepository.countAllByPerson(person);
        Long ismarked = faceMarkRepository.countAllByPersonAndIsMark(person, true);
        JSONObject obj = new JSONObject();
        obj.put("total", total);
        obj.put("ismarked", ismarked);
        return RestResultBuilder.builder().success(obj).build();
    }

    //同一个image_id的图片(所有)
    @GetMapping(value = "/image/{image_id}")
    @SuppressWarnings("unchecked")
    public RestResult sameImageID(@PathVariable("image_id") Long image_id) {
        Person person = personRepository.findOne(image_id);
        List<CallOut> lists = faceMarkRepository.findAllByPerson(person);
        return RestResultBuilder.builder().success(lists).build();
    }

}
