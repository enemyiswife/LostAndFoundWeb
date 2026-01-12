package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.common.Result;
import com.example.springboot.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/files")
public class FileController {
    private static final String filePath = System.getProperty("user.dir") + "/files/";

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if(!FileUtil.isDirectory(filePath)){
            FileUtil.mkdir(filePath);
        }
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        String realPath = filePath + fileName;
        try{
            FileUtil.writeBytes(file.getBytes(), realPath);
        }catch (IOException e){
            e.printStackTrace();
            throw new CustomException("500","上传失败");
        }

        String url = "http://localhost:9090/files/download/" + fileName;
        return Result.success(url);

    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response){
        try{
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
            OutputStream os = response.getOutputStream();
            String realPath = filePath + fileName;
            byte[] bytes = FileUtil.readBytes(realPath);
            os.write(bytes);
            os.flush();
            os.close();
        }catch (IOException e){
            e.printStackTrace();
            throw new CustomException("500","下载失败");
        }

    }
}
