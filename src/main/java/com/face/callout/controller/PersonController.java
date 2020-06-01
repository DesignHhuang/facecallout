package com.face.callout.controller;

import com.face.callout.common.restmodel.RestResult;
import com.face.callout.common.restmodel.RestResultBuilder;
import com.face.callout.entity.Person;
import com.face.callout.repository.FaceMarkRepository;
import com.face.callout.repository.PersonRepository;
import com.face.callout.service.FaceMarkService;
import com.face.callout.service.PersonService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FaceMarkRepository faceMarkRepository;

    @Autowired
    FaceMarkService faecMarkService;

    @Autowired
    PersonService personService;

    //获取所有人
    @PostMapping(value = "/persons")
    @SuppressWarnings("unchecked")
    public RestResult persons(@RequestBody @Valid JSONObject data) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        int page = data.getInt("page");
        int size = data.getInt("size");
        int isGroup = data.getInt("isGroup");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Person> persons = null;
        switch (isGroup) {
            case 0:
                persons = personRepository.findAll(pageable);
                break;
            case 1:
                persons = personRepository.findAllByIsGrouped(true, pageable);
                break;
            case 2:
                persons = personRepository.findAllByIsGrouped(false, pageable);
                break;
        }
        JSONObject returnObj = new JSONObject();
        if (persons != null) {
            JSONArray arrayLists = personService.personLists(persons);
            returnObj.put("personLists", arrayLists);
            returnObj.put("totalElements", persons.getTotalElements());
        }
        return RestResultBuilder.builder().success(returnObj).build();
    }

    //获取所有人
    @PostMapping(value = "/personsByIds")
    @SuppressWarnings("unchecked")
    public RestResult personsByIds(@RequestBody @Valid JSONObject data) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        int page = data.getInt("page");
        int size = data.getInt("size");
        int isGroup = data.getInt("isGroup");
        long peopleCount = personRepository.count();
        long startId = data.has("startId") ? data.getLong("startId") : 1;
        long endId = data.has("endId") ? data.getLong("endId") : peopleCount;

        Pageable pageable = new PageRequest(page, size, sort);
        Page<Person> persons = null;
        switch (isGroup) {
            case 0:
                persons = personRepository.findAllByIdBetween(startId, endId, pageable);
                break;
            case 1:
                persons = personRepository.findAllByIdBetweenAndIsGrouped(startId, endId, true, pageable);
                break;
            case 2:
                persons = personRepository.findAllByIdBetweenAndIsGrouped(startId, endId, false, pageable);
                break;
        }
        JSONObject returnObj = new JSONObject();
        if (persons != null) {
            JSONArray arrayLists = personService.personLists(persons);
            returnObj.put("personLists", arrayLists);
            returnObj.put("allCount", personRepository.countPeopleByIdBetween(startId, endId));
            returnObj.put("groupedCount", personRepository.countPeopleByIdBetweenAndIsGrouped(startId, endId, true));
            returnObj.put("notGroupedCount", personRepository.countPeopleByIdBetweenAndIsGrouped(startId, endId, false));
            returnObj.put("totalElements", persons.getTotalElements());
        }
        return RestResultBuilder.builder().success(returnObj).build();
    }

    //完成分组
    @PostMapping(value = "/finishGroup")
    @SuppressWarnings("unchecked")
    public RestResult finishGroup(@RequestBody @Valid JSONObject data) {
        Long personID = data.getLong("personID");
        Person person = personRepository.findOne(personID);
        person.setGrouped(true);
        personRepository.save(person);
        return RestResultBuilder.builder().success(person).build();
    }

    //修改分组
    @PostMapping(value = "/updateGroup")
    @SuppressWarnings("unchecked")
    public RestResult updateGroup(@RequestBody @Valid JSONObject data) {
        Long personID = data.getLong("personID");
        Person person = personRepository.findOne(personID);
        person.setGrouped(false);
        personRepository.save(person);
        return RestResultBuilder.builder().success(person).build();
    }
}
