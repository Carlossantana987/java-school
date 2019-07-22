package com.lambdaschool.school.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// bean shared by controller classes
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{

}
