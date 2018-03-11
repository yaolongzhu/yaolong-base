package yaolong.base.common;

import java.util.UUID;

/**
 *
 * @author
 *
 */
public class MqMsg {

	private String serialNo;

	private String requestId;

	private String type;

	private Object data;

	public MqMsg() {
		this.serialNo = UUID.randomUUID().toString();
	}

	public MqMsg(String requestId, String type) {
		this();
		this.requestId = requestId;
		this.type = type;
	}

	public MqMsg(String requestId, String type, Object data) {
		this(requestId, type);
		this.data = data;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
