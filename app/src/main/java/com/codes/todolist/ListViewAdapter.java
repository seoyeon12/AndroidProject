package com.codes.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    public ListViewAdapter() {}

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView NameTextView = (TextView) convertView.findViewById(R.id.item_name) ;
        TextView contextTextView = (TextView) convertView.findViewById(R.id.item_context) ;
        TextView timeTextView = (TextView) convertView.findViewById(R.id.item_time) ;

        ListViewItem listViewItem = listViewItemList.get(position);

        NameTextView.setText(listViewItem.getName());
        contextTextView.setText(listViewItem.getContext());
        timeTextView.setText(listViewItem.getTime());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(Integer index, String name, String time, String context) {
        ListViewItem item = new ListViewItem();
        item.setindex(index);
        item.setname(name);
        item.setContext(context);
        item.setTime(time);
        listViewItemList.add(item);
    }
}
