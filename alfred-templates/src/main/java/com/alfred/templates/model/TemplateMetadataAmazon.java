package com.alfred.templates.model;

import com.amazonaws.services.simpleemail.model.TemplateMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateMetadataAmazon {

    @JsonProperty("name")
    private String name;

    @JsonProperty("created_timestamp")
    private Date createdTimestamp;

    public TemplateMetadataAmazon(TemplateMetadata templateMetadata) {
        this.name = templateMetadata.getName();
        this.createdTimestamp = templateMetadata.getCreatedTimestamp();
    }
}
