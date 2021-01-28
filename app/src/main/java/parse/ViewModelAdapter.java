package parse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wji.bookapp.R;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

import vo.BookVO;


public class ViewModelAdapter extends ArrayAdapter<BookVO> {
    /* Adapter class를 상속을 받아서 ListView 각 항목 별로 검색 결과가 나오게 한다 */

    Context context;
    ArrayList<BookVO> list;
    BookVO vo;
    int resource;

    SQLiteDatabase database;


    public ViewModelAdapter(Context context, int resource, ArrayList<BookVO> list, ListView myListView) {

        super(context, resource, list);

        this.context = context;
        this.list = list;
        this.resource = resource;

        //ListView에 이벤트 감지자 등록
        myListView.setOnItemClickListener( list_click );
    }

    //ListView click event 감지자
    //*********여기다가 클릭 한 책을 내 독서 리스트를 저장할 수 있도록 만들기***********
    AdapterView.OnItemClickListener list_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("이 책을 리스트에 저장하시겠습니까?");

            dialog.setNegativeButton("아니요",null);
            dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String title = list.get(i).getB_title();

                    String imgUrl = list.get(i).getB_img();
                    //서버에서 가져 온 이미지 경로에서 bid만 추출 (/1029384.jpg -> 1029384만 추출)
                    String bookId = imgUrl.substring(imgUrl.lastIndexOf('/')+1, imgUrl.lastIndexOf(".jpg"));


                    Toast.makeText(context,"저장되었습니다",Toast.LENGTH_SHORT).show();
                }
            });


        }
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("T","position : " + position);

        // xml파일 -> View 구조로 바꾸어야 실제 눈으로 확인이 가능
        //xml문서를 View로 변환 시켜주는 클래스
        LayoutInflater linf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // context : listview가 여러 개 있는 MainActivity
        //convertView : 검색 결과가 눈으로 보여지는 ListView 항목을 가지고 있다(resource가 객체화 된 것)
        convertView = linf.inflate(resource, null);

        vo = list.get(position); // position이 증가할 때마다 책 한권씩 vo로 넘겨준다

        // convertView(==book_item)를 통해서 findViewById로 검색해야 한다
        TextView title = convertView.findViewById(R.id.book_title);
        TextView author = convertView.findViewById(R.id.book_author);
        ImageView img = convertView.findViewById(R.id.book_img);

        // 검색 결과를 title, author, price에 대입
        title.setText(vo.getB_title());
        author.setText("저자: "+vo.getB_author());

        //서버 통신을 통해 이미지 가지고 오기
        new ImgAsync(img).execute(vo.getB_img());

        // 검색 결과가 눈으로 보여지는 listView를 return한다
        return convertView; //convertView에 담겨진 내용을 ListView로 반환(뿌려준다)
    }

    class ImgAsync extends AsyncTask<String, Void, Bitmap>{

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
