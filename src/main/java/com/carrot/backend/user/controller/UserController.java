package com.carrot.backend.user.controller;

import com.carrot.backend.board.dto.BoardDto;
import com.carrot.backend.jobs.dto.JobsDto;
import com.carrot.backend.product.dto.ProductDto;
import com.carrot.backend.realty.dto.RealtyDto;
import com.carrot.backend.user.UserLoginForm;
import com.carrot.backend.user.domain.User;
import com.carrot.backend.user.dto.UserDto;
import com.carrot.backend.user.service.UserService;
import com.carrot.backend.util.NoUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/createUser")
    public User createUser(@RequestBody UserDto userDto) {
        User user = userService.create(userDto);
        return user;
    }

    @PostMapping("/loginUser")
    public String loginUser(@RequestBody UserLoginForm userLoginForm) {
        String userid = userService.login(userLoginForm);
        return userid;
    }

    @GetMapping("/getUser/{userid}")
    public User _getUser(@PathVariable("userid") String userid) throws NoUserException {
        User user = userService.getUser(userid);
        return user;
    }

    @GetMapping("/checkId/{userid}")
    public boolean _checkId(@PathVariable("userid") String uid) {
        boolean id = userService.checkId(uid);
        return id;
    }

    @PostMapping("/userProfileImageChange")
    public void changeUserProfile(@RequestPart(value = "userdto") UserDto userdto, @RequestPart("file") List<MultipartFile> multipartFile) throws IOException {
            userService.changeUserProfileImage(userdto, multipartFile, "users");
    }

    @PostMapping("/userProfileImageAndNicknameReset")
    public void resetUserProfile(@RequestPart(value = "userdto") UserDto userdto) throws IOException{
        userService.resetUserProfile(userdto,"users");
    }

    @PostMapping("/userProfileImageReset")
    public void resetUserProfileImage(@RequestPart(value="userdto") UserDto userdto) throws IOException{
        userService.resetUserImage(userdto,"users");
    }
    @PostMapping("/userProfileChange")
    public void changeUserNickname(@RequestPart(value = "userdto") UserDto userdto) throws IOException {
        userService.changeUsersNickname(userdto);
    }

    @PostMapping("/checkPw")
    public boolean checkPassword(@RequestBody UserDto userDto){
        return userService.checkpassword(userDto);
    }

    @PostMapping("/changePw")
    public boolean changePassword(@RequestBody UserDto userDto){
        boolean rs1 = userService.checkpassword(userDto);
        if(!rs1){
            return false;
        }
        boolean rs = userService.changeUserPassword(userDto);
        return rs;
    }

    @PostMapping("/getRealtys/{userid}")
    public List<RealtyDto> getRealtysById(@PathVariable String userid){
        return userService._getRealtys(userid);
    }

    @PostMapping("/getProducts/{userid}")
    public List<ProductDto> getProductsById(@PathVariable String userid){
        return userService._getProducts(userid);
    }

    @PostMapping("/getJobs/{userid}")
    public List<JobsDto> getJobsById(@PathVariable String userid){
        return userService._getJobs(userid);
    }

    @PostMapping("/getBoard/{userid}")
    public List<BoardDto> getBoardByID(@PathVariable String userid){
        return userService._getBoards(userid);
    }

//    @GetMapping("/getBoardCafe")
//    public List<BoardDto> getBoardCafe(){
//        return userService._getCafe();
//    }
}
