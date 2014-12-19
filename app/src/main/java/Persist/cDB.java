package Persist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Business.cFoodItem;
import Business.iItem;

/**
 * Created by tyler on 12/12/2014.
 */
public class cDB extends SQLiteOpenHelper
{
    private final static String CreateTableITEMS = "CREATE TABLE Items ( " +
                                                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                    "Name TEXT, " +
                                                    "Description TEXT, " +
                                                    "Cost REAL, " +
                                                    "FoodItem INTEGER );";

    private static final String CreateTableALLERGIES = "CREATE TABLE Allergies ( " +
                                                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                            "Ingredient TEXT )";

    private static final String CreateTableITEMS_ALLERGIES = "CREATE TABLE Items_Allergies ( " +
                                                                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                        "ItemID INTEGER, " +
                                                                        "AllergyID INTEGER, " +
                                                                        "FOREIGN KEY(ItemID) REFERENCES Items(id), " +
                                                                        "FOREIGN KEY(AllergyID)REFERENCES Allergies(id))";



    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MenuDB.db";
    //Items
    private static final String TABLE_ITEMS = "Items";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_DESC = "Description";
    private static final String KEY_COST = "Cost";
    private static final String KEY_FOODITEM = "FoodItem";
    //Allergies
    private static final String TABLE_ALLERGIES = "Allergies";
    private static final String KEY_INGREDIENT = "Ingredient";
    //Items_Allergies
    private static final String TABLE_ITEMS_ALLERGIES = "Items_Allergies";
    private static final String KEY_ALLERGYID = "AllergyID";
    private static final String KEY_ITEMID = "ItemID";

    public cDB(Context c)
    {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public List<iItem> ImportMenuItems(Context c)
    {
        String[] columns = {KEY_NAME, KEY_DESC, KEY_COST};
        String selectqueryfoodallergies = "select Name, Ingredient FROM Items JOIN Allergies on items.id = items_Allergies.itemID join items_Allergies on allergies.id = items_Allergies.allergyID where items.foodItem = 1;";
        String selectqueryfooddetails = "SELECT Description, Cost FROM Items WHERE Name = ";
        String selectquerydrinks = "SELECT * FROM Items WHERE FoodItem = 0";
        List<iItem> items = new ArrayList<iItem>();
        //List<iItem> drinkitems = new ArrayList<iItem>();
        File database = c.getDatabasePath("MenuDB.db");

        if(database.exists())
        {
            SQLiteDatabase db = this.getReadableDatabase();

            //Find all food items.
            Cursor foodcursor = db.rawQuery(selectqueryfoodallergies, null);
            if(foodcursor.moveToFirst())
            {
                while(foodcursor.isAfterLast() == false)
                {
                    List<String> allergies = new ArrayList<String>();
                    String name = foodcursor.getString(foodcursor.getColumnIndex(KEY_NAME));
                    //while cursor is on same food item, fill allergies list with ingredients specific to the current food
                    while (foodcursor.getString(foodcursor.getColumnIndex(KEY_NAME)).equals(name) && foodcursor.isAfterLast() == false)
                    {
                        allergies.add(foodcursor.getString(foodcursor.getColumnIndex(KEY_INGREDIENT)));
                        foodcursor.moveToNext();
                    }
                    //Values in db arent being edited, so sql injection risk is low?
                    //  Cursor detailscursor = db.rawQuery(selectqueryfooddetails + name, null);
                    Cursor detailscursor = db.query(TABLE_ITEMS, columns, "Name=?", new String[]{name}, null, null, null);
                    if (detailscursor.moveToFirst())
                    {
                        String desc = detailscursor.getString(detailscursor.getColumnIndex(KEY_DESC));
                        Double cost = detailscursor.getDouble(detailscursor.getColumnIndex(KEY_COST));
                        cFoodItem newitem = new cFoodItem(cost, name, desc, allergies);
                        items.add(newitem);
                    }
                }
            }
            //Find all drink items.
            Cursor drinkcursor = db.rawQuery(selectquerydrinks, null);
            //while(drinkcursor)
        }


        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CreateTableITEMS);
        db.execSQL(CreateTableALLERGIES);
        db.execSQL(CreateTableITEMS_ALLERGIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Items");
        db.execSQL("DROP TABLE IF EXISTS Allergies");
        db.execSQL("DROP TABLE IF EXISTS Items_Allergies");
        this.onCreate(db);
    }
    //This will fill the database if it does not exist.
    //Otherwise it will do nothing.
    public void InsertItems(Context c)
    {
        File database = c.getDatabasePath("MenuDB.db");
        if(!database.exists()) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues c1 = new ContentValues();
            c1.put(KEY_NAME, "Turkey Club");
            c1.put(KEY_DESC, "Turkey Sandwich on wheat bread with swiss cheese.");
            c1.put(KEY_COST, 6.99);
            c1.put(KEY_FOODITEM, 1);
            db.insert(TABLE_ITEMS, null, c1);

            ContentValues c2 = new ContentValues();
            c2.put(KEY_NAME, "Fish Tacos");
            c2.put(KEY_DESC, "Two Ahi Tuna tacos on crispy corn tortillas.");
            c2.put(KEY_COST, 7.99);
            c2.put(KEY_FOODITEM, 1);
            db.insert(TABLE_ITEMS, null, c2);

            ContentValues c3 = new ContentValues();
            c3.put(KEY_NAME, "Chicken Teriyaki");
            c3.put(KEY_DESC, "Chicken teriyaki. Enough said.");
            c3.put(KEY_COST, 7.99);
            c3.put(KEY_FOODITEM, 1);
            db.insert(TABLE_ITEMS, null, c3);

            ContentValues c4 = new ContentValues();
            c4.put(KEY_NAME, "Raspberry Lemonade");
            c4.put(KEY_DESC, "Lemonade with whole raspberries.");
            c4.put(KEY_COST, 2.99);
            c4.put(KEY_FOODITEM, 0);
            db.insert(TABLE_ITEMS, null, c4);

            ContentValues c5 = new ContentValues();
            c5.put(KEY_NAME, "Sprite");
            c5.put(KEY_DESC, "Refreshing soft drink Sprite..");
            c5.put(KEY_COST, 1.50);
            c5.put(KEY_FOODITEM, 0);
            db.insert(TABLE_ITEMS, null, c5);

            ContentValues c6 = new ContentValues();
            c6.put(KEY_NAME, "Coke");
            c6.put(KEY_DESC, "Refreshing soft drink Coke.");
            c6.put(KEY_COST, 1.50);
            c6.put(KEY_FOODITEM, 0);
            db.insert(TABLE_ITEMS, null, c6);

            ContentValues c7 = new ContentValues();
            c7.put(KEY_NAME, "SeaFood Supreme");
            c7.put(KEY_DESC, "Shrimp, salmon and pasta.");
            c7.put(KEY_COST, 10.50);
            c7.put(KEY_FOODITEM, 1);
            db.insert(TABLE_ITEMS, null, c7);

            ContentValues a1 = new ContentValues();
            a1.put(KEY_INGREDIENT, "Nuts");
            db.insert(TABLE_ALLERGIES, null, a1);

            ContentValues a2 = new ContentValues();
            a2.put(KEY_INGREDIENT, "Eggs");
            db.insert(TABLE_ALLERGIES, null, a2);

            ContentValues a3 = new ContentValues();
            a3.put(KEY_INGREDIENT, "Dairy");
            db.insert(TABLE_ALLERGIES, null, a3);

            ContentValues a4 = new ContentValues();
            a4.put(KEY_INGREDIENT, "Shellfish");
            db.insert(TABLE_ALLERGIES, null, a4);

            ContentValues a5 = new ContentValues();
            a5.put(KEY_INGREDIENT, "Wheat");
            db.insert(TABLE_ALLERGIES, null, a5);

            ContentValues a6 = new ContentValues();
            a6.put(KEY_INGREDIENT, "Fish");
            db.insert(TABLE_ALLERGIES, null, a6);

            ContentValues a7 = new ContentValues();
            a7.put(KEY_INGREDIENT, "None");
            db.insert(TABLE_ALLERGIES, null, a7);

            ContentValues ia1 = new ContentValues();
            ia1.put(KEY_ITEMID, 1);
            ia1.put(KEY_ALLERGYID, 5);
            db.insert(TABLE_ITEMS_ALLERGIES, null, ia1);

            ContentValues ia2 = new ContentValues();
            ia2.put(KEY_ITEMID, 2);
            ia2.put(KEY_ALLERGYID, 6);
            db.insert(TABLE_ITEMS_ALLERGIES, null, ia2);

            ContentValues ia3 = new ContentValues();
            ia3.put(KEY_ITEMID, 7);
            ia3.put(KEY_ALLERGYID, 4);
            db.insert(TABLE_ITEMS_ALLERGIES, null, ia3);

            ContentValues ia4 = new ContentValues();
            ia4.put(KEY_ITEMID, 7);
            ia4.put(KEY_ALLERGYID, 6);
            db.insert(TABLE_ITEMS_ALLERGIES, null, ia4);

            ContentValues ia5 = new ContentValues();
            ia5.put(KEY_ITEMID, 3);
            ia5.put(KEY_ALLERGYID, 7);
            db.insert(TABLE_ITEMS_ALLERGIES, null, ia5);


            db.close();
        }
    }
}
