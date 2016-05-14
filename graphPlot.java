/*
 * plot object, used to plot lines and dots
 * public methods:
 *  addLine, addDots: add a seriese of X,Y to be ploted as line or dots
 * TODO:
 *  add tags on axes
 *  add labels
 */

package tracking;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class graphPlot extends JPanel {
    private static final long serialVersionUID = 1L;
    class dataSet 
    {
        public double[] X; 
        public double[] Y;  
        public Color cl;
    };
    private ArrayList<dataSet> plotLines;
    private static ArrayList<dataSet> plotDots;
    private double[] xLim = {0.0, 0.0};
    private double[] yLim = {0.0, 0.0};
    
    public graphPlot() {
        plotLines = new ArrayList<dataSet>();
        plotDots = new ArrayList<dataSet>();
    }
    
    public void addLine(double[] x, double[] y){
        //add a seriese of X,Y to be ploted as line 
        dataSet newSet = buildSet(x,y); 
        newSet.cl = ind2color(plotLines.size());
        plotLines.add(newSet);
    }    

    public void addLine(pos[] posVec){
        //add a seriese of X,Y to be ploted as line 
        dataSet newSet = buildSet(posVec); 
        newSet.cl = ind2color(plotLines.size());
        plotLines.add(newSet);
    }
        
    public void addDots(double[] x, double[] y){
        //add a seriese of X,Y to be ploted as dots 
        dataSet newSet = buildSet(x,y);
        newSet.cl = ind2color(plotLines.size());
        plotDots.add(newSet);
    }
 
    public void addDots(pos[] posVec){
      //add a seriese of X,Y to be ploted as dots 
        dataSet newSet = buildSet(posVec); 
        newSet.cl = ind2color(plotDots.size());
        plotDots.add(newSet);
    }
    
    
    private dataSet buildSet(double[] x, double[] y){
        if (x.length != y.length)
            throw new IllegalArgumentException("x and y should have the same length");
        dataSet newSet = new dataSet();
        newSet.X = x;
        newSet.Y = y;   
        //update xLim & yLim
        for (int i = 0; i < x.length ; i++){
            if (xLim[0] > x[i]) xLim[0] = x[i];
            if (xLim[1] < x[i]) xLim[1] = x[i];
            if (yLim[0] > y[i]) yLim[0] = y[i];
            if (yLim[1] < y[i]) yLim[1] = y[i];
        }
        return newSet;
    }
    
    private dataSet buildSet(pos[] posVec){
        double X[] = new double[posVec.length];
        double Y[] = new double[posVec.length];
        for (int i = 0; i < posVec.length ; i++){
            X[i] = posVec[i].x();
            Y[i] = posVec[i].y();
        }
        return buildSet(X,Y);
    }
    
    private Color ind2color(int i){
        switch(i){
        case 0 : return Color.blue;
        case 1 : return Color.green;
        case 2 : return Color.red;
        case 3 : return Color.cyan;
        case 4 : return Color.magenta;        
        }
        return Color.orange;
    }
        
    final int PAD = 20;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        //set axes
        double scale, xZero,yZero;
        if ((xLim[1] - xLim[0]) > (yLim[1] - yLim[0]))  {
            scale  = (double)(w - 2*PAD)/(xLim[1] - xLim[0]); 
            xZero = (double)(-xLim[0]*scale);
            yZero = h/2 - (yLim[1] - yLim[0])/2*scale;
        }
        else {
            scale = (double)(h - 2*PAD)/(yLim[1] - yLim[0]);
            yZero = (double)(-yLim[0]*scale);
            xZero = w/2 - (xLim[1] - xLim[0])/2*scale;
        }

        // Draw X axis.
        g2.draw(new Line2D.Double(PAD, h-PAD-yZero, w-PAD, h-PAD-yZero));
        // Draw Y axis.
        g2.draw(new Line2D.Double(PAD+xZero, PAD, PAD+xZero, h-PAD));

        // Draw lines
        for (int i = 0; i < this.plotLines.size() ; i++){
            dataSet ds = this.plotLines.get(i);
            g2.setPaint(ds.cl.darker());
            for(int j = 0; j < ds.X.length-1; j++) {
                double x1 = PAD + xZero + ds.X[j] *scale;
                double y1 = h - PAD - yZero - scale*ds.Y[j];
                double x2 = PAD + xZero + ds.X[j+1]*scale;
                double y2 = h - PAD - yZero - scale*ds.Y[j+1]; 
                g2.draw(new Line2D.Double(x1, y1, x2, y2));
            }
        }
        
        // Draw data points.
        for (int i = 0; i<plotDots.size() ; i++){
            dataSet ds = plotDots.get(i);
            g2.setPaint(ds.cl.darker());
            for (int j = 0; j < ds.X.length; j++){
                double x = PAD + xZero + ds.X[j] *scale;
                double y = h - PAD - yZero - scale*ds.Y[j];
                g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
            }
        }
        
    }



    public void Draw() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}
