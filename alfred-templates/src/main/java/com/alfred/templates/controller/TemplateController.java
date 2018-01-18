package com.alfred.templates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.ListTemplatesRequest;
import com.amazonaws.services.simpleemail.model.ListTemplatesResult;

@RestController
@RequestMapping(value = "/template/")
public class TemplateController {

    @Autowired
    private AmazonSimpleEmailService ses;

    @RequestMapping(method = RequestMethod.GET)
    public ListTemplatesResult listTemplates() {
        return ses.listTemplates(new ListTemplatesRequest());
    }

}