package org.zerock.jex01.common.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.jex01.common.dto.UploadResponseDTO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Log4j2
public class UploadController {

    @GetMapping("/sample/upload")
    public void uploadGET(){
    }

    @ResponseBody
    @PostMapping("/removeFile")
    public ResponseEntity<String> removeFile(@RequestBody Map<String,String> data)throws Exception{
        //axios가 JSON(키,값으로 구성되어있음)으로 가져와야 해서 실제 filename을 보내는데 단순문자열로 받을 수가 없어서 키,값으로 구성되어있는 Map을 임시로 생성해서
        // 원래는 DTO를 생성해서 파라미터로 던져야 하는데 Map을 임시로 생성해서 간편하게 사용한다.

        // 2021/09/08/
        File file = new File("/Users/hanseul/upload"+ File.separator+ data.get("fileName"));

        boolean checkImage = Files.probeContentType(file.toPath()).startsWith("image");

        file.delete();

        if(checkImage){
            File thumbnail = new File(file.getParent(), "s_"+ file.getName()); //상위폴더
            log.info(thumbnail);
            thumbnail.delete();
        }
        return ResponseEntity.ok().body("deleted");
    }


    @GetMapping("/downFile")
    public ResponseEntity<byte[]> download(@RequestParam("file") String fileName)throws Exception{

        File file = new File("/Users/hanseul/upload"+File.separator+fileName);

        String originalFileName = fileName.substring(fileName.indexOf("_") + 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octect-stream"); //download mime type 사용
        headers.add("Content-Disposition","attachment; filename="
                + new String(originalFileName.getBytes(StandardCharsets.UTF_8),"ISO-8859-1"));
        byte[] data = FileCopyUtils.copyToByteArray(file); // 바이트 배열 생성

        //결과데이터 생성 -> 헤어와 content부분이 달라짐
//        ResponseEntity<byte[]> result = ResponseEntity.ok().headers(headers).body(data);

        return ResponseEntity.ok().headers(headers).body(data);
    }

    @GetMapping("/viewFile")
    @ResponseBody
    public ResponseEntity<byte[]> viewFile(@RequestParam("file") String fileName)throws Exception{ // 보낼때는 file 받을 때는 fileName으로 받게한다

        //실제파일위치 : /Users/hanseul/upload/2021/09/07/소봉.jpg
        File file = new File("/Users/hanseul/upload" + File.separator + fileName);

        log.info(file); //파일존재여부 확인

        //파일이 존재하는지 여부를 확인(p.526 : 컨텐츠타입이 매번 달라짐 -> 헤더메세지를 조작해주기 위해서 ResponseEntity<byte[]> 사용
        ResponseEntity<byte[]> result = null;

        //한번에 바이트배열로 바꿔주는 것 -> 바이트의 배열로 카피
        byte[] data = FileCopyUtils.copyToByteArray(file); // 이 배열을 보내주는 것

        //mime.type -> 적절한 mime type이 나온다
        String  mimeType = Files.probeContentType(file.toPath());

        log.info("mimeType: " + mimeType);

        result = ResponseEntity.ok().header("Content-Type",mimeType).body(data);
        //ok : response해서 outputStream으로 데이터 보내줬던거를 spring스럽게 한 것( Spring API로만 모든 것을 해결할 수 있도록)

        return  result;
    }

    @ResponseBody //JSON으로 처리해주는 어노테이션
    @PostMapping("/upload")
    public List<UploadResponseDTO> uploadPost(MultipartFile[] uploadFiles){ //가짜폼테그에 걸었던 이름으로 받아야 정상적으로 작동한다.

        log.info("---------------------------------");

        if(uploadFiles != null && uploadFiles.length >0){

            List<UploadResponseDTO> uploadedList = new ArrayList<>();

            for (MultipartFile multipartFile : uploadFiles){
                try {
                    uploadedList.add(uploadProcess(multipartFile));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//forEND
            return uploadedList;
        }
//        Arrays.stream(uploadFiles).forEach(multipartFile -> {
//            log.info(multipartFile);
//            
//        });
        return null;
    }

    private UploadResponseDTO uploadProcess(MultipartFile multipartFile) throws Exception{

        String uploadPath = "/Users/hanseul/upload";

        String folderName = makeFoldeer(uploadPath); //2021-09-07
        
        log.info(multipartFile.getContentType()); // 파일의 타입이 이미지라면 -> thumnails 처리
        log.info(multipartFile.getOriginalFilename());
        log.info(multipartFile.getSize());
        log.info("-------------------------------------------------");

        String fileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String originalFileName = fileName;

        fileName = uuid+"_"+fileName;

        File savedFile = new File(uploadPath+File.separator+folderName ,fileName);
        FileCopyUtils.copy(multipartFile.getBytes(),savedFile); //여기까지 오면 파일의 저장이 끝나는 것.

        //파일저장이 끝나고 Thumnail처리 -> 이미지일 경우에만
        String mimeType = multipartFile.getContentType();
        boolean checkImange = mimeType.startsWith("image");
        if(checkImange){
            File thumbnailFile = new File(uploadPath+File.separator+folderName, "s_"+fileName);
            Thumbnailator.createThumbnail(savedFile,thumbnailFile,100,100);
        }
        return UploadResponseDTO.builder()
                .uuid(uuid)
                .uploadPath(folderName.replace(File.separator,"/"))
                .fileName(originalFileName)
                .image(checkImange)
                .build();
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
