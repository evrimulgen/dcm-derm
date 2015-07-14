/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.form.graphic;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.DrawableContainer;
import de.erichseifert.gral.graphics.TableLayout;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;
import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.models.StudyModel;
import java.awt.Color;
import java.text.DateFormat;
import java.util.List;

/**
 *
 * @author mariano
 */
public class GraphicTDS extends GraphicPanel {
    
    private int selectedOption;
    private int ABCD = 1;
    private int TDS = 2;
        
    public GraphicTDS(int selectedOption, List<StudyModel> patientStudies, String localPatientId) {
        
        DataTable dataA = new DataTable(Integer.class, Double.class);
        DataTable dataB = new DataTable(Integer.class, Double.class);
        DataTable dataC = new DataTable(Integer.class, Double.class);
        DataTable dataD = new DataTable(Integer.class, Double.class);
        
        int x = 0;
        this.selectedOption = selectedOption;
        
        for (StudyModel study : patientStudies) {
            if (selectedOption == TDS) {
                String info = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "TDS");
                dataA.add(x, Double.valueOf(info));
            }
            if (selectedOption == ABCD) {
                String infoA = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "VALUEA");
                dataA.add(x, Double.valueOf(infoA));
                String infoB = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "VALUEB");
                dataB.add(x, Double.valueOf(infoB));
                String infoC = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "VALUEC");
                dataC.add(x, Double.valueOf(infoC));
                String infoD = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "VALUED");
                dataD.add(x, Double.valueOf(infoD));
            }
            x++;
        }
        
        // Create and format upper plot
        XYPlot plotA = new XYPlot(dataA);
	plotA.setPointRenderer(dataA, null);
	LineRenderer lineA = new DefaultLineRenderer2D();
	lineA.setColor(Color.blue);
	plotA.setLineRenderer(dataA, lineA);
	plotA.setInsets(new Insets2D.Double(20.0, 50.0, 40.0, 20.0));
        plotA.getAxisRenderer(XYPlot.AXIS_X).setLabel("Orden de estudio");
        plotA.getAxisRenderer(XYPlot.AXIS_Y).setLabel("Valor de TDS");
        plotA.getPlotArea().setBackground(Color.lightGray);
        //plotA.getAxisRenderer(XYPlot.AXIS_X).setTickLabelFormat(DateFormat.getDateInstance());
        
        DrawableContainer plots = new DrawableContainer(new TableLayout(1));
	plots.add(plotA);
        
        if (selectedOption == ABCD) {
            
            plotA.getAxisRenderer(XYPlot.AXIS_Y).setLabel("Valor de A");
            
            //valor B
            XYPlot plotB = new XYPlot(dataB);
            plotB.setPointRenderer(dataB, null);
            LineRenderer lineB = new DefaultLineRenderer2D();
            lineB.setColor(Color.blue);
            plotB.setLineRenderer(dataB, lineB);
            plotB.setInsets(new Insets2D.Double(20.0, 50.0, 40.0, 20.0));
            plotB.getAxisRenderer(XYPlot.AXIS_X).setLabel("Orden de estudio");
            plotB.getAxisRenderer(XYPlot.AXIS_Y).setLabel("Valor de B");
            plotB.getPlotArea().setBackground(Color.lightGray);
            plots.add(plotB);

            //valor C
            XYPlot plotC = new XYPlot(dataC);
            plotC.setPointRenderer(dataB, null);
            LineRenderer lineC = new DefaultLineRenderer2D();
            lineC.setColor(Color.blue);
            plotC.setLineRenderer(dataC, lineC);
            plotC.setInsets(new Insets2D.Double(20.0, 50.0, 40.0, 20.0));
            plotC.getAxisRenderer(XYPlot.AXIS_X).setLabel("Orden de estudio");
            plotC.getAxisRenderer(XYPlot.AXIS_Y).setLabel("valor de C");
            plotC.getPlotArea().setBackground(Color.lightGray);
            plots.add(plotC);
            
            //valor D
            XYPlot plotD = new XYPlot(dataD);
            plotD.setPointRenderer(dataD, null);
            LineRenderer lineD = new DefaultLineRenderer2D();
            lineD.setColor(Color.blue);
            plotD.setLineRenderer(dataD, lineD);
            plotD.setInsets(new Insets2D.Double(20.0, 50.0, 40.0, 20.0));
            plotD.getAxisRenderer(XYPlot.AXIS_X).setLabel("Orden de estudio");
            plotD.getAxisRenderer(XYPlot.AXIS_Y).setLabel("Valor de D");
            plotD.getPlotArea().setBackground(Color.lightGray);
            plots.add(plotD);
                        
//            plotA.getNavigator().connect(plotB.getNavigator());
//            plotB.getNavigator().connect(plotC.getNavigator());
//            plotC.getNavigator().connect(plotD.getNavigator());
        }
        
        InteractivePanel panel = new InteractivePanel(plots);
        add(panel);
    }
        
    @Override
    public String getTitle() {
        if (selectedOption == ABCD) {
            return "Evolucion ABCD";
        }
        if (selectedOption == TDS) {
            return "Evolucion Total Dermoscopy Score";
        }
        return null;
    }

    @Override
    public String getDescription() {
        return "Evolucion ABCD.";
    }
    
}
