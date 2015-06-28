package in.raster.mayam.process;

import ij.*;
import ij.measure.*;
import ij.plugin.*;
import ij.plugin.filter.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
* "AnalyzeParticles" for each slice of stack
*    record:
*        items checked in Set Measurements
*        slice# in "Slice" column
*         topLeft x,y and ncoords to allow re_autoOutline of particles
*
*/
public class CustomParticleAnalyzerPlugin implements PlugIn {

        ResultsTable rt = null;
    
	public void run(String arg) {
		if (IJ.versionLessThan("1.26i"))
			return;
		ImagePlus imp = IJ.getImage();
		analyzeStackParticles(arg, imp);
	}

	public void analyzeStackParticles(String args, ImagePlus imp) {
		if (imp.getBitDepth()==24)
			{IJ.error("Grayscale image required"); return;}
                rt = new ResultsTable();
		CustomParticleAnalyzer pa = new CustomParticleAnalyzer(512,Analyzer.getMeasurements()
                        |Measurements.AREA|Measurements.PERIMETER|Measurements.CENTROID|Measurements.ELLIPSE
                        |Measurements.CIRCULARITY,rt,0,Double.POSITIVE_INFINITY);
		int flags = 415; 
		if (flags==PlugInFilter.DONE)
			return;
		if ((flags&PlugInFilter.DOES_STACKS)!=0) {
			for (int i=1; i<=imp.getStackSize(); i++) {
				imp.setSlice(i);
				pa.analyze(imp);
			}
		} else {
			pa.analyze(imp);
                }
	}
        
        public Double getArea() {
            return rt.getValueAsDouble(ResultsTable.AREA, 0);
        }
        
        public Double getPerimeter() {
            return new BigDecimal(rt.getValueAsDouble(ResultsTable.PERIMETER,0))
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        
        public Double getFeretDiam() {
            return  new BigDecimal(rt.getValueAsDouble(ResultsTable.FERET, 0))
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        
        public Point getCentroid() {
            return new Point (Double.valueOf(rt.getValueAsDouble(ResultsTable.X_CENTROID, 0)).intValue(),
                Double.valueOf(rt.getValueAsDouble(ResultsTable.Y_CENTROID, 0)).intValue());
        }
        
        public Double getMajor() {
            return rt.getValueAsDouble(ResultsTable.MAJOR, 0);
        }
        
        public Double getMinor() {
            return rt.getValueAsDouble(ResultsTable.MINOR, 0);
        }
        
        public Double getAngle() {
            return rt.getValueAsDouble(ResultsTable.ANGLE, 0);
        }
        
        public Double getCircularity() {
            return new BigDecimal(rt.getValueAsDouble(ResultsTable.CIRCULARITY, 0))
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        
}

class CustomParticleAnalyzer extends ParticleAnalyzer {
	
        public CustomParticleAnalyzer(int opt, int ms, ResultsTable rt, double minsize, double maxsize) {
            super(opt, ms, rt, minsize, maxsize);
        }
        
	// Overrides method with the same in AnalyzeParticles that's called once for each particle
	protected void saveResults(ImageStatistics stats, Roi roi) {
		int coordinates = ((PolygonRoi)roi).getNCoordinates();
		Rectangle r = roi.getBoundingRect();
		int x = r.x+((PolygonRoi)roi).getXCoordinates()[coordinates-1];
		int y = r.y+((PolygonRoi)roi).getYCoordinates()[coordinates-1];
		analyzer.saveResults(stats, roi);
		rt.addValue("Slice", imp.getCurrentSlice());
		rt.addValue("Xtopl", x);
		rt.addValue("Ytopl", y);
		rt.addValue("nCoord", coordinates);
		//if (showResults)
		//	analyzer.displayResults();
	}
	    
}

