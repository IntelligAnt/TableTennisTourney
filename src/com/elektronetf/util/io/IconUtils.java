package com.elektronetf.util.io;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

public class IconUtils {
	public static final String FILENAME_FORMAT_1D = "icon%d.png";
	public static final String FILENAME_FORMAT_2D = "icon_%1$dx%1$d.png";
	
	public static final int[] LINUX_SIZES   = new int[] { 16, 24, 48,  96 };
	public static final int[] MAC_SIZES     = new int[] { 16, 32, 128, 512 };
	public static final int[] WINDOWS_SIZES = new int[] { 16, 32, 48,  256 };
	
	private static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
	
	private IconUtils() {
	}
	
	public static List<Image> findIconImages(String filenameFormat, int... sizes) {
		return findIconImages(IconUtils.class.getResource("/"), filenameFormat, sizes);
	}
	
	public static List<Image> findIconImages(URL location, String filenameFormat, int... sizes) {
		List<Image> iconImages = new ArrayList<>();
		
		for (int size : sizes) {
			String spec = String.format(filenameFormat, size);
			try {
				URL url = new URL(location, spec);
				iconImages.add(TOOLKIT.createImage(url));
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException("Cannot construct URL from " + location + " and " + spec, e);
			}
		}
		
		return iconImages;
	}
	
	public static List<Image> findIconImagesByOS(String filenameFormat) {
		return findIconImagesByOS(IconUtils.class.getResource("/"), filenameFormat);
	}
	
	public static List<Image> findIconImagesByOS(URL location, String filenameFormat) {
		int sizes[] = WINDOWS_SIZES;
		
		if (SystemUtils.IS_OS_LINUX) {
			sizes = LINUX_SIZES;
		} else if (SystemUtils.IS_OS_MAC) {
			sizes = MAC_SIZES;
		}
		
		return findIconImages(location, filenameFormat, sizes);
	}
}
