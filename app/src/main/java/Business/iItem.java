package Business;

/**
 * Created by tyler on 12/12/2014.
 */
public abstract class iItem
{
    double cost;
    String name;
    String description;
    public iItem(double c, String n, String d)
    {
        cost = c;
        name = n;
        description = d;
    }
    public double GetCost()
    {
        return cost;
    }

    public String GetName()
    {
        return name;
    }

    public String GetDescription()
    {
        return description;
    }

}
