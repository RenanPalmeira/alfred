package com.alfred.templates.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.alfred.templates.model.TemplateAmazon;
import com.alfred.templates.model.TemplateMetadataAmazon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.alfred.templates.conversions.Conversions;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/template")
public class TemplateController {

    @Value("${com.alfred.emailSender}")
    private String emailSender;

    @Value("${com.alfred.emailReceiver}")
    private String emailReceiver;

    @Autowired
    private AmazonSimpleEmailServiceAsync ses;

    @GetMapping
    public Flux<TemplateMetadataAmazon> listTemplates() {
        CompletableFuture<List<TemplateMetadataAmazon>> apply = Conversions
                .<ListTemplatesResult>futureToCompletableFuture()
                .apply(ses.listTemplatesAsync(new ListTemplatesRequest()))
                .thenApplyAsync(ListTemplatesResult::getTemplatesMetadata)
                .thenApplyAsync(templateMetadata -> {
                    return templateMetadata
                            .stream()
                            .map(TemplateMetadataAmazon::new)
                            .collect(Collectors.toList());
                });

        return Conversions.<TemplateMetadataAmazon>completableFutureToFlux().apply(apply);
    }

    @GetMapping("/{templateName}")
    public Mono<TemplateAmazon> getTemplate(@PathVariable String templateName) {
        GetTemplateRequest templateRequest = new GetTemplateRequest().withTemplateName(templateName);

        return Conversions
                .<GetTemplateResult>futureToMono()
                .apply(ses.getTemplateAsync(templateRequest))
                .map(GetTemplateResult::getTemplate)
                .map(TemplateAmazon::new);
    }

    /**
     * https://aws.amazon.com/blogs/ses/introducing-email-templates-and-bulk-sending/
     * @param templateAmazon
     */
    @PostMapping
    public Mono<ResponseEntity> saveTemplate(@Valid @RequestBody TemplateAmazon templateAmazon) {
        if (!templateAmazon.isValid()) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        HtmlCompressor compressor = new HtmlCompressor();
        Slugify slugify = new Slugify();

        String templateName = slugify.slugify(templateAmazon.getTemplateName());
        String subjectPart = templateAmazon.getSubjectPart();
        String htmlPart = compressor.compress(templateAmazon.getHtmlPart());

        Template template = new Template()
                .withTemplateName(templateName)
                .withSubjectPart(subjectPart)
                .withHtmlPart(htmlPart);

        CreateTemplateRequest templateRequest = new CreateTemplateRequest()
                .withTemplate(template);

        ses.createTemplateAsync(templateRequest);

        return Mono.just(ResponseEntity.noContent().build());
    }

    @PutMapping("/{templateName}")
    public Mono<ResponseEntity> putTemplate(@PathVariable String templateName,
                                            @Valid @RequestBody TemplateAmazon templateAmazon) {

        if (!templateAmazon.isValid()) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        HtmlCompressor compressor = new HtmlCompressor();

        String subjectPart = templateAmazon.getSubjectPart();
        String htmlPart = compressor.compress(templateAmazon.getHtmlPart());

        Template template = new Template()
                .withTemplateName(templateName)
                .withSubjectPart(subjectPart)
                .withHtmlPart(htmlPart);

        UpdateTemplateRequest updateTemplateRequest = new UpdateTemplateRequest()
                .withTemplate(template);

        ses.updateTemplateAsync(updateTemplateRequest);

        return Mono.just(ResponseEntity.noContent().build());
    }

    @PostMapping("/{templateName}/send")
    public Mono<ResponseEntity> sendTemplateEmailTest(@PathVariable String templateName,
                                                      @RequestBody Map<String, Map<String, Object>> context) {

        String templateData = "{}";
        try {
            if (context.get("context") != null) {
                templateData = new ObjectMapper().writeValueAsString(context.get("context"));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Destination addresses = new Destination()
                .withToAddresses(emailReceiver);

        SendTemplatedEmailRequest emailRequest = new SendTemplatedEmailRequest()
                .withTemplateData(templateData)
                .withTemplate(templateName)
                .withSource(emailSender)
                .withDestination(addresses);

        log.warn("Sent a email to '{}' with template '{}'", emailReceiver, templateName);
        ses.sendTemplatedEmailAsync(emailRequest);
        return Mono.just(ResponseEntity.noContent().build());
    }

}