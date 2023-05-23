package com.carrot.backend.user.dao;

import com.carrot.backend.board.domain.Board;
import com.carrot.backend.board.domain.QBoard;
import com.carrot.backend.board.dto.BoardDto;
import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.jobs.domain.QJobs;
import com.carrot.backend.jobs.dto.JobsDto;
import com.carrot.backend.product.domain.Product;
import com.carrot.backend.product.domain.QProduct;
import com.carrot.backend.product.dto.ProductDto;
import com.carrot.backend.realty.domain.QRealty;
import com.carrot.backend.realty.domain.Realty;
import com.carrot.backend.realty.dto.RealtyDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomizedUserRepositoryImpl implements CustomizedUserRepository{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<RealtyDto> getQslRealtyByArticleWriterId(String userid) {
        List<Realty> realtys = jpaQueryFactory
                .select(QRealty.realty)
                .from(QRealty.realty)
                .where(QRealty.realty.realtyUserid.eq(userid))
                .fetch();



        List<RealtyDto> realtyDto = new ArrayList<>();
        for(Realty realty : realtys) {
            RealtyDto realtydto = RealtyDto.builder()
                    .realtyId(realty.getRealtyId())
                    .realtyUserid(realty.getRealtyUserid())
                    .createDate(realty.getCreateDate())
                    .realtyWho(realty.getRealtyWho())
                    .realtyDealing(realty.getRealtyDealing())
                    .realtyDeposit(realty.getRealtyDeposit())
                    .realtyMonthly(realty.getRealtyMonthly())
                    .realtyShortTerm(realty.getRealtyShortTerm())
                    .realtyChangePrice(realty.getRealtyChangePrice())
                    .realtyDepositChange(realty.getRealtyDepositChange())
                    .realtyCost(realty.getRealtyCost())
                    .realtyCostContent(realty.getRealtyCostContent())
                    .realtySalePrice(realty.getRealtySalePrice())
                    .realtyCategory(realty.getRealtyCategory())
                    .realtySpace(realty.getRealtySpace())
                    .realtyArea(realty.getRealtyArea())
                    .realtyWhole(realty.getRealtyWhole())
                    .realtyFloor(realty.getRealtyFloor())
                    .realtyRoom(realty.getRealtyRoom())
                    .realtyBath(realty.getRealtyBath())
                    .realtyAddress(realty.getRealtyAddress())
                    .realtyAddressDong(realty.getRealtyAddressDong())
                    .realtyIntroduce(realty.getRealtyIntroduce())
                    .realtyLoan(realty.getRealtyLoan())
                    .realtyMove(realty.getRealtyMove())
                    .realtyMoveDate(realty.getRealtyMoveDate())
                    .realtyPet(realty.getRealtyPet())
                    .realtyParking(realty.getRealtyParking())
                    .realtyElevator(realty.getRealtyElevator())
                    .realtyInside(realty.getRealtyInside())
                    .realtyShortDeal(realty.getRealtyShortDeal())
                    .realtyMonthlyDeal(realty.getRealtyMonthlyDeal())
                    .realtyDepositDeal(realty.getRealtyDepositDeal())
                    .realtyDeal(realty.getRealtyDeal())
                    .realtyContent(realty.getRealtyContent())
                    .realtyChatting(realty.getRealtyChatting())
                    .realtyLike(realty.getRealtyLike())
                    .realtyCheck(realty.getRealtyCheck())
                    .profileImage(realty.getProfileImage())
                    .realtyDeal(realty.getRealtyDeal())
                    .reviewFinished(realty.isReviewFinished())
                    .realtyBuyUserid(realty.getRealtyBuyUserid())
                    .build();
            realtyDto.add(realtydto);
        }
        return realtyDto;
    }

    @Override
    public List<ProductDto> getQslProductByArticleWriterId(String userid) {
        List<Product> products = jpaQueryFactory
                .select(QProduct.product)
                .from(QProduct.product)
                .where(QProduct.product.productUserid.eq(userid))
                .fetch();

        List<ProductDto> productDto = new ArrayList<>();
        for(Product product : products) {
            ProductDto productdto = ProductDto.builder()
                    .productId(product.getProductId())
                    .productCategory(product.getProductCategory())
                    .productView(product.getProductView())
                    .productContent(product.getProductContent())
                    .productSubject(product.getProductSubject())
                    .productCreateTime(product.getProductCreateTime())
                    .productChatting(product.getProductChatting())
                    .productLike(product.getProductLike())
                    .productPrice(product.getProductPrice())
                    .productDealAddress(product.getProductDealAddress())
                    .profileImage(product.getProfileImage())
                    .productUserid(product.getProductUserid())

                    .productDeal(product.getProductDeal())

                    .build();
            productDto.add(productdto);
        }
        return productDto;
    }

    @Override
    public List<JobsDto> getQslJobsByArticleWriterId(String userid) {
        List<Jobs>  jobLists= jpaQueryFactory
                .select(QJobs.jobs)
                .from(QJobs.jobs)
                .where(QJobs.jobs.jobUserid.eq(userid))
                .fetch();
        List<JobsDto> jobsDto = new ArrayList<>();
        for(Jobs jobs : jobLists) {
            JobsDto jobsdto = JobsDto.builder()
                    .jobid(jobs.getJobid())
                    .jobCategory(jobs.getJobCategory())
                    .jobCheck(jobs.getJobCheck())
                    .jobContent(jobs.getJobContent())
                    .jobSubject(jobs.getJobSubject())
                    .jobStartTime(jobs.getJobStartTime())
                    .jobEndTime(jobs.getJobEndTime())
                    .jobVolunteer(jobs.getJobVolunteer())
                    .jobLike(jobs.getJobLike())
                    .jobDay(jobs.getJobDay())
                    .jobPrice(jobs.getJobPrice())
                    .jobName(jobs.getJobName())
                    .createDate(jobs.getCreateDate())
                    .jobUserid(jobs.getJobUserid())
                    .jobPlace(jobs.getJobPlace())
                    .profileImage(jobs.getProfileImage())
                    .build();
            jobsDto.add(jobsdto);
        }
        return jobsDto;
    }

    public List<BoardDto> getQslBoardByBoardWriterId(String userid) {
        List<Board> boardDtoList = jpaQueryFactory
                .select(QBoard.board)
                .from(QBoard.board)
                .where(QBoard.board.boardUserid.eq(userid))
                .fetch();

        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boardDtoList) {
            BoardDto boardDto = BoardDto.builder()
                    .boardId(board.getBoardId())
                    .boardContent(board.getBoardContent())
                    .boardUserid(board.getBoardUserid())
                    .boardCategory(board.getBoardCategory())
                    .boardAddress(board.getBoardAddress())
                    .boardView(board.getBoardView())
                    .boardChattingNum(board.getBoardChattingNum())
                    .boardAgree(board.getBoardAgree())
                    .createDate(board.getCreateDate())
                    .profileImage(board.getProfileImage())
                    .build();
            boardDtos.add(boardDto);
        }
    return boardDtos;
    }

}
