package kr.co.sboard.repository;

import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import kr.co.sboard.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleRepositoryTest {


    @Autowired
    ArticleRepository articleRepository;

   // @Test

//    void test(){
//
//        Pageable pageable = PageRequest.of(1, 10); // 객체부르기 0->1 페이지 1->2페이지
//
//        Page<Tuple> pageTuple = articleRepository.selectArticleAllForList(pageable);
//
//        List<Tuple> tupleList = pageTuple.getContent();
//
//        System.out.println(tupleList);
//
//    }

    @Test
    @Transactional
    void test2(){

        Optional<Article> optional = articleRepository.findById(1);

        if(optional.isPresent()){
            Article article = optional.get();
            System.out.println(article);
        }



    }
}