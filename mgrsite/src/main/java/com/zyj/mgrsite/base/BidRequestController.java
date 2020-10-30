package com.zyj.mgrsite.base;

import com.zyj.p2p.base.domain.UserFile;
import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.query.UserFileQueryObject;
import com.zyj.p2p.base.service.RealAuthService;
import com.zyj.p2p.base.service.UserFileService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.query.BidRequestQueryObject;
import com.zyj.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 借款相关controller
 *
 * @author onlyzyj
 * @date 2020/10/29-15:42
 */
@Controller
public class BidRequestController {

    @Autowired
    private BidRequestService bidRequestService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private RealAuthService realAuthService;

    @Autowired
    private UserFileService userFileService;

    @RequestMapping("bidrequest_publishaudit_list")
    public String bidrequestList(@ModelAttribute("qo")BidRequestQueryObject qo, Model model){
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
        model.addAttribute("pageResult",bidRequestService.query(qo));
        return "bidrequest/publish_audit";
    }

    @RequestMapping("bidrequest_publishaudit")
    @ResponseBody
    public JSONResult bidRequestPublishAudit(Long id,String remark,int state){
        bidRequestService.publishAudit(id,remark,state);
        return new JSONResult();
    }

    @RequestMapping("borrow_info")
    public String borrowInfo(Long id,Model model){
        // bidRequest;
        BidRequest bidRequest = bidRequestService.get(id);
        Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
        model.addAttribute("bidRequest",bidRequest);
        // userInfo
        model.addAttribute("userInfo",userinfo);
        // audits:这个标的审核历史
        model.addAttribute("audits",bidRequestService.listAuditHistoryByBidRequest(id));
        // realAuth:借款人实名认证信息
        model.addAttribute("realAuth",realAuthService.get(userinfo.getRealAuthId()));
        // userFiles:该借款人的风控资料信息
        UserFileQueryObject qo = new UserFileQueryObject();
        qo.setApplierId(userinfo.getId());
        qo.setState(UserFile.STATE_AUDIT);
        qo.setPageSize(-1);//-1代表查所有的
        List<UserFile> list = userFileService.queryForList(qo);
        model.addAttribute("userFiles",list);
        return "bidrequest/borrow_info";
    }
}
