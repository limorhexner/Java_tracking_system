/*
 * position object
 * can be 2D or 3D
 * initiate with (X,Y) or (X,Y,Z), all double
 * 
 * TODO: compare method
 */
package tracking;

public class pos {
    
    private final double X,Y,Z;
    private final boolean is3d;
    public pos(double x, double y){
        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates must be finite");
        if (Double.isNaN(x) || Double.isNaN(y))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        Z = 0.0;
        if (x == 0.0) this.X = 0.0;  // convert -0.0 to +0.0
        else          this.X = x;
        if (y == 0.0) this.Y = 0.0;  // convert -0.0 to +0.0
        else          this.Y = y; 
        is3d = false;
    }
    public pos(double x, double y, double z){
        if (Double.isInfinite(x) || Double.isInfinite(y) || Double.isInfinite(z))
            throw new IllegalArgumentException("Coordinates must be finite");
        if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        if (x == 0.0) this.X = 0.0;  // convert -0.0 to +0.0
        else          this.X = x;
        if (y == 0.0) this.Y = 0.0;  // convert -0.0 to +0.0
        else          this.Y = y; 
        if (z == 0.0) this.Z = 0.0;  // convert -0.0 to +0.0
        else          this.Z = z; 
        is3d = true;
    }
    
    public double x(){
        return X;
    }
    public double y(){
        return Y;        
    }
    public double z() {
        if (is3d) return Z;
        else throw new IllegalArgumentException("2D point have no Z value");
    }
    
    public boolean is3D() {
        return this.is3d;
    }
    
    public String toString(){
        double Xr = ((double)Math.round(X*1000.0))/1000.0;
        double Yr = ((double)Math.round(Y*1000.0))/1000.0;
        String st = "X: " + Xr + ", Y: " + Yr;
        if (is3d) {
            double Zr = ((double)Math.round(Z*1000.0))/1000.0;
            st = st + ", Z: " + Zr;
        }
        return st;
            
        
    }
}
