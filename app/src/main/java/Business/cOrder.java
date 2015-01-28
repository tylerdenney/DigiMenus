package Business;

import android.util.Log;

import java.text.SimpleDateFormat;
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
    String datestr;
    List<iItem> orderitems = null;
    int partysize;
    int tablenum;
    double totalcost;

    public cOrder()
    {
        orderitems = new ArrayList<>();
        partysize = 0;
        tablenum = 0;
        totalcost = 0;
        datestr = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
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
        totalcost = 0;
        for (iItem i : orderitems)
        {
            totalcost += i.GetCost();
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
    public void SetPartySize(int size)
    {
        if(size > 0)
            partysize = size;
    }
    public void SetTableNumber(int num)
    {
        if(num > 0)
            tablenum = num;
    }
    public int GetTableNumber()
    {
        return tablenum;
    }
    public int GetPartySize()
    {
        return partysize;
    }
    public double GetTotalCost()
    {
        return totalcost;
    }

    public void Reset()
    {
        tablenum = 0;
        partysize = 0;
        orderitems.clear();
        totalcost = 0;
        datestr = "";
        
    }

    public void SendRequest()
    {

    }

    public String Submit()
    {
        if(partysize != 0 && tablenum != 0 && totalcost != 0) {
            String size = Integer.toString(partysize);
            String table = Integer.toString(tablenum);
            String cost = Double.toString(totalcost);
            String items = "";
            for (int i = 0; i < orderitems.size(); ++i) {
                //append string with period for last item.
                if (i == orderitems.size() - 1)
                    items += orderitems.get(i).GetName() + ".";
                    //append string with comma for all other items.
                else
                    items += orderitems.get(i).GetName() + ",";
            }
            String orderstring = "date " + datestr + ":size " + size + ":table " + table + ":cost " + cost + ":order " + items;
            return orderstring;
        }
        else
            return "Error";
    }

    public void ViewOrder()
    {

    }
    public List<iItem> GetItems()
    {
        return orderitems;
    }

}
