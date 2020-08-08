package project.service.Impl;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.service.CloudinaryService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

  private static final String TEMP_FILE_PREFIX = "temp-file";


  private final Cloudinary cloudinary;


  public CloudinaryServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }


  //upload image to my cloudinary repository then return String url to get image
  @Override
  public String uploadImage(MultipartFile multipartFile) throws IOException {
    File imgFile = File.createTempFile(TEMP_FILE_PREFIX,
        multipartFile.getOriginalFilename());
    multipartFile.transferTo(imgFile);

    String url = this.cloudinary.
        uploader().
        upload(imgFile, new HashMap<>()).
        get("url").
        toString();

    imgFile.delete();

    return url;
  }
}
