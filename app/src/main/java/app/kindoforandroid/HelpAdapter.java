package app.kindoforandroid;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 595056078 on 2018/7/18.
 */

public class HelpAdapter extends ArrayAdapter<Integer> {
    private int resID;
    private ListView help_list;

    public HelpAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Integer> objects) {
        super(context, resource, objects);

        resID = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;
        if(convertView == null){
            v = LayoutInflater.from(getContext()).inflate(resID,null);
        }else{
            v = convertView;
        }
        ImageView iv = (ImageView) v.findViewById(R.id.help_img);
        iv.setImageResource(getItem(position));

        return v;
    }


}
