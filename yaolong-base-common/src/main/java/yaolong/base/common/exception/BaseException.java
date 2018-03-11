package yaolong.base.common.exception;

import org.slf4j.helpers.MessageFormatter;

import yaolong.base.common.ReturnCode;

/**
 * @author
 */
public class BaseException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 109895969081187243L;

    private String code = ReturnCode.HDP_EXCEPTION;

    /**
     * 默认的构造方法。
     */
    public BaseException() {
        super();
    }

    /**
     * 构造方法。
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * 自定义的构造方法。
     * 
     * @param message
     *            String
     */
    public BaseException(String message, String code) {
        super(message);
        this.code = code;
    }

    /**
     * @param format
     * @param arg
     */
    public BaseException(String format, Object... arg) {
        super(MessageFormatter.format(format, arg).getMessage());
    }

    /**
     * @param format
     * @param code
     * @param arg
     */
    public BaseException(String format, String code, Object... arg) {
        this(format, arg);
        this.code = code;
    }

    /**
     * 自定义的构造方法。
     * 
     * @param message
     *            String
     * @param cause
     *            Throwable
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 自定义的构造方法。
     * 
     * @param message
     *            String
     * @param cause
     *            Throwable
     */
    public BaseException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 自定义的构造方法。
     * 
     * @param cause
     *            Throwable
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * 自定义的构造方法。
     * 
     * @param cause
     *            Throwable
     */
    public BaseException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
