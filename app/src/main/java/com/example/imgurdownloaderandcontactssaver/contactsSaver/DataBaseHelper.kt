package com.example.imgurdownloaderandcontactssaver.contactsSaver

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

const val DBNAME = "CONTACTS_LIST"
const val TABLE = "Contacts"
const val CONTACT_NAME = "ContactName"
const val CONTACT_PHONE = "ContactPhone"
const val TAG_DBHelper = "DBHelper"

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DBNAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " +
                TABLE + " (" + CONTACT_NAME + " VARCHAR(256), " +
                CONTACT_PHONE + " VARCHAR(256))"
        db?.execSQL(createTable)
        Log.i(TAG_DBHelper, "Create table $DBNAME")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE")
        Log.i(TAG_DBHelper, "Drop table $DBNAME")
        onCreate(db)
    }

    fun insertData(contact: ContactItem) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONTACT_NAME, contact.name)
        contentValues.put(CONTACT_PHONE, contact.phone)
        Log.i(TAG_DBHelper, "Insert new data into $DBNAME")
        db.insert(TABLE, null, contentValues)
    }

    fun readData(): MutableList<ContactItem> {
        val list: MutableList<ContactItem> = ArrayList()
        val db = this.readableDatabase
        val result = db.query(
            TABLE,
            null,
            null,
            null,
            null,
            null,
            null
        )

        if (result.moveToFirst()) {
            do {
                val contactName = result.getString(result.getColumnIndex(CONTACT_NAME))
                val contactPhone = result.getString(result.getColumnIndex(CONTACT_PHONE))

                list.add(
                    ContactItem(
                        contactName,
                        contactPhone
                    )
                )
            } while (result.moveToNext())
        }
        Log.i(TAG_DBHelper, "Read data from $DBNAME")
        return list
    }

    fun deleteData() {
        val db = this.writableDatabase
        val selection = "delete from $TABLE"
        db.execSQL(selection)
        Log.i(TAG_DBHelper, "Delete all from table $DBNAME")
    }

    fun dataCount(): Int{
        val db = this.readableDatabase
        val result = db.query(
            TABLE,
            null,
            null,
            null,
            null,
            null,
            null
        )
        return result.count
    }
}