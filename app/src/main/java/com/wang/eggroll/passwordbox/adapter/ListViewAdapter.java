package com.wang.eggroll.passwordbox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.instance.PasswordItemList;
import com.wang.eggroll.passwordbox.model.PasswordItem;
import com.wang.eggroll.passwordbox.utils.AESHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eggroll on 29/03/2017.
 */

public class ListViewAdapter extends BaseAdapter implements Filterable{
    List<PasswordItem> passwordItemList = null;
    PasswordItemFilter passwordItemFilter;

    public ListViewAdapter(List<PasswordItem> passwordItemList) {
        this.passwordItemList = passwordItemList;
    }

    @Override
    public int getCount() {
        return passwordItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return passwordItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.pwd_list_item, null);

            TextView name = (TextView) convertView.findViewById(R.id.list_item_name);
            TextView pwd = (TextView) convertView.findViewById(R.id.list_item_pwd);

            viewHolder = new ViewHolder(name, pwd);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PasswordItem passwordItem = passwordItemList.get(position);

        String pwdAfterEncrypt = passwordItem.getPassword();
        String pwdBeforeEncrypt = AESHelper.decrypt(pwdAfterEncrypt, App.getSharedPreferences().getString("PASSWORD", "NULL"));

        viewHolder.getName().setText(passwordItem.getItem());
        viewHolder.getPwd().setText(pwdBeforeEncrypt);

        if(!App.isHide()){
            viewHolder.getPwd().setVisibility(View.VISIBLE);
        }else{
            viewHolder.getPwd().setVisibility(View.INVISIBLE);
        }

        return convertView;
    }



    public static class ViewHolder{
        TextView name;
        TextView pwd;

        public ViewHolder(TextView name, TextView pwd) {
            this.name = name;
            this.pwd = pwd;
        }

        public TextView getName() {
            return name;
        }

        public TextView getPwd() {
            return pwd;
        }
    }

    @Override
    public Filter getFilter() {
        if (passwordItemFilter == null){
            synchronized (ListViewAdapter.class){
                if (passwordItemFilter == null){
                    passwordItemFilter = new PasswordItemFilter();
                }
            }
        }
        return passwordItemFilter;
    }

    private class PasswordItemFilter extends Filter{

        private List<PasswordItem> original = PasswordItemList.getInstance();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0){
                results.values = original;
                results.count = original.size();
            }else{
                List<PasswordItem> resultList = new ArrayList<>();
                for (PasswordItem passwordItem : original){
                    if(passwordItem.getItem().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        resultList.add(passwordItem);
                    }
                }
                results.values = resultList;
                results.count = resultList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            passwordItemList = (List<PasswordItem>) results.values;
            notifyDataSetChanged();
        }
    }
}
