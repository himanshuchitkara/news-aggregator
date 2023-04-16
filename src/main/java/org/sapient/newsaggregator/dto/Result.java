package org.sapient.newsaggregator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Result {
    private String id;
    private String type;
    private String sectionId;
    private String sectionName;
    private Date webPublicationDate;
    private String webTitle;
    private String webUrl;
    private String apiUrl;
    private Boolean isHosted;
    private String pillarId;
    private String pillarName;
}
