package com.aion.orderservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDetails<T> {
    private Date date;
    private T errormessage;
    private String Path;
}
