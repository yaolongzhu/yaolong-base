package yaolong.base.id;

/**
 *
 * @author
 *
 */
public class IdUtils {

	public static final long MAX_LONG = 1024L;

	public static final int MAX_INT = 1024;

	public static int from(long id) {
		String bid = Long.toBinaryString(id);
		int lenth = bid.length();
		String ma = bid.substring(lenth - 22, lenth - 12);
		int msint = Integer.parseInt(ma, 2);
		return msint;
	}

	public static int workerId(long id) {
		String bid = Long.toBinaryString(id);
		int lenth = bid.length();
		String ma = bid.substring(lenth - 17, lenth - 12);
		int msint = Integer.parseInt(ma, 2);
		return msint;
	}

	public static int datacenterId(long id) {
		String bid = Long.toBinaryString(id);
		int lenth = bid.length();
		String ma = bid.substring(lenth - 22, lenth - 17);
		int msint = Integer.parseInt(ma, 2);
		return msint;
	}

	public static long general(long id) {
		return id % MAX_LONG;
	}

	public static int general(int id) {
		return id % MAX_INT;
	}

}
