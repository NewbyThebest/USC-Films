package com.geekb.uscfilms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WatchlistRvAdapter extends RecyclerView.Adapter<WatchlistRvAdapter.MyHolder> {

    private List<VideoData> mList;
    private OnItemClickListener mOnItemClickListener;

    WatchlistRvAdapter(List<VideoData> list) {
        mList = list;
    }

    public void setList(List<VideoData> mList) {
        this.mList = mList;
    }

    public interface OnItemClickListener {
        public void onItemClick(VideoData data);

        public void onItemRemoveClick(VideoData data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局R.layout.item_one转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_watchlist, parent, false);
        //将view传递给我们自定义的ViewHolder
        MyHolder holder = new MyHolder(view);
        //返回这个MyHolder实体
        return holder;
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mList.get(position));
                }

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemRemoveClick(mList.get(position));
                }
            }
        });
        Glide.with(holder.img.getContext()).load(mList.get(position).imgUrl).placeholder(R.drawable.img_default).into(holder.img);
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

        ImageView img;
        ImageView remove;
        TextView type;

        public MyHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            type = itemView.findViewById(R.id.type);
            remove = itemView.findViewById(R.id.remove);

        }
    }
}