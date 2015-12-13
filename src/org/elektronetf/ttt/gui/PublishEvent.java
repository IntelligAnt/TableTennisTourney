package org.elektronetf.ttt.gui;

import java.awt.event.ActionEvent;

import org.elektronetf.ttt.Group;

public class PublishEvent extends ActionEvent {
	public enum PublishType { PUBLISH_ADD, PUBLISH_REMOVE, PUBLISH_UPDATE };
	
	private final PublishType type;
	
	public PublishEvent(Group source, PublishType type) {
		super(source, type.ordinal(), null);
		this.type = type;
	}
	
	public Group getSource() {
		return (Group) super.getSource();
	}
	
	public PublishType getType() {
		return type;
	}
}
