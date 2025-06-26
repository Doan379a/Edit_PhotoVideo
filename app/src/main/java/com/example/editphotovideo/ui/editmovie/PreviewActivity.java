package com.example.editphotovideo.ui.editmovie;

import static com.example.editphotovideo.utils.ImageUtils.DEFAULT_FOLDER;
import com.example.editphotovideo.data.DSDatabase;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.editphotovideo.MyApplication;
import com.example.editphotovideo.R;
import com.example.editphotovideo.data.ImageData;
import com.example.editphotovideo.data.MusicData;
import com.example.editphotovideo.data.entity.MediaEntity;
import com.example.editphotovideo.data.entity.MediaType;
import com.example.editphotovideo.data.repository.MediaRepository;
import com.example.editphotovideo.data.viewmodel.MediaViewModel;
import com.example.editphotovideo.data.viewmodel.MediaViewModelFactory;
import com.example.editphotovideo.libffmpeg.FileUtils;
import com.example.editphotovideo.service.CreateVideoService;
import com.example.editphotovideo.service.ImageCreatorService;
import com.example.editphotovideo.ui.editmovie.themes.THEMES;
import com.example.editphotovideo.ui.main.MainActivity;
import com.example.editphotovideo.ui.save.KeyNewProject;
import com.example.editphotovideo.ui.save.SaveVideoActivity;
import com.example.editphotovideo.ui.songedit.SongDemoActivity;
import com.example.editphotovideo.ui.songedit.SongEditActivity;
import com.example.editphotovideo.ui.tools.speed.SpeedActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import gun0912.tedimagepicker.builder.TedImagePicker;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import me.blankm.widget.IndicatorSeekBar;
import me.blankm.widget.OnSeekChangeListener;
import me.blankm.widget.SeekParams;


public class PreviewActivity extends AppCompatActivity implements OnClickListener, OnSeekBarChangeListener, OnProgressReceiver {
    private MyApplication application;
    private ArrayList<ImageData> arrayList;
    private BottomSheetBehavior<View> behavior;
    int f21i = 0;
    private View flLoader;
    int frame;
    private FrameAdapter frameAdapter;
    private RequestManager glide;
    private Handler handler = new Handler();
    protected int id;
    private ImageEditAdapter imageEditAdapter;
    LayoutInflater inflater;
    boolean isFromTouch = false;
    private ImageView ivFrame;
    private ImageView ivPlayPause;
    private ImageView ivPreview;
    ArrayList<ImageData> lastData = new ArrayList();
    private LockRunnable lockRunnable = new LockRunnable();
    private MediaPlayer mPlayer;
    private RecyclerView rvFrame;
    private RecyclerView rvThemes;
    private float seconds = 2.0f;
    private SeekBar seekBar;
    private MoviewThemeAdapter themeAdapter;
    private String videoPath;
    private ArrayList<Uri> selectedUris;
    private TextView ivDone;
    private TextView btnAddPhoto, btnTransition, btnMusic,btnDuration,btnFrame,btnEditPhoto,tvTime,tvEndTime;
    private LinearLayout llMusic,ivDeviceMusic,ivDemoMusic;
    private MediaViewModel mediaViewModel;
    private IndicatorSeekBar indicatorSeekBar;
    private RelativeLayout rlDuration;
    private LinearLayout llLoading;
    private Boolean checkSave = false;

    class C05853 implements Runnable {
        C05853() {
        }

        public void run() {
            MyApplication.isBreak = false;
            PreviewActivity.this.application.videoImages.clear();
            PreviewActivity.this.application.min_pos = Integer.MAX_VALUE;
            Intent intent = new Intent(PreviewActivity.this.getApplicationContext(), ImageCreatorService.class);
            intent.putExtra(ImageCreatorService.EXTRA_SELECTED_THEME, PreviewActivity.this.application.getCurrentTheme());
            PreviewActivity.this.startService(intent);

        }
    }

    class C05864 extends Thread {
        C05864() {
        }

        public void run() {
            Glide.get(PreviewActivity.this).clearDiskCache();
        }
    }

    class C05875 implements DialogInterface.OnClickListener {
        C05875() {
        }

        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            PreviewActivity.this.application.videoImages.clear();
            MyApplication.isBreak = true;
            ((NotificationManager) PreviewActivity.this.getSystemService(NOTIFICATION_SERVICE)).cancel(1001);
            PreviewActivity.this.finish();
        }
    }

    class C05906 extends Thread {

        class C05892 implements Runnable {
            C05892() {
            }

            public void run() {
                PreviewActivity.this.reinitMusic();
                PreviewActivity.this.lockRunnable.play();
            }
        }

        C05906() {
        }

        public void run() {
            THEMES themes = PreviewActivity.this.application.selectedTheme;
            try {
//                FileUtils.TEMP_DIRECTORY_AUDIO.mkdirs();
//                File audioDir = new File(getExternalFilesDir(null), "Photo_Video_Edit/.temp_audio");
//                audioDir.mkdirs();
//                File tempFile = new File(audioDir, "temp.mp3");
//                if (tempFile.exists()) {
//                    FileUtils.deleteFile(tempFile);
//                }
//                InputStream in = PreviewActivity.this.getResources().openRawResource(themes.getThemeMusic());
//                FileOutputStream out = new FileOutputStream(tempFile);
//                byte[] buff = new byte[1024];
//                while (true) {
//                    int read = in.read(buff);
//                    if (read <= 0) {
//                        break;
//                    }
//                    out.write(buff, 0, read);
//                }
//                MediaPlayer player = new MediaPlayer();
//                player.setDataSource(tempFile.getAbsolutePath());
//                player.setAudioStreamType(3);
//                player.prepare();
//                final MusicData musicData = new MusicData();
//                musicData.track_data = tempFile.getAbsolutePath();
//                player.setOnPreparedListener(new OnPreparedListener() {
//                    public void onPrepared(MediaPlayer mp) {
//                        musicData.track_duration = (long) mp.getDuration();
//                        mp.start();
//                    }
//                });
//                musicData.track_Title = "temp";
//                PreviewActivity.this.application.setMusicData(musicData);
            } catch (Exception e) {

            }
            PreviewActivity.this.runOnUiThread(new C05892());
        }
    }

    class LockRunnable implements Runnable {
        ArrayList<ImageData> appList = new ArrayList();
        boolean isPause = false;

        class C05921 implements AnimationListener {
            C05921() {
            }

            public void onAnimationStart(Animation animation) {
                ivPlayPause.setImageDrawable(
                        ContextCompat.getDrawable(PreviewActivity.this, R.drawable.ic_pause_white)
                );
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {


            }
        }

        class C05932 implements AnimationListener {
            C05932() {
            }

            public void onAnimationStart(Animation animation) {
                ivPlayPause.setImageDrawable(
                        ContextCompat.getDrawable(PreviewActivity.this, R.drawable.ic_play_white)
                );
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {

            }
        }

        LockRunnable() {
        }

        void setAppList(ArrayList<ImageData> appList) {
            this.appList.clear();
            this.appList.addAll(appList);
        }

        public void run() {
            PreviewActivity.this.displayImage();
            if (!this.isPause) {
                PreviewActivity.this.handler.postDelayed(PreviewActivity.this.lockRunnable, (long) Math.round(50.0f * PreviewActivity.this.seconds));
            }
        }

        public boolean isPause() {
            return this.isPause;
        }

        public void play() {
            this.isPause = false;
            PreviewActivity.this.playMusic();
            PreviewActivity.this.handler.postDelayed(PreviewActivity.this.lockRunnable, (long) Math.round(50.0f * PreviewActivity.this.seconds));
            Animation animation = new AlphaAnimation(1.0f, 1.0f);
            animation.setDuration(500);
            animation.setFillAfter(true);
            animation.setAnimationListener(new C05921());
            PreviewActivity.this.ivPlayPause.startAnimation(animation);
                PreviewActivity.this.application.isEditModeEnable = false;
                if (ImageCreatorService.isImageComplate) {
                    Intent intent = new Intent(PreviewActivity.this.getApplicationContext(), ImageCreatorService.class);
                    intent.putExtra(ImageCreatorService.EXTRA_SELECTED_THEME, PreviewActivity.this.application.getCurrentTheme());
                    PreviewActivity.this.startService(intent);
                }
        }

        public void pause() {
            this.isPause = true;
            PreviewActivity.this.pauseMusic();
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(500);
            animation.setFillAfter(true);
            PreviewActivity.this.ivPlayPause.startAnimation(animation);
            animation.setAnimationListener(new C05932());
        }

        public void stop() {
            pause();
            PreviewActivity.this.f21i = 0;
            if (PreviewActivity.this.mPlayer != null) {
                PreviewActivity.this.mPlayer.stop();
            }
            PreviewActivity.this.reinitMusic();
            PreviewActivity.this.seekBar.setProgress(PreviewActivity.this.f21i);
        }
    }

    class C10211 extends BottomSheetCallback {
        C10211() {
        }

        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == 3 && !PreviewActivity.this.lockRunnable.isPause()) {
                PreviewActivity.this.lockRunnable.pause();
            }
        }

        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    }

    private void reinitMusic() {
        Exception e;
        MusicData musicData = this.application.getMusicData();
        if (musicData != null) {
            Log.d("reinitMusic", String.valueOf(musicData));
            this.mPlayer = MediaPlayer.create(this, Uri.parse(musicData.track_data));
            this.mPlayer.setLooping(true);
            try {
                this.mPlayer.prepare();
                return;
            } catch (Exception e2) {
                e = e2;
            }
        } else {
            return;
        }
        e.printStackTrace();
    }

    private void playMusic() {
        if (this.flLoader.getVisibility() != View.VISIBLE && this.mPlayer != null && !this.mPlayer.isPlaying()) {
            this.mPlayer.start();
        }
    }

    private void pauseMusic() {
        if (this.mPlayer != null && this.mPlayer.isPlaying()) {
            this.mPlayer.pause();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MediaRepository repository = new MediaRepository(DSDatabase.getDatabase(this).mediaDao());
        MediaViewModelFactory factory = new MediaViewModelFactory(repository);
        mediaViewModel = new ViewModelProvider(this, factory).get(MediaViewModel.class);
        selectedUris = getIntent().getParcelableArrayListExtra("selectedImages");
        this.application = MyApplication.getInstance();
        this.application.videoImages.clear();
        MyApplication.isBreak = false;
        application.setOnProgressReceiver(this);
        PreviewActivity.this.application.setSecond(2.0f);
        Intent intent = new Intent(getApplicationContext(), ImageCreatorService.class);
        intent.putExtra(ImageCreatorService.EXTRA_SELECTED_THEME, this.application.getCurrentTheme());
        startService(intent);
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_preview);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        bindView();
        init();
        addListner();
    }

    private void bindView() {
        this.flLoader = findViewById(R.id.flLoader);
        this.ivPreview = (ImageView) findViewById(R.id.previewImageView1);
        this.ivFrame = (ImageView) findViewById(R.id.ivFrame);
        this.seekBar = (SeekBar) findViewById(R.id.sbPlayTime);
        this.tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        this.tvTime = (TextView) findViewById(R.id.tvTime);
     //   this.llEdit = (LinearLayout) findViewById(R.id.llEdit);
        this.ivPlayPause =(ImageView) findViewById(R.id.ivPlayPause);
        this.rvThemes = (RecyclerView) findViewById(R.id.rvThemes);
        this.rvFrame = (RecyclerView) findViewById(R.id.rvFrame);
        this.ivDone = (TextView) findViewById(R.id.iv_done_preview);
        btnAddPhoto = (TextView) findViewById(R.id.ibAddImages);
        btnMusic = (TextView) findViewById(R.id.ibAddMusic);
        btnDuration = (TextView) findViewById(R.id.ibAddDuration);
        btnTransition = (TextView) findViewById(R.id.ibAddTransition);
        btnFrame = (TextView) findViewById(R.id.ibAddFrame);
        btnEditPhoto = (TextView) findViewById(R.id.ibEditMode);
        llMusic = (LinearLayout) findViewById(R.id.llmusic);
        ivDeviceMusic = (LinearLayout) findViewById(R.id.iv_deviceMusic);
        ivDemoMusic = (LinearLayout) findViewById(R.id.iv_demoMusic);
        indicatorSeekBar = (IndicatorSeekBar) findViewById(R.id.indicatorSeekBar);
        rlDuration = (RelativeLayout) findViewById(R.id.rl_duration);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
    }

    private void init() {
        this.seconds = this.application.getSecond();
        this.inflater = LayoutInflater.from(this);
        this.glide = Glide.with((FragmentActivity) this);
        this.arrayList = this.application.getSelectedImages();
        this.seekBar.setMax((this.arrayList.size() - 1) * 30);
        int total = (int) (((float) (this.arrayList.size() - 1)) * this.seconds);
        this.tvEndTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(total / 60), Integer.valueOf(total % 60)}));
        setUpThemeAdapter();
        this.glide.load(((ImageData) this.application.getSelectedImages().get(0)).imagePath).into(this.ivPreview);
        setTheme();
        rvThemes.setVisibility(View.VISIBLE);

        indicatorSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams p) {
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                float value = seekBar.getProgressFloat();
                Log.d("SeekValue", "Current value: " + value);
                PreviewActivity.this.seconds = value;
                PreviewActivity.this.application.setSecond(PreviewActivity.this.seconds);
                PreviewActivity.this.lockRunnable.play();
            }
        });
    }

    private void setUpThemeAdapter() {
        this.themeAdapter = new MoviewThemeAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager((Context) this, 1, RecyclerView.HORIZONTAL, false);
        GridLayoutManager gridLayoutManagerFrame = new GridLayoutManager((Context) this, 1, RecyclerView.HORIZONTAL, false);
        this.rvThemes.setLayoutManager(gridLayoutManager);
        this.rvThemes.setItemAnimator(new DefaultItemAnimator());
        this.rvThemes.setAdapter(this.themeAdapter);
        this.frameAdapter = new FrameAdapter(this);
        this.rvFrame.setLayoutManager(gridLayoutManagerFrame);
        this.rvFrame.setItemAnimator(new DefaultItemAnimator());
        this.rvFrame.setAdapter(this.frameAdapter);
    }

    private void addListner() {
        findViewById(R.id.ibAddImages).setOnClickListener(this);
        findViewById(R.id.video_clicker).setOnClickListener(this);
        this.seekBar.setOnSeekBarChangeListener(this);
        findViewById(R.id.ibAddMusic).setOnClickListener(this);
        findViewById(R.id.ibAddDuration).setOnClickListener(this);
        findViewById(R.id.ibEditMode).setOnClickListener(this);
        findViewById(R.id.iv_done_preview).setOnClickListener(this);
        findViewById(R.id.ibAddFrame).setOnClickListener(this);
        findViewById(R.id.ibAddTransition).setOnClickListener(this);
        findViewById(R.id.iv_deviceMusic).setOnClickListener(this);
        findViewById(R.id.iv_demoMusic).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    private synchronized void displayImage() {
        try {
            if (this.f21i >= this.seekBar.getMax()) {
                this.f21i = 0;
                this.lockRunnable.stop();
            } else {
                if (this.f21i > 0 && this.flLoader.getVisibility() == View.VISIBLE) {
                    this.flLoader.setVisibility(View.GONE);
                    if (!(this.mPlayer == null || this.mPlayer.isPlaying())) {
                        this.mPlayer.start();
                    }
                }
                this.seekBar.setSecondaryProgress(this.application.videoImages.size());
                if (this.seekBar.getProgress() < this.seekBar.getSecondaryProgress()) {
                    this.f21i %= this.application.videoImages.size();
                    String imagePath = this.application.videoImages.get(this.f21i);

                    if (imagePath != null && !imagePath.trim().isEmpty() && !imagePath.equals("-1") && !imagePath.equals("0xffffffff")) {
                        Glide.with(this)
                                .asBitmap()
                                .load(imagePath)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        PreviewActivity.this.ivPreview.setImageBitmap(resource);
                                    }
                                });
                    } else {
                        Log.e("GLIDE_ERROR", "Invalid imagePath: " + imagePath);
                    }

                    this.f21i++;
                    if (!this.isFromTouch) {
                        this.seekBar.setProgress(this.f21i);
                    }
                    int j = (int) ((((float) this.f21i) / 30.0f) * this.seconds);
                    int mm = j / 60;
                    int ss = j % 60;
                    this.tvTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(mm), Integer.valueOf(ss)}));
                    int total = (int) (((float) (this.arrayList.size() - 1)) * this.seconds);
                    String formattedTime = String.format("%02d:%02d", new Object[]{Integer.valueOf(total / 60), Integer.valueOf(total % 60)});
                    String[] parts = formattedTime.split(":");
                    int minutes = Integer.parseInt(parts[0]);
                    int seconds = Integer.parseInt(parts[1]);
                    float totalSeconds = minutes * 60 + seconds;
                    application.setDuration(totalSeconds+1.0f);
                    Log.d("totalSeconds", String.valueOf(totalSeconds));
                    this.tvEndTime.setText(formattedTime);
                }
            }
        } catch (Exception e) {
            this.glide = Glide.with((FragmentActivity) this);
        }
    }

    @SuppressLint({"WrongConstant"})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ibAddDuration) {
            resetButtonColors();
            btnDuration.setTextColor(getResources().getColor(R.color.color_selector_tab));
            btnDuration.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_duration_editvideo,0,0);
            rlDuration.setVisibility(View.VISIBLE);
           // this.behavior.setState(3);
        } else if (id == R.id.ibAddImages) {
            this.flLoader.setVisibility(View.GONE);
            MyApplication.isBreak = true;
            this.application.isEditModeEnable = true;
            this.lastData.clear();
            this.lastData.addAll(this.arrayList);
            TedImagePicker.with(PreviewActivity.this)
                    .selectedUri(selectedUris)
                    .min(3, getString(R.string.Please_select_at_least_3_photos))
                    .startMultiImage(uriList -> {
                        application.isEditModeEnable = false;
                        application.clearAllSelection();
                        for (Uri uri : uriList) {
                            String path = getRealPathFromUri(PreviewActivity.this, uri);
                            ImageData data = new ImageData();
                            data.setImagePath(path);
                            data.folderName = "FromPicker";
                            data.setImageCount(1);
                            application.addSelectedImage(data);
                        }
                        resetPreview();
                    });
        } else if (id == R.id.ibAddMusic) {
            resetButtonColors();
            btnMusic.setTextColor(getResources().getColor(R.color.color_selector_tab));
            btnMusic.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_music_selected_editvideo,0,0);
            llMusic.setVisibility(View.VISIBLE);
        }else if (id == R.id.iv_deviceMusic){
            this.flLoader.setVisibility(View.GONE);
            loadSongSelection();
        }else if (id == R.id.iv_demoMusic){
            startActivityForResult(new Intent(this, SongDemoActivity.class), 101);
        }
        else if (id == R.id.ibEditMode) {
            this.flLoader.setVisibility(View.GONE);
            this.application.isEditModeEnable = true;
            this.lockRunnable.pause();
            Intent intent = new Intent(this, ImageEditActivity.class);
            intent.putExtra("extra_from_preview", true);
            startActivityForResult(intent, 103);
        }else if (id == R.id.ibAddTransition){
            resetButtonColors();
            btnTransition.setTextColor(getResources().getColor(R.color.color_selector_tab));
            btnTransition.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_transition_selected_editvideo,0,0);
            rvThemes.setVisibility(View.VISIBLE);
        }else if (id == R.id.ibAddFrame){
            resetButtonColors();
            btnFrame.setTextColor(getResources().getColor(R.color.color_selector_tab));
            btnFrame.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_frame_selected_editvideo,0,0);
            rvFrame.setVisibility(View.VISIBLE);
        } else if (id == R.id.video_clicker) {
            if (this.lockRunnable.isPause()) {
                this.lockRunnable.play();
            } else {
                this.lockRunnable.pause();
            }
        } else if (id == R.id.iv_done_preview) {
            checkSave = true;
            loadProgress();
        }else if (id == R.id.iv_back){
            onBackPressed();
        }
    }

    @SuppressLint("Recycle")
    public String getRealPathFromUri(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            return cursor.getString(column_index);
        }
        return null;
    }

    private void resetPreview() {
        MyApplication.isBreak = false;
        this.arrayList = application.getSelectedImages();
        application.videoImages.clear();
        application.min_pos = Integer.MAX_VALUE;

        Intent intent = new Intent(getApplicationContext(), ImageCreatorService.class);
        intent.putExtra(ImageCreatorService.EXTRA_SELECTED_THEME, application.getCurrentTheme());
        startService(intent);

        f21i = 0;
        seekBar.setProgress(0);
        seekBar.setMax((arrayList.size() - 1) * 30);

        int total = (int) (((float) (arrayList.size() - 1)) * seconds);
        tvEndTime.setText(String.format("%02d:%02d", total / 60, total % 60));
    }


    protected void onPause() {
        super.onPause();
        this.lockRunnable.pause();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.application.isEditModeEnable = false;
        if (resultCode == -1) {
            int total;
            Intent intent;
            switch (requestCode) {
                case 101:
                    this.application.isFromSdCardAudio = true;
                    this.f21i = 0;
                    reinitMusic();
                    return;
                case 102:
                    if (isNeedRestart()) {
                        stopService(new Intent(getApplicationContext(), ImageCreatorService.class));
                        this.lockRunnable.stop();
                        this.seekBar.postDelayed(new C05853(), 1000);
                        total = (int) (((float) (this.arrayList.size() - 1)) * this.seconds);
                        this.arrayList = this.application.getSelectedImages();
                        this.seekBar.setMax((this.application.getSelectedImages().size() - 1) * 30);
                        this.tvEndTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(total / 60), Integer.valueOf(total % 60)}));
                        return;
                    }
                    if (ImageCreatorService.isImageComplate) {
                        MyApplication.isBreak = false;
                        this.application.videoImages.clear();
                        this.application.min_pos = Integer.MAX_VALUE;
                        intent = new Intent(getApplicationContext(), ImageCreatorService.class);
                        intent.putExtra(ImageCreatorService.EXTRA_SELECTED_THEME, this.application.getCurrentTheme());
                        startService(intent);
                        this.f21i = 0;
                        this.seekBar.setProgress(0);
                    }
                    total = (int) (((float) (this.arrayList.size() - 1)) * this.seconds);
                    this.arrayList = this.application.getSelectedImages();
                    this.seekBar.setMax((this.application.getSelectedImages().size() - 1) * 30);
                    this.tvEndTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(total / 60), Integer.valueOf(total % 60)}));
                    return;
                case 103:
                    this.lockRunnable.stop();
                    if (ImageCreatorService.isImageComplate || !MyApplication.isMyServiceRunning(this.application, ImageCreatorService.class)) {
                        MyApplication.isBreak = false;
                        this.application.videoImages.clear();
                        this.application.min_pos = Integer.MAX_VALUE;
                        intent = new Intent(getApplicationContext(), ImageCreatorService.class);
                        intent.putExtra(ImageCreatorService.EXTRA_SELECTED_THEME, this.application.getCurrentTheme());
                        startService(intent);
                    }
                    this.f21i = 0;
                    this.seekBar.setProgress(this.f21i);
                    this.arrayList = this.application.getSelectedImages();
                    total = (int) (((float) (this.arrayList.size() - 1)) * this.seconds);
                    this.seekBar.setMax((this.application.getSelectedImages().size() - 1) * 30);
                    this.tvEndTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(total / 60), Integer.valueOf(total % 60)}));
                    return;
                default:
                    return;
            }
        }
    }

    private boolean isNeedRestart() {
        if (this.lastData.size() > this.application.getSelectedImages().size()) {
            MyApplication.isBreak = true;
            Log.e("isNeedRestart", "isNeedRestart size");
            return true;
        }
        int i = 0;
        while (i < this.lastData.size()) {
            if (((ImageData) this.lastData.get(i)).imagePath.equals(((ImageData) this.application.getSelectedImages().get(i)).imagePath)) {
                i++;
            } else {
                MyApplication.isBreak = true;
                return true;
            }
        }
        return false;
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.f21i = progress;
        if (this.isFromTouch) {
            seekBar.setProgress(Math.min(progress, seekBar.getSecondaryProgress()));
            displayImage();
            seekMediaPlayer();
        }
    }

    private void seekMediaPlayer() {
        if (this.mPlayer != null) {
            try {
                this.mPlayer.seekTo(((int) (((((float) this.f21i) / 30.0f) * this.seconds) * 1000.0f)) % this.mPlayer.getDuration());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        this.isFromTouch = true;
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        this.isFromTouch = false;
    }

    public void reset() {
        MyApplication.isBreak = false;
        this.application.videoImages.clear();
        this.handler.removeCallbacks(this.lockRunnable);
        this.lockRunnable.stop();
        Glide.get(this).clearMemory();
        new C05864().start();
        FileUtils.deleteTempDir();
        this.glide = Glide.with((FragmentActivity) this);
        this.flLoader.setVisibility(View.VISIBLE);
        setTheme();
    }

    public void onBackPressed() {
        this.application.isEditModeEnable = false;
        if (checkSave){
            return;
        }
        onBackDialog();
//        super.onBackPressed();
    }

    private void onBackDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_alert, null);
            builder.setView(dialogView);

            AlertDialog dialog = builder.create();

            TextView btnCancel = dialogView.findViewById(R.id.tv_cancel);
            TextView btnExit = dialogView.findViewById(R.id.tv_exit);

            btnCancel.setOnClickListener(v -> dialog.dismiss());

            btnExit.setOnClickListener(v -> {
                if (mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                }

//                finish();?
                Intent intent=new Intent(PreviewActivity.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
                dialog.dismiss();
            });

            dialog.show();
    }

    public void setTheme() {
        if (this.application.isFromSdCardAudio) {
            this.lockRunnable.play();
        } else {
            new C05906().start();
        }
    }

    private void startService() {
        MyApplication.isBreak = false;
        this.application.videoImages.clear();
        this.application.min_pos = Integer.MAX_VALUE;
        Intent intent = new Intent(getApplicationContext(), ImageCreatorService.class);
        intent.putExtra(ImageCreatorService.EXTRA_SELECTED_THEME, this.application.getCurrentTheme());
        startService(intent);
        this.seekBar.setProgress(0);
        this.f21i = 0;
        int total = (int) (((float) (this.arrayList.size() - 1)) * this.seconds);
        this.arrayList = this.application.getSelectedImages();
        this.seekBar.setMax((this.application.getSelectedImages().size() - 1) * 30);
        this.tvEndTime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(total / 60), Integer.valueOf(total % 60)}));
        if (this.mPlayer != null) {
            this.mPlayer.seekTo(0);
        }
    }

    public void setFrame(int data) {
        this.frame = data;
        if (data == -1) {
            this.ivFrame.setImageDrawable(null);
        } else {
            this.ivFrame.setImageResource(data);
        }
        this.application.setFrame(data);
    }

    public int getFrame() {
        return this.application.getFrame();
    }

    private void loadSongSelection() {
        startActivityForResult(new Intent(this, SongEditActivity.class), 101);
    }

    @SuppressLint({"WrongConstant"})
    private void loadProgress() {
        llLoading.setVisibility(View.VISIBLE);
        this.lockRunnable.stop();
        this.handler.removeCallbacks(this.lockRunnable);
        startService(new Intent(this, CreateVideoService.class));
        ImageCreatorService.isImageComplate = true;
    }

    @Override
    public void onImageProgressFrameUpdate(float f) {

    }

    public void onProgressFinish(String videoPath) {
        this.videoPath = videoPath;

        File src = new File(videoPath);
        File dst = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), DEFAULT_FOLDER);

        if (!dst.exists()) dst.mkdirs();

        File outFile = new File(dst, src.getName());
        try {
            Files.copy(src.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Uri videoUri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                outFile
        );
        MediaScannerConnection.scanFile(
                PreviewActivity.this,
                new String[]{ videoUri.toString() },
                new String[]{ "video/mp4" },
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d("MediaScan", "Scanned " + path + ": uri=" + uri);
                    }
                }
        );

        MediaEntity entity = new MediaEntity(
                videoUri.toString(),
                MediaType.VIDEO
        );
        mediaViewModel.insertMedia(entity);
        Intent intent = new Intent(PreviewActivity.this, SaveVideoActivity.class);
        intent.putExtra("URI_VIDEO_INPUT", videoUri.toString());
        intent.putExtra("KEY_ACTIVITY", KeyNewProject.EDIT_VIDEO_ACTIVITY.name());
        startActivity(intent);
        llLoading.setVisibility(View.GONE);
        checkSave= false;
        PreviewActivity.this.application.setMusicData(null);
        Log.d("loadProgress", videoPath);
    }

    @Override
    public void onVideoProgressFrameUpdate(float f) {

    }
    public void resetButtonColors(){
             rvFrame.setVisibility(View.GONE);
             rvThemes.setVisibility(View.GONE);
             llMusic.setVisibility(View.GONE);
            rlDuration.setVisibility(View.GONE);
            btnMusic.setTextColor(getResources().getColor(R.color.white));
            btnDuration.setTextColor(getResources().getColor(R.color.white));
            btnTransition.setTextColor(getResources().getColor(R.color.white));
            btnFrame.setTextColor(getResources().getColor(R.color.white));


            btnMusic.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_music_editvideo, 0, 0);
            btnDuration.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_duration_editvideo, 0, 0);
            btnTransition.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_transition_editvideo, 0, 0);
            btnFrame.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_frame_editvideo, 0, 0);


    }

}
