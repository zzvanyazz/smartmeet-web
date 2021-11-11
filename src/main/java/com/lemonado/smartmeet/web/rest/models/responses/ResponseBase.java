package com.lemonado.smartmeet.web.rest.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseBase {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Error error;


    public ResponseBase() {
    }

    public ResponseBase(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
