package com.aion.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ApiResponse<T> {

    private Long statusCode;
    private String statusDescription;
    private T data;

    public ApiResponse(Long statusCode, String statusDescription, T data) {

        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.data = data;
    }

    public ApiResponse(Long statusCode, String statusDescription) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    public Long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Long statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", statusDescription='" + statusDescription + '\'' +
                ", data=" + data +
                '}';
    }
}
