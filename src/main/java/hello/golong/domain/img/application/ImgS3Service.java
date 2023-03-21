package hello.golong.domain.img.application;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class ImgS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public URL getObject(String storedFileName) throws IOException {
        return amazonS3.getUrl(bucket, storedFileName);
    }

    public String download(String fileName) throws IOException {
        return getObject(fileName).toString();
    }

}
