package org.sapient.newsaggregator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NYTimesSearchAPIResponse {
    private String status;
    private String copyright;
    private Response response;
}
