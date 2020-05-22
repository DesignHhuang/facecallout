package com.face.callout.service;

import com.face.callout.entity.CallOut;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FaceMarkService {

    public JSONArray list2jsonarray(List<CallOut> lists) {
        JSONArray arrayLists = new JSONArray();
        for (CallOut facemark : lists) {
            JSONObject obj = new JSONObject();
            obj.put("sex", facemark.isSex());
            obj.put("country", facemark.isCountry());
            obj.put("sunGlasses", facemark.isSunGlasses());
            obj.put("masks", facemark.isMasks());
            obj.put("glasses", facemark.isGlasses());
            obj.put("attrArray", facemark.getAttrArray());
            obj.put("personid", facemark.getPerson().getId());
            obj.put("imageUrl", facemark.getImageUrl());
            obj.put("hat", facemark.isHat());
            obj.put("id", facemark.getId());
            obj.put("person", facemark.getPerson());
            obj.put("dataset", facemark.getDataset());
            obj.put("datasetid", facemark.getDataset().getId());
            obj.put("age", facemark.getAge());
            obj.put("mark", facemark.isMark());
            arrayLists.add(obj);
        }
        return arrayLists;
    }
}
