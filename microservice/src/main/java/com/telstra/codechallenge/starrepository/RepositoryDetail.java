package com.telstra.codechallenge.starrepository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RepositoryDetail {
    @JsonProperty("name")
    private String name;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("watchers_count")
    private String watchersCount;
    @JsonProperty("language")
    private String language;
    @JsonProperty("description")
    private String description;

}
