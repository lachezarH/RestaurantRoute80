package project.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfiguration {

  private String cloudName = "db5xoamht";

  private String apiKey = "847329479286269";

  private String apiSecret = "KQ22salIqQoyNx_7pg0d6M0wEjI";

  @Bean
  public Cloudinary cloudinary() {

    return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret));
  }
}
