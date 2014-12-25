package com.goobers.digimenus;

import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Business.iItem;

/**
 * Created by tyler on 12/23/2014.
 */
public class cFoodMenuFragment extends ListFragment
{
    ListView menu_list = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        menu_list = getListView();
        List<iItem> items = cMenu.GetFoodItems();
        List<String> foodnames = new ArrayList<String>();
        List<Double> foodcosts = new ArrayList<Double>();
        for(iItem i : items)
        {
            foodnames.add(i.GetName());
            foodcosts.add(i.GetCost());
        }
        ListAdapter list_adapter = new ArrayAdapter<String>(getActivity(),R.layout.simple_text_layout,foodnames);
        setListAdapter(list_adapter);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        menu_list = null;
    }
}
