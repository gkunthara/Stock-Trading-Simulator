package com.brian.brian.stockapp_2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Brian on 11/14/2017.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nizhegorodtsev.Stock;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper implements Serializable {
    static final String DATABASE_TITLE = "databaseNotes";
    static final int DATABASE_VERSION = 1;

    // Portfolio Table
    static final String TABLE_PORTFOLIO = "tablePortfolio";
    static final String TICKER_PORTFOLIO = "ticker";
    static final String PORTFOLIO_TAG = "PortfolioDatabaseHelper";

    // Watchlist Table
    static final String TABLE_WATCHLIST = "tableWatchlist";
    static final String TICKER_WATCHLIST = "ticker";
    static final String WATCHLIST_TAG = "PortfolioDatabaseHelper";

    // Cash Table
    static final String TABLE_CASH = "tableCash";
    static final String CASH_CASH = "cash";
    static final String CASH_TAG = "PortfolioDatabaseHelper";

    // constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_TITLE, null, DATABASE_VERSION);
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TAG", "OnCreate");
        // Initialize Portfolio Table
        String sqlCreate = "CREATE TABLE " + TABLE_PORTFOLIO + "(" +
                "ticker" + " TEXT PRIMARY KEY, " +
                "amountOwned" + " INTEGER)";
        Log.d(PORTFOLIO_TAG, "onCreate: " + sqlCreate);
        db.execSQL(sqlCreate);


//        // Initialize Watchlist Table
//        sqlCreate = "CREATE TABLE " + TABLE_WATCHLIST + "(" +
//                TICKER_WATCHLIST + " TEXT PRIMARY KEY)";
//        Log.d(WATCHLIST_TAG, "onCreate: " + sqlCreate);
//        db.execSQL(sqlCreate);


        // Initialize Cash Table
        sqlCreate = "CREATE TABLE " + TABLE_CASH + "(" +
                CASH_CASH + " DOUBLE PRIMARY KEY)";
        Log.d(CASH_TAG, "onCreate: " + sqlCreate);
        db.execSQL(sqlCreate);


        // Initialize Cash Value
        String sqlInsertNote = "INSERT INTO " + TABLE_CASH +
                " VALUES(" + 10000 + ")";
        Log.d("Set cash onCreate", "Set cash to 10,000: " + sqlInsertNote);
        db.execSQL(sqlInsertNote);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}



    public void rebuildDatabase() {
        updateCash(10000.0);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS tablePortfolio");
        db.execSQL("DROP TABLE IF EXISTS tableWatchlist");

        Log.d("TAG", "OnCreate");
        // Initialize Portfolio Table
        String sqlCreate = "CREATE TABLE " + TABLE_PORTFOLIO + "(" +
                "ticker" + " TEXT PRIMARY KEY, " +
                "amountOwned" + " INTEGER)";
        Log.d(PORTFOLIO_TAG, "onCreate: " + sqlCreate);
        db.execSQL(sqlCreate);
    }


    // Add a ticker to portfolio or watchlist
    public void insertStock(String ticker, int amount, String table) {
        String sqlInsertNote = "INSERT INTO " + table + " VALUES('"
                + ticker + "'," + String.valueOf(amount) + ")";
        Log.d(table + ": ", "insertTicker: " + sqlInsertNote);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlInsertNote);
    }


    public void deleteStock(String ticker, String table) {
        String sqlDeleteNote = "DELETE FROM " + table + " WHERE " + "ticker" + " = '" + ticker + "'";
        Log.d("table" + ":", "deleteNote: " + sqlDeleteNote);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlDeleteNote);
        db.close();
    }



    // warmup task #2
    public Cursor getSelectAllNotesCursor(String table) {
        // cursor used to navigate through query results
        // a table of records
        // SQL select statement to query our table
        // SELECT * FROM tableNotes
        // * means all columns
        String sqlSelectAll = "SELECT * FROM " + table;
        Log.d(table + ": ", "getSelectAllNotesCursor: " + sqlSelectAll);
        // get reference to the database, read only
        SQLiteDatabase db = this.getReadableDatabase();
        // call rawQuery(), which returns a Cursor reference
        Cursor cursor = db.rawQuery(sqlSelectAll, null);

        // dont close the db, cursor needs it
        return cursor;
    }

    public List<String> getSelectAllTickers(String table) {
        // for debugging purposes only
        // get a cursor, navigate through all records and construct a list
        // of notes
        // A list of notes is something we are familiar
        List<String> tickers = new ArrayList<>();
        // populate the list with records information
        Cursor cursor = getSelectAllNotesCursor(table);
        // cursor does not start at the first record, there may not be a first record
        // we need move our cursor through the results table
        // relevant methods: moveToFirst(), moveToNext(), moveToLast()
        while (cursor.moveToNext()) { // returns false when there is no next
            String content = cursor.getString(0);
            tickers.add(content);
        }

        return tickers;
    }

    /*
    public void updateNoteById(int id, Note newNote) {
        // UPDATE tableNotes SET TITLE = 'SPIKE',
        // CATEGORY = '' WHERE _id = 1
        // use id and newNote instead of hardcoded values
        // get a database reference
        // execute the SQL
        String sqlUpdate = "UPDATE " + TABLE_NOTES +
                " SET " + TITLE + " = '" + newNote.getTitle() +
                "', " + CATEGORY + " = '" + newNote.getCategory() +
                "' WHERE " + ID + " = " + id;
        Log.d(TAG, "updateNoteById: " + sqlUpdate);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }
    */


    public List<Integer> getAmountsOwned() {
        List<Integer> amountsOwned = new ArrayList<>();

        Cursor cursor = getSelectAllNotesCursor(TABLE_PORTFOLIO);
        // cursor does not start at the first record, there may not be a first record
        // we need move our cursor through the results table
        // relevant methods: moveToFirst(), moveToNext(), moveToLast()
        while (cursor.moveToNext()) { // returns false when there is no next
            int content = cursor.getInt(1);
            amountsOwned.add(content);
        }

        return amountsOwned;
    }


    public double getCash() {
        Double cash = 0.0;

        Cursor cursor = getSelectAllNotesCursor(TABLE_CASH);
        // cursor does not start at the first record, there may not be a first record
        // we need move our cursor through the results table
        // relevant methods: moveToFirst(), moveToNext(), moveToLast()
        while (cursor.moveToNext()) { // returns false when there is no next
            cash = cursor.getDouble(0);
        }

        Log.d("Cash Amount:", String.valueOf(cash));
        return cash;
    }

    public void updateCash(double cash) {
        String sqlUpdate = "UPDATE " + TABLE_CASH +
                " SET " + CASH_CASH + " = " + cash;
        Log.d(String.valueOf(cash), "updateNoteById: " + sqlUpdate);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    public void setAmount(String ticker, int amount) {
        String sqlUpdate = "UPDATE " + TABLE_PORTFOLIO +
                " SET " + "amountOwned" + " = " + amount +
                " WHERE " + "ticker" + " = '" + ticker + "'";
        Log.d("Setting Amount:", sqlUpdate);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }


    public void deleteTicker(String ticker, String table) {
        String sqlDeleteNote = " DELETE FROM " + table + " WHERE " + "ticker" + " = " + ticker;
        Log.d("table" + ":", "deleteNote: " + sqlDeleteNote);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlDeleteNote);
        db.close();
    }


    public int getAmountOwned (String ticker) {

        String sqlSelectAmount = "SELECT * FROM tablePortfolio WHERE ticker = '" + ticker + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlSelectAmount, null);
        int amountOwned = 0;
        while (cursor.moveToNext()) { // returns false when there is no next
            amountOwned = cursor.getInt(1);
        }
        db.close();

        return amountOwned;
    }


    public void deleteAllTicker(String table) {
        String sqlDeleteNotes = " DELETE FROM " + table;
        Log.d(table + ":", "deleteAllNotes: " + sqlDeleteNotes);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlDeleteNotes);
        db.close();
    }
}
