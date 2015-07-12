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
import java.util.List;


/**
 *
 * @author mariano
 */
public class GraphicDescriptor extends GraphicPanel {
    
	/** Version id for serialization. */
	private static final long serialVersionUID = 6832343098989019088L;

        private int selectedOption;
        private int AREA = 1;
        private int PERIM = 2;
        private int SYMM = 3;
        private int DIAM = 4;
        private int CIRC = 5;
        private int RECT = 6;
        private int BORDER = 7;

	public GraphicDescriptor(int selectedOption, List<StudyModel> patientStudies, String localPatientId) {
            
            // Generate data
            DataTable data = new DataTable(Integer.class, Double.class);
            int x = 0;
            this.selectedOption = selectedOption;
            for (StudyModel study : patientStudies) {
                if (selectedOption == AREA) {
                    String info = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "AREA");
                    data.add(x, Double.valueOf(info) );
                }
                if (selectedOption == PERIM) {
                    String info = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "PERIMETER");
                    data.add(x, Double.valueOf(info) );
                }
                if (selectedOption == SYMM) {
                    String info = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "SYMMETRY");
                    data.add(x, Double.valueOf(info) );
                }
                if (selectedOption == DIAM) {
                    String info = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "DIAMETER");
                    data.add(x, Double.valueOf(info) );
                }
                if (selectedOption == CIRC) {
                    String info = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "CIRC");
                    data.add(x, Double.valueOf(info) );
                }
                if (selectedOption == RECT) {
                    String info = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "RECT");
                    data.add(x, Double.valueOf(info) );
                }
                if (selectedOption == BORDER) {
                    String info = ApplicationContext.databaseRef.listDescriptor(localPatientId, study.getStudyUID(), "BORDER");
                    data.add(x, Double.valueOf(info) );
                }
                x++;
            }
            
            // Create and format upper plot
            XYPlot plotUpper = new XYPlot(data);
            plotUpper.setPointRenderer(data, null);
            LineRenderer lineUpper = new DefaultLineRenderer2D();
            lineUpper.setColor(Color.blue);
            plotUpper.setLineRenderer(data, lineUpper);
            LineRenderer areaUpper = new DefaultLineRenderer2D();
            areaUpper.setColor(Color.blue);
            plotUpper.setLineRenderer(data, areaUpper);
            plotUpper.setInsets(new Insets2D.Double(20.0, 50.0, 40.0, 20.0));
            plotUpper.getAxisRenderer(XYPlot.AXIS_X).setLabel("Orden de estudio");
            plotUpper.getAxisRenderer(XYPlot.AXIS_Y).setLabel("Medida en m.m.");
            plotUpper.getPlotArea().setBackground(Color.lightGray);
            
            DrawableContainer plots = new DrawableContainer(new TableLayout(1));
            plots.add(plotUpper);
            InteractivePanel panel = new InteractivePanel(plots);
            panel.setBackground(Color.gray);
            add(panel);
	}


	@Override
	public String getTitle() {
            if (selectedOption == AREA) {
		return "Evolucion Area";
            }
            if (selectedOption == PERIM) {
		return "Evolucion Perimetro";
            }
            if (selectedOption == SYMM) {
		return "Evolucion Simetria";
            }
            if (selectedOption == CIRC) {
		return "Evolucion Circularidad";
            }
            if (selectedOption == RECT) {
		return "Evolucion Rectangularidad";
            }
            if (selectedOption == BORDER) {
		return "Evolucion Irregularidad";
            }
            if (selectedOption == DIAM) {
		return "Evolucion Diametro";
            }
            return null;
	}

	@Override
	public String getDescription() {
		return "Evolucion Descriptores.";
	}

}

