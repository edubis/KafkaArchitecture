package com.example.kafka.architecture.icompras.faturamento.bucket;


import com.example.kafka.architecture.icompras.faturamento.config.props.MinioProps;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BucketService {

    private final MinioClient client;
    private final MinioProps props;

    public void upload(BucketFile file) {
        try {
            var obj = PutObjectArgs.builder()
                    .bucket(props.getBucketName())
                    .object(file.name())
                    .stream(file.is(), file.size(), -1)
                    .contentType(file.type().toString())
                    .build();
            client.putObject(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl(String file) {
        try {
            var obj = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(props.getBucketName())
                    .object(file)
                    .expiry(1, TimeUnit.HOURS)
                    .build();

            return client.getPresignedObjectUrl(obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
