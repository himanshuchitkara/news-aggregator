package org.sapient.newsaggregator.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
public class SearchResponse {
    private String id;
    private String type;
    private String sectionName;
    private String webTitle;
    private String webUrl;
    private String apiUrl;
    private String materialType;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResponse that = (SearchResponse) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
