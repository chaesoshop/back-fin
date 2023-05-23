package com.carrot.backend.user.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User implements UserDetails{

    @Id
    @NotEmpty
    private String userid;

    @NotEmpty
    private String password;

    private String password1;

    @NotEmpty(message = "이름은 필수 항목입니다.")
    private String username;

    @NotEmpty(message = "생년월일은 필수 항목입니다.")
    private String birth;

//    @Column(unique = true)
//    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Size(max = 40)
    private String nickname;

    @NotEmpty(message = "연락처는 필수항목입니다.")
    private String phone;

    @NotEmpty(message = "주소는 필수항목입니다.")
    private String address;

    private double temp;

    private String role;

    @Column
    private String profileImage;

    @Column
    private Integer sadReview;

    @Column
    private Integer smileReview;

    @Column
    private Integer happyReview;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return getRole();
            }

        });
        return collect;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}