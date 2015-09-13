package com.gz.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.gz.home.R;
import com.gz.home.app.Constant;
import com.gz.home.customerview.CircleImageView;
import com.gz.home.datamodel.User;
import com.gz.home.utils.LogUtil;

import java.util.List;

/**
 * Created by host on 2015/8/28.
 */
public class MemberAdapter extends BaseAdapter {
    private List<User> userList;
    private User me;
    private LayoutInflater inflater;
    public MemberAdapter(Context context,List<User> userList,User me){
        this.userList=userList;
        this.me=me;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.adapter_member,parent,false);
            viewHolder.Avater=(CircleImageView)convertView.findViewById(R.id.member_avater);
            viewHolder.Name=(TextView)convertView.findViewById(R.id.member_name);
            viewHolder.Detail=(TextView)convertView.findViewById(R.id.member_detail);
            viewHolder.Tag=(TextView)convertView.findViewById(R.id.member_tag);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        User currentItem=userList.get(position);
        new AQuery(viewHolder.Avater).image(currentItem.getAvatar());
        viewHolder.Name.setText(currentItem.getName());
        viewHolder.Detail.setText(currentItem.getDetail());


        //设置标志
//        LogUtil.i("father:",""+ (me.getFather()==null));
//        LogUtil.i("father id",me.getFather().getObjectId());
//        LogUtil.i("currentitem",""+(currentItem==null));
//        LogUtil.i("currentid",currentItem.getObjectId());
        if(me.getFather()!=null&&
                me.getFather().getObjectId()!=null&&
                me.getFather().getObjectId().equals(currentItem.getObjectId())){
            viewHolder.Tag.setText("爸爸");
        }else if(me.getMother()!=null&&
                me.getMother().getObjectId()!=null&&
                me.getMother().getObjectId().equals(currentItem.getObjectId())){
            viewHolder.Tag.setText("妈妈");
        }else if(me.getSpouse()!=null&&
                me.getSpouse().getObjectId()!=null&&
                me.getSpouse().getObjectId().equals(currentItem.getObjectId())) {
            if(me.getSpouse().getSex()== Constant.USER.MALE){
                viewHolder.Tag.setText("丈夫");
            }else {
                viewHolder.Tag.setText("妻子");
            }
        }else{
            viewHolder.Tag.setVisibility(View.GONE);
        }


        return convertView;
    }

    public void setUserList(List<User> list){
        this.userList=list;
    }
    public void setMe(User user){
        this.me=user;
    }

    public final class ViewHolder{
        public CircleImageView Avater;
        public TextView Name;
        public TextView Detail;
        public TextView Tag;
    }


}
