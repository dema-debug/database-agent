package com.demo.databaseagent.exception;

/**
 * @Description 全局异常处理器
 * @Author xr
 * @Date 2025/7/20 10:53
 */
public class BaseException extends RuntimeException {

    /**
     * 请求错误
     */
    public static int REQUEST_ERROR = 100001;
    /**
     * 请求咚咚错误
     */
    public static int REQUEST_DONG_ERROR = 100002;
    public static int REQUEST_CMDB_ERROR = 100003;
    public static int REQUEST_CONTAINER_ERROR = 100004;
    /**
     * 权限错误
     */
    public static int AUTH_ERROR = 200001;
    public static int AUTHENTICATION_ERROR = 200002;
    public static int PRIVILEGE_ERROR = 200003;
    public static int RESOURCE_NOTFOUND_ERROR = 200004;
    /**
     * 数据库错误
     */
    public static int DATABASE_ERROR = 300001;
    public static int DATABASE_NOTFOUND_ERROR = 300002;
    /**
     * 用户身份识别业务模块错误
     */
    public static int USER_ERROR = 400001;

    /**
     * CICD-pipeline业务模块错误
     */
    public static int PIPELINE_ERROR = 500001;
    /**
     * 资源管理业务模块错误
     */
    public static int METADATA_ERROR = 600001;
    /**
     * 待补充
     */
    public static int UNEXPECTED_ERROR = 700001;

    /**
     * 数据校验错误
     */
    public static int DATA_CHECK_ERROR = 700002;

    public static int SCHEDULE_ERROR = 800001;

    public int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
