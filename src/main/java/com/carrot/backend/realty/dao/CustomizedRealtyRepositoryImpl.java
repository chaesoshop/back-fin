package com.carrot.backend.realty.dao;

import com.carrot.backend.realty.domain.QRealty;
import com.carrot.backend.realty.domain.Realty;
import com.carrot.backend.realty.dto.RealtyDto;
import com.carrot.backend.realtyImage.domain.QRealtyImage;
import com.carrot.backend.realtyLike.domain.QRealtyLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomizedRealtyRepositoryImpl implements CustomizedRealtyRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public RealtyDto getQslRealtyAndImagesByRealtyId(Integer realtyId) {
        Realty realty = jpaQueryFactory
                .select(QRealty.realty)
                .from(QRealty.realty)
                .innerJoin(QRealtyImage.realtyImage)
                .on(QRealty.realty.realtyId.eq(realtyId))
                .fetchOne();

        List<String> imagePaths = new ArrayList<>();
        realty.getImages().stream().forEach(realtyImage->imagePaths.add(realtyImage.getRealtyPath()));

        RealtyDto realtyDto = RealtyDto.builder()
                .realtyId(realty.getRealtyId())
                .realtyUserid(realty.getRealtyUserid())
                .createDate(realty.getCreateDate())
                .realtyWho(realty.getRealtyWho())
                .realtyDealing(realty.getRealtyDealing())
                .realtyDeposit(realty.getRealtyDeposit())
                .realtyMonthly(realty.getRealtyMonthly())
                .realtyShortTerm(realty.getRealtyShortTerm())
                .realtyIntroduce(realty.getRealtyIntroduce())
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
                .reviewFinished(realty.isReviewFinished())
                .realtyBuyUserid(realty.getRealtyBuyUserid())
                .images(imagePaths)
                .build();
        return realtyDto;
    }

    @Override
    @Transactional
    public void deleteQslRealtyAndImagesByRealtyId(Integer realtyId) {
        Long realty1 = jpaQueryFactory
                .delete(QRealtyLike.realtyLike)
                .where(QRealtyLike.realtyLike.realty.realtyId.eq(realtyId))
                .execute();
        Long realty = jpaQueryFactory
                .delete(QRealty.realty)
                .where(QRealty.realty.realtyId.eq(realtyId))
                .execute();
    }
}
