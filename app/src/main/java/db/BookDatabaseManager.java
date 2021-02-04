package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/* 책 데이터베이스 */
public class BookDatabaseManager {
    static final String DB_BOOK = "book.db";   //DB이름
    static final String TABLE_BOOK = "bookList"; //Table 이름
    static final int DB_VERSION = 1;			//DB 버전

    Context myContext = null;

    private static BookDatabaseManager myDBManager = null;
    private SQLiteDatabase mydatabase = null;

    //MovieDatabaseManager 싱글톤 패턴으로 구현
    public static BookDatabaseManager getInstance(Context context)
    {
        if(myDBManager == null)
        {
            myDBManager = new BookDatabaseManager(context);
        }

        return myDBManager;
    }

    private BookDatabaseManager(Context context)
    {
        myContext = context;

        //DB Open
        mydatabase = context.openOrCreateDatabase(DB_BOOK, context.MODE_PRIVATE,null);

        //Table 생성
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BOOK +
                "(" + "idx INTEGER PRIMARY KEY AUTOINCREMENT," + // 책 idx
                "id TEXT," + // 로그인 한 id
                "title TEXT," + // 책 제목
                "book_img TEXT," + // 책 이미지
                "register TEXT);"); //  책 등록 날짜

        // 외래키 활성화
        mydatabase.setForeignKeyConstraintsEnabled(true);
        mydatabase.execSQL("PRAGMA foreign_keys = 1;");
    }

    // insert
    public long insert(ContentValues addRowValue)
    {
        return mydatabase.insert(TABLE_BOOK, null, addRowValue);
    }

    // select를 위한 query문
    public Cursor query(String[] colums,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
                        String orderby)
    {
        return mydatabase.query(TABLE_BOOK,
                colums,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderby);
    }

    // delete
    public int delete(String whereClause,
                      String[] whereArgs)
    {
        return mydatabase.delete(TABLE_BOOK,
                whereClause,
                whereArgs);
    }
}
