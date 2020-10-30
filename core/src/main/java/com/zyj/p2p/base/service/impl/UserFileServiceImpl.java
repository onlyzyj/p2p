package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.SystemDictionaryItem;
import com.zyj.p2p.base.domain.UserFile;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.mapper.UserFileMapper;
import com.zyj.p2p.base.query.PageResult;
import com.zyj.p2p.base.query.UserFileQueryObject;
import com.zyj.p2p.base.service.UserFileService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author onlyzyj
 * @date 2020/10/24-16:52
 */
@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileMapper userFileMapper;

    @Autowired
    private UserinfoService userinfoService;

    @Override
    public void apply(String fileName) {
        UserFile uf = new UserFile();
        uf.setApplier(UserContext.getCurrent());
        uf.setApplyTime(new Date());
        uf.setImage(fileName);
        uf.setState(UserFile.STATE_NORMAL);
        userFileMapper.insert(uf);
    }

    @Override
    public List<UserFile> listFilesByHasType(Long logininfoId, boolean hasType) {
        return userFileMapper.listFilesByHasType(logininfoId,hasType);
    }


    @Override
    public void batchUpdateFileType(Long[] id, Long[] fileType) {
        for (int i = 0; i < id.length; i++) {
            UserFile uf = userFileMapper.selectByPrimaryKey(id[i]);
            SystemDictionaryItem item = new SystemDictionaryItem();
            item.setId(fileType[i]);
            uf.setFileType(item);
            userFileMapper.updateByPrimaryKey(uf);

        }
    }

    @Override
    public PageResult query(UserFileQueryObject qo) {
        int count = userFileMapper.queryForCount(qo);
        if(count>0){
            List<UserFile> list = userFileMapper.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public List<UserFile> queryForList(UserFileQueryObject qo) {
        return userFileMapper.query(qo);
    }

    @Override
    public void audit(Long id, int state, int score, String remark) {
        //找到userfile
        UserFile uf = userFileMapper.selectByPrimaryKey(id);
        if (uf!=null&&uf.getState()==UserFile.STATE_NORMAL){
            //设置通用属性
            uf.setAuditor(UserContext.getCurrent());
            uf.setAuditTime(new Date());
            uf.setState(state);
            if (state == UserFile.STATE_AUDIT){
                uf.setScore(score);
                Userinfo userinfo = userinfoService.get(uf.getApplier().getId());
                userinfo.setScore(userinfo.getScore()+score);
                userinfoService.update(userinfo);
            }
            userFileMapper.updateByPrimaryKey(uf);
        }
    }
}
