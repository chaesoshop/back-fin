package com.carrot.backend.search.controller;

import com.carrot.backend.board.domain.Board;
import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.product.domain.Product;
import com.carrot.backend.realty.domain.Realty;
import com.carrot.backend.search.domain.Search;
import com.carrot.backend.search.dto.SearchDto;
import com.carrot.backend.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/searchProduct")
    public List<Product> searchAll(@RequestBody SearchDto searchDto){
        return searchService.searchProduct(searchDto);
    }

    @PostMapping("/searchJobs")
    public List<Jobs> searchJobs(@RequestBody SearchDto searchDto){
        return searchService.searchJobs(searchDto);
    }

    @PostMapping("/searchRealty")
    public List<Realty> searchRealty(@RequestBody SearchDto searchDto){
        return searchService.searchRealtys(searchDto);
    }

    @PostMapping("/searchBoard")
    public List<Board> searchBoard(@RequestBody SearchDto searchDto){
        return searchService.searchBoards(searchDto);
    }

    @GetMapping("/getHotSearch")
    public List<Search> getHotSearch(){
        return searchService._getHotSearch();
    }
}
