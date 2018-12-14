package com.demo.global.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.global.RtnResult;
import com.demo.global.enums.RtnResultCode;

/**
 * 自定义异常
 * @author hzhigg
 * 2018年12月14日
 */
@ControllerAdvice
public class MyExceptionHander {
    public static final Logger logger = LoggerFactory.getLogger(MyExceptionHander.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RtnResult handlerMethodArgumentException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        StringBuffer errMes = new StringBuffer();
        if(bindingResult.hasErrors()){
            List<ObjectError> ls=bindingResult.getAllErrors();
            for (int i = 0; i < ls.size(); i++) {
                errMes.append("["+ls.get(i).getDefaultMessage()+"]");
            }
            logger.error("[参数异常]{}",errMes);
            return new RtnResult().Fail(RtnResultCode.PARAMTER_IS_NULL,errMes.toString());
        }else{
            logger.error("[系统异常]{}",e);
            return RtnResult.Fail();
        }
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RtnResult runtimeError(Exception e) {
        logger.error("[系统异常]{}",e);
        return RtnResult.Fail(RtnResultCode.SYSTEM_EXCEPTION);
    }
    
    @ExceptionHandler(ParamException.class)
    @ResponseBody
    public RtnResult paramException(ParamException e) {
        logger.error("[参数异常]{}",e);
        return new RtnResult(RtnResultCode.PARAMTER_IS_ERROR);
    }
    
    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public RtnResult serverException(ServerException e) {
        logger.error("[运行异常]{}",e);
        return new RtnResult(e.getErrorCode(),e.getErrorMsg());
    }
}

