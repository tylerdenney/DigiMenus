package com.goobers.digimenus;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import Business.cItemFactory;
import Business.iItem;
import Business.cOrder;
/**
 * Created by tyler on 12/12/2014.
 */
public class cMenu extends Fragment
{
    public final static int MAX_ITEMS = 7;

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
        items = new ArrayList<iItem>();
        PopulateItems();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode,data);

    }



    public void DisplayHomeScreen()
    {

    }
    public void PopulateItems()
    {
        if(items.size() == 0)
        {
            items = itemfactory.PopulateItems(getActivity());
        }
        if(items.size() == MAX_ITEMS)
        {

        }
        else
        {
            PopupFragment box = new PopupFragment();
            Bundle args = new Bundle();
            args.putString("title", "Error ");
            args.putString("message", "Populating Menu from DB Failed");
            box.setArguments(args);
            box.setTargetFragment(this, 0); //return to this fragment.
            box.show(getFragmentManager(), "dialog");
        }
    }

    public void ListenToHost()
    {

    }
}

