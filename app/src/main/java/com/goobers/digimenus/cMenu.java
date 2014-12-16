package com.goobers.digimenus;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import Business.cItemFactory;
import Business.iItem;
import Business.cOrder;
/**
 * Created by tyler on 12/12/2014.
 */
public class cMenu extends Fragment
{
    cItemFactory itemfactory;
    List<iItem> items;
    cOrder order;
    public cMenu()
    {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        return rootView;
    }
    @Override
    public void onAttach(Activity activity)
    {
        //send context in onAttach, because if send before it could send null.
        super.onAttach(activity);
        itemfactory = new cItemFactory(activity);

    }



    public void DisplayHomeScreen()
    {

    }
    public void PopulateItems()
    {

    }

    public void ListenToHost()
    {

    }
}

