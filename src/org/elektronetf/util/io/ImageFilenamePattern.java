package org.elektronetf.util.io;

import java.util.regex.Pattern;
import org.apache.commons.io.IOCase;

public class ImageFilenamePattern {
	private final Pattern pattern;
	
	public ImageFilenamePattern() {
		this(".*", Extension.PNG);
	}
	
	public ImageFilenamePattern(String regex) {
		this(regex, Extension.PNG);
	}
	
	public ImageFilenamePattern(Extension ext) {
		this(".*", ext);
	}
	
	public ImageFilenamePattern(String regex, Extension ext) {
		int flags = !IOCase.SYSTEM.isCaseSensitive() ? Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE : 0;
		pattern = Pattern.compile(regex + ext.toRegex(), flags);
	}
	
	public boolean matches(String filename) {
		return matches(filename, "");
	}
	
	public boolean matches(String filename, String baseName) {
		if (filename == null || baseName == null || !IOCase.SYSTEM.checkStartsWith(filename, baseName)) {
			return false;
		}
		
		return pattern.matcher(filename.replace(baseName, "")).matches();
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	@Override
	public String toString() {
		return pattern.toString();
	}
	
	public enum Extension {
		GIF("gif"),
		JPG("jpg", "jpeg", "jpe", "jif", "jfif", "jfi"),
		PNG("png", "apng"),
		XBM("xbm");
		
		private String[] exts;
		
		private Extension(String... exts) {
			this.exts = exts;
		}
		
		public String[] getVariants() {
			return exts.clone();
		}
		
		public String toRegex() {
			String regex = "\\.";
			if (exts.length == 1) {
				return regex + exts[0];
			}
			regex += "(" + exts[0];
			for (int i = 1; i < exts.length; i++) {
				regex += "|" + exts[i];
			}
			return regex + ")";
		}
		
		@Override
		public String toString() {
			return "." + exts[0];
		}
	}
}
