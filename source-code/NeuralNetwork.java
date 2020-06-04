public class NeuralNetwork
{
    //features
    private double eta = 0.2;
    private double alpha = 0.5;
    private Architecture architecture = new Architecture ( "2,2,1" );
    private Layers layers = new Layers ( );
    
    public NeuralNetwork ( )
    {
        
        for ( int lsI = 0; lsI < architecture.size ( ); lsI ++ )
        {
            layers.add ( new Layer ( ) );
            
            for ( int lI = 0; lI <= architecture.get ( lsI ); lI ++ )
            {
                int numberOfWeightsFromNextNeuron = ( lsI + 1 < architecture.size ( ) ? architecture.get ( lsI + 1 ): 0 );
                
                layers.get ( lsI ).add ( new Neuron ( lI, numberOfWeightsFromNextNeuron, eta, alpha ) );
                
                //set bias neuron
                layers.get ( lsI ).get ( layers.get ( lsI ).size ( ) - 1 ).setOutcome ( 1.0 );
            }
        }
    }
    
    public void doForwardPropagation ( int inputs [ ] )
    {
        for ( int iI = 0; iI < inputs.length; iI ++ )
            layers.get ( 0 ).get ( iI ).setOutcome ( inputs [ iI ] );
            
        for ( int lsI = 1; lsI < layers.size ( ); lsI ++ )
        {
            Layer priorLayer = layers.get ( lsI - 1 );
            
            for ( int lI = 0; lI < architecture.get ( lsI ); lI ++ )
                layers.get ( lsI ).get ( lI ).doForwardPropagation ( priorLayer );
        }
    }
    
    public void doBackwardPropagation ( int target ) 
    {
        Neuron outcomeNeuron = layers.get ( layers.size ( ) - 1 ).get ( 0 );
        
        outcomeNeuron.calculateOutcomeGradient ( target );
        
        for ( int lI = architecture.size ( ) - 2; lI > 0; lI -- )
        {
            Layer nextLayer = layers.get ( lI + 1 );
            Layer currentLayer = layers.get ( lI );
            
            for ( int cI = 0; cI < currentLayer.size ( ); cI ++ )
                currentLayer.get ( cI ).calculateHiddenGradient ( nextLayer );
        }
       
        for ( int lI = architecture.size ( ) - 1; lI > 0; lI -- )
        {
            Layer priorLayer = layers.get ( lI - 1 );
            Layer currentLayer = layers.get ( lI );
            
            for ( int cI = 0; cI < currentLayer.size ( ) - 1; cI ++ )
                currentLayer.get ( cI ).updateWeights ( priorLayer );
        }
    }
    
    public double getOutcome ( )
    {
        return layers.get ( layers.size ( ) - 1 ).get ( 0 ).getOutcome ( );
    }
}
