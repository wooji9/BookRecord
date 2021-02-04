package parse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.wji.bookapp.MemoListActivity;
import com.wji.bookapp.R;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

import db.BookDatabaseManager;
import vo.BookItem;

public class BookListAdapter extends ArrayAdapter<BookItem> {

    int resource;
    Context context;
    ArrayList<BookItem> list;
    BookItem item;

    BookDatabaseManager db;


    public BookListAdapter(Context context, int resource, ArrayList<BookItem> list, ListView bookSaveList) {
        super(context, resource, list);

        this.context = context;
        this.resource = resource;
        this.list = list;

        db = BookDatabaseManager.getInstance(context);

        bookSaveList.setOnItemClickListener(list_click);


    }

    // book List 중 하나 클릭 시
    AdapterView.OnItemClickListener list_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String title = list.get(i).getTitle();
            AlertDialog.Builder dial = new AlertDialog.Builder(context);

            dial.setTitle(title);
            dial.setMessage("삭제하시겠습니까? 메모하시겠습니까?");

            dial.setNeutralButton("뒤로가기",null);

            // delete
            dial.setPositiveButton("삭제하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int a) {
                    int idx = list.get(i).getIdx();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("정말 삭제하시겠습니까?");

                    dialog.setNegativeButton("아니요",null);
                    dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int b) {
                            String whereClause = "idx = ?"; // == delete from bookList where idx = 삭제하고 싶은 책 idx
                            String[] whereArgs = {""+idx};
                            int n = db.delete(whereClause,whereArgs);

                            if(n>0){ // 삭제 완료되면 Toast 띄우기
                                Toast.makeText(context,"삭제되었습니다",Toast.LENGTH_SHORT).show();
                                // 삭제 후 내용 업데이트 되도록 getMemoData() 호출?
                            }
                        }
                    });

                    dialog.show();


                }
            });

            // memo
            dial.setNegativeButton("메모하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int a) {
                    int idx = list.get(i).getIdx(); // 메모하고 싶은 책의 idx

                    Intent intent = new Intent(context, MemoListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("n",""+idx); // 메모하고 싶은 책의 idx를 가지고 MemoListActivity로 전환
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            dial.show();

        }
    };


    @Override
    public View getView(int position,View convertView, ViewGroup parent) { // resource를 ListView로 보이게 해주는 메서드

        LayoutInflater linf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = linf.inflate(resource, null);

        item = list.get(position); // position이 증가할 때마다 책 한권씩 vo로 넘겨준다

        TextView title = convertView.findViewById(R.id.booklist_title);
        ImageView img = convertView.findViewById(R.id.booklist_img);

        title.setText(item.getTitle());

        new ImgAsync(img).execute(item.getBook_img());

        return convertView;
    }

    // 책의 이미지 가져오기
    class ImgAsync extends AsyncTask<String, Void, Bitmap> {

        Bitmap bitmap;
        ImageView imageView;

        public ImgAsync(ImageView imageView){ // ImageView인 img가 들어옴
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL img_url = new URL(strings[0]);

                //inputStream을 통해 이미지 로드
                BufferedInputStream bis = new BufferedInputStream(img_url.openStream());
                // 읽은 것을 bitmap 형식으로 디코딩
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
            }
            catch (Exception e){

            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //bitmap 객체를 imageview로 전환
            imageView.setImageBitmap(bitmap);
        }
    }


}
