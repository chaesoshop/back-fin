package com.carrot.backend.realtyImage.controller;

import com.carrot.backend.realty.service.RealtyService;
import com.carrot.backend.realtyImage.service.RealtyImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RealtyImageController {
    private final RealtyImageService realtyImageService;
    private final RealtyService realtyService;
}
