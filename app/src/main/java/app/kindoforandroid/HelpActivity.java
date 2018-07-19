package app.kindoforandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/10/31 0031.
 */
public class HelpActivity extends Activity {

    List<Integer> resourceIDList = new ArrayList<>();
    ListView listView;
    HelpAdapter helpAdapter;
//    int location = 0;
    LinearLayout btn_back;
//    ImageView help_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle("游戏帮助");

//        Toast.makeText(getApplicationContext(),"点击跳到下一条教程",Toast.LENGTH_LONG).show();

        listView = (ListView) findViewById(R.id.help_list);
        btn_back = (LinearLayout) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        help_pic = (ImageView) findViewById(R.id.help_pic);

        loadRes();
        helpAdapter = new HelpAdapter(getApplicationContext(),R.layout.item_help,resourceIDList);
        listView.setAdapter(helpAdapter);

//        if(resourceIDList.size()>0){
//            help_pic.setImageResource(resourceIDList.get(0));
//            help_pic.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(++location >= resourceIDList.size()){
//                        location = 0;
//                    }
//                    help_pic.setImageResource(resourceIDList.get(location));
//                }
//            });
//        }

    }

    private void loadRes(){
        resourceIDList.add(R.drawable.help1);
        resourceIDList.add(R.drawable.help2);
        resourceIDList.add(R.drawable.help3);
        resourceIDList.add(R.drawable.help4);
        resourceIDList.add(R.drawable.help5);
        resourceIDList.add(R.drawable.help6);
        resourceIDList.add(R.drawable.help7);
        resourceIDList.add(R.drawable.help8);
        resourceIDList.add(R.drawable.help9);
    }

}
