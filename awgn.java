/*
 * AWGN noise model
 * initiate with (mu,sigma) or (muX,sigmaX,muY,sigmaY) or (muX,sigmaX,muY,sigmaY,muZ,sigmaZ) * 
 */

package tracking;
import java.util.Random;

public class awgn extends noiseModel{
    private double muX, muY, muZ, sigmaX, sigmaY, sigmaZ;
    private boolean is3d;
    Random rnd;
    
    public awgn(double mu, double sigma){
        double[] params = {mu, sigma};
        setParams(params);
    }
    
    public awgn(double muX, double sigmaX, double muY, double sigmaY){
        double[] params = {muX, sigmaX, muY, sigmaY};
        setParams(params);
    }
    
    public awgn(double muX, double sigmaX, double muY, double sigmaY, double muZ, double sigmaZ){
        double[] params = {muX, sigmaX, muY, sigmaY, muZ, sigmaZ};
        setParams(params);
    }
    
    public awgn(double[] params){
        setParams(params);
    } 
    

    private void setParams(double[] params){
        if (params.length ==2){ //mu&sigma
            muX = muY = muZ = params[0];
            sigmaX = sigmaY = sigmaZ = params[1];
            is3d = true;
        } else if (params.length ==4){//muX,sigmaX,muY,sigmaY
            muX = params[0]; sigmaX = params[1];
            muY = params[2]; sigmaY = params[3];
            is3d = false;            
        } else if (params.length == 6){
            muX = params[0]; sigmaX = params[1];
            muY = params[2]; sigmaY = params[3];
            muZ = params[4]; sigmaZ = params[5];
            is3d = true; 
        } else{
            throw new IllegalArgumentException("awgn needs 2,4 or 6 paramenere");
        }
        rnd = new Random();
    }
    

    public pos getMeasure(pos truePos) {
        double X,Y,Z;
        X = truePos.x() + sigmaX*rnd.nextGaussian() + muX;
        Y = truePos.y() + sigmaY*rnd.nextGaussian() + muY;
        if (truePos.is3D()) {
            if (!is3d){
                throw new IllegalArgumentException("point is 3d but object is only 2d");
            }
            Z = truePos.z() + sigmaZ*rnd.nextGaussian() + muZ;
            return new pos(X,Y,Z);
        } else 
            return new pos(X,Y);
    }

}

