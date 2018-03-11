package yaolong.base.common.base;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import yaolong.base.ar.ActiveRecordException;
import yaolong.base.common.Result;
import yaolong.base.common.ReturnCode;
import yaolong.base.common.exception.BaseException;
import yaolong.base.common.util.JsonUtils;
import yaolong.base.common.util.SessionUtils;
import yaolong.base.inf.Audit;
import yaolong.base.inf.UserInfo;

/**
 * @author
 */
public class BaseController {
    public static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 页码默认值:1
     */
    public static final int PAGE_NUMBER = 1;

    /**
     * 每页数据条目数,默认值:10
     */
    public static final int PAGE_SIZE = 10;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        Result result = new Result();
        if (BaseException.class.isAssignableFrom(e.getClass())) {
            BaseException ce = (BaseException) e;
            result.setCode(ce.getCode());
            result.setMsg(ce.getMessage());
        } else if (ActiveRecordException.class.isAssignableFrom(e.getClass())) {
            ActiveRecordException ce = (ActiveRecordException) e;
            result.setCode(ce.getCode());
            result.setMsg("违反数据规则，请联系管理员.");
        } else {
            result.setCode(ReturnCode.RUNTIME_EXCEPTION);
            result.setMsg("存在非法或错误数据，请联系管理员.");
        }
        logger.debug("Base Exception Handler:", e);
        return result;
    }

    /**
     * 获取session中user对象
     * 
     * @return
     */
    protected UserInfo getUser() {
        return SessionUtils.getUser();
    }

    /**
     * 获取session中的审计对象
     * 
     * @return
     */
    protected Audit getAudit() {
        return SessionUtils.getAudit();
    }

    /**
     * 从map中获取页码
     * 
     * @param data
     * @return
     */
    protected int pageNumber(Map<String, Object> data) {
        Integer pageNumber = getInteger(data, "pageNumber");
        return pageNumber == null ? PAGE_NUMBER : pageNumber;
    }

    /**
     * 从请求参数中获取页码
     * 
     * @return
     */
    protected int pageNumber() {
        HttpServletRequest request = this.getHttpServletRequest();
        String str = request.getParameter("pageNumber");
        if (str == null && !StringUtils.hasText(str)) {
            return PAGE_NUMBER;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            logger.info("pageNumber 参数转换出现错误!", e);
        }
        return PAGE_NUMBER;
    }

    /**
     * 从map中获取每页展示数据条目数
     * 
     * @param data
     * @return
     */
    protected int pageSize(Map<String, Object> data) {
        Integer pageSize = getInteger(data, "pageSize");
        return pageSize == null ? PAGE_SIZE : pageSize;
    }

    /**
     * 从请求参数中获取每页展示数据条目数
     * 
     * @return
     */
    protected int pageSize() {
        HttpServletRequest request = this.getHttpServletRequest();
        String str = request.getParameter("pageSize");
        if (str == null && !StringUtils.hasText(str)) {
            return PAGE_SIZE;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            logger.info("pageSize 参数转换出现错误!", e);
        }
        return PAGE_SIZE;
    }

    /**
     * 获取HttpServletRequest
     * 
     * @return
     */
    protected HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
            return sra.getRequest();
        }
        return null;
    }

    /**
     * 获取HttpServletResponse
     * 
     * @return
     */
    protected HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
            return sra.getResponse();
        }
        return null;
    }

    /**
     * 向页面直接输出json字符串
     * 
     * @param json
     * @return
     */
    protected String outJsonToView(String json) {
        try {
            HttpServletResponse response = getHttpServletResponse();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
            return null;
        } catch (Exception e) {
            logger.error("向页面写入数据异常.", e);
        }
        return null;
    }

    /**
     * 从map中获取Integer
     * 
     * @param map
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected Integer getInteger(Map map, String key) {
        if (map.containsKey(key)) {
            return (Integer) map.get(key);
        }
        return null;
    }

    /**
     * 从map中获取String
     * 
     * @param map
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected String getString(Map map, String key) {
        if (map.containsKey(key)) {
            return map.get(key).toString();
        }
        return null;
    }

    /**
     * 从map中获取Object
     * 
     * @param map
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected Object getObject(Map map, String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    /**
     * 从map中获取Long
     * 
     * @param map
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected Long getLong(Map map, String key) {
        if (map.containsKey(key)) {
            return (Long) map.get(key);
        }
        return null;
    }

    /**
     * 从map中获取Boolean
     * 
     * @param map
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected Boolean getBoolean(Map map, String key) {
        if (map.containsKey(key)) {
            return (Boolean) map.get(key);
        }
        return null;
    }

    /**
     * 从指定map的key中,获取数据数据
     * 
     * @param map
     * @param key
     * @param type
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected <T> T getObject(Map map, String key, Class<T> type) {
        if (map.containsKey(key)) {
            return JsonUtils.parseJson(map.get(key).toString(), type);
        }
        return null;
    }

}
