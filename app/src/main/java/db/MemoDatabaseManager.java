package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/* 메모 데이터베이스 */
public class MemoDatabaseManager {
    static final String DB_MEMO = "book.db";   //DB이름
    static final String TABLE_MEMO = "memoList"; //Table 이름
    static final int DB_VERSION = 1;			//DB 버전

    Context myContext = null;

    private static MemoDatabaseManager myDBManager = null;
    private SQLiteDatabase db = null;

    //MovieDatabaseManager 싱글톤 패턴으로 구현
    public static MemoDatabaseManager getInstance(Context context)
    {
        if(myDBManager == null)
        {
            myDBManager = new MemoDatabaseManager(context);
        }

        return myDBManager;
    }

    private MemoDatabaseManager(Context context)
    {
        myContext = context;

        //DB Open
        db = context.openOrCreateDatabase(DB_MEMO, context.MODE_PRIVATE,null);

        //Table 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MEMO +
                "(" + "m_idx INTEGER PRIMARY KEY AUTOINCREMENT," + // 메모 idx
                "idx TEXT NOT NULL," + // 메모를 등록 한 책의 idx
                "m_title TEXT," + // 메모 제목
                "m_content TEXT," + //  메모 내용
                "m_register TEXT," + // 메모 등록 날짜
                "FOREIGN KEY (idx) REFERENCES bookList(idx) ON DELETE CASCADE );"); // 책이 삭제되면 해당 책에 메모한 내용도 삭제되도록 외래키 추가

        // 외래키 활성화
        db.setForeignKeyConstraintsEnabled(true);
        db.execSQL("PRAGMA foreign_keys = 1;");

    }

    // insert
    public long insert(ContentValues addRowValue)
    {
        return db.insert(TABLE_MEMO, null, addRowValue);
    }

    // select를 위한 query
    public Cursor query(String[] colums,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
                        String orderby)
    {
        return db.query(TABLE_MEMO,
                colums,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderby);
    }

    // update
    public int update(ContentValues updateRowValue,
                      String whereClause,
                      String[] whereArgs)
    {
        return db.update(TABLE_MEMO,
                updateRowValue,
                whereClause,
                whereArgs);
    }

    // delete
    public int delete(String whereClause,
                      String[] whereArgs)
    {
        // 외래키 활성화
        db.setForeignKeyConstraintsEnabled(true);
        db.execSQL("PRAGMA foreign_keys = 1;");

        return db.delete(TABLE_MEMO,
                whereClause,
                whereArgs);
    }
}
