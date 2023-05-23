package com.carrot.backend.realty.service;

import com.carrot.backend.realty.dao.CustomizedRealtyRepositoryImpl;
import com.carrot.backend.realty.dao.RealtyRepository;
import com.carrot.backend.realty.domain.Realty;
import com.carrot.backend.realty.dto.RealtyDto;
import com.carrot.backend.util.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RealtyService {

    private final RealtyRepository realtyRepository;

    private final CustomizedRealtyRepositoryImpl customizedRealtyRepository;

    public List<Realty> getsRealty(){
        return realtyRepository.findAll();
    }

    public Realty getRealty(Integer realtyId){
        return realtyRepository.findById(realtyId).orElseThrow(() -> new DataNotFoundException("realty not found"));
    }

    public Integer createRealty(RealtyDto realtyDto) {
        Realty newRealty = new Realty();

        newRealty.setRealtyWho(realtyDto.getRealtyWho());
        newRealty.setRealtyCategory(realtyDto.getRealtyCategory());
        newRealty.setRealtySpace(realtyDto.getRealtySpace());
        newRealty.setRealtyArea(realtyDto.getRealtyArea());
        newRealty.setRealtyRoom(realtyDto.getRealtyRoom());
        newRealty.setRealtyBath(realtyDto.getRealtyBath());
        newRealty.setRealtyAddress(realtyDto.getRealtyAddress());
        newRealty.setRealtyWhole(realtyDto.getRealtyWhole());
        newRealty.setRealtyFloor(realtyDto.getRealtyFloor());
        newRealty.setRealtyMove(realtyDto.getRealtyMove());
        newRealty.setRealtyMoveDate(realtyDto.getRealtyMoveDate());
        newRealty.setRealtyLoan(realtyDto.getRealtyLoan());
        newRealty.setRealtyPet(realtyDto.getRealtyPet());
        newRealty.setRealtyParking(realtyDto.getRealtyParking());
        newRealty.setRealtyElevator(realtyDto.getRealtyElevator());
        newRealty.setReviewFinished(false);
        String[] insideArr = new String[realtyDto.getRealtyInside().length];
        String[] inside =  realtyDto.getRealtyInside();
        if(insideArr.length == 0){
            newRealty.setRealtyInside(null);
        }else {
            for(int i = 0; i < insideArr.length; i++){
                if(i == insideArr.length-1){
                    insideArr[i] = inside[i];
                   break;
                }
                insideArr[i] = inside[i] + ", ";
            }
        }

        newRealty.setRealtyInside(insideArr);
        newRealty.setRealtyContent(realtyDto.getRealtyContent());
        LocalDateTime date = LocalDateTime.now();
        String dates = date.toString();
        String yymmdd = dates.substring(0,10);
        newRealty.setCreateDate(yymmdd);
        newRealty.setRealtyDeposit(realtyDto.getRealtyDeposit());
        newRealty.setRealtyMonthly(realtyDto.getRealtyMonthly());
        newRealty.setRealtyShortTerm(realtyDto.getRealtyShortTerm());
        newRealty.setRealtyChangePrice(realtyDto.getRealtyChangePrice());
        newRealty.setRealtyDepositChange(realtyDto.getRealtyDepositChange());
        newRealty.setRealtyCost(realtyDto.getRealtyCost());
        newRealty.setRealtyCostContent(realtyDto.getRealtyCostContent());
        newRealty.setRealtySalePrice(realtyDto.getRealtySalePrice());
        newRealty.setRealtyDealing(realtyDto.getRealtyDealing());
        newRealty.setRealtyCheck(0);
        newRealty.setRealtyChatting(0);
        newRealty.setRealtyDeal("판매중");
        newRealty.setRealtyUserid(realtyDto.getRealtyUserid());
        newRealty.setRealtyLike(0);
        newRealty.setRealtyIntroduce(realtyDto.getRealtyIntroduce());
        newRealty.setRealtyAddressDong(realtyDto.getRealtyAddressDong());
        realtyRepository.save(newRealty);

        return newRealty.getRealtyId();
    }
    public RealtyDto getRealtyAndImage(Integer realtyId) {
        RealtyDto realtyDto = customizedRealtyRepository.getQslRealtyAndImagesByRealtyId(realtyId);
        return realtyDto;
    }
    public void _realtyCheck(Integer realtyId) {
        Realty realty = realtyRepository.findByRealtyId(realtyId).orElseThrow(()-> new DataNotFoundException("realty not found"));
        realty.setRealtyCheck(realty.getRealtyCheck() +1);
        realtyRepository.save(realty);
    }
    public List<Realty> getSearch(String search) {
        List<Realty> realties = realtyRepository.findByRealtyAddressContaining(search);
        return realties;
    }

    public void addChatNumRealty(Integer articleId) {
        Realty realty = realtyRepository.findByRealtyId(articleId).get();
        realty.setRealtyChatting(realty.getRealtyChatting()+1);
        realtyRepository.save(realty);
    }

    public List<Realty> realtyDong() {
        return realtyRepository.findAll(Sort.by(Sort.Direction.ASC,  "createDate"));
    }

    public List<Realty> hotRealty() {
        return realtyRepository.findAll(Sort.by(Sort.Direction.ASC, "realtyCheck", "createDate"));
    }

    public Realty setRealty(Integer realtyId, RealtyDto realtyDto) {
        Realty newRealty = realtyRepository.findByRealtyId(realtyId).get();

        newRealty.setRealtyWho(realtyDto.getRealtyWho());
        newRealty.setRealtyCategory(realtyDto.getRealtyCategory());
        newRealty.setRealtySpace(realtyDto.getRealtySpace());
        newRealty.setRealtyArea(realtyDto.getRealtyArea());
        newRealty.setRealtyRoom(realtyDto.getRealtyRoom());
        newRealty.setRealtyBath(realtyDto.getRealtyBath());
        newRealty.setRealtyAddress(realtyDto.getRealtyAddress());
        newRealty.setRealtyWhole(realtyDto.getRealtyWhole());
        newRealty.setRealtyFloor(realtyDto.getRealtyFloor());
        newRealty.setRealtyMove(realtyDto.getRealtyMove());
        newRealty.setRealtyMoveDate(realtyDto.getRealtyMoveDate());
        newRealty.setRealtyLoan(realtyDto.getRealtyLoan());
        newRealty.setRealtyPet(realtyDto.getRealtyPet());
        newRealty.setRealtyParking(realtyDto.getRealtyParking());
        newRealty.setRealtyElevator(realtyDto.getRealtyElevator());
        String[] insideArr = new String[realtyDto.getRealtyInside().length];
        String[] inside =  realtyDto.getRealtyInside();
        if(insideArr.length == 0){
            newRealty.setRealtyInside(null);
        }else {
            for(int i = 0; i < insideArr.length; i++){
                if(i == insideArr.length-1){
                    insideArr[i] = inside[i];
                    break;
                }
                insideArr[i] = inside[i] + ", ";
            }
        }

        newRealty.setRealtyInside(insideArr);
        newRealty.setRealtyContent(realtyDto.getRealtyContent());
        LocalDateTime date = LocalDateTime.now();
        String dates = date.toString();
        String yymmdd = dates.substring(0,10);
        newRealty.setCreateDate(yymmdd);
        newRealty.setRealtyDeposit(realtyDto.getRealtyDeposit());
        newRealty.setRealtyMonthly(realtyDto.getRealtyMonthly());
        newRealty.setRealtyShortTerm(realtyDto.getRealtyShortTerm());
        newRealty.setRealtyChangePrice(realtyDto.getRealtyChangePrice());
        newRealty.setRealtyDepositChange(realtyDto.getRealtyDepositChange());
        newRealty.setRealtyCost(realtyDto.getRealtyCost());
        newRealty.setRealtyCostContent(realtyDto.getRealtyCostContent());
        newRealty.setRealtySalePrice(realtyDto.getRealtySalePrice());
        newRealty.setRealtyDealing(realtyDto.getRealtyDealing());


        newRealty.setRealtyUserid(realtyDto.getRealtyUserid());

        newRealty.setRealtyIntroduce(realtyDto.getRealtyIntroduce());
        newRealty.setRealtyAddressDong(realtyDto.getRealtyAddressDong());


        return realtyRepository.save(newRealty);
    }
}

