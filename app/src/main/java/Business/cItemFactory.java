package Business;

/**
 * Created by tyler on 12/12/2014.
 */

import android.content.Context;

import java.util.List;

import Persist.cDB;
public class cItemFactory
{
    cDB myDB;
    public cItemFactory(Context c)
    {
        myDB = new cDB(c);
        myDB.InsertItems(c);
    }
    private iItem CreateFood()
    {
        return null;
    }
    private iItem CreateDrink()
    {
        return null;
    }
    public List<iItem> PopulateItems()
    {
        return null;
    }


}
