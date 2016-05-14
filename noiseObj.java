/*
 * oiseObj extends noiseModel to a specified noise type
 * for now support only Additive White Gaussian Noise
 * initiate with type String and variable number of input arguments according to pos and noise type 
 * 
 */
package tracking;

public class noiseObj extends noiseModel {
    private noiseModel nm;    
    public noiseObj(String type, double...params){
        switch (type){
        case "AWGN" : nm = new awgn(params);
            break;
        default: throw new IllegalArgumentException("wrong noise type");
        }
    }
        
    public pos getMeasure(pos truePos) {
        return nm.getMeasure(truePos);
    }

    
}

