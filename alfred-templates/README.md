# alfred-templates

Helpful to manage the emails templates in AWS SES

## Configuration 

- create a `a-example-yaml-configuration.yml` in config/ or a folder of your preference

Follow a example of this file:

```
com.alfred:
  region: "REGIAN OF YOUR ACCOUNT"
  accessKey: "YOUR ACCESS KEY HERE"
  secretKey: "YOUR SECREY KEY HERE"
```

## Running

`java -jar build/libs/alfred-templates-0.0.1-SNAPSHOT.jar --spring.config.location=config/a-example-yaml-configuration.yml`

## References

- https://spring.io/blog/2016/09/22/new-in-spring-5-functional-web-framework
- https://dzone.com/articles/spring-5-reactive-web-services
- https://gist.github.com/ShigeoTejima/f2997f57405010c3caca
- https://github.com/aws/aws-sdk-java/blob/master/src/samples/AmazonSimpleEmailService/AmazonSESSample.java
- https://aws.amazon.com/documentation/ses/
- https://docs.aws.amazon.com/ses/latest/APIReference/API_ListTemplates.html
- https://www.concrete.com.br/2017/07/28/reactive-spring-construindo-uma-api-rest-com-reactive-spring-and-spring-boot-2-0/