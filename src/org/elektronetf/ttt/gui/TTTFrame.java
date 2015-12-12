package org.elektronetf.ttt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.elektronetf.ttt.TourneyData;
import org.elektronetf.util.gui.GUIUtils;
import org.elektronetf.util.io.IconUtils;

public abstract class TTTFrame extends JFrame {
	public static void main(String[] args) {
		GUIUtils.setSystemLookAndFeel();
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
					new File(TTTFrame.class.getResource("resources/" + FONT_NAME + ".otf").getFile())));
//			GUIUtils.setDefaultFont(DEF_FONT_NAME, -1, (size) -> 2 * size); // TODO Default display font
		} catch (FontFormatException | IOException e) {
			GUIUtils.showError("Cannot create font " + FONT_NAME);
		}
		
		SwingUtilities.invokeLater(() -> {
			GraphicsDevice[] gds = ge.getScreenDevices();
			assert(gds.length >= 1);
			
			TourneyData data = new TourneyData();
			
			if (gds[0].isFullScreenSupported()) {
				TTTFrame frame = new TTTControlFrame();
				frame.bindData(data);
				gds[0].setFullScreenWindow(frame);
			} else {
				String msg = "Display 1 doesn't support full screen";
				GUIUtils.showError(msg);
				throw new HeadlessException(msg);
			}
			
			if (gds.length < 2) {
				GUIUtils.showWarning("Single display detected, only the Control interface will be shown");
				return;
			}
			
			if (gds[1].isFullScreenSupported()) {
				TTTFrame frame = new TTTDisplayFrame();
				frame.bindData(data);
				gds[1].setFullScreenWindow(frame);
			} else {
				String msg = "Display 2 doesn't support full screen";
				GUIUtils.showError(msg);
				throw new HeadlessException(msg);
			}
			
			if (!gds[0].getDefaultConfiguration().getBounds().getSize().equals(
					gds[1].getDefaultConfiguration().getBounds().getSize())) {
				GUIUtils.showWarning("Displays with different resolutions detected");
			}
		});
	}
	
	static final Dimension	SCREEN_SIZE		= Toolkit.getDefaultToolkit().getScreenSize();
	static final int		BASE_SIZE		= SCREEN_SIZE.width;
	static final int		DIV_SIZE		= (int) (BASE_SIZE * 0.01);
	static final int		BORDER_SIZE		= DIV_SIZE / 2;
	static final int		TOP_BAR_HEIGHT	= (int) (BASE_SIZE * 0.1);
	
	static final String		FONT_NAME		= "NK233";
	static final int		LOGO_FONT_SIZE	= (int) (TOP_BAR_HEIGHT * 0.3125);   
	static final int		INFO_FONT_SIZE	= (int) (TOP_BAR_HEIGHT * 0.265);
	
	static final Color		BG_COLOR		= Color.WHITE;
	static final Color		FG_COLOR		= new Color(0x023756);
	static final Color		SHADE_COLOR		= new Color(0x34495E);
	static final Color		ACCENT_COLOR	= new Color(0xF1C40F);
	
	protected static final String VENUE	= "СТК Палилула";
	protected static final String DATE	= new SimpleDateFormat("dd.MM.yyyy.").format(new Date());
	
//	private static final Image ICON_IMAGE;
	private static final Image LOGO_IMAGE;
	private static final Image HEART_IMAGE;
	private static final Style DEF_STYLE = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	
	static {
		try {
//			ICON_IMAGE	= ImageIO.read(TTTFrame.class.getResource("resources/icon.png"));
			LOGO_IMAGE	= ImageIO.read(TTTFrame.class.getResource("resources/logo.png"));
			HEART_IMAGE	= ImageIO.read(TTTFrame.class.getResource("resources/heart.png"));
		} catch (final IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public TTTFrame() {
		super("Турнир у стоном тенису – Електрон ЕТФ");
//		setIconImage(ICON_IMAGE);
		setIconImages(IconUtils.findIconImagesByOS(getClass().getResource("resources/"), IconUtils.FILENAME_FORMAT_2D));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setUndecorated(true);
		setSize(SCREEN_SIZE);
		getContentPane().setBackground(BG_COLOR);
		setLocationByPlatform(true);
		
		setLayout(new BorderLayout());
		initComponents();
	}
	
	public void bindData(TourneyData data) {
		panel.setData(data);
	}

	protected abstract void initPanel();
	
	private void initComponents() {
//		labelLogo = new JLabel(" ") {
//			@Override
//			protected void paintComponent(Graphics g) {
//				Dimension size = getParent().getSize();
//				int len = (int) (size.width * 0.15);
//				setSize(len, len);
//				getParent().setSize(size.width, len + 2 * border);
//				Image img = logoImage.getScaledInstance(len, len, Image.SCALE_SMOOTH);
//				g.drawImage(img, 0, 0, this);
//			}
//		};
		labelLogo = new JLabel(new ImageIcon(LOGO_IMAGE.getScaledInstance(-1, TOP_BAR_HEIGHT,Image.SCALE_SMOOTH)));
		
		textPaneLogo = new JTextPane();
		textPaneLogo.setOpaque(false);
		textPaneLogo.setEditable(false);
		addLogoText(LOGO_FONT_SIZE);
		
		textPaneInfo = new JTextPane();
		textPaneInfo.setOpaque(false);
		textPaneInfo.setEditable(false);
		addInfoText(INFO_FONT_SIZE);
		
		labelHeart = new JLabel(new ImageIcon(HEART_IMAGE.getScaledInstance(-1, TOP_BAR_HEIGHT,Image.SCALE_SMOOTH)));
		
		panelTopBar = new JPanel();
		panelTopBar.setLayout(new BoxLayout(panelTopBar, BoxLayout.LINE_AXIS));
		panelTopBar.setBackground(SHADE_COLOR);
		Border outsideBorder = new MatteBorder(0, 0, BORDER_SIZE, 0, ACCENT_COLOR) {
			@Override
			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				super.paintBorder(c, g, x, y, width, height);
				Insets insets = getBorderInsets();
				Color oldColor = g.getColor();
				g.translate(x, y);
				float alpha = 0.9f;
				float da = alpha / width * 3f;
				for (int dx = 0; alpha > 0; alpha -= da, dx++) {
					float[] comps = SHADE_COLOR.getColorComponents(null);
					g.setColor(new Color(comps[0], comps[1], comps[2], alpha));
					g.drawLine(dx, height - insets.bottom, dx, height);
					g.drawLine(width - dx, height - insets.bottom, width - dx, height);
				}
				g.translate(-x, -y);
				g.setColor(oldColor);
			}
		};
		Border insideBorder = BorderFactory.createEmptyBorder(DIV_SIZE, DIV_SIZE, DIV_SIZE, DIV_SIZE);
		panelTopBar.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
		panelTopBar.add(labelLogo);
		panelTopBar.add(Box.createHorizontalStrut(DIV_SIZE));
		panelTopBar.add(textPaneLogo);
		panelTopBar.add(Box.createHorizontalGlue());
		panelTopBar.add(textPaneInfo);
		panelTopBar.add(labelHeart);
		add(panelTopBar, BorderLayout.PAGE_START);
		
		initPanel();
		scrollPane = new JScrollPane(panel);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	private void addLogoText(int fontSize) {
		StyledDocument doc = textPaneLogo.getStyledDocument();
		String[] text = { "СТУДЕНТСКА ОРГАНИЗАЦИЈА\n", "ЕЛЕКТРОН" };
		
		Style normal = doc.addStyle("normal", DEF_STYLE);
		StyleConstants.setForeground(normal, Color.WHITE);
		StyleConstants.setFontFamily(normal, FONT_NAME);
		StyleConstants.setFontSize(normal, fontSize);
		
		Style large = doc.addStyle("large", normal);
		StyleConstants.setFontSize(large, (int) (fontSize * 2.8667));
		
		addTextToDocument(doc, text, new Style[]{ normal, large });
	}
	
	private void addInfoText(int fontSize) {
		StyledDocument doc = textPaneInfo.getStyledDocument();
		String[] text = { "Хуманитарни турнир у\n", "СТОНОМ ТЕНИСУ\n\n", VENUE + " " + DATE };
		
		Style normal = doc.addStyle("normal", DEF_STYLE);
		StyleConstants.setForeground(normal, new Color(0x95A5A6));
		StyleConstants.setFontFamily(normal, FONT_NAME);
		StyleConstants.setFontSize(normal, fontSize);
		StyleConstants.setItalic(normal, true);
		
		Style white = doc.addStyle("white", normal);
		StyleConstants.setForeground(white, Color.WHITE);
		
		addTextToDocument(doc, text, new Style[]{ normal, white, normal });
	}
	
	private void addTextToDocument(StyledDocument doc, String[] text, Style[] styles) {
		assert(text.length == styles.length);
		
		try {
			for (int i = 0; i < text.length; i++) {
				doc.insertString(doc.getLength(), text[i], styles[i]);
			}
		} catch (BadLocationException e) {
			GUIUtils.showError("Cannot add text to " + doc);
		}
	}
	
	protected TourneyPanel panel;
	
	private JLabel labelHeart;
	private JLabel labelLogo;
	private JPanel panelTopBar;
	private JScrollPane scrollPane;
	private JTextPane textPaneLogo;
	private JTextPane textPaneInfo;
}
