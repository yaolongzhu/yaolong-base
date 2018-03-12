package yaolong.base.mvc.base;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import yaolong.base.common.ReturnCode;
import yaolong.base.common.util.JsonUtils;

/**
 * @author yaolong
 */
public class ValidatorHandlerExceptionResolver implements HandlerExceptionResolver, PriorityOrdered {

    public static Logger logger = LoggerFactory.getLogger(ValidatorHandlerExceptionResolver.class);

    private int order;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        if (ex instanceof BindException) {
            BindException be = (BindException) ex;
            if (be.hasFieldErrors()) {
                List<FieldError> allErrors = be.getFieldErrors();
                Map<String, String> map = new HashMap<String, String>();
                for (FieldError fe: allErrors) {
                    String f = fe.getField();
                    if (map.containsKey(f)) {
                        String n = map.get(f) + " & " + fe.getDefaultMessage();
                        map.put(f, n);
                    } else {
                        map.put(f, fe.getDefaultMessage());
                    }
                }
                //				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setStatus(HttpServletResponse.SC_OK);
                try {
                    // response.setContentType("text/html;charset=UTF-8");
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    HashMap<String, Object> mm = new HashMap<String, Object>();
                    mm.put("code", ReturnCode.BIND_EXCEPTION);
                    mm.put("msg", "数据绑定异常.");
                    String json = JsonUtils.toJson(mm);
                    writer.write(json);
                    writer.flush();
                    //writer.close();
                    return null;
                } catch (Exception e) {
                    logger.error("向页面写入错误代码异常.", e);
                }
                return null;

                // HashMap<String, Object> mm = new HashMap<String, Object>();
                // mm.put("errors", Utils.toJson(map));
                // mm.put("formId", request.getParameter("cdpFormId"));
                // return new ModelAndView("validateMassagePage", mm);
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
