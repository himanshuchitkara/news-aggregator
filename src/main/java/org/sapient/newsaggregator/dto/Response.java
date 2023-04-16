package org.sapient.newsaggregator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Response {
    private List<Doc> docs;
    private MetaData meta;
}
