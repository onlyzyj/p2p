package com.zyj.p2p.base.controller;

import com.alibaba.fastjson.JSON;
import com.zyj.p2p.base.domain.UserFile;
import com.zyj.p2p.base.service.SystemDictionaryService;
import com.zyj.p2p.base.service.UserFileService;
import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.base.util.UploadUtil;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 风控资料相关
 *
 * @author onlyzyj
 * @date 2020/10/24-16:53
 */
@Controller
public class UserFileController {

    @Autowired
    private UserFileService userFileService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private SystemDictionaryService systemDictionaryService;

    @RequestMapping("userFile")
    public String userFile(Model model, HttpServletRequest request){
        List<UserFile> userFiles = userFileService.listFilesByHasType(UserContext.getCurrent().getId(),false);
        //有没有文件没有选类型并提交的
        if(userFiles.size()>0){
            model.addAttribute("userFiles",userFiles);
            model.addAttribute("fileTypes",systemDictionaryService.listByParentSn("userFileType"));
            return "userFiles_commit";
        }else{
            model.addAttribute("sessionid",request.getSession().getId());
            userFiles = userFileService.listFilesByHasType(UserContext.getCurrent().getId(),true);
            model.addAttribute("userFiles",userFiles);
            return "userFiles";
        }
    }

    /**
     * 处理上传用户风控文件
     */
    @RequestMapping("userFileUpload")
    @ResponseBody
    public void useFIleUpload(MultipartFile file){
        String basePath = servletContext.getRealPath("/upload");
        String fileName = UploadUtil.upload(file,basePath);
        fileName = "/upload/"+fileName;
        userFileService.apply(fileName);
    }

    /**
     * 处理用户风控文件类型选择
     */
    @RequestMapping("userFile_selectType")
    @ResponseBody
    public JSONResult userFileSelectType(Long[] fileType,Long[] id){
        if(fileType!=null&&id!=null&&fileType.length==id.length){
            userFileService.batchUpdateFileType(id,fileType);
        }
        return new JSONResult();
    }
}
