package com.example.editphotovideo.ui.editmovie;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.editphotovideo.MyApplication;
import com.example.editphotovideo.R;
import com.example.editphotovideo.libffmpeg.FileUtils;
import com.example.editphotovideo.utils.ScalingUtilities;

import java.io.FileOutputStream;

public class FrameAdapter extends Adapter<FrameAdapter.Holder> {
    PreviewActivity activity;
    private MyApplication application;
    private int[] drawable = new int[]{-1, R.drawable.f_1, R.drawable.f_15, R.drawable.f_17, R.drawable.f_18, R.drawable.f_2, R.drawable.f_6, R.drawable.f_7, R.drawable.f_3, R.drawable.f_4, R.drawable.f_5, R.drawable.f_10, R.drawable.f_11, R.drawable.f_12, R.drawable.f_13, R.drawable.f_14, R.drawable.f_19, R.drawable.f_20, R.drawable.f_8, R.drawable.f_9, R.drawable.f_23};
    private RequestManager glide;
    private LayoutInflater inflater;
    int lastPos = 0;
    int selectedPos = 0;


    public class Holder extends ViewHolder {
        private RelativeLayout clickableView;
        private ImageView ivThumb;
        private View mainView;
        private TextView tvThemeName;

        public Holder(View v) {
            super(v);
            this.ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
            this.tvThemeName = (TextView) v.findViewById(R.id.tvThemeName);
            this.clickableView = (RelativeLayout) v.findViewById(R.id.clickableView);
            this.mainView = v;
        }
    }

    public FrameAdapter(PreviewActivity activity) {
        this.activity = activity;
        this.application = MyApplication.getInstance();
        this.inflater = LayoutInflater.from(activity);
        this.glide = Glide.with((FragmentActivity) activity);
    }


    public int getItemCount() {
        return this.drawable.length;
    }

    public int getItem(int pos) {
        return this.drawable[pos];
    }

    public void onBindViewHolder(Holder holder, final int pos) {
        final int themes = getItem(pos);
        Log.d("themesivThumb", String.valueOf(themes));
        holder.ivThumb.setScaleType(ScaleType.FIT_XY);
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) holder.ivThumb.getLayoutParams();

        int marginInPx;
        if (pos == 0) {
            marginInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 1,
                    activity.getResources().getDisplayMetrics());
        } else {
            marginInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 9,
                    activity.getResources().getDisplayMetrics());
        }
        layoutParams.setMargins(marginInPx, marginInPx, marginInPx, marginInPx);

        holder.ivThumb.setLayoutParams(layoutParams);
        if (themes != -1) {
            Glide.with(activity)
                    .load(themes)
                    .into(holder.ivThumb);
        } else {
            Glide.with(activity)
                    .load(R.drawable.img_none)
                    .into(holder.ivThumb);
        }

        if (pos == 0) {
            holder.tvThemeName.setVisibility(View.VISIBLE);
            holder.tvThemeName.setText(activity.getString(R.string.none));
            holder.tvThemeName.setTextColor(ContextCompat.getColor(activity, R.color.color_selector_tab));
            Glide.with(activity)
                    .load(R.drawable.img_none_selected)
                    .into(holder.ivThumb);
        }

        if (pos == selectedPos) {
            holder.clickableView.setBackgroundResource(R.drawable.bg_width_radius_green_selected);
            holder.tvThemeName.setTextColor(ContextCompat.getColor(activity, R.color.color_selector_tab));
            holder.tvThemeName.setVisibility(pos == 0 ? View.VISIBLE : View.GONE);

        } else {
            holder.clickableView.setBackgroundResource(R.drawable.bg_width_radius_green_unselected);
            holder.tvThemeName.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.tvThemeName.setVisibility(pos == 0 ? View.VISIBLE : View.GONE);
        }
        if (pos == 0) {
            if (pos == selectedPos) {
                Glide.with(activity).load(R.drawable.img_none_selected).into(holder.ivThumb);
            } else {
                Glide.with(activity).load(R.drawable.img_none).into(holder.ivThumb);
            }
        } else {
            Glide.with(activity).load(themes).into(holder.ivThumb);
        }
        holder.clickableView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (themes != FrameAdapter.this.activity.getFrame()) {
                    FrameAdapter.this.activity.setFrame(themes);
                    int previousPos = selectedPos;
                    selectedPos = pos;
                    notifyItemChanged(previousPos);
                    notifyItemChanged(selectedPos);
                    if (themes != -1) {
                        FrameAdapter.this.notifyItemChanged(FrameAdapter.this.lastPos);
                        FrameAdapter.this.notifyItemChanged(pos);
                        FrameAdapter.this.lastPos = pos;
                        FileUtils.deleteFile(FileUtils.frameFile);
                        try {
                            Bitmap bm = ScalingUtilities.scaleCenterCrop(BitmapFactory.decodeResource(FrameAdapter.this.activity.getResources(), themes), MyApplication.VIDEO_WIDTH, MyApplication.VIDEO_HEIGHT);
                            FileOutputStream outStream = new FileOutputStream(FileUtils.frameFile);
                            bm.compress(CompressFormat.PNG, 100, outStream);
                            outStream.flush();
                            outStream.close();
                            bm.recycle();
                            System.gc();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        });
    }

    public Holder onCreateViewHolder(ViewGroup parent, int pos) {
        return new Holder(this.inflater.inflate(R.layout.movie_theme_items, parent, false));
    }
}
