package yaolong.base.ar;

/**
 * @author
 */
public class ActiveRecordException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 3430520273130416470L;

    /**
     * 默认持久层错误代码
     */
    private String code = "5";

    /**
     * 
     */

    public ActiveRecordException(String message) {
        super(message);
    }

    public ActiveRecordException(Throwable cause) {
        super(cause);
    }

    public ActiveRecordException(String message, String code) {
        super(message);
        this.code = code;
    }

    public ActiveRecordException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public ActiveRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActiveRecordException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
