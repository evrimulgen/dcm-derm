package org.weasis.dicom.viewer2d.dockable;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.noos.xing.mydoggy.ToolWindowAnchor;
import org.weasis.dicom.viewer2d.Messages;
import org.weasis.core.api.gui.util.JMVUtils;
import org.weasis.core.api.gui.util.JSliderW;
import org.weasis.core.api.gui.util.SliderChangeListener;
import org.weasis.core.api.gui.util.ToggleButtonListener;
import org.weasis.core.api.util.FontTools;
import org.weasis.core.ui.docking.PluginTool;
import org.weasis.core.ui.editor.image.ImageViewerEventManager;
import org.weasis.dicom.viewer2d.EventManager;
import org.weasis.core.api.gui.util.ActionState;
import org.weasis.core.api.gui.util.ActionW;
import org.weasis.core.api.gui.util.ComboItemListener;
import javax.swing.JLabel;


/**
 *
 * @author mariano
 */
public class DermAnalisysTool extends PluginTool {

    public static final String BUTTON_NAME = "Derm Analisys";

    public static final Font TITLE_FONT = FontTools.getFont12Bold();
    public static final Color TITLE_COLOR = Color.GRAY;

    private final Border spaceY = BorderFactory.createEmptyBorder(10, 3, 0, 3);

    public DermAnalisysTool(String pluginName) {
        super(BUTTON_NAME, pluginName, ToolWindowAnchor.RIGHT, PluginTool.TYPE.mainTool);
        setDockableWidth(290);
        jbInit();
    }
    
    @Override
    protected void changeToolWindowAnchor(ToolWindowAnchor anchor) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void jbInit() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(getWindowLevelPanel());
//        add(getTransformPanel());
//        add(getSlicePanel());
//        add(getResetPanel());

        final JPanel panel_1 = new JPanel();
        panel_1.setAlignmentY(Component.TOP_ALIGNMENT);
        panel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel_1.setLayout(new GridBagLayout());
        add(panel_1);
    }
    
    public JPanel getWindowLevelPanel() {

        final JPanel winLevelPanel = new JPanel();
        winLevelPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        winLevelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        winLevelPanel.setLayout(new BoxLayout(winLevelPanel, BoxLayout.Y_AXIS));
        
        winLevelPanel.setBorder(BorderFactory.createCompoundBorder(spaceY,
            new TitledBorder(null, Messages.getString("DermAnalisysTool.ia"), 
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, TITLE_FONT, TITLE_COLOR)));
        
        ActionState winAction = EventManager.getInstance().getAction(ActionW.WINDOW);
        
        if (winAction instanceof SliderChangeListener) {
            final JSliderW windowSlider = ((SliderChangeListener) winAction).createSlider(4, false);
            JMVUtils.setPreferredWidth(windowSlider, 100);
            winLevelPanel.add(windowSlider.getParent());
        }
        
        ActionState levelAction = EventManager.getInstance().getAction(ActionW.LEVEL);
        
        if (levelAction instanceof SliderChangeListener) {
            final JSliderW levelSlider = ((SliderChangeListener) levelAction).createSlider(4, false);
            levelSlider
                .setMajorTickSpacing((ImageViewerEventManager.LEVEL_LARGEST - ImageViewerEventManager.LEVEL_SMALLEST) / 4);
            JMVUtils.setPreferredWidth(levelSlider, 100);
            winLevelPanel.add(levelSlider.getParent());
        }

        ActionState presetAction = EventManager.getInstance().getAction(ActionW.PRESET);
        
        if (presetAction instanceof ComboItemListener) {
            final JPanel panel_3 = new JPanel();
            panel_3.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 3));
            final JLabel presetsLabel = new JLabel();
            panel_3.add(presetsLabel);
            presetsLabel.setText(Messages.getString("DermAnalisysTool.presets"));
            final JComboBox presetComboBox = ((ComboItemListener) presetAction).createCombo();
            presetComboBox.setMaximumRowCount(10);
            panel_3.add(presetComboBox);
            winLevelPanel.add(panel_3);
        }

        ActionState lutAction = EventManager.getInstance().getAction(ActionW.LUT);
        
        if (lutAction instanceof ComboItemListener) {
            final JPanel panel_4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 3));
            final JLabel lutLabel = new JLabel();
            lutLabel.setText(Messages.getString("DermAnalisysTool.lut")); 
            panel_4.add(lutLabel);
            final JComboBox lutcomboBox = ((ComboItemListener) lutAction).createCombo();
            panel_4.add(lutcomboBox);
            ActionState invlutAction = EventManager.getInstance().getAction(ActionW.INVERSELUT);
            if (invlutAction instanceof ToggleButtonListener) {
                panel_4.add(((ToggleButtonListener) invlutAction).createCheckBox(Messages
                    .getString("DermAnalisysTool.inverse")));
            }
            winLevelPanel.add(panel_4);
        }

        ActionState filterAction = EventManager.getInstance().getAction(ActionW.FILTER);
        
        if (filterAction instanceof ComboItemListener) {
            final JPanel panel_4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 3));
            final JLabel lutLabel = new JLabel();
            lutLabel.setText(Messages.getString("DermAnalisysTool.filter"));
            panel_4.add(lutLabel);
            final JComboBox filtercomboBox = ((ComboItemListener) filterAction).createCombo();
            panel_4.add(filtercomboBox);
            winLevelPanel.add(panel_4);
        }
        
                
        //ActionState analisysAction = EventManager.getInstance().getAction(ActionW.DERMA);
        final JPanel panel_4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 3));
        final JLabel lutLabel = new JLabel();
        lutLabel.setText(Messages.getString("DermAnalisysTool.test"));
        panel_4.add(lutLabel);
        final JButton testButton = new JButton();
        testButton.setText(Messages.getString("DermAnalisysTool.test"));
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("action test performed...");
            }
        });
        panel_4.add(testButton);
        winLevelPanel.add(panel_4);
        
        return winLevelPanel;
    }
}

