package com.alfred.templates.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;

@Configuration
class AwsSESConfiguration {

    @Value("${com.alfred.region}")
    private String REGION;

    @Value("${com.alfred.accessKey}")
    private String ACCESSKEY;

    @Value("${com.alfred.secretKey}")
    private String SECRETKEY;

    @Bean
    AmazonSimpleEmailServiceAsync ses() {

        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESSKEY, SECRETKEY);

        return AmazonSimpleEmailServiceAsyncClientBuilder.standard()
              .withCredentials(new AWSStaticCredentialsProvider(credentials))
              // For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html
              .withRegion(REGION)
              .build();
    }
}