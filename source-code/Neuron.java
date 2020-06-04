import java.util.ArrayList;
import java.util.Random;

public class Neuron
{
    //features
    private int neuronId;
    private int numberOfWeightsFromNextNeuron;
    private double eta;
    private double alpha;
    private double outcome;
    private double gradient;
    private ArrayList <Synapse> weights = new ArrayList <Synapse> ( );
    
    public Neuron ( int neuronId, int numberOfWeightsFromNextNeuron, double eta, double alpha )
    {
        this.neuronId = neuronId;
        this.numberOfWeightsFromNextNeuron = numberOfWeightsFromNextNeuron;
        this.eta = eta;
        this.alpha = alpha;
        gradient = 0;
        
        for ( int wI = 0; wI < numberOfWeightsFromNextNeuron; wI ++ )
        {
            weights.add ( new Synapse ( ) );
            weights.get ( wI ).setWeight ( new Random ( ).nextDouble ( ) );
        }
    }
    
    
    //getters
    public double getOutcome ( )
    {
        return outcome;
    }
    public double getGradient ( )
    {
        return gradient;
    }
    public ArrayList <Synapse> getWeights ( )
    {
        return weights;
    }
    public double getActivation ( double value )
    {
        return Math.tanh ( value );
    }
    public double getPrimeActivation ( double value )
    {
        return 1 - Math.pow ( Math.tanh ( value ), 2 );
    }
    public double getDistributedWeightSigma ( Layer nextLayer )
    {
        double sigma = 0;
        
        for ( int nI = 0; nI < nextLayer.size ( ) - 1; nI ++ )
            sigma += getWeights ( ).get ( nI ).getWeight ( ) * nextLayer.get ( nI ).getGradient ( );
        
        return sigma;
    }

    
    //setters
    public void setGradient ( double value )
    {
        gradient = value;
    }
    public void setOutcome ( double value )
    {
        outcome = value;
    }
    public void calculateHiddenGradient ( Layer nextLayer )
    {
        double sigma = getDistributedWeightSigma ( nextLayer );
        
        setGradient ( getPrimeActivation ( getOutcome ( ) ) * sigma );
    }
    public void calculateOutcomeGradient ( int target )
    {
        double delta = target - getOutcome ( );
        
        setGradient ( getPrimeActivation ( getOutcome ( ) ) * delta );
    }
    
    
    public void doForwardPropagation ( Layer priorLayer )
    {
        double sigma = 0;
        
        for ( int pI = 0; pI < priorLayer.size ( ); pI ++ )
            sigma += priorLayer.get ( pI ).getWeights ( ).get ( neuronId ).getWeight ( ) * priorLayer.get ( pI ).getOutcome ( );
            
        setOutcome ( getActivation ( sigma ) );
    }
    public void updateWeights ( Layer priorLayer )
    {
        for ( int pI = 0; pI < priorLayer.size ( ); pI ++ )
        {
            double priorDeltaWeight = priorLayer.get ( pI ).getWeights ( ).get ( neuronId ).getDeltaWeight ( );
            
            double newDeltaWeight = ( eta * getGradient ( ) * priorLayer.get ( pI ).getOutcome ( ) ) + ( alpha * priorDeltaWeight );
            
            priorLayer.get ( pI ).getWeights ( ).get ( neuronId ).setWeight ( priorLayer.get ( pI ).getWeights ( ).get ( neuronId ).getWeight ( ) + newDeltaWeight );
            priorLayer.get ( pI ).getWeights ( ).get ( neuronId ).setDeltaWeight ( newDeltaWeight );
        }
    }

}
