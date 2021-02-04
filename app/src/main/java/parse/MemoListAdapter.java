package parse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wji.bookapp.MemoViewActivity;
import com.wji.bookapp.R;

import java.util.ArrayList;

import vo.MemoVO;

public class MemoListAdapter extends ArrayAdapter<MemoVO> {

    int resource;
    Context context;
    ArrayList<MemoVO> list;
    MemoVO item;

    public MemoListAdapter(Context context, int resource, ArrayList<MemoVO> list, ListView memoList) {
        super(context, resource, list);

        this.context = context;
        this.resource = resource;
        this.list = list;

        memoList.setOnItemClickListener(list_click);

    }

    // 메모 list 중 하나를 클릭하면
    AdapterView.OnItemClickListener list_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            int m_idx = list.get(i).getM_idx(); // 클릭 한 메모의 idx

            Intent intent = new Intent(context, MemoViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("m_idx",""+m_idx); // 클릭 한 메모의 idx를 가지고 MemoViewActitivity로 전환
            intent.putExtras(bundle);

            context.startActivity(intent);

        }
    };


    @Override
    public View getView(int position,View convertView, ViewGroup parent) { // resource를 ListView로 보이게 하는 함수

        LayoutInflater linf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = linf.inflate(resource, null);

        item = list.get(position); // position이 증가할 때마다 책 한권씩 vo로 넘겨준다

        TextView title = convertView.findViewById(R.id.memo_title);

        title.setText(item.getM_title());


        return convertView;
    }

}
