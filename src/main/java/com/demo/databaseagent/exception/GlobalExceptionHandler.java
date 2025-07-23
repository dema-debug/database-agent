package com.demo.databaseagent.exception;

import com.demo.databaseagent.response.BaseResponse;
import com.demo.databaseagent.response.BeanValidationErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 错误异常拦截器
 * @Author xr
 * @Date 2025/7/18 13:24
 */
@RestControllerAdvice
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }

    /**
     * 拦截restClient异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<BaseResponse> handleException(RestClientException e, HttpServletRequest request) {
        if (e instanceof HttpClientErrorException hcee) {
            logger.error("Request client error", e);
            return ResponseEntity
                    .status(hcee.getStatusCode())
                    .body(new BaseResponse(BaseException.AUTH_ERROR, hcee.getStatusCode().toString()));
        } else {
            logger.error("Unexpected error", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(BaseException.UNEXPECTED_ERROR, "Unexpected error " + e));
        }
    }

    /**
     * 拦截DataAccess异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public BaseResponse handleException(DataAccessException e, HttpServletRequest request) {
        logger.error("SQL query error", e);
        return new BaseResponse(BaseException.DATABASE_ERROR, "SQL query error");
    }

    /**
     * 也是web相关异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServletException.class)
    public BaseResponse handleException(ServletException e, HttpServletRequest request) {
        return new BaseResponse(BaseException.REQUEST_ERROR, "请求失败");
    }

    /**
     * 拦截请求参数异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public BaseResponse handleException(NumberFormatException e, HttpServletRequest request) {
        logger.error("Regexp pattern error", e);
        return new BaseResponse(BaseException.REQUEST_ERROR, "Parse String Error.");
    }


    /**
     * 断言校验拦截
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse handleException(IllegalArgumentException e, HttpServletRequest request) {
        logger.error("断言校验不通过", e);
        return new BaseResponse(BaseException.REQUEST_ERROR, e.getMessage());
    }

    /**
     * 拦截已知异常外的其他异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(Exception e, HttpServletRequest request) throws Exception {
        if (e.getClass().getName().startsWith("org.springframework")) {
            logger.info("Rethrowing exception", e);
            throw e;
        }
        logger.error("Unexpected error", e);
        return new BaseResponse(BaseException.UNEXPECTED_ERROR, "Unexpected error:" + e);
    }

    /**
     * 将代码中拦截到所有自定义BaseException然后handler到再抛出去
     *
     * @param e 自定义的exception
     * @return BaseResponse
     */
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(BaseException.class)
    public BaseResponse handleException(BaseException e) {
        return new BaseResponse(e);
    }

    /**
     * 根据错误信息返回详细内容
     *
     * @param bindingResult 错误信息
     * @return message细节内容
     */
    private Object responseFromBindResult(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(x -> x.getField() + " - " + x.getDefaultMessage())
                .collect(Collectors.toList());
        BeanValidationErrorResponse response = new BeanValidationErrorResponse();
        response.setDetails(errors);
        response.setMessage(StringUtils.join(errors, ","));
        return response;
    }

    /**
     * 拦截请求参数无效异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return responseFromBindResult(e.getBindingResult());
    }

    /**
     * 拦截请求参数超过最大最小值异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public Object handleException(BindException e, HttpServletRequest request) {
        return responseFromBindResult(e.getBindingResult());
    }

    /**
     * 根据错误信息返回详细内容
     *
     * @param message 错误信息
     * @return message细节内容
     */
    private Object responseFromMessage(String message) {
        List<String> errors = new ArrayList<>();
        errors.add(message);
        BeanValidationErrorResponse response = new BeanValidationErrorResponse();
        response.setDetails(errors);
        response.setMessage(StringUtils.join(errors, ","));
        return response;
    }

    /**
     * 根据错误信息返回详细内容
     *
     * @param message http-code
     * @param errors  错误信息
     * @return message细节内容
     */
    private Object responseFromMessage(String message, List<String> errors) {
        BeanValidationErrorResponse response = new BeanValidationErrorResponse();
        response.setMessage(message);
        response.setDetails(errors);
        return response;
    }

    /**
     * 拦截缺少请求参数异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleException(MissingServletRequestParameterException e, HttpServletRequest request) {
        logger.error("MissingServletRequestParameterException", e);
        return responseFromMessage("缺少必要参数");
    }

    /**
     * 拦截数据参数类型不匹配异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public Object handleException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        return responseFromMessage("参数类型不匹配");
    }

    /**
     * 拦截数据解析（JSON解析错误或@JsonFormat格式错误）异常
     *
     * @param e 异常信息
     * @return 异常状态码和状态信息
     */
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleException(HttpMessageNotReadableException e, HttpServletRequest request) {
        logger.error("HttpMessageNotReadableException", e);
        return responseFromMessage("参数格式错误");
    }

}
