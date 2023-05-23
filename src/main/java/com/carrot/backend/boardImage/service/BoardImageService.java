package com.carrot.backend.boardImage.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.carrot.backend.board.dao.BoardRepository;
import com.carrot.backend.board.dao.CustomizedBoardRepositoryImpl;
import com.carrot.backend.board.domain.Board;
import com.carrot.backend.board.service.BoardService;
import com.carrot.backend.boardImage.dao.BoardImageRepository;
import com.carrot.backend.boardImage.domain.BoardImage;
import com.carrot.backend.boardImage.dto.BoardImageDto;
import com.carrot.backend.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Component
public class BoardImageService {
    private final AmazonS3Client amazonS3Client;
    private final BoardImageRepository boardImageRepository;
    private final BoardService boardService;
    private final CustomizedBoardRepositoryImpl customizedBoardRepository;
    private final BoardRepository boardRepository;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public List<BoardImageDto> uploads(Integer boardId, List<MultipartFile> multipartFile, String dirName) throws IOException {
        List<File> uploadFile = new ArrayList<File>();
        for(int i=0;i< multipartFile.size();i++) {
            System.out.println("size :"+multipartFile.size());

            MultipartFile files = multipartFile.get(i);
            File upload = convert(files)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
            uploadFile.add(upload);
        }

        return upload(boardId, uploadFile, dirName);
    }

    private List<BoardImageDto> upload (Integer boardId, List<File> uploadFile, String dirName){
        List<BoardImageDto> images = new ArrayList<>();
        for(int i=0;i< uploadFile.size();i++){
            String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.get(i).getName();


            String path = putS3(uploadFile.get(i), fileName);

            removeNewFile(uploadFile.get(i));
            BoardImageDto image = new BoardImageDto(boardId,path);
            images.add(i,image);

            BoardImage boardImage = new BoardImage();
            boardImage.setBoardPath(path);
            boardImage.setBoard(boardService.getBoard(boardId));
            boardImageRepository.save(boardImage);

            if(i==0) {
                Board board = boardRepository.findById(boardId).orElseThrow(() -> new DataNotFoundException("board not found"));
                board.setProfileImage(path);
                boardRepository.save(board);
            }

        }
//FileUploadResponse DTO로 반환해준다.
        return images;
        //return uploadImageUrl;
    }

    private String putS3 (File uploadFile, String fileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));


        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void boardDelete(Integer boardId, String dirName) {
        List<BoardImage> boardImages = boardImageRepository.findByBoardBoardId(boardId);

        if (boardImages.size() > 0){
            String[] image = new String[boardImages.size()];
            for(int i =0; i<boardImages.size(); i++){
                image[i] = boardImages.get(i).getBoardPath();
                String[] filename = image[i].split(dirName + "/");
                deleteS3File(filename[1], dirName);

                BoardImage boardImage = boardImages.get(i);
                boardImageRepository.delete(boardImage);
            }

            customizedBoardRepository.deleteQslBoardAndImageByBoardId(boardId);
        }
        else{
            customizedBoardRepository.deleteQslBoardAndImageByBoardId(boardId);
        }
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

}
