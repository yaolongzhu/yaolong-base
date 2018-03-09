package yaolong.base.id;

/**
 * 
 * @author
 *
 */
public class IdWorker {
	// 时间差位 1451577600000(2016-01-01 00:00:00)
	// 1461392400000(2016-04-23 14:20:00)
	private static final long twepoch = 1461392400000L;
	// 机器标识位数
	private static final long workerIdBits = 5L;
	// 数据中心标识位数
	private static final long datacenterIdBits = 5L;
	// 机器ID最大值
	private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// 数据中心ID最大值
	private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	// 毫秒内自增位
	private static final long sequenceBits = 12L;
	// 机器ID偏左移12位
	private static final long workerIdShift = sequenceBits;
	// 数据中心ID左移17位
	private static final long datacenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒左移22位
	private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

	private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

	private static long lastTimestamp = -1L;

	private long sequence = 0L;
	private final long workerId;
	private final long datacenterId;

	public IdWorker(long workerId, long datacenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException("工作ID最小值为0，最大值为31.");
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException("数据中心ID最小值为0，最大值为31.");
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			try {
				throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp)
						+ " milliseconds");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (lastTimestamp == timestamp) {
			// 当前毫秒内，则+1
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				// 当前毫秒内计数满了，则等待下一秒
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		// ID偏移组合生成最终的ID，并返回ID
		long nextId = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;
		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

}
