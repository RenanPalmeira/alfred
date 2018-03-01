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

    @NotNull
    @Size(min = 1)
    @JsonProperty("template_name")
    private String templateName;

    @NotNull
    @Size(min = 1)
    @JsonProperty("subject_part")
    private String subjectPart;

    @NotNull
    @Size(min = 1)
    @JsonProperty("text_part")
    private String textPart;

    @NotNull
    @Size(min = 1)
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
            && getSubjectPart()!=null
            && !getSubjectPart().isEmpty()
            && getTextPart()!=null
            && !getTextPart().isEmpty()
            && getHtmlPart()!=null
            && !getHtmlPart().isEmpty();
    }
}
