package Business;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by tyler on 12/12/2014.
 */
public class cOrder
{
    final int MAX_ITEMS = 20;
    Date date;
    List<iItem> orderitems = null;
    int partysize;
    int tablenum;
    double totalcost;

    public cOrder()
    {
        orderitems = new ArrayList<>();
    }

    public void AddItem(iItem item)
    {
        int oldsize = orderitems.size();
        if(oldsize != MAX_ITEMS)
        {
            orderitems.add(item);
            if(oldsize == orderitems.size() - 1) //item successfully added.
                CalculateCost();
        }
    }

    private void CalculateCost()
    {
        if(totalcost >= 0)
        {
            for (iItem i : orderitems)
            {
                totalcost += i.GetCost();
            }
        }
        if(totalcost >= 0)
            Log.d("e", "Cost updated successfully");
    }
    public boolean Cancel()
    {
        return false;
    }

    public void RemoveItem(String itemname)
    {
        boolean cont = true;
        int oldsize = orderitems.size();
        if(orderitems.size() != 0) {
            for (int i = 0; i < orderitems.size() && cont == true; ++i)
            {
                if(orderitems.get(i).GetName().equals(itemname))
                {
                    cont = false;
                    orderitems.remove(i);
                    CalculateCost();
                }


            }
        }
        if(!(orderitems.size() == oldsize - 1))
            Log.d("e", "Item not removed correctly");
    }

    public void Reset()
    {

    }

    public void SendReqeust()
    {

    }

    public void Submit()
    {

    }

    public void ViewOrder()
    {

    }
    public List<iItem> GetItems()
    {
        return orderitems;
    }

}
