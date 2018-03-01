package com.alfred.templates.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.alfred.templates.model.TemplateAmazon;
import com.alfred.templates.model.TemplateMetadataAmazon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.alfred.templates.conversions.Conversions;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/template")
public class TemplateController {

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
    public Mono<ResponseEntity>  saveTemplate(@Valid @RequestBody TemplateAmazon templateAmazon) {
        if (!templateAmazon.isValid()) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        String templateName = templateAmazon.getTemplateName();
        String subjectPart = templateAmazon.getSubjectPart();
        String textPart = templateAmazon.getTextPart();
        String htmlPart = templateAmazon.getHtmlPart();

        Template template = new Template()
                .withTemplateName(templateName)
                .withSubjectPart(subjectPart)
                .withTextPart(textPart)
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

        String subjectPart = templateAmazon.getSubjectPart();
        String textPart = templateAmazon.getTextPart();
        String htmlPart = templateAmazon.getHtmlPart();

        Template template = new Template()
                .withTemplateName(templateName)
                .withSubjectPart(subjectPart)
                .withTextPart(textPart)
                .withHtmlPart(htmlPart);

        UpdateTemplateRequest updateTemplateRequest = new UpdateTemplateRequest()
                .withTemplate(template);

        ses.updateTemplateAsync(updateTemplateRequest);

        return Mono.just(ResponseEntity.noContent().build());
    }

}