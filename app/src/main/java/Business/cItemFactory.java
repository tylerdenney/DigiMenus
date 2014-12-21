package Business;

/**
 * Created by tyler on 12/12/2014.
 */

import android.content.Context;

import com.goobers.digimenus.cMenu;

import java.util.ArrayList;
import java.util.List;

import Persist.cDB;
public class cItemFactory
{
    final int MAX_ITEMS = 7;
    cDB myDB;
    public cItemFactory(Context c)
    {
        myDB = new cDB(c);
        myDB.InsertItems(c);
    }
    public List<iItem> PopulateItems(Context c)
    {
        List<iItem> allitems = myDB.ImportMenuItems(c);
        if(allitems.size() == cMenu.MAX_ITEMS)
            return allitems;
        else
            return null;
    }
    private iItem CreateFood()
    {
        return null;
    }
    private iItem CreateDrink()
    {
        return null;
    }


}
