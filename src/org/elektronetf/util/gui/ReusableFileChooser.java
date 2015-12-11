package org.elektronetf.util.gui;

import java.awt.Component;
import java.io.File;
import java.util.concurrent.Callable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.IOCase;

public class ReusableFileChooser extends JFileChooser {
	private File lastApprovedFile;
	private Callable<Boolean> customApproveAction;
	private boolean overwrites;
	
	public ReusableFileChooser() {
		this(null);
	}
	
	public ReusableFileChooser(FileSystemView fsv) {
		super(fsv);
	}
	
	@Override
	public void approveSelection() {
		File file = getSelectedFile();
		
		setSelectedFile(fixFilenameExtension(file));
		
		if (!callCustomApproveAction()) {
			return;
		}
		
		switch (getDialogType()) {
		case OPEN_DIALOG:
			if (!file.exists()) {
				GUIUtils.showMessage(this, file.getPath() + "\nFile not found.");
				return;
			}
			break;
			
		case SAVE_DIALOG:
			if (file.exists() && !showOverwriteConfirmDialog(file)) {
				return;
			}
			break;
		}
		
		super.approveSelection();
	}
	
	@Override
	public int showDialog(Component parent, String approveButtonText) {
		int option = super.showDialog(parent, approveButtonText);
		
		if (option == APPROVE_OPTION) {
			lastApprovedFile = getSelectedFile();
		}
		
		return option;
	}
	
	public int showOpenDialogAtLocation(Component parent, File location) {
		setDialogType(OPEN_DIALOG);
		return showDialogAtLocation(parent, location, null);
	}
	
	public int showSaveDialogAtLocation(Component parent, File location) {
		setDialogType(SAVE_DIALOG);
		return showDialogAtLocation(parent, location, null);
	}
	
	public int showDialogAtLocation(Component parent, File location, String approveButtonText) {
		setSelectedFile(new File(""));
		
		if (location != null && location.isDirectory()) {
			setCurrentDirectory(location);
		} else {
			setSelectedFile(location);
		}
		
		return showDialog(parent, approveButtonText);
	}
	
	public File getLastApprovedFile() {
		return lastApprovedFile;
	}
	
	public Callable<Boolean> getCustomApproveAction() {
		return customApproveAction;
	}
	
	public void setCustomApproveAction(Callable<Boolean> customApproveAction) {
		this.customApproveAction = customApproveAction;
	}
	
	public boolean shouldOverwrite() {
		return overwrites;
	}
	
	public void setOverwrites(boolean overwrites) {
		this.overwrites = overwrites;
	}
	
	private File fixFilenameExtension(File file) {
		FileFilter filter = getFileFilter();
		
		if (!(filter instanceof FileNameExtensionFilter)) {
			return file;
		}
		
		String[] exts = ((FileNameExtensionFilter) filter).getExtensions();
		for (String ext : exts) {
			if (IOCase.SYSTEM.checkEndsWith(file.getName(), "." + ext)) {
				return file;
			}
		}
		
		return new File(file.getPath() + "." + exts[0]);
	}
	
	private boolean callCustomApproveAction() {
		if (customApproveAction == null) {
			return true;
		}
		
		try {
			return customApproveAction.call();
		} catch (Exception e) {
			GUIUtils.showMessage(this, e.getMessage());
			return false;
		}
	}
	
	private boolean showOverwriteConfirmDialog(File file) {
		if (overwrites) {
			return true;
		}
		
		int option = JOptionPane.showConfirmDialog(this,
				file.getPath() + " already exists.\nDo you want to replace it?",
				"Save", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		return option == JOptionPane.YES_OPTION;
	}
}
