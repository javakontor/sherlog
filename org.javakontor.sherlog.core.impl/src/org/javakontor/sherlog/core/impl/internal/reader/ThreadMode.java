package org.javakontor.sherlog.core.impl.internal.reader;

public class ThreadMode {

	public static final ThreadMode	ORIGINAL			= new ThreadMode("ORIGINAL");
	public static final ThreadMode	DATENHERKUNFT	= new ThreadMode("DATENHERKUNFT");
	public static final ThreadMode	PATTERN				= new ThreadMode("PATTERN");

	static private ThreadMode				threadMode		= ORIGINAL;

	private String									threadId;

	private ThreadMode(final String threadMode) {
		super();
		this.threadId = threadMode;
	}

	static public ThreadMode getThreadMode() {
		return threadMode;
	}

	static public ThreadMode getThreadMode(final String tm) {
		if (ORIGINAL.threadId.equals(tm)) {
			return ORIGINAL;
		}
		if (PATTERN.threadId.equals(tm)) {
			return PATTERN;
		}
		if (DATENHERKUNFT.threadId.equals(tm)) {
			return DATENHERKUNFT;
		}
		return null;
	}

	static public void setThreadMode(final ThreadMode tMode) {
		threadMode = tMode;
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("[ThreadMode:");
		buffer.append(threadMode);
		buffer.append(" threadId: ");
		buffer.append(this.threadId);
		buffer.append("]");
		return buffer.toString();
	}

}
