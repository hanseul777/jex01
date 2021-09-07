package org.zerock.jex01.common.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Controller
@Log4j2
public class UploadController {

    @GetMapping("/sample/upload")
    public void uploadGET(){
    }

    @PostMapping("/upload")
    public void uploadPost(MultipartFile[] uploadFiles){ //가짜폼테그에 걸었던 이름으로 받아야 정상적으로 작동한다.

        log.info("---------------------------------");

        if(uploadFiles != null && uploadFiles.length >0){
            for (MultipartFile multipartFile : uploadFiles){
                try {
                    uploadProcess(multipartFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        Arrays.stream(uploadFiles).forEach(multipartFile -> {
//            log.info(multipartFile);
//            
//        });
    }

    private void uploadProcess(MultipartFile multipartFile) throws Exception{

        String uploadPath = "/Users/hanseul/upload";

        String folderName = makeFoldeer(uploadPath); //2021-09-07
        
        log.info(multipartFile.getContentType()); // 파일의 타입이 이미지라면 -> thumnails 처리
        log.info(multipartFile.getOriginalFilename());
        log.info(multipartFile.getSize());
        log.info("-------------------------------------------------");

        String fileName = multipartFile.getOriginalFilename();
        fileName = UUID.randomUUID().toString()+"-"+fileName;

        File savedFile = new File(uploadPath+File.separator+folderName ,fileName);
            FileCopyUtils.copy(multipartFile.getBytes(),savedFile); //여기까지 오면 파일의 저장이 끝나는 것.

        //파일저장이 끝나고 Thumnail처리 -> 이미지일 경우에만
        String mimeType = multipartFile.getContentType();
        if(mimeType.startsWith("image")){
            File thumbnailFile = new File(uploadPath+File.separator+folderName, "s_"+fileName);
            Thumbnailator.createThumbnail(savedFile,thumbnailFile,100,100);
        }
    }

    private String makeFoldeer(String uploadPath) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = simpleDateFormat.format(date); //2021-09-07
        String folderName = str.replace("-",File.separator); //-를 /로 바꿔줌
        File uploadFolder = new File(uploadPath, folderName);
        if (uploadFolder.exists() == false){//만약에 폴더가 없었다면 -> 자동으로 생성해줌
            uploadFolder.mkdirs();
        }
        return folderName;
    }
}
