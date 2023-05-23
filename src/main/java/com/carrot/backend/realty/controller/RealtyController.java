package com.carrot.backend.realty.controller;

import com.carrot.backend.realty.domain.Realty;
import com.carrot.backend.realty.dto.RealtyDto;
import com.carrot.backend.realty.service.RealtyService;
import com.carrot.backend.realtyImage.service.RealtyImageService;
import com.carrot.backend.realtyLike.service.RealtyLikeService;
import com.carrot.backend.reatlyReview.domain.RealtyReview;
import com.carrot.backend.reatlyReview.dto.RealtyReviewDto;
import com.carrot.backend.reatlyReview.service.RealtyReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class RealtyController {

    private final RealtyService realtyService;
    private final RealtyImageService realtyImageService;

    private final RealtyLikeService realtyLikeService;

    private final RealtyReviewService realtyReviewService;
    @GetMapping("/realty")
    public List<Realty> getsRealty(){
        return realtyService.getsRealty();
    }

   @GetMapping("/realty/{realtyId}")
    public Realty getRealty(@PathVariable Integer realtyId){
        return realtyService.getRealty(realtyId);
   }

   @PostMapping("/createRealty")
    public Realty createRealty(@RequestBody RealtyDto realtyDto){
        Integer id = realtyService.createRealty(realtyDto);
        return realtyService.getRealty(id);
   }

    @PostMapping("/createRealtyImages")
    public Realty createRealtyImg(@RequestPart(value = "realtyDto") RealtyDto realtyDto, @RequestPart("file") List<MultipartFile> multipartFile) throws IOException {
        Integer id = realtyService.createRealty(realtyDto);
        realtyImageService.uploads(id, multipartFile, "realtyImages");
        return realtyService.getRealty(id);
    }

    @GetMapping("/getRealtyWithImage/{realtyId}")
    public RealtyDto getRealtyAndImages(@PathVariable Integer realtyId){
        return realtyService.getRealtyAndImage(realtyId);
    }

    @GetMapping("/likeRealtyCheck/{realtyId}")
    public boolean isLikedRealty(@RequestParam Integer realtyId,@RequestParam  String userid){
        return realtyLikeService.checkLikeRealty(realtyId, userid);
    }

    @GetMapping("/likeRealty/{realtyId}")
    public boolean likeRealty(@RequestParam Integer realtyId,@RequestParam String userid){
        return realtyLikeService.addRealtyLike(realtyId,userid);
    }

    @PostMapping("/realtyCheck/{realtyId}")
    public void realtyCheck (@PathVariable Integer realtyId){
        realtyService._realtyCheck(realtyId);
    }

    @PostMapping("/realtyDelete/{realtyId}")
    public void realtyDelete(@PathVariable Integer realtyId){
        realtyImageService.realtyDelete(realtyId, "realtyImages");
    }

    @GetMapping("/realtysearch/{search}")
    public List<Realty> realtySearch(@PathVariable String search){
        List<Realty> realties = realtyService.getSearch(search);
        return realties;
    }

    @PostMapping("/realtyReview")
    public RealtyReview realtyReview(@RequestBody RealtyReviewDto realtyReviewDto){
        return realtyReviewService.addRealtyReview(realtyReviewDto);
    }

    @PostMapping("/realtyBuyReview")
    public RealtyReview realtyBuyReview(@RequestBody RealtyReviewDto realtyReviewDto){

        return realtyReviewService.addBuyRealtyReview(realtyReviewDto);
    }
    @GetMapping("/realtyDong")
    public List<Realty> realtyDong(){
        return realtyService.realtyDong();
    }

    @GetMapping("/hotRealty")
    public List<Realty> hotRealty(){
        return realtyService.hotRealty();
    }
    @PostMapping("/realtyEdit/{realtyId}")
    public Realty realtyEdit(@PathVariable Integer realtyId, @RequestBody RealtyDto realtyDto){
        return realtyService.setRealty(realtyId , realtyDto);
    }

    @PostMapping("/RealtyImagesEdit/{realtyId}")
    public void productImageEdit(@PathVariable Integer realtyId, @RequestPart(value = "realtyDto") RealtyDto realtyDto, @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) throws IOException {
        realtyService.setRealty(realtyId , realtyDto);
        if(multipartFile != null){
            realtyImageService.deleteImage(realtyId,"realtyImages");

            realtyImageService.uploads(realtyId, multipartFile, "realtyImages");
        }
    }

}
