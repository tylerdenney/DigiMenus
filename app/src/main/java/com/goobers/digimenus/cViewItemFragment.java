package com.goobers.digimenus;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import Business.iItem;

/**
 * Created by tyler on 1/13/2015.
 */
public class cViewItemFragment extends Fragment implements View.OnClickListener
{
    iItem currentitem;
    private TextView txtname;
    private TextView txtdesc;
    private TextView txtprice;
    private ImageView pic;
    private Button addbutton;


    public cViewItemFragment() {

    }

    public void SetItem(iItem i) {
        currentitem = i;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_item, container, false);
        addbutton = (Button) rootView.findViewById(R.id.addbutton);
        addbutton.setOnClickListener(this);
        return rootView;
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.addbutton:
                cMenu.SelectItemOnClick(currentitem.GetName());
                Toast.makeText(getActivity(), currentitem.GetName() + " Added to Order.", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        txtname = (TextView)getActivity().findViewById(R.id.item_name);
        txtdesc = (TextView)getActivity().findViewById(R.id.item_description);
        txtprice = (TextView)getActivity().findViewById(R.id.item_cost);
        txtname.setText(currentitem.GetName());
        txtdesc.setText(currentitem.GetDescription());
        txtprice.setText(String.valueOf(currentitem.GetCost()));
        pic = (ImageView)getActivity().findViewById(R.id.item_pic);
        pic.setImageResource(getId(currentitem.GetName(),R.drawable.class));
       // pic.setImageDrawable(GetPictureResource(currentitem.GetName()));
    }

    private Drawable GetPictureResource(String resname)
    {
        resname = resname.replace(" ", "");
        resname = resname.toLowerCase();
        resname += ".jpg";
        resname = "party_icon.png";
        try {
            int resid = Resources.getSystem().getIdentifier(resname, "drawable",getActivity().getPackageName());
            return Resources.getSystem().getDrawable(resid);
        }
        catch(Exception e)
        {
            return null;
        }

    }
    public static int getId(String resourceName, Class<?> c)
    {
        resourceName = resourceName.replace(" ", "");
        resourceName = resourceName.toLowerCase();
        try
        {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        }
        catch (Exception e)
        {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    @Override
    public void onDestroy()

    {
        super.onDestroy();
        txtname = null;
        txtdesc = null;
        pic = null;
    }

}
