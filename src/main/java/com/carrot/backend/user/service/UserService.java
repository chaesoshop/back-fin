package com.carrot.backend.user.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.carrot.backend.board.dto.BoardDto;
import com.carrot.backend.chatting.dao.ChattingRoomRepository;
import com.carrot.backend.chatting.domain.ChattingRoom;
import com.carrot.backend.jobs.dto.JobsDto;
import com.carrot.backend.product.dto.ProductDto;
import com.carrot.backend.realty.dto.RealtyDto;
import com.carrot.backend.user.UserLoginForm;
import com.carrot.backend.user.dao.CustomizedUserRepositoryImpl;
import com.carrot.backend.user.dao.UserRepository;
import com.carrot.backend.user.domain.User;
import com.carrot.backend.user.dto.UserDto;
import com.carrot.backend.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Component
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3Client amazonS3Client;

    private final ChattingRoomRepository chattingRoomRepository;

    private final CustomizedUserRepositoryImpl customizedUserRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public User create(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String username = userDto.getUsername();
        String userid = userDto.getUserid();
        String password = userDto.getPassword();
        String birth = userDto.getBirth().replaceAll("[^0123456789]","");
        String year = birth.substring(0,4);
        String month = birth.substring(4,6);
        String day = birth.substring(6);
        birth = (year + "-" + month + "-" + day);
        String address = userDto.getAddress();
        String email;
        if(userDto.getEmail()!="") {
            email = userDto.getEmail();
        }else {
            email = "이메일 없음";
        }
        String nickname;
        if(userDto.getNickname()!=""){
            nickname = userDto.getNickname();
        }else {
            nickname = "닉네임 없음";
        }
        String phone = userDto.getPhone();
        User user = User.builder()
                .username(username)
                .userid(userid)
                .password(passwordEncoder.encode(password))
                .birth(birth)
                .address(address)
                .email(email)
                .nickname(nickname)
                .phone(phone)
                .role("ROLE_USER")
                .temp(36.5)
                .happyReview(0)
                .sadReview(0)
                .smileReview(0)
                .build();

        userRepository.save(user);
        return user;
    }
    public User getUser(String userid) {

            User user = this.userRepository.findByUserid(userid).orElseThrow(()-> new UsernameNotFoundException("user not found"));
            return user;

    }

    public boolean checkId(String userid) {
        Optional<User> user = userRepository.findByUserid(userid);
        if(user.isPresent()){
            return false;
        }else{
            return true;
        }
    }

    public String login(UserLoginForm userLoginForm) throws UsernameNotFoundException{
        User user = getUser(userLoginForm.getUserid());
        if(user==null){
            return "false";
        }

        if(passwordEncoder.matches(userLoginForm.getPassword(),user.getPassword())) {
            return ("true"+user.getUserid());
        }else {

            return "false";
        }
    }

public void changeUserProfileImage(UserDto userdto, List<MultipartFile> multipartFile, String users) throws IOException {
    List<File> uploadFile = new ArrayList<File>();
    for(int i=0;i< multipartFile.size();i++) {

            MultipartFile files = multipartFile.get(i);
            File upload = convert(files)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
            uploadFile.add(upload);
        }
    upload(userdto, uploadFile, users);
}
    public void deleteS3File(String fileName, String bucketFolder){
        String file = bucketFolder+"/"+fileName;
        amazonS3Client.deleteObject(bucket,file);
    }

        private void removeNewFile (File targetFile){
            if (targetFile.delete()) {
                log.info("파일이 삭제되었습니다.");
            } else {
                log.info("파일이 삭제되지 못했습니다.");
            }
        }

    public void changeUsersNickname(UserDto userdto){
        User user = userRepository.findByUserid(userdto.getUserid()).orElseThrow(()-> new DataNotFoundException("user not found"));
        user.setNickname(userdto.getNickname());
        userRepository.save(user);
    }
        private void upload (UserDto userdto, List<File> uploadFile, String dirName){
                String userid = userdto.getUserid();
                String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.get(0).getName();

            User user = userRepository.findByUserid(userid).orElseThrow(()-> new DataNotFoundException("user not found"));

            String file = user.getProfileImage();
            if(file!=null) {
                String[] filename = file.split(dirName + "/");
                deleteS3File(filename[1], dirName);
            }
                String path = putS3(uploadFile.get(0), fileName);

                removeNewFile(uploadFile.get(0));
                user.setProfileImage(path);
                user.setNickname(userdto.getNickname());
                userRepository.save(user);

            changeRoomImage(user,path);

        }
        private String putS3 (File uploadFile, String fileName){
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                    CannedAccessControlList.PublicRead));
            return amazonS3Client.getUrl(bucket, fileName).toString();
        }

        private Optional<File> convert (MultipartFile file) throws IOException {


            File convertFile = new File(file.getOriginalFilename());

            if (convertFile.createNewFile()) {

                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            }

            return Optional.empty();
        }


    public void resetUserProfile(UserDto userdto, String bucketFolder) {
        User user = userRepository.findByUserid(userdto.getUserid()).orElseThrow(()-> new DataNotFoundException("user not found"));
        user.setNickname("닉네임 없음");
        String file = user.getProfileImage();
        String[] filename = file.split(bucketFolder+"/");
        deleteS3File(filename[1], bucketFolder);
        user.setProfileImage(null);
        userRepository.save(user);
        resetRoomImage(user);

    }

    public void resetUserImage(UserDto userdto,String bucketFolder){
        User user = userRepository.findByUserid(userdto.getUserid()).orElseThrow(()-> new DataNotFoundException("user not found"));
        String file = user.getProfileImage();
        String[] filename = file.split(bucketFolder+"/");
        deleteS3File(filename[1],bucketFolder);
        user.setProfileImage(null);
        userRepository.save(user);
        resetRoomImage(user);

    }

    public void resetRoomImage (User user){
        List<ChattingRoom> chattingRooms = chattingRoomRepository.findByMyNameOrYourNameContaining(user.getUserid(),user.getUserid());
        for(int i=0;i<chattingRooms.size(); i++){
            if(chattingRooms.get(i).getMyName().equals(user.getUserid())){
                chattingRooms.get(i).setMyURL(null);
                chattingRoomRepository.save(chattingRooms.get(i));
            }else{
                chattingRooms.get(i).setYourURL(null);
                chattingRoomRepository.save(chattingRooms.get(i));
            }
        }
    }
    public void changeRoomImage (User user,String URL){
        List<ChattingRoom> chattingRooms = chattingRoomRepository.findByMyNameOrYourNameContaining(user.getUserid(),user.getUserid());
        for(int i=0;i<chattingRooms.size(); i++){
            if(chattingRooms.get(i).getMyName().equals(user.getUserid())){
                chattingRooms.get(i).setMyURL(URL);
                chattingRoomRepository.save(chattingRooms.get(i));
            }else{
                chattingRooms.get(i).setYourURL(URL);
                chattingRoomRepository.save(chattingRooms.get(i));
            }
        }
    }
    public boolean checkpassword(UserDto userDto) {
        User user = userRepository.findByUserid(userDto.getUserid()).orElseThrow(()-> new DataNotFoundException("user not found"));
        if(user==null){
            return false;
        }

        if(passwordEncoder.matches(userDto.getPassword(),user.getPassword())) {
            return true;
        }else {

            return false;
        }
    }

    public boolean changeUserPassword(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByUserid(userDto.getUserid()).orElseThrow(()-> new DataNotFoundException("user not found"));
        user.setPassword(passwordEncoder.encode(userDto.getPassword1()));
       userRepository.save(user);
       return true;



    }

    public List<RealtyDto> _getRealtys(String userid) {
        List<RealtyDto> realtyDtos = customizedUserRepository.getQslRealtyByArticleWriterId(userid);
        return realtyDtos;
    }

    public List<ProductDto> _getProducts(String userid) {
        List<ProductDto> productDtos = customizedUserRepository.getQslProductByArticleWriterId(userid);
        return productDtos;
    }

    public List<JobsDto> _getJobs(String userid) {
        List<JobsDto> jobsDtos = customizedUserRepository.getQslJobsByArticleWriterId(userid);
        return jobsDtos;
    }

    public List<BoardDto> _getBoards(String userid) {
        List<BoardDto> boardDtos = customizedUserRepository.getQslBoardByBoardWriterId(userid);
        return boardDtos;
    }

//    public List<BoardDto> _getCafe() {
//    List<BoardDto> boardDtos = customizedUserRepository.getQslBoardByCafeId();
//        return boardDtos;
//    }
}

