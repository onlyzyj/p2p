package com.zyj.p2p.base.controller;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.query.UserFileQueryObject;
import com.zyj.p2p.base.service.AccountService;
import com.zyj.p2p.base.service.RealAuthService;
import com.zyj.p2p.base.service.UserFileService;
import com.zyj.p2p.base.service.UserinfoService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.UserContext;
import com.zyj.p2p.business.domain.BidRequest;
import com.zyj.p2p.business.query.BidRequestQueryObject;
import com.zyj.p2p.business.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 网站首页
 *
 * @author onlyzyj
 * @date 2020/10/30-16:02
 */
@Controller
public class IndexController {
    @Autowired
    private BidRequestService bidRequestService;



    @RequestMapping("index")
    public String index(Model model){
        //bidRequest
        model.addAttribute("bidRequests",bidRequestService.listIndex(5));
        return "main";
    }

    /**
     * 投资列表的框框
     */
    @RequestMapping("invest")
    public String investIndex(){
        return "invest";
    }

    /**
     * 投资列表明细
     */
    @RequestMapping("invest_list")
    public String investList(BidRequestQueryObject qo,Model model){
        if (qo.getBidRequestState()==-1){
            qo.setBidRequestStates(new int[]{BidConst.BIDREQUEST_STATE_BIDDING,
                    BidConst.BIDREQUEST_STATE_PAYING_BACK,
                    BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK });
        }
        model.addAttribute("pageResult",bidRequestService.query(qo));
        return "invest_list";
    }


}
