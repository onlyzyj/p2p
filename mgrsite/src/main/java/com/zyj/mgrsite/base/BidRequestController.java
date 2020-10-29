package com.zyj.mgrsite.base;

import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.JSONResult;
import com.zyj.p2p.business.query.BidRequestQueryObject;
import com.zyj.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
