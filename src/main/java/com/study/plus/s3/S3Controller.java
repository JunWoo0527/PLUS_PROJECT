package com.study.plus.s3;

import com.study.plus.global.constant.ResponseCode;
import com.study.plus.global.dto.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class S3Controller {

  private final S3Service service;

  public S3Controller(S3Service service) {
    this.service = service;
  }

  @PostMapping("/{userId}")
  public ResponseEntity<SuccessResponse> uploadImage(
      @PathVariable("userId") Long userId,
      @RequestParam(value = "file") MultipartFile file) {

    service.uploadImage(file, userId);
    return ResponseEntity.ok().body(new SuccessResponse(ResponseCode.SUCCESS_UPLOADIMAGE));
  }

}
