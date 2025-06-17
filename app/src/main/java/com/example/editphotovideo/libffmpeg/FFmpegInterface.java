package com.example.editphotovideo.libffmpeg;

import com.example.editphotovideo.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.example.editphotovideo.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.util.Map;

interface FFmpegInterface {
    void execute(Map<String, String> map, String[] strArr, FFmpegExecuteResponseHandler fFmpegExecuteResponseHandler) throws FFmpegCommandAlreadyRunningException;

    void execute(String[] strArr, FFmpegExecuteResponseHandler fFmpegExecuteResponseHandler) throws FFmpegCommandAlreadyRunningException;

    String getDeviceFFmpegVersion() throws FFmpegCommandAlreadyRunningException;

    String getLibraryFFmpegVersion();

    boolean isFFmpegCommandRunning();

    boolean killRunningProcesses();

    void loadBinary(FFmpegLoadBinaryResponseHandler fFmpegLoadBinaryResponseHandler) throws FFmpegNotSupportedException;

    void setTimeout(long j);
}
