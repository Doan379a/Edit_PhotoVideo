package com.example.editphotovideo.service;

import android.app.IntentService;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Audio.Media;
import android.text.TextUtils;
import android.util.Log;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegKitConfig;
import com.arthenica.ffmpegkit.ReturnCode;
import com.arthenica.ffmpegkit.Session;
import com.example.editphotovideo.MyApplication;
import com.example.editphotovideo.R;
import com.example.editphotovideo.libffmpeg.FileUtils;
import com.example.editphotovideo.libffmpeg.Util;
import com.example.editphotovideo.ui.editmovie.OnProgressReceiver;
import com.example.editphotovideo.utils.ScalingUtilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateVideoService extends IntentService {
    MyApplication application;
    private File audioFile;
    private File audioIp;
    int last;
    private Builder mBuilder;
    private NotificationManager mNotifyManager;
    String timeRe;
    private float toatalSecond;

    public CreateVideoService() {
        this(CreateVideoService.class.getName());
    }

    public CreateVideoService(String name) {
        super(name);
        this.timeRe = "\\btime=\\b\\d\\d:\\d\\d:\\d\\d.\\d\\d";
        this.last = 0;
    }

    protected void onHandleIntent(Intent intent) {
        this.application = MyApplication.getInstance();
        this.mNotifyManager = (NotificationManager) getSystemService("notification");
        this.mBuilder = new Builder(this);
        this.mBuilder.setContentTitle("Creating Video").setContentText("Making in progress").setSmallIcon(R.mipmap.ic_launcher);
        createVideo();
    }
    private void createVideo() {
        long startTime = System.currentTimeMillis();
        this.toatalSecond = (this.application.getSecond() * ((float) this.application.getSelectedImages().size())) - 1.0f;
        joinAudio();
        while (!ImageCreatorService.isImageComplate) {
            Log.e("isImageComplate", "ImageCreatorService.isImageComplate");
        }
        File videoListFile = new File(FileUtils.TEMP_DIRECTORY, "video.txt");
        if (videoListFile.exists()) videoListFile.delete();
        Log.d("videoListSize", String.valueOf(application.videoImages.size()));
        float targetDurationSeconds = application.getDuration();
        int imageCount = application.videoImages.size();
        float durationPerImage = targetDurationSeconds / imageCount;
        for (int i = 0; i < application.videoImages.size(); i++) {
            String path = application.videoImages.get(i);
            appendVideoLog("file '" + path + "'");
            Log.d("videoListFile", "file '" + path + "'");
                appendVideoLog(String.format(Locale.US, "duration %.2f", durationPerImage));
        }
        String lastPath = application.videoImages.get(imageCount - 1);
        appendVideoLog("file '" + lastPath + "'");
        Log.d("videoListFile", "file '" + lastPath + "'");
        String videoPath = new File(FileUtils.APP_DIRECTORY, getVideoName()).getAbsolutePath();
        Log.d("videoPath", videoPath);
        StringBuilder commandBuilder = new StringBuilder();

        commandBuilder
                .append("-y ")
                .append("-fflags +genpts ")
                .append("-f concat -safe 0 ")
                .append("-i ").append("\"").append(videoListFile.getAbsolutePath()).append("\" ");


        if (this.application.getMusicData() != null) {
            commandBuilder.append("-i ").append("\"").append(this.audioFile.getAbsolutePath()).append("\" ");
        }
        if (this.application.getFrame() != -1) {
            if (!FileUtils.frameFile.exists()) {
                try {
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), this.application.getFrame());
                    if (bm.getWidth() != MyApplication.VIDEO_WIDTH || bm.getHeight() != MyApplication.VIDEO_HEIGHT) {
                        bm = ScalingUtilities.scaleCenterCrop(bm, MyApplication.VIDEO_WIDTH, MyApplication.VIDEO_HEIGHT);
                    }
                    FileOutputStream out = new FileOutputStream(FileUtils.frameFile);
                    bm.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush(); out.close();
                    bm.recycle(); System.gc();
                } catch (Exception ignored) {}
            }
            if (this.application.getMusicData() != null) {
                commandBuilder.append("-i ").append("\"").append(FileUtils.frameFile.getAbsolutePath()).append("\" ")
                        .append("-filter_complex \"[0:v][2:v]overlay=0:0\" ");
            }else {
                commandBuilder.append("-i ").append("\"").append(FileUtils.frameFile.getAbsolutePath()).append("\" ")
                        .append("-filter_complex \"[0:v][1:v]overlay=0:0\" ");
            }
        }

        commandBuilder
                .append("-vsync vfr ")
                .append("-c:v libx264 ")
                .append("-preset ultrafast ")
                .append("-b:v 2000k ")
                .append("-pix_fmt yuv420p ")
                .append("-t ").append(application.getDuration()).append(" ");

        if (application.getMusicData() != null) {
            commandBuilder.append("-c:a aac -b:a 192k ");
        }

        commandBuilder.append("\"").append(videoPath).append("\"");


        Session session = FFmpegKit.execute(commandBuilder.toString());

        if (ReturnCode.isSuccess(session.getReturnCode())) {
            Log.d("FFmpegKit", "Video created successfully!");
        } else {
            Log.e("FFmpegKit", "Failed: " + session.getFailStackTrace());
        }

        this.mBuilder.setContentText("Video created :" + FileUtils.getDuration(System.currentTimeMillis() - startTime)).setProgress(0, 0, false);

        try {
            long fileSize = new File(videoPath).length();
            ContentValues values = new ContentValues();
            values.put("_data", videoPath);
            values.put("_size", fileSize);
            values.put("mime_type", "video/mp4");
            values.put("artist", getResources().getString(R.string.app_name));
            values.put("duration", application.getDuration());
            getContentResolver().insert(Media.getContentUriForPath(videoPath), values);
        } catch (Exception ignored) {}

        try {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(videoPath))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.application.clearAllSelection();
        final String str = videoPath;
        new Handler(Looper.getMainLooper()).post(() -> {
            OnProgressReceiver receiver = CreateVideoService.this.application.getOnProgressReceiver();
            if (receiver != null) {
                receiver.onVideoProgressFrameUpdate(100.0f);
                receiver.onProgressFinish(str);
            }
        });

        FileUtils.deleteTempDir();
        this.application.setFrame(-1);
        this.application.clearAllSelection();
        this.application.isFromSdCardAudio = false;
        ImageCreatorService.isImageComplate = false;
        stopSelf();
    }

    private void joinAudio() {

        if (this.application.getMusicData() == null || this.application.getMusicData().track_data == null) {
            Log.d("FFmpegKit", "Không có nhạc nền, bỏ qua joinAudio()");
            return;
        }

        this.audioIp = new File(FileUtils.TEMP_DIRECTORY, "audio.txt");
        this.audioFile = new File(FileUtils.APP_DIRECTORY, "audio.mp3");

        if (audioIp.exists()) audioIp.delete();
        if (audioFile.exists()) audioFile.delete();

        int d = 0;
        while (true) {
            appendAudioLog(String.format("file '%s'", this.application.getMusicData().track_data));
            if (this.toatalSecond * 1000.0f <= (this.application.getMusicData().track_duration * ((long) d))) {
                break;
            }
            d++;
        }
        String command = String.format(
                "-y -f concat -safe 0 -i \"%s\" -c:a libmp3lame -b:a 192k \"%s\"",
                audioIp.getAbsolutePath(),
                audioFile.getAbsolutePath()
        );
        Log.d("FFmpegKit", "Join audio command: " + command);
        FFmpegKit.execute(command);
    }

    private int durationToprogtess(String input) {
        int progress = 0;
        Matcher matcher = Pattern.compile(this.timeRe).matcher(input);
        int HOUR = 60 * 60;
        if (TextUtils.isEmpty(input) || !input.contains("time=")) {
            Log.e("time", "not contain time " + input);
            return this.last;
        }
        while (matcher.find()) {
            String time = matcher.group();
            String[] splitTime = time.substring(time.lastIndexOf(61) + 1).split(":");
            float hour = ((Float.valueOf(splitTime[0]).floatValue() * ((float) HOUR)) + (Float.valueOf(splitTime[1]).floatValue() * ((float) 60))) + Float.valueOf(splitTime[2]).floatValue();
            Log.e("time", "totalSecond:" + hour);
            progress = (int) ((100.0f * hour) / this.toatalSecond);
            updateInMili(hour);
        }
        this.last = progress;
        return progress;
    }

    private void updateInMili(float time) {
        final double progress = (((double) time) * 100.0d) / ((double) this.toatalSecond);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                OnProgressReceiver receiver = CreateVideoService.this.application.getOnProgressReceiver();
                if (receiver != null) {
                    Log.e("timeToatal", "progress __" + progress);
                    receiver.onVideoProgressFrameUpdate((float) progress);
                }
            }
        });
    }

    private String getVideoName() {
        return "video_" + new SimpleDateFormat("yyyy_MMM_dd_HH_mm_ss", Locale.ENGLISH).format(new Date()) + ".mp4";
    }

    public void appendVideoLog(String text) {
        File videoLog = new File(FileUtils.TEMP_DIRECTORY, "video.txt");

        File parentDir = videoLog.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            Log.d("CHECK_DIR", "Created parent: " + created + " Path: " + parentDir.getAbsolutePath());
            if (!created) {
                Log.e("CHECK_DIR", "Không thể tạo thư mục: " + parentDir.getAbsolutePath());
            }
        }
        try {
            if (!videoLog.exists()) {
                boolean createdFile = videoLog.createNewFile();
                Log.d("CHECK_FILE", "Created file: " + createdFile);
            }

            BufferedWriter buf = new BufferedWriter(new FileWriter(videoLog, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            Log.e("appendVideoLog", "Lỗi khi ghi video.txt: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void appendAudioLog(String text) {
        File audioLog = new File(FileUtils.TEMP_DIRECTORY, "audio.txt");
        Log.d("TEMP_PATHAudioLog", audioLog.getAbsolutePath());

        try {
            if (!FileUtils.TEMP_DIRECTORY.exists()) {
                FileUtils.TEMP_DIRECTORY.mkdirs();
            }

            if (!audioLog.exists()) {
                audioLog.createNewFile();
            }

            BufferedWriter buf = new BufferedWriter(new FileWriter(audioLog, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("appendAudioLog", "Lỗi khi ghi audio.txt: " + e.getMessage());
        }
    }

}
