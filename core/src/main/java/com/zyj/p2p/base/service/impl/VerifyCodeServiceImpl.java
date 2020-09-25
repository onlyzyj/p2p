package com.zyj.p2p.base.service.impl;

import com.zyj.p2p.base.domain.Userinfo;
import com.zyj.p2p.base.service.VerifyCodeService;
import com.zyj.p2p.base.util.BidConst;
import com.zyj.p2p.base.util.DateUtil;
import com.zyj.p2p.base.util.UserContext;
import com.zyj.p2p.base.vo.VerifyCodeVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * @author onlyzyj
 * @date 2020/9/23-17:09
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    //模拟短信网关的账号、密码、私钥以及提供的url接口
    @Value("${sms.username}")
    private String username;

    @Value("${sms.password}")
    private String password;

    @Value("${sms.apikey}")
    private String apiKey;

    @Value("${sms.url}")
    private String url;

    @Override
    public void sendVerifyCode(String phoneNumber) {
        // 判断当前是否能够发送短信
        // 从session中获取最后一次发送短信的时间
        VerifyCodeVO vc = UserContext.getVerifyCodeVO();
        if(vc == null || DateUtil.secondsBetween(new Date(),vc.getLastSendTime())>90){
            String verifyCode = UUID.randomUUID().toString().substring(0,4);
            //发送短信
            try {
                //创建一个URL对象
                URL url = new URL(this.url);
                // 通过URL得到一个HTTPURLConnection连接对象;
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                // 拼接POST请求的内容
                StringBuilder content = new StringBuilder(100)
                        .append("username=").append(username)
                        .append("&password=").append(password)
                        .append("&apikey=").append(apiKey).append("&mobile=")
                        .append(phoneNumber).append("&content=")
                        .append("验证码是:").append(verifyCode).append(",请在5分钟内使用");
                // 发送POST请求,POST或者GET一定要大写
                conn.setRequestMethod("POST");
                // 设置POST请求是有请求体的
                conn.setDoOutput(true);
                // 写入POST请求体
                conn.getOutputStream().write(content.toString().getBytes());
                // getInputStream()就是发送了请求，同时得到了响应流，再通过以下方法转变为String
                String response = StreamUtils.copyToString(
                        conn.getInputStream(), Charset.forName("UTF-8"));
                if (response.startsWith("success:")) {
                    // 发送成功
                    // 把手机号码,验证码,发送时间装配到VO中并保存到session
                    vc = new VerifyCodeVO();
                    vc.setLastSendTime(new Date());
                    vc.setPhoneNumber(phoneNumber);
                    vc.setVerifyCode(verifyCode);
                    UserContext.putVerifyCodeVO(vc);
                } else {
                    // 发送失败
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("短信发送失败!");
            }
        }else{
            throw new RuntimeException("操作过于频繁");
        }
    }

    @Override
    public boolean verify(String phoneNumber, String verifyCode) {
        VerifyCodeVO vc = UserContext.getVerifyCodeVO();
        if (vc != null
                && vc.getPhoneNumber().equals(phoneNumber)
                && vc.getVerifyCode().equalsIgnoreCase(verifyCode)
                && DateUtil.secondsBetween(new Date(),vc.getLastSendTime()) <= BidConst.VERIFY_VALIDATE_SECOND
        ){
         return true;
        }else{
            return false;
        }
    }

}
