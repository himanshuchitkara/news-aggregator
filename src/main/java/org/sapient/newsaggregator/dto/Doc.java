package org.sapient.newsaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Doc {
    @JsonProperty("abstract")
    private String webTitle;
    private String web_url;
    private String snippet;
    private String lead_paragraph;
    private String print_section;
    private Integer print_page;
    private String source;
    private List<Object> multimedia;
    private Object headline;
    private List<Object> keywords;
    private Date pub_date;
    private String document_type;
    private String news_desk;
    private String section_name;
    private Object byline;
    private String type_of_material;
    private String _id;
    private Integer word_count;
    private String uri;
}
