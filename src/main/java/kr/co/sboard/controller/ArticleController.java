package kr.co.sboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final FileService fileService;

    @GetMapping("/article/list")
    public String list(){
        return "article/list";
    }

    @GetMapping("/article/modify")
    public String modify(){
        return "article/modify";
    }

    @GetMapping("/article/searchList")
    public String searchList(){
        return "article/searchList";
    }

    @GetMapping("/article/view")
    public String view(){
        return "article/view";
    }

    @GetMapping("/article/write")
    public String write(){
        return "article/write";
    }

    @PostMapping("/article/write")
    public String write(ArticleDTO articleDTO, HttpServletRequest request){

        String regip = request.getRemoteAddr();
        articleDTO.setReg_ip(regip);

        log.info("articleDTO = {}", articleDTO);



        // 파일업로드
        List<FileDTO> fileDTOList = fileService.upload(articleDTO);

        // 글저장
        int fileCnt = fileDTOList.size();
        articleDTO.setFile_cnt(fileCnt); //위의 리스트의 갯수임
        articleService.save(articleDTO);

        // 파일 저장
        for(FileDTO fileDTO : fileDTOList){
            fileService.save(fileDTO);

        }




        return "redirect:/article/list";
    }


}