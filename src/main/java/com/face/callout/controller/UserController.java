package com.face.callout.controller;

import com.face.callout.common.restmodel.RestResult;
import com.face.callout.common.restmodel.RestResultBuilder;
import com.face.callout.entity.User;
import com.face.callout.repository.UserRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    UserRepository userRepository;

    //获取当前用户信息
    @GetMapping(value = "/{id}")
    @SuppressWarnings("unchecked")
    public RestResult current(@PathVariable("id") String id) {
        User user = userRepository.findOne(id);
        return RestResultBuilder.builder().success(user).build();
    }

    /*@PostMapping(value = "/current")
    @SuppressWarnings("unchecked")
    public RestResult current(@RequestBody @Valid JSONObject data) {
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
    }*/
}
