import java.util.ArrayList;

public class Architecture extends ArrayList <Integer>
{
    //features
    private String description = "";
    
    Architecture ( String description ) 
    {
        buildArchitecture ( description );
    }
    
    public void buildArchitecture ( String description )
    {
        String [ ] components = description.split ( "," );
        
        for ( int cI = 0; cI < components.length; cI ++ )
            add ( Integer.parseInt ( components [ cI ] ) );
    }
}