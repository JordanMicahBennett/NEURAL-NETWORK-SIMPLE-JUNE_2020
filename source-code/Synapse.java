public class Synapse
{
    //features
    private double weight;
    private double deltaWeight;
    
    public Synapse ( )
    {
    }
    
    
    public double getWeight ( )
    {
        return weight;
    }
    public double getDeltaWeight ( )
    {
        return deltaWeight;
    }
    public void setWeight ( double value )
    {
        weight = value;
    }
    public void setDeltaWeight ( double value )
    {
        deltaWeight = value;
    }
}
