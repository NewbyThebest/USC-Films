package com.geekb.uscfilms;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ReviewsRvAdapter extends RecyclerView.Adapter<ReviewsRvAdapter.MyHolder> {

    private List<ReviewData> mList;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(ReviewData data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    ReviewsRvAdapter(List<ReviewData> list) {
        mList = list;
    }

    public void setList(List<ReviewData> mList) {
        this.mList = mList;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局R.layout.item_one转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reviews, parent, false);
        //将view传递给我们自定义的ViewHolder
        MyHolder holder = new MyHolder(view);
        //返回这个MyHolder实体
        return holder;

    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ReviewData data = mList.get(position);
        if (!TextUtils.isEmpty(data.created)) {
            String[] y = data.created.split(",");
            String year = y[0] + ", " + y[1];
            holder.userInfo.setText(data.author + " " + year);
        }else {
            holder.userInfo.setText(data.author);
        }

        holder.comment.setText(data.content);
        holder.star.setText(data.rating);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mList.get(position));
                }
            }
        });
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * 自定义的ViewHolder
     */
    class MyHolder extends RecyclerView.ViewHolder {

        TextView userInfo;
        TextView star;
        TextView comment;

        public MyHolder(View itemView) {
            super(itemView);
            userInfo = itemView.findViewById(R.id.user_info);
            star = itemView.findViewById(R.id.star);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}