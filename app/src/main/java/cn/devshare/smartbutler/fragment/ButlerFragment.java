package cn.devshare.smartbutler.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.adapter.ChatListAdapter;
import cn.devshare.smartbutler.entity.ChatData;
import cn.devshare.smartbutler.utils.L;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.fragment
 * Class describe:问答机器人
 * Author: cheng
 * Create time: 2017/6/26 19:44
 */

public class ButlerFragment extends Fragment implements View.OnClickListener{
    private ListView mChatListView;
    private List<ChatData> chatDataList=new ArrayList<ChatData>();
    private EditText textTv;
    private Button sendBt;
    ChatListAdapter chatListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_butler, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mChatListView = (ListView) view.findViewById(R.id.mChatListView);
        mChatListView.setDivider(null);
        textTv = (EditText) view.findViewById(R.id.et_text);
        sendBt = (Button) view.findViewById(R.id.btn_send);
        sendBt.setOnClickListener(this);
        chatListAdapter=new ChatListAdapter(getContext(),chatDataList);
        mChatListView.setAdapter(chatListAdapter);
        addLeftItem("欢迎使用聊天机器人");
    }

    @Override
    public void onClick(View v) {
        String text=textTv.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            addRightItem(text);
            String url="http://api.qingyunke.com/api.php?key=free&appid=0&msg="+text;
            RxVolley.get(url, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    L.i("The response text is "+t);
                    textTv.setText("");
                    parseJson(t);
                }
            });
        }
    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            String responseText=jsonObject.getString("content");
            addLeftItem(responseText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void addRightItem(String text) {
        ChatData chatData=new ChatData();
        chatData.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        chatData.setText(text);
        chatDataList.add(chatData);
        chatListAdapter.notifyDataSetChanged();
        mChatListView.setSelection(mChatListView.getBottom());
    }

    private void addLeftItem(String text) {
        ChatData chatData=new ChatData();
        chatData.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        chatData.setText(text);
        chatDataList.add(chatData);
        chatListAdapter.notifyDataSetChanged();
        mChatListView.setSelection(mChatListView.getBottom());
    }
}
