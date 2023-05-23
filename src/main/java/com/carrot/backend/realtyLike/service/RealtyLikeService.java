package com.carrot.backend.realtyLike.service;

import com.carrot.backend.realty.dao.RealtyRepository;
import com.carrot.backend.realty.domain.Realty;
import com.carrot.backend.realtyLike.dao.RealtyLikeRepository;
import com.carrot.backend.realtyLike.domain.RealtyLike;
import com.carrot.backend.user.dao.UserRepository;
import com.carrot.backend.user.domain.User;
import com.carrot.backend.util.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RealtyLikeService {
    private final RealtyLikeRepository realtyLikeRepository;
    private final UserRepository userRepository;
    private final RealtyRepository realtyRepository;

    public boolean addRealtyLike(Integer realtyid, String userid){
        User user = userRepository.findByUserid(userid).orElseThrow(()-> new DataNotFoundException("user not found"));
        Realty realty = realtyRepository.findByRealtyId(realtyid).orElseThrow(()-> new DataNotFoundException("realty not found"));
        if(isNotAlreadyLikeRealty(realty,user)){
            realtyLikeRepository.save(new RealtyLike(realty, user));
            int prev = realty.getRealtyLike();
            realty.setRealtyLike(prev+1);
            return true;
        }else{
            RealtyLike like = realtyLikeRepository.findByRealtyAndUser(realty,user).orElseThrow(()-> new DataNotFoundException("like not found"));
            int prev = realty.getRealtyLike();
            realty.setRealtyLike(prev-1);
            realtyLikeRepository.delete(like);
            return false;
        }
    }
    private boolean isNotAlreadyLikeRealty(Realty realty, User user) {
        return realtyLikeRepository.findByRealtyAndUser(realty, user).isEmpty();
    }

    public boolean checkLikeRealty(Integer realtyId, String userid) {
        User user = userRepository.findByUserid(userid).orElseThrow(()-> new DataNotFoundException("user not found"));
        Realty realty = realtyRepository.findByRealtyId(realtyId).orElseThrow(()-> new DataNotFoundException("realty not found"));
        if(isNotAlreadyLikeRealty(realty,user)){

            return false;
        }else{

            return true;
        }
    }
}
