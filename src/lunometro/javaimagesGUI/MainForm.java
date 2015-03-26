package javaimagesGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.media.jai.PlanarImage;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import objeto.Objeto;
import objeto.Pixel;
import procesamiento.Binarizar;
import procesamiento.DetectarObjetos;
import procesamiento.HSVRange;
import procesamiento.ImageUtil;
import procesamiento.RgbHsv;
import procesamiento.SobelFilter;
import JavaImages.ImageProcessing;
import JavaImages.ImageProcessing_AdvancedFilters;
import JavaImages.ImageProcessing_BasicFilters;
import JavaImages.ImageProcessing_Histogram;
import JavaImages.ImageProcessing_MethodABDC;
import JavaImages.ImageProcessing_OpenImage;
import JavaImages.ImageProcessing_Resize;
import JavaImages.ImageProcessing_Resize.ScaleType;
import JavaImages.ImageProcessing_SaveImage;
import JavaImages.ImageProcessing_TransformFormatImages;


public class MainForm extends javax.swing.JFrame {
	static{
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}
	
	private static final long serialVersionUID = 6600005232377093167L;

	private ImageProcessing ObjProcessing;
	private ImageProcessing_OpenImage ObjOpenImage;
	private ImageProcessing_SaveImage ObjSaveImage;
	private ImageProcessing_BasicFilters ObjBasicFilters;
	private ImageProcessing_AdvancedFilters ObjAdvancedFilters;
	private ImageProcessing_TransformFormatImages ObjTransformFormats;
	private ImageProcessing_MethodABDC ObjMethodABDC;
	private ImageProcessing_Histogram ObjHistogram;
	private ImageProcessing_Resize ObjResize;
	private CreateHistogram ObjBarHistogram;
	private Boolean flagImage;
	private BufferedImage bufferImageTemp;

	private void DeleteText(JTextField jtextfield) {
		jtextfield.setText("");
	}

	private void interfaceModifications() {
		this.jSplitPanel1.setDividerLocation(0.85);
		this.jSplitPanel2_1.setDividerLocation(0.8);
		this.jSplitPanel2_1.setResizeWeight(1);
		this.jSplitPanel1.setResizeWeight(1);
	}

	private void enabledPanels(Boolean enabled) {
		for (int i = 0; i < jTabbedPane1.getComponentCount(); i++) {
			if (jTabbedPane1.getComponent(i) instanceof JPanel) {
				JPanel jpanelTemp = (JPanel) jTabbedPane1.getComponent(i);
				for (int j = 0; j < jpanelTemp.getComponentCount(); j++) {
					jpanelTemp.getComponent(j).setEnabled(enabled);
				}
			}
		}
		if (enabled == false) {
			for (int j = 0; j < this.jPanel3.getComponentCount(); j++) {
				this.jPanel3.getComponent(j).setEnabled(!enabled);
			}
		}
	}

	private void initializeObjects() {
		ObjProcessing = new ImageProcessing();
		ObjOpenImage = new ImageProcessing_OpenImage();
		ObjSaveImage = new ImageProcessing_SaveImage();
		ObjBasicFilters = new ImageProcessing_BasicFilters();
		ObjAdvancedFilters = new ImageProcessing_AdvancedFilters();
		ObjTransformFormats = new ImageProcessing_TransformFormatImages();
		ObjMethodABDC = new ImageProcessing_MethodABDC();
		ObjHistogram = new ImageProcessing_Histogram();
		ObjResize = new ImageProcessing_Resize();
		ObjBarHistogram = new CreateHistogram();
		ObjProcessing.attachTextAreaStatus(jTextArea_Log);
	}

	private void formInitialize() {
		interfaceModifications();
		initializeObjects();
		enabledPanels(false);
		this.flagImage = false;
	}

	private void showActivity(Boolean visible) {
		if (visible == true) {
			this.jSplitPanel1.setDividerLocation(0.85);
		} else {
			this.jSplitPanel1.setDividerLocation(0.9999);
		}
	}

	private void openImage() {
		this.bufferImageTemp = ObjOpenImage
				.openFile(ImageProcessing.imageFormat.all_images);
		if (this.bufferImageTemp != null) {
			this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
			if (this.flagImage == false) {
				this.flagImage = true;
				enabledPanels(true);
			}
		}
	}

	private void openURL() {
		if (!"".equals(this.JTextfield_URL.getText())) {
			this.bufferImageTemp = ObjOpenImage.openUrl(this.JTextfield_URL
					.getText());
			if (bufferImageTemp != null) {
				this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
				if (flagImage == false) {
					this.flagImage = true;
					enabledPanels(true);
				}
			}
		}
	}

	private void saveImage() {
		if (this.jLabelImage.getIcon() != null) {
			ImageProcessing.imageFormat extension;
			String extSelected = this.jComboBox1.getSelectedItem().toString(); 
			if("BMP".equals(extSelected)){
				extension = ImageProcessing.imageFormat.bmp;
			}else if("GIF".equals(extSelected)){
				extension = ImageProcessing.imageFormat.gif;
			}else if("JPG".equals(extSelected)){
				extension = ImageProcessing.imageFormat.jpg;
			}else if("PNG".equals(extSelected)){
				extension = ImageProcessing.imageFormat.png;
			}else{
				extension = ImageProcessing.imageFormat.jpg;
			}
			
			ObjSaveImage.saveFile((BufferedImage) ObjTransformFormats
					.iconToImage(jLabelImage.getIcon()), extension);
		}
	}

	private void undoImage() {
		this.bufferImageTemp = ObjProcessing.undoImage();
		if (this.bufferImageTemp != null) {
			this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
		}
	}

	private void redoImage() {
		this.bufferImageTemp = ObjProcessing.redoImage();
		if (this.bufferImageTemp != null) {
			this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
		}
	}

	private void lastOpenedImage() {
		this.bufferImageTemp = ObjProcessing.lastOpenedImage();
		if (this.bufferImageTemp != null) {
			this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
			this.JTexfield_lastOpened.setText(ObjProcessing
					.lastInfoOpenedImage());
		}
	}

	private void deleteAllImages() {
		int selected = JOptionPane
				.showConfirmDialog(
						null,
						"Do you want to delete all images? This action canÂ´t be undone ...",
						"Warning", JOptionPane.YES_NO_OPTION);
		if (JOptionPane.YES_OPTION == selected) {
			ObjProcessing.deleteAllStoredImages();
		}
	}

	private void grayScale() {
		this.bufferImageTemp = ObjTransformFormats
				.iconToBufferedImage(this.jLabelImage.getIcon());
		this.jLabelImage.setIcon(ObjTransformFormats
				.bufferedImageToImageIcon(ObjBasicFilters
						.grayScale(bufferImageTemp)));
	}

	private void blackWhite() {
		this.bufferImageTemp = ObjTransformFormats
				.iconToBufferedImage(this.jLabelImage.getIcon());
		this.jLabelImage.setIcon(ObjTransformFormats
				.bufferedImageToImageIcon(ObjBasicFilters.blackAndWhite(
						bufferImageTemp, jSlider_Threshold.getValue())));
	}

	private void invertColors() {
		this.bufferImageTemp = ObjTransformFormats
				.iconToBufferedImage(this.jLabelImage.getIcon());
		String modelColorSelected = jComboBox_Invert.getSelectedItem().toString();
		if("RGB".equals(modelColorSelected)){
			this.bufferImageTemp = ObjBasicFilters.invertColorsRGB(
					this.bufferImageTemp,
					ImageProcessing_BasicFilters.invertColorsAvailable.RGB);
		}else if("RED".equals(modelColorSelected)){
			this.bufferImageTemp = ObjBasicFilters.invertColorsRGB(
					this.bufferImageTemp,
					ImageProcessing_BasicFilters.invertColorsAvailable.red);
		}else if("GREEN".equals(modelColorSelected)){
			this.bufferImageTemp = ObjBasicFilters.invertColorsRGB(
					this.bufferImageTemp,
					ImageProcessing_BasicFilters.invertColorsAvailable.green);
			
		}else if("BLUE".equals(modelColorSelected)){
			this.bufferImageTemp = ObjBasicFilters.invertColorsRGB(
					this.bufferImageTemp,
					ImageProcessing_BasicFilters.invertColorsAvailable.blue);
		}
		
		this.jLabelImage.setIcon(ObjTransformFormats
				.bufferedImageToImageIcon(this.bufferImageTemp));
	}

	private void basicFilters() {
		this.bufferImageTemp = ObjTransformFormats
				.iconToBufferedImage(this.jLabelImage.getIcon());

		String filterSelected = jComboBox_BasicFilters.getSelectedItem().toString(); 
		
		if("RED".equals(filterSelected)){
			this.bufferImageTemp = ObjBasicFilters.basicFilters(
					this.bufferImageTemp, ImageProcessing_BasicFilters.filtersAvailable.red);
		}else if("GREEN".equals(filterSelected)){
			this.bufferImageTemp = ObjBasicFilters.basicFilters(
					this.bufferImageTemp, ImageProcessing_BasicFilters.filtersAvailable.green);
		}else if("BLUE".equals(filterSelected)){
			this.bufferImageTemp = ObjBasicFilters.basicFilters(
					this.bufferImageTemp,ImageProcessing_BasicFilters.filtersAvailable.blue);
		}
		
		this.jLabelImage.setIcon(ObjTransformFormats
				.bufferedImageToImageIcon(this.bufferImageTemp));
	}

	private void RGBto() {
		this.bufferImageTemp = ObjTransformFormats
				.iconToBufferedImage(this.jLabelImage.getIcon());

		String destSelected = jComboBox_RGB.getSelectedItem().toString(); 
		if("GBR".equals(destSelected)){
			this.bufferImageTemp = ObjBasicFilters.RGBto(this.bufferImageTemp,
					ImageProcessing_BasicFilters.RGBTransformAvailable.GRB);
		}else if("BRG".equals(destSelected)){
			this.bufferImageTemp = ObjBasicFilters.RGBto(this.bufferImageTemp,
					ImageProcessing_BasicFilters.RGBTransformAvailable.BRG);
		}else if("GRB".equals(destSelected)){
			this.bufferImageTemp = ObjBasicFilters.RGBto(this.bufferImageTemp,
					ImageProcessing_BasicFilters.RGBTransformAvailable.GRB);
		}else if("BGR".equals(destSelected)){
			this.bufferImageTemp = ObjBasicFilters.RGBto(this.bufferImageTemp,
					ImageProcessing_BasicFilters.RGBTransformAvailable.BGR);
		}else if("RBG".equals(destSelected)){
			this.bufferImageTemp = ObjBasicFilters.RGBto(this.bufferImageTemp,
					ImageProcessing_BasicFilters.RGBTransformAvailable.RBG);
		}
		
		this.jLabelImage.setIcon(ObjTransformFormats
				.bufferedImageToImageIcon(this.bufferImageTemp));
	}

	private void blackWhiteSmoothing() {
		this.bufferImageTemp = ObjTransformFormats
				.iconToBufferedImage(this.jLabelImage.getIcon());
		this.jLabelImage.setIcon(ObjTransformFormats
				.bufferedImageToImageIcon(ObjAdvancedFilters
						.blackAndWhiteSmoothing(this.bufferImageTemp,
								this.jSlider_ThresholdSmoothing.getValue(),
								this.jSlider_Range.getValue())));
	}

	private void contrastModificate() {
		this.bufferImageTemp = ObjTransformFormats
				.iconToBufferedImage(this.jLabelImage.getIcon());
		double valueContrast = (double) ((double) this.jSlider_Contrast
				.getValue() / 100);
		this.jLabelImage.setIcon(ObjTransformFormats
				.bufferedImageToImageIcon(ObjAdvancedFilters.contrast(
						bufferImageTemp, valueContrast)));
	}

	private ScaleType scaleTypeSelect(String scaleType) {
		ScaleType scaleTypeSelected = ScaleType.SCALE_DEFAULT;
		
		if("SCALE_AREA_AVERAGING".equals(scaleType)){
			scaleTypeSelected = ScaleType.SCALE_AREA_AVERAGING;
		}else if("SCALE_DEFAULT".equals(scaleType)){
			scaleTypeSelected = ScaleType.SCALE_DEFAULT;
		}else if("SCALE_FAST".equals(scaleType)){
			scaleTypeSelected = ScaleType.SCALE_FAST;
		}else if("SCALE_REPLICATE".equals(scaleType)){
			scaleTypeSelected = ScaleType.SCALE_REPLICATE;
		}else if("SCALE_SMOOTH".equals(scaleType)){
			scaleTypeSelected = ScaleType.SCALE_SMOOTH;
		}
		
		return scaleTypeSelected;
	}

	private void resizeImage() {
		ScaleType scaleType = scaleTypeSelect(jComboBox_ResizeType.toString());
		this.bufferImageTemp = ObjTransformFormats
				.iconToBufferedImage(this.jLabelImage.getIcon());
		int widht, height;
		widht = Integer.parseInt(jFormatted_ResizeWidth.getText());
		height = Integer.parseInt(jFormatted_ResizeHeight.getText());
		this.jLabelImage.setIcon(ObjTransformFormats
				.bufferedImageToImageIcon(ObjResize.resizeImage(
						bufferImageTemp, widht, height, scaleType)));
	}

	private void currentSize() {
		jTextField_CurrentSize.setText(this.jLabelImage.getIcon()
				.getIconWidth()
				+ "x"
				+ this.jLabelImage.getIcon().getIconHeight());
	}

	private void resizePercentImage() {
		ScaleType scaleType = scaleTypeSelect(jComboBox_ResizePercentType
				.toString());
		this.bufferImageTemp = ObjTransformFormats
				.iconToBufferedImage(this.jLabelImage.getIcon());
		int widht, height;
		widht = Integer.parseInt(jFormatted_ResizeWidthPercent.getText());
		height = Integer.parseInt(jFormatted_ResizeHeightPErcent.getText());
		this.jLabelImage.setIcon(ObjTransformFormats
				.bufferedImageToImageIcon(ObjResize.resizeImagePercent(
						bufferImageTemp, widht, height, scaleType)));
	}

	private void showHistogram() {
		int[] histogram;
		CreateHistogram.availableChannel histogramColor;
		String histogramPaletteSelected = this.jComboBoxHistogram.getSelectedItem().toString();
		if("RED".equals(histogramPaletteSelected)){
			histogram = ObjHistogram.histogramRed(ObjTransformFormats
					.iconToBufferedImage(this.jLabelImage.getIcon()));
			histogramColor = CreateHistogram.availableChannel.red;

		}else if("GREEN".equals(histogramPaletteSelected)){
			histogram = ObjHistogram.histogramGreen(ObjTransformFormats
					.iconToBufferedImage(this.jLabelImage.getIcon()));
			histogramColor = CreateHistogram.availableChannel.green;
		}else if("BLUE".equals(histogramPaletteSelected)){
			histogram = ObjHistogram.histogramBlue(ObjTransformFormats
					.iconToBufferedImage(this.jLabelImage.getIcon()));
			histogramColor = CreateHistogram.availableChannel.blue;
		}else if("ALPHA".equals(histogramPaletteSelected)){
			histogram = ObjHistogram.histogramAlpha(ObjTransformFormats
					.iconToBufferedImage(this.jLabelImage.getIcon()));
			histogramColor = CreateHistogram.availableChannel.alpha;
		}else if("GRAYSCALE".equals(histogramPaletteSelected)){
			histogram = ObjHistogram.histogramGrayscale(ObjTransformFormats
					.iconToBufferedImage(this.jLabelImage.getIcon()));
			histogramColor = CreateHistogram.availableChannel.grayscale;
		}else{
			histogram = ObjHistogram.histogramGrayscale(ObjTransformFormats
					.iconToBufferedImage(this.jLabelImage.getIcon()));
			histogramColor = CreateHistogram.availableChannel.grayscale;
		}

		ObjBarHistogram.createHistogramBarChart(histogram, jPanelHistogram,
				histogramColor);
		this.jLabel_MaxHistogram.setText("Max value "
				+ ObjHistogram.getMaxValue()[0] + ", with "
				+ ObjHistogram.getMaxValue()[1] + " pixels");
		this.jLabel_MinHistogram.setText("Min value "
				+ ObjHistogram.getMinValue()[0] + ", with "
				+ ObjHistogram.getMinValue()[1] + " pixels");
	}

	public MainForm() {
		setTitle("Lunometro");
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		jButton1 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jSplitPanel1 = new javax.swing.JSplitPane();
		jSplitPanel2_1 = new javax.swing.JSplitPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		jLabelImage = new javax.swing.JLabel();
		jButton3 = new javax.swing.JButton();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel3 = new javax.swing.JPanel();
		jButton12 = new javax.swing.JButton();
		jComboBox1 = new javax.swing.JComboBox();
		jSeparator2 = new javax.swing.JSeparator();
		JTextfield_URL = new javax.swing.JTextField();
		jButton11 = new javax.swing.JButton();
		jButton10 = new javax.swing.JButton();
		jPanel4 = new javax.swing.JPanel();
		jButton7 = new javax.swing.JButton();
		jButton8 = new javax.swing.JButton();
		jButton9 = new javax.swing.JButton();
		JTexfield_lastOpened = new javax.swing.JTextField();
		jSeparator1 = new javax.swing.JSeparator();
		jButton13 = new javax.swing.JButton();
		jPanel5 = new javax.swing.JPanel();
		jButton14 = new javax.swing.JButton();
		jButton15 = new javax.swing.JButton();
		jLabel_Threshold = new javax.swing.JLabel();
		jSlider_Threshold = new javax.swing.JSlider();
		jButton16 = new javax.swing.JButton();
		jComboBox_Invert = new javax.swing.JComboBox();
		jComboBox_BasicFilters = new javax.swing.JComboBox();
		jButton17 = new javax.swing.JButton();
		jButton18 = new javax.swing.JButton();
		jComboBox_RGB = new javax.swing.JComboBox();
		jPanel6 = new javax.swing.JPanel();
		jButton19 = new javax.swing.JButton();
		jLabel_ThresholdSmoothing = new javax.swing.JLabel();
		jSlider_ThresholdSmoothing = new javax.swing.JSlider();
		jSlider_Range = new javax.swing.JSlider();
		jLabel_Range = new javax.swing.JLabel();
		jButton22 = new javax.swing.JButton();
		jSlider_Contrast = new javax.swing.JSlider();
		jLabel_Contrast = new javax.swing.JLabel();
		jPanel7 = new javax.swing.JPanel();
		jButton20 = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jButton21 = new javax.swing.JButton();
		jComboBoxHistogram = new javax.swing.JComboBox();
		jPanelHistogram = new javax.swing.JPanel();
		jLabel_MaxHistogram = new javax.swing.JLabel();
		jLabel_MinHistogram = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		jButton26 = new javax.swing.JButton();
		jTextField_CurrentSize = new javax.swing.JTextField();
		jButton27 = new javax.swing.JButton();
		jComboBox_ResizeType = new javax.swing.JComboBox();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jButton23 = new javax.swing.JButton();
		jComboBox_ResizePercentType = new javax.swing.JComboBox();
		jSeparator4 = new javax.swing.JSeparator();
		jSeparator5 = new javax.swing.JSeparator();
		jSlider_ResizeWidth = new javax.swing.JSlider();
		jSlider_ResizeHeight = new javax.swing.JSlider();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jFormatted_ResizeWidth = new javax.swing.JFormattedTextField();
		jFormatted_ResizeHeight = new javax.swing.JFormattedTextField();
		jFormatted_ResizeWidthPercent = new javax.swing.JFormattedTextField();
		jFormatted_ResizeHeightPErcent = new javax.swing.JFormattedTextField();
		jPanel8 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea_Log = new javax.swing.JTextArea();
		jButton2 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent evt) {
				formWindowOpened(evt);
			}
		});
		addWindowStateListener(new java.awt.event.WindowStateListener() {
			public void windowStateChanged(java.awt.event.WindowEvent evt) {
				formWindowStateChanged(evt);
			}
		});

		jToolBar1.setRollover(true);

		jButton1.setText("Undo");
		jButton1.setFocusable(false);
		jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton1);

		jButton5.setText("Redo");
		jButton5.setFocusable(false);
		jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton5);

		jButton6.setText("Last opened image");
		jButton6.setFocusable(false);
		jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton6);

		jSplitPanel1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		jSplitPanel1.setMinimumSize(new java.awt.Dimension(500, 500));

		jLabelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jScrollPane1.setViewportView(jLabelImage);

		jSplitPanel2_1.setLeftComponent(jScrollPane1);

		jButton3.setText("jButton3");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jSplitPanel2_1.setRightComponent(jButton3);

		jButton12.setText("Save image");
		jButton12.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton12ActionPerformed(evt);
			}
		});

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"BMP", "GIF", "JPG", "PNG", "NOTHING" }));

		JTextfield_URL.setText("http://");
		JTextfield_URL.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				JTextfield_URLMouseClicked(evt);
			}
		});

		jButton11.setText("Open URL");
		jButton11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton11ActionPerformed(evt);
			}
		});

		jButton10.setText("Open local");
		jButton10.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton10ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addGap(10,
																				10,
																				10)
																		.addComponent(
																				JTextfield_URL))
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jButton10,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				498,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jButton11,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jSeparator2))
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jButton12,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				289,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jComboBox1,
																				0,
																				203,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		jPanel3Layout
				.setVerticalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jButton10)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButton11)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												JTextfield_URL,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jSeparator2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jButton12)
														.addComponent(
																jComboBox1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(293, Short.MAX_VALUE)));

		jTabbedPane1.addTab("Open/Save image", jPanel3);

		jButton7.setText("Undo image");
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton7ActionPerformed(evt);
			}
		});

		jButton8.setText("Redo image");
		jButton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton8ActionPerformed(evt);
			}
		});

		jButton9.setText("Last opened image");
		jButton9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton9ActionPerformed(evt);
			}
		});

		jButton13.setText("Delete all stored images");
		jButton13.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton13ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
				jPanel4);
		jPanel4Layout.setHorizontalGroup(
			jPanel4Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jButton13, GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
						.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(JTexfield_lastOpened, 857, 857, 857)
						.addComponent(jButton9, GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
						.addComponent(jButton8, GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
						.addComponent(jButton7, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		jPanel4Layout.setVerticalGroup(
			jPanel4Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jButton7)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jButton8)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jButton9)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(JTexfield_lastOpened, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jButton13)
					.addContainerGap(264, Short.MAX_VALUE))
		);
		jPanel4.setLayout(jPanel4Layout);

		jTabbedPane1.addTab("Main", jPanel4);

		jButton14.setText("Grayscale");
		jButton14.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton14ActionPerformed(evt);
			}
		});

		jButton15.setText("Black and white");
		jButton15.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton15ActionPerformed(evt);
			}
		});

		jLabel_Threshold.setText("Threshold 128");

		jSlider_Threshold.setMaximum(255);
		jSlider_Threshold.setValue(128);
		jSlider_Threshold
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						jSlider_ThresholdStateChanged(evt);
					}
				});

		jButton16.setText("Invert colors");
		jButton16.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton16ActionPerformed(evt);
			}
		});

		jComboBox_Invert.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "RGB", "RED", "GREEN", "BLUE" }));

		jComboBox_BasicFilters.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "RED", "GREEN", "BLUE" }));

		jButton17.setText("Filter");
		jButton17.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton17ActionPerformed(evt);
			}
		});

		jButton18.setText("RGB to");
		jButton18.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton18ActionPerformed(evt);
			}
		});

		jComboBox_RGB.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "GBR", "GRB", "BRG", "BGR", "RBG" }));

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(
				jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout
				.setHorizontalGroup(jPanel5Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel5Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jSlider_Threshold,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jButton14,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																jPanel5Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel5Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jButton16,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								264,
																								Short.MAX_VALUE)
																						.addComponent(
																								jButton18,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								jButton17,
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel5Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jComboBox_BasicFilters,
																								0,
																								228,
																								Short.MAX_VALUE)
																						.addComponent(
																								jComboBox_RGB,
																								0,
																								1,
																								Short.MAX_VALUE)
																						.addComponent(
																								jComboBox_Invert,
																								0,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)))
														.addComponent(
																jButton15,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																jPanel5Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel_Threshold)
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		jPanel5Layout
				.setVerticalGroup(jPanel5Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel5Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jButton14)
										.addGap(4, 4, 4)
										.addComponent(jButton15)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel_Threshold)
										.addGap(3, 3, 3)
										.addComponent(
												jSlider_Threshold,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jComboBox_Invert,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jButton16))
										.addGap(7, 7, 7)
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jComboBox_BasicFilters,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jButton17))
										.addGap(7, 7, 7)
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jButton18)
														.addComponent(
																jComboBox_RGB,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(228, Short.MAX_VALUE)));

		jTabbedPane1.addTab("Basic filters", jPanel5);

		jButton19.setText("Black and white smoothing");
		jButton19.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton19ActionPerformed(evt);
			}
		});

		jLabel_ThresholdSmoothing.setText("Threshold 128");

		jSlider_ThresholdSmoothing.setMaximum(255);
		jSlider_ThresholdSmoothing.setValue(128);
		jSlider_ThresholdSmoothing
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						jSlider_ThresholdSmoothingStateChanged(evt);
					}
				});

		jSlider_Range.setMaximum(127);
		jSlider_Range.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSlider_RangeStateChanged(evt);
			}
		});

		jLabel_Range.setText("Range 50");

		jButton22.setText("Contrast");
		jButton22.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton22ActionPerformed(evt);
			}
		});

		jSlider_Contrast.setMinimum(-100);
		jSlider_Contrast.setValue(20);
		jSlider_Contrast
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						jSlider_ContrastStateChanged(evt);
					}
				});

		jLabel_Contrast.setText("Contrast 0.2");
		jLabel_Contrast.setToolTipText("");
		
		JButton btnBinarizar = new JButton();
		btnBinarizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				binarizar();
			}
		});
		btnBinarizar.setText("Binarizar");
		
		JButton btnBorde = new JButton();
		btnBorde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bordes();
			}
		});
		btnBorde.setText("Ver Borde");

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(
				jPanel6);
		jPanel6Layout.setHorizontalGroup(
			jPanel6Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel6Layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jButton22, GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
						.addComponent(jButton19, GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
						.addGroup(jPanel6Layout.createSequentialGroup()
							.addComponent(jSlider_ThresholdSmoothing, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jSlider_Range, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
						.addGroup(jPanel6Layout.createSequentialGroup()
							.addComponent(jLabel_ThresholdSmoothing)
							.addPreferredGap(ComponentPlacement.RELATED, 743, Short.MAX_VALUE)
							.addComponent(jLabel_Range))
						.addGroup(jPanel6Layout.createSequentialGroup()
							.addComponent(jLabel_Contrast)
							.addGap(0, 796, Short.MAX_VALUE))
						.addComponent(jSlider_Contrast, GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
						.addComponent(btnBinarizar, GroupLayout.PREFERRED_SIZE, 857, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBorde, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 857, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		jPanel6Layout.setVerticalGroup(
			jPanel6Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jButton19)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(jPanel6Layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jLabel_Range)
						.addComponent(jLabel_ThresholdSmoothing))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(jPanel6Layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(jSlider_ThresholdSmoothing, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(jSlider_Range, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(jButton22)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jLabel_Contrast)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jSlider_Contrast, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(51)
					.addComponent(btnBinarizar)
					.addGap(33)
					.addComponent(btnBorde)
					.addContainerGap(136, Short.MAX_VALUE))
		);
		jPanel6.setLayout(jPanel6Layout);

		jTabbedPane1.addTab("Advanced filters", jPanel6);

		jButton20.setText("Show activity");
		jButton20.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton20ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(
				jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanel7Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jButton20,
								javax.swing.GroupLayout.DEFAULT_SIZE, 498,
								Short.MAX_VALUE).addContainerGap()));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel7Layout.createSequentialGroup().addContainerGap()
						.addComponent(jButton20)
						.addContainerGap(405, Short.MAX_VALUE)));

		jTabbedPane1.addTab("Window", jPanel7);

		jButton21.setText("Show histogram");
		jButton21.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton21ActionPerformed(evt);
			}
		});

		jComboBoxHistogram.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "RED", "GREEN", "BLUE", "ALPHA", "GRAYSCALE" }));

		javax.swing.GroupLayout jPanelHistogramLayout = new javax.swing.GroupLayout(
				jPanelHistogram);
		jPanelHistogram.setLayout(jPanelHistogramLayout);
		jPanelHistogramLayout.setHorizontalGroup(jPanelHistogramLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE));
		jPanelHistogramLayout.setVerticalGroup(jPanelHistogramLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 376, Short.MAX_VALUE));

		jLabel_MaxHistogram
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		jLabel_MinHistogram
				.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jLabel_MinHistogram,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPanelHistogram,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jButton21,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				250,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jComboBoxHistogram,
																				0,
																				242,
																				Short.MAX_VALUE))
														.addComponent(
																jLabel_MaxHistogram,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jButton21)
														.addComponent(
																jComboBoxHistogram,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanelHistogram,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel_MinHistogram)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel_MaxHistogram)
										.addContainerGap()));

		jTabbedPane1.addTab("Histogram", jPanel1);

		jButton26.setText("Current size");
		jButton26.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton26ActionPerformed(evt);
			}
		});

		jTextField_CurrentSize
				.setHorizontalAlignment(javax.swing.JTextField.CENTER);

		jButton27.setText("Resize");
		jButton27.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton27ActionPerformed(evt);
			}
		});

		jComboBox_ResizeType.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "SCALE_AREA_AVERAGING", "SCALE_DEFAULT",
						"SCALE_FAST", "SCALE_REPLICATE", "SCALE_SMOOTH" }));

		jLabel3.setText("Width");

		jLabel4.setText("Height");

		jButton23.setText("Resize percent");
		jButton23.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton23ActionPerformed(evt);
			}
		});

		jComboBox_ResizePercentType
				.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
						"SCALE_AREA_AVERAGING", "SCALE_DEFAULT", "SCALE_FAST",
						"SCALE_REPLICATE", "SCALE_SMOOTH" }));

		jSlider_ResizeWidth.setMaximum(500);
		jSlider_ResizeWidth.setMinimum(1);
		jSlider_ResizeWidth.setValue(150);
		jSlider_ResizeWidth
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						jSlider_ResizeWidthStateChanged(evt);
					}
				});

		jSlider_ResizeHeight.setMaximum(500);
		jSlider_ResizeHeight.setMinimum(1);
		jSlider_ResizeHeight.setValue(150);
		jSlider_ResizeHeight
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						jSlider_ResizeHeightStateChanged(evt);
					}
				});

		jLabel5.setText("Width percent");

		jLabel6.setText("Height percent");

		jFormatted_ResizeWidth
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		jFormatted_ResizeWidth.setText("500");

		jFormatted_ResizeHeight
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		jFormatted_ResizeHeight.setText("500");

		jFormatted_ResizeWidthPercent
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		jFormatted_ResizeWidthPercent.setText("120");

		jFormatted_ResizeHeightPErcent
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#0"))));
		jFormatted_ResizeHeightPErcent.setText("120");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jSeparator5)
														.addComponent(
																jSeparator4,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								jSlider_ResizeWidth,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								244,
																								Short.MAX_VALUE)
																						.addComponent(
																								jButton23,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								jButton27,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								jButton26,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								jLabel3,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								jPanel2Layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel5)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jFormatted_ResizeWidthPercent))
																						.addComponent(
																								jFormatted_ResizeWidth,
																								javax.swing.GroupLayout.Alignment.LEADING))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jComboBox_ResizeType,
																								0,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								jTextField_CurrentSize)
																						.addComponent(
																								jLabel4,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								jComboBox_ResizePercentType,
																								0,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								jSlider_ResizeHeight,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								248,
																								Short.MAX_VALUE)
																						.addGroup(
																								jPanel2Layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel6)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jFormatted_ResizeHeightPErcent))
																						.addComponent(
																								jFormatted_ResizeHeight))))
										.addContainerGap()));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jTextField_CurrentSize,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jButton26))
										.addGap(13, 13, 13)
										.addComponent(
												jSeparator4,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jButton27)
														.addComponent(
																jComboBox_ResizeType,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel3)
														.addComponent(jLabel4))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jFormatted_ResizeWidth,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jFormatted_ResizeHeight,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jSeparator5,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jComboBox_ResizePercentType,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jButton23))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel5)
														.addComponent(jLabel6)
														.addComponent(
																jFormatted_ResizeWidthPercent,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jFormatted_ResizeHeightPErcent,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jSlider_ResizeWidth,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jSlider_ResizeHeight,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(189, Short.MAX_VALUE)));

		jTabbedPane1.addTab("Resize image", jPanel2);

		jSplitPanel2_1.setRightComponent(jTabbedPane1);
		
		panel = new JPanel();
		jTabbedPane1.addTab("Calculo ABCD", null, panel, null);
		
		btnArea = new JButton();
		btnArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calcularABCD();
			}
		});
		btnArea.setText("Calcular");
		
		JLabel lblA = new JLabel("A: ");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		lblB = new JLabel("B:");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		lblC = new JLabel("C:");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		lblD = new JLabel("D:");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		JLabel lblFds = new JLabel("FDS:");
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		
		JButton btnNewButton = new JButton("Registrar Estudio");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO Insertar en BD Derby el analisis.
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnArea, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblA)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(textPane, Alignment.LEADING)
										.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
											.addComponent(textField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
											.addGap(27)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(lblFds)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(textField_4))
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(lblB, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
													.addGap(18)
													.addComponent(lblC, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
											.addGap(18)
											.addComponent(lblD, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(153)
							.addComponent(btnNewButton)))
					.addContainerGap(507, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(btnArea)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblA)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(3)
									.addComponent(lblB))
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblC)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(3)
									.addComponent(lblD)))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(34)
									.addComponent(lblFds))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(31)
									.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(btnNewButton)
					.addContainerGap(151, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

		jSplitPanel1.setTopComponent(jSplitPanel2_1);

		jTextArea_Log.setBackground(new java.awt.Color(0, 0, 0));
		jTextArea_Log.setColumns(20);
		jTextArea_Log.setForeground(new java.awt.Color(0, 255, 0));
		jTextArea_Log.setRows(5);
		jScrollPane2.setViewportView(jTextArea_Log);

		jButton2.setText("Hide");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jButton4.setText("Show all");

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel1.setText("Show activity");

		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(
				jPanel8);
		jPanel8.setLayout(jPanel8Layout);
		jPanel8Layout
				.setHorizontalGroup(jPanel8Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel8Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel8Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel8Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel1)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				696,
																				Short.MAX_VALUE)
																		.addComponent(
																				jButton4)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jButton2))
														.addComponent(
																jScrollPane2))));
		jPanel8Layout
				.setVerticalGroup(jPanel8Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel8Layout
										.createSequentialGroup()
										.addGroup(
												jPanel8Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jButton4,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																18,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jButton2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																18,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel1))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												143, Short.MAX_VALUE)
										.addContainerGap()));

		jSplitPanel1.setRightComponent(jPanel8);

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Edit");
		jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jSplitPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jToolBar1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										25,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSplitPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	public Color calcularPromedioColorPunto(Pixel punto, int tamanioRec){
		int ancho = tamanioRec / 2;
		int r = 0;
		int g = 0;
		int b = 0;
		int cantPuntos = 0;
		
		for(int i=1;i<ancho;i++){
			Pixel pIzq = new Pixel(punto.getX()-i,punto.getY(),null);
			Pixel pDer = new Pixel(punto.getX()+i,punto.getY(),null);
			Pixel pArr = new Pixel(punto.getX(),punto.getY()+1,null);
			Pixel pAba = new Pixel(punto.getX(),punto.getY()-1,null);

			Color color1 = ImageUtil.getColorPunto(pIzq, PlanarImage.wrapRenderedImage(this.bufferImageTemp));
			Color color2 = ImageUtil.getColorPunto(pDer, PlanarImage.wrapRenderedImage(this.bufferImageTemp));
			Color color3 = ImageUtil.getColorPunto(pArr, PlanarImage.wrapRenderedImage(this.bufferImageTemp));
			Color color4 = ImageUtil.getColorPunto(pAba, PlanarImage.wrapRenderedImage(this.bufferImageTemp));

			if (color1 != null){
				r += color1.getRed();
				g += color1.getGreen();
				b += color1.getBlue();
				cantPuntos++;
			}

			if (color2 != null){
				r += color2.getRed();
				g += color2.getGreen();
				b += color2.getBlue();
				cantPuntos++;
			}

			if (color3 != null){
				r += color3.getRed();
				g += color3.getGreen();
				b += color3.getBlue();
				cantPuntos++;
			}
			
			if (color4 != null){
				r += color4.getRed();
				g += color4.getGreen();
				b += color4.getBlue();
				cantPuntos++;
			}


		}

		if (cantPuntos > 0){
			r = r / cantPuntos;
			g = g / cantPuntos;
			b = b / cantPuntos;
		}
		
		return new Color(r,g,b);
	}	
	
	/**
	 * Calculo Metodo ABCD
	 */
	protected void calcularABCD() {

		HSVRange range = new HSVRange();
		Color c = calcularPromedioColorPunto(new Pixel(10,10,null), 4);
		float[] hsvRangeFondo = RgbHsv.RGBtoHSV(c.getRed(), c.getGreen(), c.getBlue());
		range.setHMin(hsvRangeFondo[0]);
		range.setSMin(hsvRangeFondo[1]);
		range.setVMin(hsvRangeFondo[2]);
		
		Binarizar ef = new Binarizar(PlanarImage.wrapRenderedImage(this.bufferImageTemp), null, null);
		PlanarImage output = ef.execute();

		try {
			DetectarObjetos detectar = new DetectarObjetos(output, PlanarImage.wrapRenderedImage(this.bufferImageTemp), range);
			output = detectar.execute();
			
			Objeto mancha = detectar.getObjetos().get(0);
			ObjMethodABDC.calcularABCD(mancha);
			this.textField.setText(String.valueOf(ObjMethodABDC.getIndicadorA()));
			this.textField_1.setText(String.valueOf(ObjMethodABDC.getIndicadorB()));
			this.textField_2.setText(String.valueOf(ObjMethodABDC.getIndicadorC()));
			this.textField_3.setText(String.valueOf(ObjMethodABDC.getIndicadorD()));
			
			Double indicadorFDS = ObjMethodABDC.calcularFDS(); 
			this.textField_4.setText(String.valueOf(indicadorFDS));
			if(indicadorFDS > 5.45) {
				this.textPane.setText("Atención: La lesión tiene una gran probabilidad de ser un cáncer de piel \n "
						+ "de tipo melanoma maligno. Por tanto, requiere una extirpación lo antes posible y su \n"
						+ "tratamiento depende del estadio en el que se encuentre el melanoma");
			}else if(indicadorFDS > 4.75 && indicadorFDS < 5.45) {
				this.textPane.setText("Atención: La lesión tiene probabilidad moderada de ser melanoma maligno. \n"
						+ "En este caso, es necesaria una vigilancia constante de la evolución de la lesión para\n"
						+ " verificar si puede convertirse o no en un melanoma maligno.");
			}else{
				this.textPane.setText("Atención: Es probable que la lesión sea un cáncer de piel de tipo benigno. \n"
						+ "Por lo que no es necesaria una extirpación, pero quizás requiera un tratamiento diferente, \n"
						+ "dependiendo del cáncer que sea.");
			}
			
			this.bufferImageTemp = output.getAsBufferedImage();
			this.jLabelImage.setIcon(new ImageIcon(output.getAsBufferedImage()));	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	protected void binarizar() {
		Binarizar ef = new Binarizar(PlanarImage.wrapRenderedImage(this.bufferImageTemp), null, null);
		PlanarImage output = ef.execute();
		this.bufferImageTemp = output.getAsBufferedImage();
		this.jLabelImage.setIcon(new ImageIcon(output.getAsBufferedImage()));	
	}

	protected void bordes() {
		SobelFilter ef = new SobelFilter(PlanarImage.wrapRenderedImage(this.bufferImageTemp));
		PlanarImage output = ef.execute();
		this.bufferImageTemp = output.getAsBufferedImage();
		this.jLabelImage.setIcon(new ImageIcon(output.getAsBufferedImage()));	
	}

	private void formWindowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowOpened
		formInitialize();
	}// GEN-LAST:event_formWindowOpened

	private void formWindowStateChanged(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowStateChanged

	}// GEN-LAST:event_formWindowStateChanged

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		showActivity(true);
	}// GEN-LAST:event_jButton3ActionPerformed

	private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton12ActionPerformed
		saveImage();
	}// GEN-LAST:event_jButton12ActionPerformed

	private void JTextfield_URLMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_JTextfield_URLMouseClicked
		DeleteText(JTextfield_URL);
	}// GEN-LAST:event_JTextfield_URLMouseClicked

	private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton11ActionPerformed
		openURL();
	}// GEN-LAST:event_jButton11ActionPerformed

	private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton10ActionPerformed
		openImage();
	}// GEN-LAST:event_jButton10ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		showActivity(false);
	}// GEN-LAST:event_jButton2ActionPerformed

	private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
		undoImage();
	}// GEN-LAST:event_jButton7ActionPerformed

	private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton8ActionPerformed
		redoImage();
	}// GEN-LAST:event_jButton8ActionPerformed

	private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton9ActionPerformed
		lastOpenedImage();
	}// GEN-LAST:event_jButton9ActionPerformed

	private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton13ActionPerformed
		deleteAllImages();
	}// GEN-LAST:event_jButton13ActionPerformed

	private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton14ActionPerformed
		grayScale();
	}// GEN-LAST:event_jButton14ActionPerformed

	private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton15ActionPerformed
		blackWhite();
	}// GEN-LAST:event_jButton15ActionPerformed

	private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton16ActionPerformed
		invertColors();
	}// GEN-LAST:event_jButton16ActionPerformed

	private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton17ActionPerformed
		basicFilters();
	}// GEN-LAST:event_jButton17ActionPerformed

	private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton18ActionPerformed
		RGBto();
	}// GEN-LAST:event_jButton18ActionPerformed

	private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton19ActionPerformed
		blackWhiteSmoothing();
	}// GEN-LAST:event_jButton19ActionPerformed

	private void jSlider_ThresholdSmoothingStateChanged(
			javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSlider_ThresholdSmoothingStateChanged
		Events.changeLabel(
				jLabel_ThresholdSmoothing,
				"Threshold "
						+ String.valueOf(jSlider_ThresholdSmoothing.getValue()));
	}// GEN-LAST:event_jSlider_ThresholdSmoothingStateChanged

	private void jSlider_RangeStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSlider_RangeStateChanged
		Events.changeLabel(jLabel_Range,
				"Range " + String.valueOf(jSlider_Range.getValue()));
	}// GEN-LAST:event_jSlider_RangeStateChanged

	private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton20ActionPerformed
		showActivity(true);
	}// GEN-LAST:event_jButton20ActionPerformed

	private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton21ActionPerformed
		showHistogram();
	}// GEN-LAST:event_jButton21ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		undoImage();
	}// GEN-LAST:event_jButton1ActionPerformed

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
		redoImage();
	}// GEN-LAST:event_jButton5ActionPerformed

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
		lastOpenedImage();
	}// GEN-LAST:event_jButton6ActionPerformed

	private void jSlider_ThresholdStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSlider_ThresholdStateChanged
		Events.changeLabel(jLabel_Threshold,
				"Threshold " + String.valueOf(jSlider_Threshold.getValue()));
	}// GEN-LAST:event_jSlider_ThresholdStateChanged

	private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton22ActionPerformed
		contrastModificate();
	}// GEN-LAST:event_jButton22ActionPerformed

	private void jSlider_ContrastStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSlider_ContrastStateChanged
		double valueContrast = (double) ((double) this.jSlider_Contrast
				.getValue() / 100);
		Events.changeLabel(jLabel_Contrast,
				"Contrast " + String.valueOf(valueContrast));
	}// GEN-LAST:event_jSlider_ContrastStateChanged

	private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton26ActionPerformed
		currentSize();
	}// GEN-LAST:event_jButton26ActionPerformed

	private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton27ActionPerformed
		resizeImage();
	}// GEN-LAST:event_jButton27ActionPerformed

	private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton23ActionPerformed
		resizePercentImage();
	}// GEN-LAST:event_jButton23ActionPerformed

	private void jSlider_ResizeWidthStateChanged(
			javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSlider_ResizeWidthStateChanged
		Events.changeTextField(jFormatted_ResizeWidthPercent,
				String.valueOf(jSlider_ResizeWidth.getValue()));
	}// GEN-LAST:event_jSlider_ResizeWidthStateChanged

	private void jSlider_ResizeHeightStateChanged(
			javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSlider_ResizeHeightStateChanged
		Events.changeTextField(jFormatted_ResizeHeightPErcent,
				String.valueOf(jSlider_ResizeHeight.getValue()));
	}// GEN-LAST:event_jSlider_ResizeHeightStateChanged

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainForm().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTextField JTexfield_lastOpened;
	private javax.swing.JTextField JTextfield_URL;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton10;
	private javax.swing.JButton jButton11;
	private javax.swing.JButton jButton12;
	private javax.swing.JButton jButton13;
	private javax.swing.JButton jButton14;
	private javax.swing.JButton jButton15;
	private javax.swing.JButton jButton16;
	private javax.swing.JButton jButton17;
	private javax.swing.JButton jButton18;
	private javax.swing.JButton jButton19;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton20;
	private javax.swing.JButton jButton21;
	private javax.swing.JButton jButton22;
	private javax.swing.JButton jButton23;
	private javax.swing.JButton jButton26;
	private javax.swing.JButton jButton27;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;
	private javax.swing.JButton jButton8;
	private javax.swing.JButton jButton9;
	private javax.swing.JComboBox jComboBox1;
	private javax.swing.JComboBox jComboBoxHistogram;
	private javax.swing.JComboBox jComboBox_BasicFilters;
	private javax.swing.JComboBox jComboBox_Invert;
	private javax.swing.JComboBox jComboBox_RGB;
	private javax.swing.JComboBox jComboBox_ResizePercentType;
	private javax.swing.JComboBox jComboBox_ResizeType;
	private javax.swing.JFormattedTextField jFormatted_ResizeHeight;
	private javax.swing.JFormattedTextField jFormatted_ResizeHeightPErcent;
	private javax.swing.JFormattedTextField jFormatted_ResizeWidth;
	private javax.swing.JFormattedTextField jFormatted_ResizeWidthPercent;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabelImage;
	private javax.swing.JLabel jLabel_Contrast;
	private javax.swing.JLabel jLabel_MaxHistogram;
	private javax.swing.JLabel jLabel_MinHistogram;
	private javax.swing.JLabel jLabel_Range;
	private javax.swing.JLabel jLabel_Threshold;
	private javax.swing.JLabel jLabel_ThresholdSmoothing;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanelHistogram;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JSeparator jSeparator4;
	private javax.swing.JSeparator jSeparator5;
	private javax.swing.JSlider jSlider_Contrast;
	private javax.swing.JSlider jSlider_Range;
	private javax.swing.JSlider jSlider_ResizeHeight;
	private javax.swing.JSlider jSlider_ResizeWidth;
	private javax.swing.JSlider jSlider_Threshold;
	private javax.swing.JSlider jSlider_ThresholdSmoothing;
	private javax.swing.JSplitPane jSplitPanel1;
	private javax.swing.JSplitPane jSplitPanel2_1;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTextArea jTextArea_Log;
	private javax.swing.JTextField jTextField_CurrentSize;
	private javax.swing.JToolBar jToolBar1;
	private JPanel panel;
	private JButton btnArea;
	private JTextField textField;
	private JLabel lblB;
	private JTextField textField_1;
	private JLabel lblC;
	private JTextField textField_2;
	private JLabel lblD;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextPane textPane;
}
