package com.carrot.backend.search.service;

import com.carrot.backend.board.dao.BoardRepository;
import com.carrot.backend.board.domain.Board;
import com.carrot.backend.jobs.dao.JobsRepository;
import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.product.dao.ProductRepository;
import com.carrot.backend.product.domain.Product;
import com.carrot.backend.realty.dao.RealtyRepository;
import com.carrot.backend.realty.domain.Realty;
import com.carrot.backend.search.dao.SearchRepository;
import com.carrot.backend.search.domain.Search;
import com.carrot.backend.search.dto.SearchDto;
import com.carrot.backend.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    private final ProductRepository productRepository;

    private final RealtyRepository realtyRepository;

    private final JobsRepository jobsRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;
    public List<Product> searchProduct(SearchDto searchDto){

        if(searchRepository.findBySearchWord(searchDto.getSearchWord()).isPresent()){
            Search search1 = searchRepository.findBySearchWord(searchDto.getSearchWord()).get();
            search1.setSearchNum(search1.getSearchNum()+1);
            searchRepository.save(search1);
        }else {
            Search search2 = new Search();
            search2.setSearchWord(searchDto.getSearchWord());
            search2.setSearchNum(1);
            searchRepository.save(search2);
        }

        List<Product> products = productRepository.findByProductSubjectContainingOrProductContentContainingOrProductCategoryContainingOrProductDealAddressContaining(searchDto.getSearchWord(),searchDto.getSearchWord(),searchDto.getSearchWord(),searchDto.getSearchWord());


        return products;

    }

    public List<Jobs> searchJobs(SearchDto searchDto){
        if(searchRepository.findBySearchWord(searchDto.getSearchWord()).isPresent()){


            Search search1 = searchRepository.findBySearchWord(searchDto.getSearchWord()).get();
            search1.setSearchNum(search1.getSearchNum()+1);
            searchRepository.save(search1);
        }else {


            Search search2 = new Search();
            search2.setSearchWord(searchDto.getSearchWord());
            search2.setSearchNum(1);

            searchRepository.save(search2);
        }

        List<Jobs> jobs = jobsRepository.findByJobContentContainingOrJobDayContainingOrJobNameContainingOrJobPlaceContainingOrJobSubjectContaining(searchDto.getSearchWord(),searchDto.getSearchWord(),searchDto.getSearchWord(),searchDto.getSearchWord(),searchDto.getSearchWord());


        return jobs;

    }

    public List<Realty> searchRealtys(SearchDto searchDto) {
        if(searchRepository.findBySearchWord(searchDto.getSearchWord()).isPresent()){
            Search search1 = searchRepository.findBySearchWord(searchDto.getSearchWord()).get();
            search1.setSearchNum(search1.getSearchNum()+1);
            searchRepository.save(search1);
        }else {
            Search search2 = new Search();
            search2.setSearchWord(searchDto.getSearchWord());
            search2.setSearchNum(1);

            searchRepository.save(search2);
        }

        List<Realty> realtys = realtyRepository.findByRealtyAddressContainingOrRealtyCategoryContainingOrRealtyContentContainingOrRealtyDealingContainingOrRealtyIntroduceContaining(searchDto.getSearchWord(),searchDto.getSearchWord(),searchDto.getSearchWord(),searchDto.getSearchWord(),searchDto.getSearchWord());


        return realtys;
    }

    public List<Board> searchBoards(SearchDto searchDto) {
        if(searchRepository.findBySearchWord(searchDto.getSearchWord()).isPresent()){
            Search search1 = searchRepository.findBySearchWord(searchDto.getSearchWord()).get();
            search1.setSearchNum(search1.getSearchNum()+1);
            searchRepository.save(search1);
        }else {
            Search search2 = new Search();
            search2.setSearchWord(searchDto.getSearchWord());
            search2.setSearchNum(1);

            searchRepository.save(search2);
        }

        List<Board> boards = boardRepository.findByBoardAddressContainingOrBoardContentContaining(searchDto.getSearchWord(),searchDto.getSearchWord());


        return boards;
    }

    public List<Search> _getHotSearch() {
        return searchRepository.findAll(Sort.by(Sort.Direction.ASC,"searchNum"));
    }
}
