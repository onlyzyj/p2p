package com.zyj.p2p.base.query;

import com.zyj.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 实名认证查询对象
 * 
 * @author Administrator
 * 
 */
@Setter
@Getter
public class RealAuthQueryObject extends QueryObject {
    private int state = -1;
    private Date beginDate;
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setBeginDate(Date beginDate){
        this.beginDate = beginDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    //不为空则返回最后该天的最后一秒
    public Date getEndDate(){
        return endDate == null ? null : DateUtil.endOfDay(endDate);
    }

}
