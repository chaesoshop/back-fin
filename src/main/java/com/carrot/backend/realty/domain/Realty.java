package com.carrot.backend.realty.domain;


import com.carrot.backend.realtyImage.domain.RealtyImage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "realty")
public class Realty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer realtyId;

    @Column
    String realtyUserid;

    @Column
    String realtyBuyUserid;

    String createDate;

    @Column
    @NotNull
    String realtyWho;

    @Column
    String realtyDealing;

    @Column
    String realtyDeposit;

    @Column
    String realtyMonthly;

    @Column
    String realtyShortTerm;

    @Column
    String realtyChangePrice;

    @Column
    String realtyDepositChange;

    @Column
    String realtyCost;

    @Column
    String realtyCostContent;

    @Column
    String realtySalePrice;

    @Column
    String realtyCategory;

    @Column
    @NotNull
    String realtySpace;

    @Column
    @NotNull
    String realtyArea;

    @Column
    String realtyWhole;

    @Column
    @NotNull
    String realtyFloor;

    @Column
    @NotNull
    String realtyRoom;

    @Column
    @NotNull
    String realtyBath;

    @Column
    @NotEmpty
    String realtyAddress;

    @Column
    String realtyAddressDong;

    @Column
    String realtyLoan;

    @Column
    String realtyMove;

    @Column
    String realtyMoveDate;

    @Column
    String realtyPet;

    @Column
    String realtyParking;

    @Column
    String realtyElevator;

    @Column
    String[] realtyInside;

    @Column
    String realtyShortDeal;

    @Column
    String realtyMonthlyDeal;

    @Column
    String realtyDepositDeal;

    @Column
    String realtyDeal;

    @Column(length = 200)
    @NotEmpty
    String realtyContent;

    @Column
    Integer realtyChatting;

    @Column
    Integer realtyLike;

    @Column
    Integer realtyCheck;

    @Column
    String profileImage;

    @OneToMany(mappedBy = "realty", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<RealtyImage> images;

    @Column
    String realtyIntroduce;

    @Column
    boolean reviewFinished;


}