package Business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyler on 12/12/2014.
 */
public class cFoodItem extends iItem
{
    List<String> commonallergies;
    String portionsize;

    public cFoodItem(Double c, String n, String d, List<String> allergies)
    {
        super(c, n, d);
        commonallergies = new ArrayList<String>();
        commonallergies.addAll(allergies);
    }
    public void SetPortionSize(String p)
    {
        portionsize = p;
    }
    public String GetPortionSize()
    {
        return portionsize;
    }
}
