package com.alfred.templates.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.alfred.templates.conversions.Conversions;

@RestController
@RequestMapping(value = "/template")
public class TemplateController {

    @Autowired
    private AmazonSimpleEmailServiceAsync ses;

    @GetMapping("/")
    public Flux<TemplateMetadata> listTemplates() {
        CompletableFuture<List<TemplateMetadata>> apply = Conversions
                .<ListTemplatesResult>futureToCompletableFuture()
                .apply(ses.listTemplatesAsync(new ListTemplatesRequest()))
                .thenApplyAsync(ListTemplatesResult::getTemplatesMetadata);

        return Conversions.<TemplateMetadata>completableFutureToFlux().apply(apply);
    }

    @GetMapping("/{templateName}")
    public Mono<Template> getTemplate(@PathVariable String templateName) {
        GetTemplateRequest templateRequest = new GetTemplateRequest().withTemplateName(templateName);

        return Conversions
                .<GetTemplateResult>futureToMono()
                .apply(ses.getTemplateAsync(templateRequest))
                .map(GetTemplateResult::getTemplate);
    }

    @PostMapping("/")
    public String saveTemplate() {
        String templateName = "";
        String templateHtml = "";

        Template template = new Template()
                .withTemplateName(templateName)
                .withHtmlPart(templateHtml);

        CreateTemplateRequest templateRequest = new CreateTemplateRequest()
                .withTemplate(template);

        // ses.createTemplateAsync(templateRequest);

        return "OK";
    }

    @PutMapping("/{templateName}")
    public String putTemplate(@PathVariable String templateName) {
        String templateHtml = "";

        Template template = new Template()
                .withTemplateName(templateName)
                .withHtmlPart(templateHtml);

        UpdateTemplateRequest updateTemplateRequest = new UpdateTemplateRequest()
                .withTemplate(template);

        // ses.updateTemplateAsync(updateTemplateRequest);

        return "UPDATE";
    }

}