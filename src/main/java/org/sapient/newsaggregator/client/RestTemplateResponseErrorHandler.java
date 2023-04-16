package org.sapient.newsaggregator.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sapient.newsaggregator.exception.NewsAggregatorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
	
	@Override
	public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
		HttpStatus status = clientHttpResponse.getStatusCode();
		return status.is4xxClientError() || status.is5xxServerError();
	}

	@Override
	public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
		if (clientHttpResponse.getStatusCode().is5xxServerError()) {
			throw new NewsAggregatorException("Service is currently unavailable");
		} else if (clientHttpResponse.getStatusCode().is4xxClientError())
		{
			throw new NewsAggregatorException("Unauthorized access");
		}
	}
}
