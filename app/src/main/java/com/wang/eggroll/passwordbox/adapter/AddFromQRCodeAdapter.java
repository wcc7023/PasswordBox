package com.wang.eggroll.passwordbox.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.model.PasswordItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by eggroll on 15/04/2017.
 */

public class AddFromQRCodeAdapter extends BaseAdapter {

    List<PasswordItem> sharedList;

    HashMap<Integer, Boolean> isSelect = new HashMap<>();

    public HashMap<Integer, Boolean> getIsSelect() {
        return isSelect;
    }

    public AddFromQRCodeAdapter(List<PasswordItem> sharedList) {
        this.sharedList = sharedList;

        for (int i = 0; i < sharedList.size(); i++) {
            isSelect.put(i, false);
        }
    }

    @Override
    public int getCount() {
        return sharedList.size();
    }

    @Override
    public Object getItem(int position) {
        return sharedList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.add_list_item, null);

            TextView name = (TextView) convertView.findViewById(R.id.name_add_from_qrcode);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_add_from_qrcode);

            viewHolder = new ViewHolder(name, checkBox);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.getName().setText(sharedList.get(position).getItem());
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

    private class ViewHolder{
        TextView name;
        CheckBox checkBox;

        public ViewHolder(TextView name, CheckBox checkBox) {
            this.name = name;
            this.checkBox = checkBox;
        }

        public TextView getName() {
            return name;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }
}
