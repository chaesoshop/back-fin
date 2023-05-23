package com.carrot.backend.jobImage.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.carrot.backend.jobImage.dao.JobsImageRepository;
import com.carrot.backend.jobImage.domain.JobsImages;
import com.carrot.backend.jobImage.dto.JobsImagesDto;
import com.carrot.backend.jobs.dao.CustomizedJobsRepositoryImpl;
import com.carrot.backend.jobs.dao.JobsRepository;
import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.jobs.service.JobsService;
import com.carrot.backend.jobsApply.dao.JobsApplyRepository;
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
public class JobsImageService {
    private final AmazonS3Client amazonS3Client;
    private final JobsImageRepository jobsImageRepository;
    private final JobsService jobsService;

    private final JobsRepository jobsRepository;

    private final CustomizedJobsRepositoryImpl customizedJobsRepository;
    private final JobsApplyRepository jobsApplyRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<JobsImagesDto> uploads(Integer jobsId, List<MultipartFile> multipartFile, String dirName) throws IOException {
        List<File> uploadFile = new ArrayList<File>();
        for(int i=0;i< multipartFile.size();i++) {
            System.out.println("size :"+multipartFile.size());

            MultipartFile files = multipartFile.get(i);
            File upload = convert(files)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
            uploadFile.add(upload);
        }

        return upload(jobsId, uploadFile, dirName);
    }

    private List<JobsImagesDto> upload (Integer jobsId, List<File> uploadFile, String dirName){
        List<JobsImagesDto> images = new ArrayList<>();
        for(int i=0;i< uploadFile.size();i++){
            String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.get(i).getName();


            String path = putS3(uploadFile.get(i), fileName);

            removeNewFile(uploadFile.get(i));
            JobsImagesDto image = new JobsImagesDto(jobsId,path);
            images.add(i,image);

            JobsImages jobImages = new JobsImages();
            jobImages.setJobPath(path);
            jobImages.setJobs(jobsService.getJob(jobsId));
            jobsImageRepository.save(jobImages);
            if(i==0) {
                Jobs jobs = jobsRepository.findById(jobsId).orElseThrow(() -> new DataNotFoundException("jobs not found"));
                jobs.setProfileImage(path);
                jobsRepository.save(jobs);
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

    public void jobsDelete(Integer jobsId, String dirName){
        List<JobsImages> jobs = jobsImageRepository.findAllByJobsJobid(jobsId);
        if(jobs.size() > 0){
            String[] jobsImages = new String[jobs.size()];
            for(int i =0; i < jobs.size(); i++){
                jobsImages[i] = jobs.get(i).getJobPath();
                String[] filename = jobsImages[i].split(dirName + "/");
                deleteS3File(filename[1], dirName);

                JobsImages images = jobs.get(i);
                jobsImageRepository.delete(images);
            }
            customizedJobsRepository.deleteQslJobsAndImagesByJobId(jobsId);
        }else{
            customizedJobsRepository.deleteQslJobsAndImagesByJobId(jobsId);
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
