package com.study.plus.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {

  private final static Long MAX_FILE_SIZE = 10 * 1024 * 1024L;

  @Value("${application.bucket.name}")
  private String bucketName;

  private AmazonS3 s3Client;

  @Autowired
  public S3Service(AmazonS3 s3Client) {
    this.s3Client = s3Client;
  }

  public void uploadImage(MultipartFile file, Long userId) {
    String originalFileName = file.getOriginalFilename();
    String fileType = file.getContentType();

    try {
      // 최대용량 체크
      if (file.getSize() > MAX_FILE_SIZE) {
        throw new FileUploadException("10MB 이하 파일만 업로드 할수 있습니다.");
      }

      // File Type 체크
      if (!fileType.startsWith("image")) {
        throw new FileUploadException("이미지 파일만 업로드할수 있습니다.");
      }

      // 저장 파일명 중복방지 고유명으로 변경
      String newFileName = generateUniqueFileName(originalFileName, userId);

      // MultipartFile -> File
      File uploadfile = convert(file).orElseThrow(() ->
          new IllegalArgumentException("파일전환에 실패하였습니다.")
      );

      s3Client.putObject(new PutObjectRequest(bucketName, newFileName, uploadfile));
      uploadfile.delete();

    } catch (IOException e) {
      new FileUploadException(e.getMessage());
    }
  }

  // 로컬에 파일 업로드 하기
  private Optional<File> convert(MultipartFile file) throws IOException {
    File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }

//  public File multipartFileToFile(MultipartFile multipartFile, String newFileName)
//      throws IOException {
//    File file = new File(newFileName);
//    multipartFile.transferTo(file);
//    return file;
//  }

  private String generateUniqueFileName(String orginalFileName, Long userId) {
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//    String timeStamp = dateFormat.format(new Date());
    return userId + "_" + orginalFileName;
  }


}
