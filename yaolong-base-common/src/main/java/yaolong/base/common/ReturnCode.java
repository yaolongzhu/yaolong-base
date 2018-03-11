package yaolong.base.common;

/**
 * @author
 */
public class ReturnCode {

    /**
     * 返回成功
     */
    public static final String SUCCESS = "1";

    /**
     * BaseException 未识别异常类别时的默认异常代码
     */
    public static final String HDP_EXCEPTION = "2";

    /**
     * 数据绑定失败
     */
    public static final String BIND_EXCEPTION = "3";

    /**
     * Java运行时异常：RuntimeException
     */
    public static final String RUNTIME_EXCEPTION = "4";

    /**
     * 持久层操作异常
     */
    public static final String AR_EXCEPTION = "5";

    /**
     * 可识别的基础操作异常：HDP操作Exception
     */
    public static final String HDP_OPTION_EXCEPTION = "6";

    /**
     * 未登录
     */
    public static final String HDP_UN_LOGIN = "7";

    /**
     * 登录失败
     */
    public static final String HDP_LOGIN_EXCEPTION = "8";

    /**
     * 未正确选择租户
     */
    public static final String HDP_TENANT_FAIL = "9";

    /**
     * 无访问权限
     */
    public static final String HDP_UN_AUTH = "10";

    /**
     * 无数据权限
     */
    public static final String HDP_UN_DATA_AUTH = "11";

    /**
     * 无审计信息
     */
    public static final String HDP_UN_AUDIT = "12";

    /**
     * 重复请求
     */
    public static final String HDP_REPEAT = "13";
    
    /**
     * 非法请求
     */
    public static final String HDP_ILLEGAL = "14";

    /**
     * 返回消息--成功
     */
    public static final String MSG_SUCCESS_EN = "success";

    /**
     * 返回消息--中文成功
     */
    public static final String MSG_SUCCESS_CN = "成功.";

    /*
     * Server status codes; see RFC 2068.
     */

    /**
     * Status code (100) indicating the client can continue.
     */
    public static final String SC_CONTINUE = "0100";

    /**
     * Status code (101) indicating the server is switching protocols according
     * to Upgrade header.
     */
    public static final String SC_SWITCHING_PROTOCOLS = "0101";

    /**
     * Status code (200) indicating the request succeeded normally.
     */
    public static final String SC_OK = "0200";

    /**
     * Status code (201) indicating the request succeeded and created a new
     * resource on the server.
     */
    public static final String SC_CREATED = "0201";

    /**
     * Status code (202) indicating that a request was accepted for processing,
     * but was not completed.
     */
    public static final String SC_ACCEPTED = "0202";

    /**
     * Status code (203) indicating that the meta information presented by the
     * client did not originate from the server.
     */
    public static final String SC_NON_AUTHORITATIVE_INFORMATION = "0203";

    /**
     * Status code (204) indicating that the request succeeded but that there
     * was no new information to return.
     */
    public static final String SC_NO_CONTENT = "0204";

    /**
     * Status code (205) indicating that the agent <em>SHOULD</em> reset the
     * document view which caused the request to be sent.
     */
    public static final String SC_RESET_CONTENT = "0205";

    /**
     * Status code (206) indicating that the server has fulfilled the partial
     * GET request for the resource.
     */
    public static final String SC_PARTIAL_CONTENT = "0206";

    /**
     * Status code (300) indicating that the requested resource corresponds to
     * any one of a set of representations, each with its own specific location.
     */
    public static final String SC_MULTIPLE_CHOICES = "0300";

    /**
     * Status code (301) indicating that the resource has permanently moved to a
     * new location, and that future references should use a new URI with their
     * requests.
     */
    public static final String SC_MOVED_PERMANENTLY = "0301";

    /**
     * Status code (302) indicating that the resource has temporarily moved to
     * another location, but that future references should still use the
     * original URI to access the resource. This definition is being retained
     * for backwards compatibility. SC_FOUND is now the preferred definition.
     */
    public static final String SC_MOVED_TEMPORARILY = "0302";

    /**
     * Status code (302) indicating that the resource reside temporarily under a
     * different URI. Since the redirection might be altered on occasion, the
     * client should continue to use the Request-URI for future
     * requests.(HTTP/1.1) To represent the status code (302), it is recommended
     * to use this variable.
     */
    public static final String SC_FOUND = "0302";

    /**
     * Status code (303) indicating that the response to the request can be
     * found under a different URI.
     */
    public static final String SC_SEE_OTHER = "0303";

    /**
     * Status code (304) indicating that a conditional GET operation found that
     * the resource was available and not modified.
     */
    public static final String SC_NOT_MODIFIED = "0304";

    /**
     * Status code (305) indicating that the requested resource <em>MUST</em> be
     * accessed through the proxy given by the <code><em>Location</em></code>
     * field.
     */
    public static final String SC_USE_PROXY = "0305";

    /**
     * Status code (307) indicating that the requested resource resides
     * temporarily under a different URI. The temporary URI <em>SHOULD</em> be
     * given by the <code><em>Location</em></code> field in the response.
     */
    public static final String SC_TEMPORARY_REDIRECT = "0307";

    /**
     * Status code (400) indicating the request sent by the client was
     * syntactically incorrect.
     */
    public static final String SC_BAD_REQUEST = "0400";

    /**
     * Status code (401) indicating that the request requires HTTP
     * authentication.
     */
    /**
     * 无权限 Status code (401) indicating that the request requires HTTP
     * authentication.
     */
    public static final String SC_UNAUTHORIZED = "0401";

    /**
     * Status code (402) reserved for future use.
     */
    public static final String SC_PAYMENT_REQUIRED = "0402";

    /**
     * 未登录 Status code (403) indicating the server understood the request but
     * refused to fulfill it.
     */
    public static final String SC_FORBIDDEN = "0403";

    /**
     * Status code (404) indicating that the requested resource is not
     * available.
     */
    public static final String SC_NOT_FOUND = "0404";

    /**
     * Status code (405) indicating that the method specified in the
     * <code><em>Request-Line</em></code> is not allowed for the resource
     * identified by the <code><em>Request-URI</em></code>.
     */
    public static final String SC_METHOD_NOT_ALLOWED = "0405";

    /**
     * Status code (406) indicating that the resource identified by the request
     * is only capable of generating response entities which have content
     * characteristics not acceptable according to the accept headers sent in
     * the request.
     */
    public static final String SC_NOT_ACCEPTABLE = "0406";

    /**
     * Status code (407) indicating that the client <em>MUST</em> first
     * authenticate itself with the proxy.
     */
    public static final String SC_PROXY_AUTHENTICATION_REQUIRED = "0407";

    /**
     * Status code (408) indicating that the client did not produce a request
     * within the time that the server was prepared to wait.
     */
    public static final String SC_REQUEST_TIMEOUT = "0408";

    /**
     * Status code (409) indicating that the request could not be completed due
     * to a conflict with the current state of the resource.
     */
    public static final String SC_CONFLICT = "0409";

    /**
     * Status code (410) indicating that the resource is no longer available at
     * the server and no forwarding address is known. This condition
     * <em>SHOULD</em> be considered permanent.
     */
    public static final String SC_GONE = "0410";

    /**
     * Status code (411) indicating that the request cannot be handled without a
     * defined <code><em>Content-Length</em></code>.
     */
    public static final String SC_LENGTH_REQUIRED = "0411";

    /**
     * Status code (412) indicating that the precondition given in one or more
     * of the request-header fields evaluated to false when it was tested on the
     * server.
     */
    public static final String SC_PRECONDITION_FAILED = "0412";

    /**
     * Status code (413) indicating that the server is refusing to process the
     * request because the request entity is larger than the server is willing
     * or able to process.
     */
    public static final String SC_REQUEST_ENTITY_TOO_LARGE = "0413";

    /**
     * Status code (414) indicating that the server is refusing to service the
     * request because the <code><em>Request-URI</em></code> is longer than the
     * server is willing to Stringerpret.
     */
    public static final String SC_REQUEST_URI_TOO_LONG = "0414";

    /**
     * Status code (415) indicating that the server is refusing to service the
     * request because the entity of the request is in a format not supported by
     * the requested resource for the requested method.
     */
    public static final String SC_UNSUPPORTED_MEDIA_TYPE = "0415";

    /**
     * Status code (416) indicating that the server cannot serve the requested
     * byte range.
     */
    public static final String SC_REQUESTED_RANGE_NOT_SATISFIABLE = "0416";

    /**
     * Status code (417) indicating that the server could not meet the
     * expectation given in the Expect request header.
     */
    public static final String SC_EXPECTATION_FAILED = "0417";

    /**
     * Status code (500) indicating an error inside the HTTP server which
     * prevented it from fulfilling the request.
     */
    public static final String SC_StringERNAL_SERVER_ERROR = "0500";

    /**
     * Status code (501) indicating the HTTP server does not support the
     * functionality needed to fulfill the request.
     */
    public static final String SC_NOT_IMPLEMENTED = "0501";

    /**
     * Status code (502) indicating that the HTTP server received an invalid
     * response from a server it consulted when acting as a proxy or gateway.
     */
    public static final String SC_BAD_GATEWAY = "0502";

    /**
     * Status code (503) indicating that the HTTP server is temporarily
     * overloaded, and unable to handle the request.
     */
    public static final String SC_SERVICE_UNAVAILABLE = "0503";

    /**
     * Status code (504) indicating that the server did not receive a timely
     * response from the upstream server while acting as a gateway or proxy.
     */
    public static final String SC_GATEWAY_TIMEOUT = "0504";

    /**
     * Status code (505) indicating that the server does not support or refuses
     * to support the HTTP protocol version that was used in the request
     * message.
     */
    public static final String SC_HTTP_VERSION_NOT_SUPPORTED = "0505";

}
