package com.elektronetf.util.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public abstract class AbstractHandler extends Handler {
	public void close() throws SecurityException {
	}

	public void flush() {
	}

	public void publish(LogRecord record) {
	}
}
