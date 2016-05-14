package tracking;
/*
 * general noise model, noise type not specified
 * methods:
 *    abstract public pos getMeasure(pos truePos);  generate noised position measures
 *    public pos[] getMultyMeasures(pos[] truePos)  generate a sequence of noised measures
 */
public abstract class noiseModel {
    abstract public pos getMeasure(pos truePos);
    
    public pos[] getMultyMeasures(pos[] truePos) {
        pos[] out = new pos[truePos.length];
        int i = 0;
        for (pos p : truePos){
            out[i++] = getMeasure(p);
        }
        return out;
    }
}
