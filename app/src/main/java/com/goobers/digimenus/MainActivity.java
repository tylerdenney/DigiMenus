package com.goobers.digimenus;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity implements PopupFragment.EditDialogListener
{
    private String[] drawer_items;
    private DrawerLayout drawer_layout;
    private ListView drawer_listview;
    private ActionBarDrawerToggle drawer_toggle;


    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    //1 for editing partysize, 2 for editing tablenum
    private int editing = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_listview = (ListView) findViewById(R.id.left_drawer);
        drawer_items = getResources().getStringArray(R.array.nav_items);


        DrawerItem[] draweritems = new DrawerItem[3];
        draweritems[0] = new DrawerItem(R.drawable.foodicon, "Food");
        draweritems[1] = new DrawerItem(R.drawable.drinkicon, "Drink");
        draweritems[2] = new DrawerItem(R.drawable.placeholdericon, "Order");

        DrawerCustomAdapter adapter = new DrawerCustomAdapter(this, R.layout.drawer_item_layout,draweritems);
        drawer_listview.setAdapter(adapter);

        //drawer_listview.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_text_layout, drawer_items));
        drawer_listview.setOnItemClickListener(new DrawerOnClickListener());
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.action_bar_container, new cMenu())
                    .commit();
        }

        //Testing
        mTitle = mDrawerTitle = getTitle();
        drawer_layout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer_toggle = new ActionBarDrawerToggle(this, drawer_layout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close)
        {
          public void onDrawerClosed(View view)
          {
              getSupportActionBar().setTitle(mTitle);
              invalidateOptionsMenu();
          }
            public void onDrawerOpened(View drawerview)
            {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        drawer_layout.setDrawerListener(drawer_toggle);



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawer_toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawer_toggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void setTitle(CharSequence title)
    {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = drawer_layout.isDrawerOpen(drawer_listview);
        //menu.findItem(R.id.action_bar).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

//end testing
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        if (drawer_toggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch(item.getItemId())
        {
            case R.id.action_party:
                editing = 1;
                SetPartySizeOnClick();
                return true;
            case R.id.action_table:
                editing = 2;
                SetTableNumOnClick();
                return true;
            case R.id.action_reset:
                cMenu.ResetonClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void SetTableNumOnClick()
    {
        PopupFragment box = new PopupFragment();
        Bundle args = new Bundle();
        args.putString("title", "Table Number");
        args.putString("message", "Please Enter the table number your party is seated at.");
        box.setArguments(args);
        box.show(getSupportFragmentManager(), "dialog");
    }
    public void SetPartySizeOnClick()
    {
        PopupFragment box = new PopupFragment();
        Bundle args = new Bundle();
        args.putString("title", "Party Size ");
        args.putString("message", "Please enter your party's size.");
        box.setArguments(args);
        box.show(getSupportFragmentManager(), "dialog");
    }

    public void onFinishEditDialog(int input)
    {
        if(editing == 1)
        cMenu.SetPartySizeOnClick(input);
        if(editing == 2)
            cMenu.SetTableNumOnClick(input);
    }



    //subclass for the navigation drawer's onclick event.
    public class DrawerOnClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            SelectItem(position);
        }
        private void SelectItem(int position)
        {
            Fragment frag = null;
            switch (position)
            {
                case 0:
                    frag = new cFoodMenuFragment();
                    break;
                case 1:
                    frag = new cBeverageMenuFragment();
                    break;
                case 2:
                    frag = new cOrderFragment();
                    break;
                default:
                    break;
            }
            if(frag != null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag).commit();
                drawer_listview.setItemChecked(position, true);
                drawer_listview.setSelection(position);
                drawer_layout.closeDrawer(drawer_listview);
                getSupportActionBar().setTitle(drawer_items[position]);
            }
        }
    }

}
