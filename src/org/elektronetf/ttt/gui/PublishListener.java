package org.elektronetf.ttt.gui;

import java.util.EventListener;

public interface PublishListener extends EventListener {
	public void publishPerformed(PublishEvent evt);
}
