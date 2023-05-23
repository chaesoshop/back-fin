package com.carrot.backend.notification.domain;

import com.carrot.backend.util.DataNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class RelatedURL {
    private static final int MAX_LENGTH = 255;

    @Column(nullable = false,length = MAX_LENGTH)
    private String url;

    public RelatedURL(String url) {
        if (isNotValidRelatedURL(url)) {
            throw new DataNotFoundException("not found");
        }
        this.url = url;
    }

    private boolean isNotValidRelatedURL(String url) {
        return Objects.isNull(url) || url.length() > MAX_LENGTH || url.isEmpty();

    }

}