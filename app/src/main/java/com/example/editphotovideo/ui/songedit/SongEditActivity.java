package com.example.editphotovideo.ui.songedit;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


import com.example.editphotovideo.data.MusicData;
import com.example.editphotovideo.soundfile.CheapSoundFile;
import com.example.editphotovideo.soundfile.CheapSoundFile.ProgressListener;
import com.example.editphotovideo.R;
import com.example.editphotovideo.ui.editmovie.MyApplication;
import com.example.editphotovideo.utils.SongMetadataReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SongEditActivity extends AppCompatActivity {
    private boolean isFromItemClick = false;
    boolean isPlaying = false;
    private MusicAdapter mAdapter;
    private String mArtist;
    private boolean mCanSeekAccurately;
    private float mDensity;
    private int mEndPos;
    private boolean mEndVisible;
    private String mExtension;
    private File mFile;
    private String mFilename = "record";
    private int mFlingVelocity;
    private Handler mHandler;
    private boolean mIsPlaying;
    private boolean mKeyDown;
    private int mLastDisplayedEndPos;
    private int mLastDisplayedStartPos;
    private boolean mLoadingKeepGoing;
    private long mLoadingLastUpdateTime;
    private int mMaxPos;
    private ArrayList<MusicData> mMusicDatas;
    private RecyclerView mMusicList;
    private int mOffset;
    private int mOffsetGoal;
    private ImageButton mPlayButton;
    private OnClickListener mPlayListener = new C08214();
    private int mPlayStartMsec;
    private int mPlayStartOffset;
    private MediaPlayer mPlayer;
    private ProgressDialog mProgressDialog;
    private String mRecordingFilename;
    private Uri mRecordingUri;
    private CheapSoundFile mSoundFile;
    private int mStartPos;
    private TextView mStartText;
    private boolean mStartVisible;
    private String mTitle;
    private boolean mTouchDragging;
    private int mWidth;
    private MusicData selectedMusicData;
    private AppCompatImageView appCompatImageView;

    class C08214 implements OnClickListener {
        C08214() {
        }

        public void onClick(View sender) {
            SongEditActivity.this.onPlay(SongEditActivity.this.mStartPos);
        }
    }

    public class LoadMusics extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;

        protected void onPreExecute() {
            super.onPreExecute();
            this.pDialog = new ProgressDialog(SongEditActivity.this);
            this.pDialog.setTitle("Please wait");
            this.pDialog.setMessage("Loading music...");
            this.pDialog.show();
        }

        protected Void doInBackground(Void... paramVarArgs) {
            SongEditActivity.this.mMusicDatas = SongEditActivity.this.getMusicFiles();
            Log.d("LoadMusics", "Music size: " + mMusicDatas.size());

            if (SongEditActivity.this.mMusicDatas.size() > 0) {
                SongEditActivity.this.selectedMusicData = (MusicData) SongEditActivity.this.mMusicDatas.get(0);
                SongEditActivity.this.mFilename = SongEditActivity.this.selectedMusicData.getTrack_data();
            } else {
                SongEditActivity.this.mFilename = "record";
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.pDialog.dismiss();
            if (!SongEditActivity.this.mFilename.equals("record")) {
                SongEditActivity.this.setUpRecyclerView();
                SongEditActivity.this.loadFromFile();
                SongEditActivity.this.supportInvalidateOptionsMenu();
            } else if (SongEditActivity.this.mMusicDatas.size() > 0) {
                Toast.makeText(SongEditActivity.this.getApplicationContext(), "No Music found in device\nPlease add music in sdCard", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class MusicAdapter extends Adapter<MusicAdapter.Holder> {
        SparseBooleanArray booleanArray = new SparseBooleanArray();
        RadioButton mButton;
        int mSelectedChoice = 0;
        private ArrayList<MusicData> musicDatas;

        public class Holder extends ViewHolder {
            public CheckBox radioMusicName;

            public Holder(View v) {
                super(v);
                this.radioMusicName = (CheckBox) v.findViewById(R.id.radioMusicName);
            }
        }

        public MusicAdapter(ArrayList<MusicData> mMusicDatas) {
            this.musicDatas = mMusicDatas;
            this.booleanArray.put(0, true);
        }

        public Holder onCreateViewHolder(ViewGroup parent, int paramInt) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_items, parent, false));
        }

        public void onBindViewHolder(Holder holder, final int pos) {
            Log.d("LoadMusics", "Binding track: " + musicDatas.get(pos).track_displayName);

            holder.radioMusicName.setText(((MusicData) this.musicDatas.get(pos)).track_displayName);
            holder.radioMusicName.setChecked(this.booleanArray.get(pos, false));
            holder.radioMusicName.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    MusicAdapter.this.booleanArray.clear();
                    MusicAdapter.this.booleanArray.put(pos, true);
                    SongEditActivity.this.onPlay(-1);
                    MusicAdapter.this.playMusic(pos);
                    SongEditActivity.this.isFromItemClick = true;
                    MusicAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public int getItemCount() {
            return this.musicDatas.size();
        }

        public void playMusic(int pos) {
            if (this.mSelectedChoice != pos) {
                SongEditActivity.this.selectedMusicData = (MusicData) SongEditActivity.this.mMusicDatas.get(pos);
                Log.d("selectedMusicData", selectedMusicData.toString());
                SongEditActivity.this.mFilename = SongEditActivity.this.selectedMusicData.getTrack_data();
                SongEditActivity.this.loadFromFile();

                SongEditActivity.this.playMusicFromFile(SongEditActivity.this.mFilename);
            }
            this.mSelectedChoice = pos;
        }
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.mRecordingFilename = null;
        this.mRecordingUri = null;
        this.mPlayer = null;
        this.mIsPlaying = false;
        this.mSoundFile = null;
        this.mKeyDown = false;
        this.mHandler = new Handler();
        loadGui();
        init();
    }

    private void bindView() {
        this.mMusicList = (RecyclerView) findViewById(R.id.rvMusicList);
    }

    private void init() {
        new LoadMusics().execute(new Void[0]);
        appCompatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMusicData == null) {
                    Toast.makeText(SongEditActivity.this, "No music selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProgressDialog dialog = new ProgressDialog(SongEditActivity.this);
                dialog.setTitle(R.string.progress_dialog_saving);
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                new Handler().postDelayed(() -> {
                    dialog.dismiss();
                    MyApplication.getInstance().setMusicData(selectedMusicData);
                    Log.d("selectedMusicData", selectedMusicData.toString());
                    setResult(RESULT_OK);
                    finish();
                }, 500);
            }
        });


    }

    private void setUpRecyclerView() {
        this.mAdapter = new MusicAdapter(this.mMusicDatas);
        this.mMusicList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        this.mMusicList.setItemAnimator(new DefaultItemAnimator());
        this.mMusicList.setAdapter(this.mAdapter);
    }

    private ArrayList<MusicData> getMusicFiles() {
        ArrayList<MusicData> mMusicDatas = new ArrayList<>();

        Cursor mCursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{"_id", "title", "_data", "_display_name", "duration"},
                "is_music != 0",
                null,
                "title ASC"
        );

        if (mCursor != null) {
            int trackId = mCursor.getColumnIndex("_id");
            int trackTitle = mCursor.getColumnIndex("title");
            int trackDisplayName = mCursor.getColumnIndex("_display_name");
            int trackData = mCursor.getColumnIndex("_data");
            int trackDuration = mCursor.getColumnIndex("duration");

            while (mCursor.moveToNext()) {
                String path = mCursor.getString(trackData);
                if (isAudioFile(path)) {
                    MusicData musicData = new MusicData();
                    musicData.track_Id = mCursor.getLong(trackId);
                    musicData.track_Title = mCursor.getString(trackTitle);
                    musicData.track_data = path;
                    musicData.track_duration = mCursor.getLong(trackDuration);
                    musicData.track_displayName = mCursor.getString(trackDisplayName);
                    mMusicDatas.add(musicData);
                }
            }

            mCursor.close();
        } else {
            Log.d("DEBUG", "getMusicFiles: mCursor is null — maybe missing permission?");
        }

        return mMusicDatas;
    }


    private boolean isAudioFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        return path.endsWith(".mp3");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 62) {
            return super.onKeyDown(keyCode, event);
        }
        onPlay(this.mStartPos);
        return true;
    }

    private void loadGui() {
        setContentView((int) R.layout.activity_add_music);
        getWindow().setFlags(1024, 1024);
        bindView();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.mDensity = metrics.density;
        this.mPlayButton = (ImageButton) findViewById(R.id.play);
        this.appCompatImageView = (AppCompatImageView) findViewById(R.id.iv_done);
        this.mPlayButton.setOnClickListener(this.mPlayListener);
        enableDisableButtons();
        this.mMaxPos = 0;
        this.mLastDisplayedStartPos = -1;
        this.mLastDisplayedEndPos = -1;
        this.mStartVisible = true;
        this.mEndVisible = true;
    }

    private void playMusicFromFile(String filePath) {
        try {
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }

            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(filePath);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.prepare();
            mPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to play music", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        this.mFile = new File(this.mFilename);
        this.mExtension = getExtensionFromFilename(this.mFilename);
        SongMetadataReader metadataReader = new SongMetadataReader(this, this.mFilename);
        this.mTitle = metadataReader.mTitle;
        this.mArtist = metadataReader.mArtist;
        String titleLabel = this.mTitle;
        if (this.mArtist != null && this.mArtist.length() > 0) {
            titleLabel = new StringBuilder(String.valueOf(titleLabel)).append(" - ").append(this.mArtist).toString();
        }
        setTitle(titleLabel);
        this.mLoadingLastUpdateTime = System.currentTimeMillis();
        this.mLoadingKeepGoing = true;
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setProgressStyle(1);
        this.mProgressDialog.setCancelable(true);
        this.mProgressDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                SongEditActivity.this.mLoadingKeepGoing = false;
            }
        });
        this.mProgressDialog.show();
        final ProgressListener listener = new ProgressListener() {
            public boolean reportProgress(double fractionComplete) {
                long now = System.currentTimeMillis();
                if (now - SongEditActivity.this.mLoadingLastUpdateTime > 100) {
                    SongEditActivity.this.mProgressDialog.setProgress((int) (((double) SongEditActivity.this.mProgressDialog.getMax()) * fractionComplete));
                    SongEditActivity.this.mLoadingLastUpdateTime = now;
                }
                return SongEditActivity.this.mLoadingKeepGoing;
            }
        };
        this.mCanSeekAccurately = false;
        new Thread() {

            class C08142 implements Runnable {
                C08142() {
                }

                public void run() {
                    SongEditActivity.this.finishOpeningSoundFile();
                    SongEditActivity.this.playMusicFromFile(SongEditActivity.this.mFilename);
                }
            }

            public void run() {
                try {
                    SongEditActivity.this.mSoundFile = CheapSoundFile.create(SongEditActivity.this.mFile.getAbsolutePath(), listener);
                    if (SongEditActivity.this.mSoundFile == null) {
                        String err;
                        SongEditActivity.this.mProgressDialog.dismiss();
                        String[] components = SongEditActivity.this.mFile.getName().toLowerCase().split("\\.");
                        if (components.length < 2) {
                            //    err = SongEditActivity.this.getResources().getString(R.string.no_extension_error);
                        } else {
                            //  err = new StringBuilder(String.valueOf(SongEditActivity.this.getResources().getString(R.string.bad_extension_error))).append(" ").append(components[components.length - 1]).toString();
                        }

                        return;
                    }
                    SongEditActivity.this.mProgressDialog.dismiss();
                    if (SongEditActivity.this.mLoadingKeepGoing) {
                        SongEditActivity.this.mHandler.post(new C08142());
                    } else {
                        SongEditActivity.this.finish();
                    }
                } catch (final Exception e) {
                    SongEditActivity.this.mProgressDialog.dismiss();
                    e.printStackTrace();
                    SongEditActivity.this.mHandler.post(new Runnable() {
                        public void run() {
                            //      SongEditActivity.this.handleFatalError("ReadError", SongEditActivity.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
    }

    @SuppressLint({"ResourceType"})
    private void enableDisableButtons() {
        if (this.mIsPlaying) {
            this.mPlayButton.setImageResource(17301539);
            return;
        }
        this.mPlayButton.setImageResource(17301540);
    }


    private synchronized void handlePause() {
        if (this.mPlayer != null && this.mPlayer.isPlaying()) {
            this.mPlayer.pause();
        }
        this.mIsPlaying = false;
        enableDisableButtons();
    }

    private synchronized void onPlay(int startPosition) {
        if (this.mIsPlaying) {
            handlePause();
        } else if (!(this.mPlayer == null || startPosition == -1)) {
            try {
                this.mPlayStartOffset = 0;
                try {
                    this.mPlayer.reset();
                    this.mPlayer.setAudioStreamType(3);
                    this.mPlayer.prepare();
                    this.mPlayStartOffset = this.mPlayStartMsec;
                } catch (Exception e) {
                    System.out.println("Exception trying to play file subset");
                    this.mPlayer.reset();
                    this.mPlayer.setAudioStreamType(3);
                    this.mPlayer.setDataSource(this.mFile.getAbsolutePath());
                    this.mPlayer.prepare();
                    this.mPlayStartOffset = 0;
                }
                this.mPlayer.setOnCompletionListener(new OnCompletionListener() {
                    public synchronized void onCompletion(MediaPlayer arg0) {
                        SongEditActivity.this.handlePause();
                    }
                });
                this.mIsPlaying = true;
                if (this.mPlayStartOffset == 0) {
                    this.mPlayer.seekTo(this.mPlayStartMsec);
                }
                this.mPlayer.start();
                enableDisableButtons();
            } catch (Exception e2) {
            }
        }
    }

    private void finishOpeningSoundFile() {
        this.mLastDisplayedStartPos = -1;
        this.mLastDisplayedEndPos = -1;
        this.mTouchDragging = false;
        this.mOffset = 0;
        this.mOffsetGoal = 0;
        this.mFlingVelocity = 0;
        if (this.mEndPos > this.mMaxPos) {
            this.mEndPos = this.mMaxPos;
        }
        if (this.isFromItemClick) {
            onPlay(this.mStartPos);
        }
    }


    private String getExtensionFromFilename(String filename) {
        return filename.substring(filename.lastIndexOf(46), filename.length());
    }

    public void onBackPressed() {
        setResult(0);
        super.onBackPressed();
        if (this.isPlaying) {
            this.mPlayer.release();
        }
    }

    protected void onDestroy() {
        if (this.mPlayer != null && this.mPlayer.isPlaying()) {
            this.mPlayer.stop();
        }
        this.mPlayer = null;
        super.onDestroy();
    }
}
