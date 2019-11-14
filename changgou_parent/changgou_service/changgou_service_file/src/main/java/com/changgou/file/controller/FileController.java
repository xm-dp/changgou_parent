package com.changgou.file.controller;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.file.util.FastDFSClient;
import com.changgou.file.util.FastDFSFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController
{

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file){
        try
        {
            //判断文件是否存在
            if (file == null){
                throw new RuntimeException("文件不存在");
            }
            //获取文件完整名称
            String filename = file.getOriginalFilename();
            if (filename == null){
                throw new RuntimeException("文件不存在");
            }
            //获取文件扩展名
            String extName = filename.substring(filename.lastIndexOf(".") + 1);
            //获取文件内容
            byte[] fileBytes = file.getBytes();
            //封装文件实体类
            FastDFSFile fastDFSFile = new FastDFSFile(filename, fileBytes, extName);
            //使用工具类进行文件上传，接收返回值
            String[] uploadResult = FastDFSClient.upload(fastDFSFile);
            //封装结果集
            String result = FastDFSClient.getTrackerUrl()+uploadResult[0]+"/"+uploadResult[1];
            return new Result(true,StatusCode.OK,"文件上传成功",result);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Result(false, StatusCode.ERROR,"文件上传失败");
    }
}
