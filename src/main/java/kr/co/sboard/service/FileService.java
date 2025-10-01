package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    // application.properties에서 설정한 업로드 경로 주입
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    /**
     * 파일 업로드 기능
     * - 게시글 DTO에서 파일 목록을 꺼내서
     * - 서버 경로에 저장하고
     * - DB 저장용 FileDTO 리스트로 반환
     */
    public List<FileDTO> upload(ArticleDTO articleDTO) {

        // 업로드 경로 확인 후 없으면 생성
        File fileUploadPath = new File(uploadPath);
        if (!fileUploadPath.exists()) {
            fileUploadPath.mkdirs();
        }

        String absolutePath = fileUploadPath.getAbsolutePath();
        List<MultipartFile> fileList = articleDTO.getFiles();

        // 반환할 FileDTO 리스트
        List<FileDTO> fileDTOList = new ArrayList<>();

        for(MultipartFile multiFile : fileList) {

            // 파일이 존재할 경우만 처리
            if(!multiFile.isEmpty()){

                String oriName = multiFile.getOriginalFilename();   // 원본 파일명
                String ext = oriName.substring(oriName.lastIndexOf(".")); // 확장자
                String savedName = UUID.randomUUID().toString() + ext;   // 저장 파일명(중복 방지)

                try {
                    // 실제 디렉토리에 파일 저장
                    multiFile.transferTo(new File(absolutePath, savedName));

                    // DB 저장용 DTO 생성
                    FileDTO fileDTO = FileDTO.builder()
                            .oname(oriName)   // 원래 파일명
                            .sname(savedName) // 서버에 저장된 파일명
                            .build();

                    fileDTOList.add(fileDTO);

                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }

        return fileDTOList;
    }

    /**
     * 파일 다운로드 기능 (미완성)
     * - 실제 파일 경로를 Path 객체로 불러와서
     * - MIME 타입 확인 (ex: image/png, application/pdf)
     * - 추후 HttpServletResponse를 통해 파일을 내려줄 예정
     */
    public void download(FileDTO fileDTO) {
        Path path = Paths.get(uploadPath + File.separator + fileDTO.getSname()); // 서버에 저장된 파일 경로

        try {
            // 파일의 MIME 타입 추출 (브라우저에 내려줄 때 필요)
            String contentType = Files.probeContentType(path);
            fileDTO.setContentType(contentType); // 얕은복사

            // 파일 자원 객체 core.io { 실제 다운로드 객체 } -> 컨트롤러로 넘겨야함
            Resource resource = new InputStreamResource(Files.newInputStream(path));
            fileDTO.setResource(resource); // 얕은 복사


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 파일 단건 조회
     * - 파일 번호(fno)로 DB에서 파일 정보 가져오기
     * - Entity -> DTO 변환 후 반환
     * - 현재는 Optional empty일 때 리턴값 없음 (추가 처리 필요)
     */
    public FileDTO getFile(int fno) {
        Optional<kr.co.sboard.entity.File> optFile = fileRepository.findById(fno);

        if (optFile.isPresent()) {
            kr.co.sboard.entity.File file = optFile.get();
            return modelMapper.map(file, FileDTO.class); // Entity -> DTO 변환
        }

        // TODO: 파일이 없을 때 null 리턴 또는 예외 처리 필요
        return null;
    }

    // 파일 전체 목록 조회 (미구현)
    public void getFileAll(){}

    // 파일 저장 (DTO -> Entity 변환 후 DB 저장)
    public void save(FileDTO fileDTO){
        kr.co.sboard.entity.File file = modelMapper.map(fileDTO, kr.co.sboard.entity.File.class);
        fileRepository.save(file);
    }

    // 파일 수정 (미구현)
    public void modify(){}
    public void modifyDownloadCount(FileDTO fileDTO){
        int download = fileDTO.getDownload();
        fileDTO.setDownload(download + 1);

        kr.co.sboard.entity.File file = modelMapper.map(fileDTO, kr.co.sboard.entity.File.class);
        fileRepository.save(file);

    }

    // 파일 삭제 (미구현)
    public void remove(){}
}
