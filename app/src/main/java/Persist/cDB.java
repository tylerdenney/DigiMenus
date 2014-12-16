package Persist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Contacts;

import java.util.List;

import Business.cFoodItem;
import Business.iItem;

/**
 * Created by tyler on 12/12/2014.
 */
public class cDB extends SQLiteOpenHelper
{
    String CreateTableITEMS = "CREATE TABLE Items ( " +
                                                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                    "Name TEXT, " +
                                                    "Description TEXT, " +
                                                    "Cost REAL, " +
                                                    "FoodItem INTEGER );";

    String CreateTableALLERGIES = "CREATE TABLE Allergies ( " +
                                                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                            "Ingredient TEXT )";

    String CreateTableITEMS_ALLERGIES = "CREATE TABLE Items_Allergies ( " +
                                                                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                        "ItemID INTEGER, " +
                                                                        "AllergyID INTEGER, " +
                                                                        "FOREIGN KEY(ItemID) REFERENCES Items(id), " +
                                                                        "FOREIGN KEY(AllergyID)REFERENCES Allergies(id))";



    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MenuDB.db";

    public cDB(Context c)
    {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public List<iItem> ImportMenuItems()
    {
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CreateTableITEMS);
        //db.execSQL(CreateTableALLERGIES);
        //db.execSQL(CreateTableITEMS_ALLERGIES);
        //InsertItems();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Items");
        this.onCreate(db);
    }

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

    public void InsertItems()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c1 = new ContentValues();
        c1.put(KEY_NAME, "Turkey Club");
        c1.put(KEY_DESC,"Turkey Sandwich on wheat bread with swiss cheese." );
        c1.put(KEY_COST, 6.99);
        c1.put(KEY_FOODITEM, 1);
        db.insert(TABLE_ITEMS, null, c1);

        ContentValues c2 = new ContentValues();
        c2.put(KEY_NAME, "Fish Tacos");
        c2.put(KEY_DESC,"Two Ahi Tuna tacos on crispy corn tortillas." );
        c2.put(KEY_COST, 7.99);
        c2.put(KEY_FOODITEM, 1);
        db.insert(TABLE_ITEMS, null, c2);

        ContentValues c3 = new ContentValues();
        c3.put(KEY_NAME, "Chicken Teriyaki");
        c3.put(KEY_DESC,"Chicken teriyaki. Enough said." );
        c3.put(KEY_COST, 7.99);
        c3.put(KEY_FOODITEM, 1);
        db.insert(TABLE_ITEMS, null, c3);

        ContentValues c4 = new ContentValues();
        c4.put(KEY_NAME, "Raspberry Lemonade");
        c4.put(KEY_DESC,"Lemonade with whole raspberries." );
        c4.put(KEY_COST, 2.99);
        c4.put(KEY_FOODITEM, 0);
        db.insert(TABLE_ITEMS, null, c4);

        ContentValues c5 = new ContentValues();
        c5.put(KEY_NAME, "Sprite");
        c5.put(KEY_DESC,"Refreshing soft drink Sprite.." );
        c5.put(KEY_COST, 1.50);
        c5.put(KEY_FOODITEM, 0);
        db.insert(TABLE_ITEMS, null, c5);

        ContentValues c6 = new ContentValues();
        c6.put(KEY_NAME, "Coke");
        c6.put(KEY_DESC,"Refreshing soft drink Coke." );
        c6.put(KEY_COST, 1.50);
        c6.put(KEY_FOODITEM, 0);
        db.insert(TABLE_ITEMS, null, c6);

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

       // ContentValues ia1 = new ContentValues();
       // ia1.put(KEY_ITEMID, 3);

        db.close();
    }
    public void GetItem()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c1 = new ContentValues();
        c1.put(KEY_NAME, "Turkey Club");
        c1.put(KEY_DESC,"Turkey Sandwich on wheat bread with swiss cheese." );
        c1.put(KEY_COST, 6.99);
        c1.put(KEY_FOODITEM, 1);
        db.insert(TABLE_ITEMS, null, c1);

        db.close();

        SQLiteDatabase db2 = this.getReadableDatabase();
        String query = "SELECT * FROM Items WHERE id = 1";
        Cursor cursor = db2.rawQuery(query, null);
        cursor.moveToNext();
        String f = new String(cursor.getString(cursor.getColumnIndex("Name")));

    }

}
