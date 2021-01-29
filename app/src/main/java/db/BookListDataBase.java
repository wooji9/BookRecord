package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BookListDataBase {

    public static class DatabaseHelper extends SQLiteOpenHelper{

        public static String NAME = "bookList.db";
        public static int VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onOpen(SQLiteDatabase db) {

        }

        @Override
        public SQLiteDatabase getWritableDatabase() {
            return super.getWritableDatabase();
        }

        @Override
        public SQLiteDatabase getReadableDatabase() {
            return super.getReadableDatabase();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
