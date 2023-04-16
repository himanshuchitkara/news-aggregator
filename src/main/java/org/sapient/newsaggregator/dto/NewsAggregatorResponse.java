package org.sapient.newsaggregator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsAggregatorResponse<T> {
    private String status;
    private T data;
    private String errorMessage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
}
