package com.carrot.backend.reatlyReview.service;

import com.carrot.backend.realty.dao.RealtyRepository;
import com.carrot.backend.realty.domain.Realty;
import com.carrot.backend.reatlyReview.dao.RealtyReviewRepository;
import com.carrot.backend.reatlyReview.domain.RealtyReview;
import com.carrot.backend.reatlyReview.dto.RealtyReviewDto;
import com.carrot.backend.user.dao.UserRepository;
import com.carrot.backend.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RealtyReviewService {
    private final RealtyRepository realtyRepository;
    private  final RealtyReviewRepository realtyReviewRepository;
    private final UserRepository userRepository;
    public RealtyReview addRealtyReview(RealtyReviewDto realtyReviewDto) {
        RealtyReview review = new RealtyReview();

        String userid = realtyReviewDto.getRealtySellUserId();
        User sellUser = userRepository.findByUserid(userid).get();
        review.setSellUser(sellUser);
        User buyUser = userRepository.findByUserid(realtyReviewDto.getRealtyBuyUserId()).get();
        review.setBuyUser(buyUser);

        review.setRealtyReview(realtyReviewDto.getRealtyReview());
        Realty realty = realtyRepository.findByRealtyId(realtyReviewDto.getRealtyId()).get();
        review.setRealty(realty);
        realty.setRealtyBuyUserid(buyUser.getUserid());
        realty.setRealtyDeal("거래 완료");

        review.setReqReview(sellUser);
        review.setResReview(buyUser);

        realtyRepository.save(realty);

        if(review.getRealtyReview().equals("별로예요")){
            buyUser.setTemp(buyUser.getTemp() - 0.5);
        }else if(review.getRealtyReview().equals("좋아요")){
            buyUser.setTemp(buyUser.getTemp() + 0.5);
        }else if(review.getRealtyReview().equals("최고예요")){
            buyUser.setTemp(buyUser.getTemp() + 1);
        }
        userRepository.save(buyUser);
        return realtyReviewRepository.save(review);
    }

    public RealtyReview addBuyRealtyReview(RealtyReviewDto realtyReviewDto) {
        RealtyReview realtyReview = new RealtyReview();

        String userid = realtyReviewDto.getRealtySellUserId();
        User sellUser = userRepository.findByUserid(userid).get();
        realtyReview.setSellUser(sellUser);
        User buyUser = userRepository.findByUserid(realtyReviewDto.getRealtyBuyUserId()).get();
        realtyReview.setBuyUser(buyUser);

        realtyReview.setRealtyReview(realtyReviewDto.getRealtyReview());
        Realty realty = realtyRepository.findByRealtyId(realtyReviewDto.getRealtyId()).get();
        realtyReview.setRealty(realty);

        realty.setReviewFinished(true);
        realtyReview.setReqReview(buyUser);
        realtyReview.setResReview(sellUser);



        if(realtyReviewDto.getRealtyReview().equals("별로예요")){
            sellUser.setTemp(sellUser.getTemp() - 0.5);


        }else if(realtyReviewDto.getRealtyReview().equals("좋아요")){
            sellUser.setTemp(sellUser.getTemp() + 0.5);

        }else if(realtyReviewDto.getRealtyReview().equals("최고예요")){
            sellUser.setTemp(sellUser.getTemp() + 1);

        }
        userRepository.save(sellUser);

        return realtyReviewRepository.save(realtyReview);
    }
}
