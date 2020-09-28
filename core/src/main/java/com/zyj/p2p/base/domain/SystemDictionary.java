package com.zyj.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典分类
 */

@Setter@Getter
public class SystemDictionary extends BaseDomian{
    private String sn;
    private String title;

    public String getJsonString(){
        Map<String,Object> json = new HashMap<>();
        json.put("id",getId());
        json.put("sn",getSn());
        json.put("title",getTitle());
        return JSONObject.toJSONString(json);
    }
}