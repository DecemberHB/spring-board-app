package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleDTO getArticle(int ano){

        Optional<Article> optArticle = articleRepository.findById(ano);
        if(optArticle.isPresent()){
            Article article = optArticle.get();
            return modelMapper.map(article, ArticleDTO.class);
        }
        return null;
    }
    public List<ArticleDTO> getArticleAll(){

        List<Article> list = articleRepository.findAll();
        return list.stream()
                .map(entity -> modelMapper.map(entity, ArticleDTO.class))
                .toList();
    }

    public void save(ArticleDTO articleDTO){

        // 게시글 쓰기
        Article article = modelMapper.map(articleDTO, Article.class);
        articleRepository.save(article);

        // 파일 업로드


    }

    public void modify(ArticleDTO articleDTO){

        if(articleRepository.existsById(articleDTO.getAno())){
            Article article = modelMapper.map(articleDTO, Article.class);
            articleRepository.save(article);
        }
    }

    public void remove(int ano){
        articleRepository.deleteById(ano);
    }

}