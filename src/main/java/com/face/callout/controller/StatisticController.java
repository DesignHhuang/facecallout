package com.face.callout.controller;

import com.face.callout.common.restmodel.RestResult;
import com.face.callout.common.restmodel.RestResultBuilder;
import com.face.callout.entity.CallOut;
import com.face.callout.entity.Statistic;
import com.face.callout.repository.FaceMarkRepository;
import com.face.callout.repository.PersonRepository;
import com.face.callout.repository.StatisticRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticController {
    @Autowired
    FaceMarkRepository faceMarkRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    StatisticRepository statisticRepository;

    //基于ID统计信息
    @GetMapping(value = "/ids")
    @SuppressWarnings("unchecked")
    public RestResult statisticsByIds() {
        JSONObject result = new JSONObject();
        long count = personRepository.count();
        JSONArray jsonArray = new JSONArray();
        long i;
        for (i = 1; i + 1000 < count; i += 1000) {
            JSONObject tmp = new JSONObject();
            Statistic sta = statisticRepository.findByStartId(i);
            tmp.put("name", sta == null ? "未分配" : sta.getName());
            tmp.put("startId", i);
            tmp.put("part", Long.toString(i) + "-" + Long.toString(i + 999));
            tmp.put("finished", personRepository.countPeopleByIdBetweenAndIsGrouped(i, i + 999, true));
            jsonArray.add(tmp);
        }
        if (i < count) {
            JSONObject tmp = new JSONObject();
            Statistic sta = statisticRepository.findByStartId(i);
            tmp.put("name", sta == null ? "未分配" : sta.getName());
            tmp.put("startId", i);
            tmp.put("part", Long.toString(i) + "-" + Long.toString(count));
            tmp.put("finished", personRepository.countPeopleByIdBetweenAndIsGrouped(i, count, true));
            jsonArray.add(tmp);
        }
        result.put("statistics", jsonArray);
        return RestResultBuilder.builder().success(result).build();
    }

    //分配任务
    @PostMapping(value = "/sendtask")
    @SuppressWarnings("unchecked")
    public RestResult sendtask(@RequestBody @Valid JSONObject data) {
        long startId = data.getLong("startId");
        String name = data.getString("name");
        Statistic statistic = new Statistic();
        statistic.setName(name);
        statistic.setStartId(startId);
        statisticRepository.save(statistic);
        return RestResultBuilder.builder().success(statistic).build();
    }
}
