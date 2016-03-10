package com.spdukraine.testtask.search.advice;

import com.spdukraine.testtask.search.exceptions.BadPageValueException;
import com.spdukraine.testtask.search.exceptions.DeadLinkException;
import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler
{
    public static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "DirectoryNotCreatedException")
    @ExceptionHandler(value = {DirectoryNotCreatedException.class})
    public void DirectoryNotCreatedExceptionHandler(Exception e)
    {
        LOGGER.info("DirectoryNotCreatedExceptionHandler() " + e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "DeadLinkExceptionHandler")
    @ExceptionHandler(value = {DeadLinkException.class, BadPageValueException.class})
    public void DeadLinkExceptionHandler(Exception e)
    {
        LOGGER.info("DeadLinkExceptionHandler() " + e.getMessage());
    }
}
