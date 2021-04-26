package com.geekb.uscfilms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchRvAdapter extends RecyclerView.Adapter<SearchRvAdapter.MyHolder> {

    private List<VideoData> mList;
    private OnItemClickListener mOnItemClickListener;

    SearchRvAdapter(List<VideoData> list) {
        mList = list;
    }

    public void setList(List<VideoData> mList) {
        this.mList = mList;
    }

    public interface OnItemClickListener {
        public void onItemClick(VideoData data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局R.layout.item_one转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
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
        TextView type;
        TextView title;
        TextView score;

        public MyHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            type = itemView.findViewById(R.id.type);
            title = itemView.findViewById(R.id.title);
            score = itemView.findViewById(R.id.score);
        }
    }
}