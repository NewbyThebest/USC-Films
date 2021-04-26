package com.geekb.uscfilms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TopRvAdapter extends RecyclerView.Adapter<TopRvAdapter.MyHolder> {

    private List<VideoData> mList;//数据源
    private OnItemClickListener mOnItemClickListener;

    TopRvAdapter(List<VideoData> list) {
        mList = list;
    }

    public void setList(List<VideoData> mList) {
        this.mList = mList;
    }

    public interface OnItemClickListener {
        public void onItemClick(VideoData data);

        public void onMoreClick(VideoData data, View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局R.layout.item_one转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_img, parent, false);
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
                mOnItemClickListener.onItemClick(mList.get(position));

            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onMoreClick(mList.get(position), holder.more);
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
        ImageView more;

        public MyHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            more = itemView.findViewById(R.id.more);

        }
    }
}