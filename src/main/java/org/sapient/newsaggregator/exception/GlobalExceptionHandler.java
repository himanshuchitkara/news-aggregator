package org.sapient.newsaggregator.exception;

import org.sapient.newsaggregator.dto.NewsAggregatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({NewsAggregatorException.class, Exception.class})
    public ResponseEntity<NewsAggregatorResponse> handlePromFileWatcherException(Exception ex) {
        logError(ex);
        NewsAggregatorResponse response = NewsAggregatorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.toString()).errorMessage(ex.getMessage()).timestamp(new Date()).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(Exception ex) {
        LOG.error(ex.getMessage(), ex);
    }
}
