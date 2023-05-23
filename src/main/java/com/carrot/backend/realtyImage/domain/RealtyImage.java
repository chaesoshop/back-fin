package com.carrot.backend.realtyImage.domain;

import com.carrot.backend.realty.domain.Realty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "realty_image")
public class RealtyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long realtyImageId;

    String realtyPath;

    @ManyToOne
    private Realty realty;
}
