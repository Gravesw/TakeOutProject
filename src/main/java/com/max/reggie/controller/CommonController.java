package com.max.reggie.controller;

//文件的上传与下载
import com.max.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;
    /* 
    文件上传
     * @Param   MultipartFile file 参数名必须跟前端保持一致
     * @return  R<String>
     * @author: Max
     * @data:   2022/10/12
     */
    @PostMapping("/upload")             //参数名必须跟前端保持一致
    public R<String> upload(MultipartFile file){
        //file是临时文件，需要转存，否则本次运行结束后会消失

        //原始文件名，但会有出现名字重复的现象
        String originalFilename = file.getOriginalFilename();

        //文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用uuid随机生成文件名+后缀名
        String filename = UUID.randomUUID().toString()+suffix;
        //创建一个目录对象
        File dir = new File(basePath);
            //判断目录是否存在
        if(!dir.exists()){
            //目录不存在，创建目录
            dir.mkdir();
        }
        try {
            file.transferTo(new File(basePath+filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }


    /*
    文件下载方法
     * @author: Max
     * @data:   2022/10/12
     */
    @GetMapping("/download")
    public void download(String name , HttpServletResponse response){
        //不用返回值是因为通过输出流向页面写回二进制数据
        //输出流要通过response来获得
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fis = new FileInputStream(new File(basePath+name));
            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream sos = response.getOutputStream();
            //设置响应回去的文件类型
            response.setContentType("image/jpeg");
            byte[] bytes= new byte[1024];
            int length = 0 ;
            while(( length = fis .read(bytes)) != -1){
                sos.write(bytes,0,length);
                sos.flush();
            }
            //关闭资源
            sos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
