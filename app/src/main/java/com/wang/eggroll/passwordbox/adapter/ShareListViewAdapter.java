package com.wang.eggroll.passwordbox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.model.PasswordItem;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by eggroll on 02/04/2017.
 */

public class ShareListViewAdapter extends BaseAdapter {

    List<PasswordItem> passwordItemList;

    public static HashMap<Integer, Boolean> getIsSelect() {
        return isSelect;
    }

    private static HashMap<Integer, Boolean> isSelect = new HashMap<>();

    public ShareListViewAdapter(List<PasswordItem> passwordItemList) {
        this.passwordItemList = passwordItemList;

        //initHashMap
        for (int i = 0; i < passwordItemList.size(); i++) {
           isSelect.put(i, false);
        }
    }

    @Override
    public int getCount() {
        return PasswordItemList.getInstance().size();
    }

    @Override
    public Object getItem(int position) {
        return PasswordItemList.getInstance().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.share_list_item, null);

            TextView textView = (TextView) convertView.findViewById(R.id.share_list_item_name);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            viewHolder = new ViewHolder(textView, checkBox);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PasswordItem passwordItem = PasswordItemList.getInstance().get(position);

        viewHolder.getName().setText(passwordItem.getItem());
        viewHolder.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelect.get(position)){
                    isSelect.put(position, false);
                }else {
                    isSelect.put(position, true);
                }
                notifyDataSetChanged();
            }
        });
        viewHolder.getCheckBox().setChecked(isSelect.get(position));
        return convertView;
    }

    public class ViewHolder{
        TextView name;
        CheckBox checkBox;

        public ViewHolder(TextView name, CheckBox checkBox) {
            this.name = name;
            this.checkBox = checkBox;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public TextView getName() {
            return name;
        }
    }
}
