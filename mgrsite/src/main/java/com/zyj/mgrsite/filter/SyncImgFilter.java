package com.zyj.mgrsite.filter;

import com.zyj.p2p.base.util.BidConst;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author onlyzyj
 * @date 2020/11/9-19:35
 */
public class SyncImgFilter implements Filter {
    private ServletContext ctx;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.ctx = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        // 得到这次要请求的图片是啥;
        String file = req.getRequestURI();
        // 去本地应用找这个图片;
        String fileFullPath = ctx.getRealPath(file);
        // 如果图片存在,放行;
        File localFile = new File(fileFullPath);
        if (localFile.exists()) {
            chain.doFilter(req, resp);
        } else {
            // 如果图片不存在,去公共文件夹拷贝;
            File publicFile = new File(BidConst.PUBLIC_IMG_SHARE_PATH,
                    FilenameUtils.getName(file));
            if (publicFile.exists()) {
                //将公共文件夹的图片拷贝到项目本地路径
                FileUtils.copyFile(publicFile, localFile);
                // 下面代码保证图片复制完成后再放行，可以避免图片显示不了的问题
                resp.setHeader("Cache-Control", "no-store");
                resp.setHeader("Pragma", "no-cache");
                resp.setDateHeader("Expires", 0);
                ServletOutputStream responseOutputStream = response
                        .getOutputStream();
                responseOutputStream.write(FileUtils
                        .readFileToByteArray(publicFile));
                responseOutputStream.flush();
                responseOutputStream.close();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
