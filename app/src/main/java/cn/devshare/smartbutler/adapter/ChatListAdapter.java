package cn.devshare.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.entity.ChatData;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.adapter
 * Class describe:聊天adapter
 * Author: cheng
 * Create time: 2017/7/3 10:12
 */
public class ChatListAdapter extends BaseAdapter {
    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ChatData> chatDatas;

    public ChatListAdapter(Context context, List<ChatData> chatDatas) {
        this.context = context;
        this.chatDatas = chatDatas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chatDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return chatDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderLeft viewHolderLeft = null;
        ViewHolderRight viewHolderRight = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeft = new ViewHolderLeft();
                    convertView = layoutInflater.inflate(R.layout.left_item, null);
                    viewHolderLeft.leftText = (TextView) convertView.findViewById(R.id.left_text_tv);
                    convertView.setTag(viewHolderLeft);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRight = new ViewHolderRight();
                    convertView = layoutInflater.inflate(R.layout.right_item, null);
                    viewHolderRight.rightText = (TextView) convertView.findViewById(R.id.right_text_tv);
                    convertView.setTag(viewHolderRight);
                    break;
            }

        } else {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeft = (ViewHolderLeft) convertView.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRight = (ViewHolderRight) convertView.getTag();
                    break;
            }
        }
        ChatData chatData = chatDatas.get(position);
        switch (type) {
            case VALUE_LEFT_TEXT:
                viewHolderLeft.leftText.setText(chatData.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRight.rightText.setText(chatData.getText());
                break;
        }
        return convertView;
    }


    @Override
    public int getItemViewType(int position) {
        ChatData chatData = chatDatas.get(position);
        int type = chatData.getType();
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return chatDatas.size()+1;
    }

    class ViewHolderLeft {
        private TextView leftText;
    }

    class ViewHolderRight {
        private TextView rightText;
    }
}
