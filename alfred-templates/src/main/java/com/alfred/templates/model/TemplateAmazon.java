package com.alfred.templates.model;

import com.amazonaws.services.simpleemail.model.Template;
import com.amazonaws.services.simpleemail.model.TemplateMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateAmazon {

    @JsonProperty("template_name")
    private String templateName;

    @JsonProperty("subject_part")
    private String subjectPart;

    @JsonProperty("text_part")
    private String textPart = "";

    @JsonProperty("html_part")
    private String htmlPart;

    public TemplateAmazon(Template template) {
        this.templateName = template.getTemplateName();
        this.subjectPart = template.getSubjectPart();
        this.textPart = template.getTextPart();
        this.htmlPart = template.getHtmlPart();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return getTemplateName()!=null
            && !getTemplateName().isEmpty()
            && getTemplateName().length() > 3
            && getSubjectPart()!=null
            && !getSubjectPart().isEmpty()
            && getSubjectPart().length() > 3
            && getHtmlPart()!=null
            && !getHtmlPart().isEmpty()
            && getHtmlPart().length() > 3;
    }
}
