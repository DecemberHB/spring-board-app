package kr.co.sboard.dto;


import kr.co.sboard.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor

public class PageResponseDTO { // 페이지 정보 받는 DTO

    private List<ArticleDTO> dtoList; // 목록 데이터를 받기 위해

    private String cate;
    private int pg;
    private int size;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev,next; // 이전 , 다음 버튼

    private String searchType;
    private String keyword;

    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<ArticleDTO> dtoList, int total) {
        // 생성자

        this.cate = pageRequestDTO.getCate();
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.startNo = total - ((pg -1) * size);

        this.end = (int)Math.ceil(this.pg / 10.0) *10;
        this.start = this.end - 9;

        int last = (int)Math.ceil(total / (double)size);

        this.end = Math.min(last, this.end);
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;

        this.searchType = pageRequestDTO.getSearchType();
        this.keyword = pageRequestDTO.getKeyword();
  }

}

