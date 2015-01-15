package com.goobers.digimenus;

import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Business.iItem;

/**
 * Created by tyler on 12/23/2014.
 */
public class cFoodMenuFragment extends ListFragment
{
    ListView menu_list = null;
    List<iItem> items;
    public cFoodMenuFragment()
    {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_food);

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(R.string.actionbar_food);
        menu_list = getListView();
        items = cMenu.GetFoodItems();
        List<String> foodnames = new ArrayList<String>();
        List<Double> foodcosts = new ArrayList<Double>();
        for(iItem i : items)
        {
            foodnames.add(i.GetName());
            foodcosts.add(i.GetCost());
        }
        ListAdapter list_adapter = new ArrayAdapter<String>(getActivity(),R.layout.simple_text_layout,foodnames);
        setListAdapter(list_adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3)
            {
                String itemname = (String) menu_list.getItemAtPosition(arg2);
                cMenu.SelectItemOnClick(itemname);
                Toast.makeText(getActivity(), itemname + " Added to Order.", Toast.LENGTH_LONG).show();
                return true;

            }});
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        cViewItemFragment frag = new cViewItemFragment();
        frag.SetItem(items.get(position));
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag).addToBackStack(null).commit();
    }
    //TODO Add long click to view item.
    //@Override
    //public void onListItemLongClick(ListView l, View v, int position, long id)
    //{

    //}

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        menu_list = null;
    }
}
