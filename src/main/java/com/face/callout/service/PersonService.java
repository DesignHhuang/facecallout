package com.face.callout.service;

import com.face.callout.entity.CallOut;
import com.face.callout.entity.Person;
import com.face.callout.repository.FaceMarkRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {

    @Autowired
    FaceMarkRepository faceMarkRepository;

    @Autowired
    FaceMarkService faecMarkService;

    public JSONArray personLists(Page<Person> persons) {
        JSONArray arrayLists = new JSONArray();
        for (Person person : persons) {
            Long total = faceMarkRepository.countAllByPerson(person);
            Long ismarked = faceMarkRepository.countAllByPersonAndIsMark(person, true);
            List<CallOut> lists = faceMarkRepository.findTop7ByPerson(person);
            JSONArray array = faecMarkService.list2jsonarray(lists);
            JSONObject obj = new JSONObject();
            obj.put("total", total);
            obj.put("ismarked", ismarked);
            obj.put("id", person.getId());
            obj.put("isGrouped", person.getGrouped());
            obj.put("lists", array);
            obj.put("noone", false);
            arrayLists.add(obj);
        }
        return arrayLists;
    }
}
