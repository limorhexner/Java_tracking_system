/*
 * general client program for debug
 * 
 */
package tracking;

public class client {

    public static void main(String[] args) {
      
        double[] X = new double[10];
        double[] Y = new double[10];
        pos[] posVec = new pos[10];
        for (int i = 0; i<10 ; i++){
            double x = i;
            double y = i*1.5 - 5;
            posVec[i] = new pos(x,y);
            X[i] = x;
            Y[i] = 20-y;
        }
        //add noise
        noiseObj no1 = new noiseObj("AWGN", 0, 0.5);
        noiseObj no2 = new noiseObj("AWGN", 1, 0.0 , 0, 1);
        pos[] posVecNoise1 = no1.getMultyMeasures(posVec);
        pos[] posVecNoise2 = no2.getMultyMeasures(posVec);
        
        graphPlot g = new graphPlot();
        g.addLine(posVec);
        g.addLine(X,Y);
        g.addDots(posVec);
        g.addDots(posVecNoise1);
        g.addDots(posVecNoise2);
        
        g.Draw();
        
        
    }

}
