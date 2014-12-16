package Business;

/**
 * Created by tyler on 12/12/2014.
 */
public class cDrinkItem extends iItem
{
    String size;

    public cDrinkItem(Double c, String n, String d)
    {
        super(c, n, d);
    }
    public void SetSize(String s)
    {
        size = s;
    }
    public String GetSize()
    {
        return size;
    }
}
