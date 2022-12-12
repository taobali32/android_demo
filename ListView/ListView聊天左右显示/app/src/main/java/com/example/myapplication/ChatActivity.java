package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {


    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

         listView = (ListView) findViewById(R.id.list_view_demo);


        ArrayList<ChatMessage> chatMessageArray = new ArrayList<>();

        chatMessageArray.add(new ChatMessage(1,2,"刘晓明","8:20","你好吗",true));
        chatMessageArray.add(new ChatMessage(2,1,"小军","8:21","我很好",false));


        chatMessageArray.add(new ChatMessage(1,2,"刘晓明","8:22","今天天气怎么样",true));
        chatMessageArray.add(new ChatMessage(2,1,"小军","8:23","很热",false));
//
         listView.setAdapter(new ChatMessageAdapter(chatMessageArray,this));
    }

    public class ChatMessage{
        private int mId;
        private int mFriendID;

        private String mName;

        private String mDate;

        private String mContent;

        private boolean mIsComeMessage;

        public ChatMessage(int mId, int mFriendID, String mName, String mDate, String mContent, boolean mIsComeMessage) {
            this.mId = mId;
            this.mFriendID = mFriendID;
            this.mName = mName;
            this.mDate = mDate;
            this.mContent = mContent;
            this.mIsComeMessage = mIsComeMessage;
        }

        public int getmId() {
            return mId;
        }

        public void setmId(int mId) {
            this.mId = mId;
        }

        public int getmFriendID() {
            return mFriendID;
        }

        public void setmFriendID(int mFriendID) {
            this.mFriendID = mFriendID;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmDate() {
            return mDate;
        }

        public void setmDate(String mDate) {
            this.mDate = mDate;
        }

        public String getmContent() {
            return mContent;
        }

        public void setmContent(String mContent) {
            this.mContent = mContent;
        }

        public boolean ismIsComeMessage() {
            return mIsComeMessage;
        }

        public void setmIsComeMessage(boolean mIsComeMessage) {
            this.mIsComeMessage = mIsComeMessage;
        }



    }

    public  static  class  ChatMessageAdapter extends BaseAdapter {

        ArrayList<ChatMessage>  mChatMessage = new ArrayList<>();

        private Context mContent;

        interface  IMmessageViewType{
            int COM_MESSAGE = 0;
            int TO_MESSAGE = 1;
        }

        public ChatMessageAdapter(ArrayList<ChatMessage> mChatMessage, Context mContent) {
            this.mChatMessage = mChatMessage;
            this.mContent = mContent;
        }

        @Override
        public int getCount() {
            return mChatMessage.size();
        }

        @Override
        public Object getItem(int i) {
            return mChatMessage.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getItemViewType(int position) {
            // 根据不同type返回不同视图

            ChatMessage chatMessage = mChatMessage.get(position);

            return chatMessage.mIsComeMessage ? IMmessageViewType.COM_MESSAGE : IMmessageViewType.TO_MESSAGE;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            ChatMessage chatMessage = mChatMessage.get(i);

            ViewHolder viewHolder = new ViewHolder();
            final ChatMessage entity = mChatMessage.get(i);
            boolean isComMsg = entity.ismIsComeMessage();

            if (convertView == null){

                if (isComMsg){
                    convertView = LayoutInflater.from(mContent).inflate(R.layout.chatting_item_msg_text_left,null);
                }else {
                    convertView = LayoutInflater.from(mContent).inflate(R.layout.chatting_item_msg_text_rigth,null);
                }

                viewHolder = new ViewHolder();

                viewHolder.mDate = (TextView) convertView.findViewById(R.id.tv_send_time);
                viewHolder.mName = (TextView) convertView.findViewById(R.id.user_name);
                viewHolder.mContent = (TextView) convertView.findViewById(R.id.tv_chat_content);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_user_head);

                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.mDate.setText(chatMessage.mDate);
            viewHolder.mName.setText(chatMessage.getmName());
            viewHolder.mContent.setText(chatMessage.mContent);

            return convertView;
        }

        class ViewHolder {
            public TextView mName;
            public TextView mDate;
            public TextView mContent;
            public TextView mIsComeMessage;
            public ImageView imageView;

        }
    }


}