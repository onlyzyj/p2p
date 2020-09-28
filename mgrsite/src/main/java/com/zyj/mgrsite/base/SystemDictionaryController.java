package com.zyj.mgrsite.base;

import com.zyj.p2p.base.domain.SystemDictionary;
import com.zyj.p2p.base.domain.SystemDictionaryItem;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.query.SystemDictionaryQueryObject;
import com.zyj.p2p.base.service.SystemDictionaryService;
import com.zyj.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author onlyzyj
 * @date 2020/9/25-16:19
 */
@Controller
public class SystemDictionaryController {

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    @RequestMapping("systemDictionary_list")
    public String queryService(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model){
        PageResult pageResult = systemDictionaryService.queryDics(qo);
        model.addAttribute("pageResult",pageResult);
        return "systemdic/systemDictionary_list";
    }

    /**
     * 添加/修改数据字典
     */
    @RequestMapping("systemDictionary_update")
    @ResponseBody
    public JSONResult sysDicUpdateAndAdd(SystemDictionary systemDictionary){
        systemDictionaryService.updataAndAdd(systemDictionary);
        return new JSONResult();
    }

    @RequestMapping("systemDictionaryItem_list")
    public String  sysDicItem(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model){
        PageResult pageResult = systemDictionaryService.queryItems(qo);
        model.addAttribute("pageResult",pageResult);
        model.addAttribute("systemDictionaryGroups",systemDictionaryService.listAllDics());
        return "systemdic/systemDictionaryItem_list";
    }


    /**
     * 添加/修改数据字典明细
     */
    @RequestMapping("systemDictionaryItem_update")
    @ResponseBody
    public JSONResult sysDicUpdateAndAdd(SystemDictionaryItem systemDictionaryItem){
        systemDictionaryService.saveAndUpdateItem(systemDictionaryItem);
        return new JSONResult();
    }
}
