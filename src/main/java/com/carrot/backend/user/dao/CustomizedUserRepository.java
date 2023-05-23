package com.carrot.backend.user.dao;

import com.carrot.backend.board.dto.BoardDto;
import com.carrot.backend.jobs.dto.JobsDto;
import com.carrot.backend.product.dto.ProductDto;
import com.carrot.backend.realty.dto.RealtyDto;

import java.util.List;

public interface CustomizedUserRepository {
    List<RealtyDto> getQslRealtyByArticleWriterId(String userid);
    List<ProductDto> getQslProductByArticleWriterId(String userid);
    List<JobsDto> getQslJobsByArticleWriterId(String userid);
    List<BoardDto> getQslBoardByBoardWriterId(String userid);
}
