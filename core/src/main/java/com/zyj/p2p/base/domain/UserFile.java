package com.zyj.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 风控材料
 *
 * @author onlyzyj
 * @date 2020/10/24-16:11
 */
@Setter@Getter@ToString
public class UserFile extends BaseAuditDomain {
    private String image;//风控材料图片
    private SystemDictionaryItem fileType;//风控材料分类
    private int score;//风控材料评分

    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", id);
        json.put("applier", this.applier.getUsername());
        json.put("fileType", this.fileType.getTitle());
        json.put("image", image);
        return JSONObject.toJSONString(json);
    }
}
