package com.example.editphotovideo.soundfile;

//import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CheapMP3 extends CheapSoundFile {
    private static int[] BITRATES_MPEG1_L3 = new int[]{0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 0};
    private static int[] BITRATES_MPEG2_L3 = new int[]{0, 8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160, 0};
    private static int[] SAMPLERATES_MPEG1_L3 = new int[]{44100, 48000, 32000, 0};
    private static int[] SAMPLERATES_MPEG2_L3 = new int[]{22050, 24000, 16000, 0};
    private int mAvgBitRate;
    private int mBitrateSum;
    private int mFileSize;
    private int[] mFrameGains;
    private int[] mFrameLens;
    private int[] mFrameOffsets;
    private int mGlobalChannels;
    private int mGlobalSampleRate;
    private int mMaxFrames;
    private int mMaxGain;
    private int mMinGain;
    private int mNumFrames;

    static class C11721 implements Factory {
        C11721() {
        }

        public CheapSoundFile create() {
            return new CheapMP3();
        }

        public String[] getSupportedExtensions() {
            return new String[]{"mp3"};
        }
    }

    public static Factory getFactory() {
        return new C11721();
    }

    public int getNumFrames() {
        return this.mNumFrames;
    }

    public int[] getFrameOffsets() {
        return this.mFrameOffsets;
    }

    public int getSamplesPerFrame() {
        return 1152;
    }

    public int[] getFrameLens() {
        return this.mFrameLens;
    }

    public int[] getFrameGains() {
        return this.mFrameGains;
    }

    public int getFileSizeBytes() {
        return this.mFileSize;
    }

    public int getAvgBitrateKbps() {
        return this.mAvgBitRate;
    }

    public int getSampleRate() {
        return this.mGlobalSampleRate;
    }

    public int getChannels() {
        return this.mGlobalChannels;
    }

    public String getFiletype() {
        return "MP3";
    }

    public int getSeekableFrameOffset(int frame) {
        if (frame <= 0) {
            return 0;
        }
        if (frame >= this.mNumFrames) {
            return this.mFileSize;
        }
        return this.mFrameOffsets[frame];
    }

    public void ReadFile(File inputFile) throws FileNotFoundException, IOException {
        super.ReadFile(inputFile);
        this.mNumFrames = 0;
        this.mMaxFrames = 64;
        this.mFrameOffsets = new int[this.mMaxFrames];
        this.mFrameLens = new int[this.mMaxFrames];
        this.mFrameGains = new int[this.mMaxFrames];
        this.mBitrateSum = 0;
        this.mMinGain = 255;
        this.mMaxGain = 0;
        this.mFileSize = (int) this.mInputFile.length();
        FileInputStream stream = new FileInputStream(this.mInputFile);
        int pos = 0;
        int offset = 0;
        byte[] buffer = new byte[12];
        while (pos < mFileSize - 12) {
            // Read 12 bytes at a time and look for a sync code (0xFF)
            while (offset < 12) {
                offset += stream.read(buffer, offset, 12 - offset);
            }
            int bufferOffset = 0;
            while (bufferOffset < 12 &&
                    buffer[bufferOffset] != -1)
                bufferOffset++;

            if (mProgressListener != null) {
                boolean keepGoing = mProgressListener.reportProgress(
                        pos * 1.0 / mFileSize);
                if (!keepGoing) {
                    break;
                }
            }

            if (bufferOffset > 0) {
                // We didn't find a sync code (0xFF) at position 0;
                // shift the buffer over and try again
                for (int i = 0; i < 12 - bufferOffset; i++)
                    buffer[i] = buffer[bufferOffset + i];
                pos += bufferOffset;
                offset = 12 - bufferOffset;
                continue;
            }

            // Check for MPEG 1 Layer III or MPEG 2 Layer III codes
            int mpgVersion = 0;
            if (buffer[1] == -6 || buffer[1] == -5) {
                mpgVersion = 1;
            } else if (buffer[1] == -14 || buffer[1] == -13) {
                mpgVersion = 2;
            } else {
                bufferOffset = 1;
                for (int i = 0; i < 12 - bufferOffset; i++)
                    buffer[i] = buffer[bufferOffset + i];
                pos += bufferOffset;
                offset = 12 - bufferOffset;
                continue;
            }

            // The third byte has the bitrate and samplerate
            int bitRate;
            int sampleRate;
            if (mpgVersion == 1) {
                // MPEG 1 Layer III
                bitRate = BITRATES_MPEG1_L3[(buffer[2] & 0xF0) >> 4];
                sampleRate = SAMPLERATES_MPEG1_L3[(buffer[2] & 0x0C) >> 2];
            } else {
                // MPEG 2 Layer III
                bitRate = BITRATES_MPEG2_L3[(buffer[2] & 0xF0) >> 4];
                sampleRate = SAMPLERATES_MPEG2_L3[(buffer[2] & 0x0C) >> 2];
            }

            if (bitRate == 0 || sampleRate == 0) {
                bufferOffset = 2;
                for (int i = 0; i < 12 - bufferOffset; i++)
                    buffer[i] = buffer[bufferOffset + i];
                pos += bufferOffset;
                offset = 12 - bufferOffset;
                continue;
            }

            // From here on we assume the frame is good
            mGlobalSampleRate = sampleRate;
            int padding = (buffer[2] & 2) >> 1;
            int frameLen = 144 * bitRate * 1000 / sampleRate + padding;

            int gain;
            if ((buffer[3] & 0xC0) == 0xC0) {
                // 1 channel
                mGlobalChannels = 1;
                if (mpgVersion == 1) {
                    gain = ((buffer[10] & 0x01) << 7) +
                            ((buffer[11] & 0xFE) >> 1);
                } else {
                    gain = ((buffer[9] & 0x03) << 6) +
                            ((buffer[10] & 0xFC) >> 2);
                }
            } else {
                // 2 channels
                mGlobalChannels = 2;
                if (mpgVersion == 1) {
                    gain = ((buffer[9]  & 0x7F) << 1) +
                            ((buffer[10] & 0x80) >> 7);
                } else {
                    gain = 0;  // ???
                }
            }

            mBitrateSum += bitRate;

            mFrameOffsets[mNumFrames] = pos;
            mFrameLens[mNumFrames] = frameLen;
            mFrameGains[mNumFrames] = gain;
            if (gain < mMinGain)
                mMinGain = gain;
            if (gain > mMaxGain)
                mMaxGain = gain;

            mNumFrames++;
            if (mNumFrames == mMaxFrames) {
                // We need to grow our arrays.  Rather than naively
                // doubling the array each time, we estimate the exact
                // number of frames we need and add 10% padding.  In
                // practice this seems to work quite well, only one
                // resize is ever needed, however to avoid pathological
                // cases we make sure to always double the size at a minimum.

                mAvgBitRate = mBitrateSum / mNumFrames;
                int totalFramesGuess =
                        ((mFileSize / mAvgBitRate) * sampleRate) / 144000;
                int newMaxFrames = totalFramesGuess * 11 / 10;
                if (newMaxFrames < mMaxFrames * 2)
                    newMaxFrames = mMaxFrames * 2;

                int[] newOffsets = new int[newMaxFrames];
                int[] newLens = new int[newMaxFrames];
                int[] newGains = new int[newMaxFrames];
                for (int i = 0; i < mNumFrames; i++) {
                    newOffsets[i] = mFrameOffsets[i];
                    newLens[i] = mFrameLens[i];
                    newGains[i] = mFrameGains[i];
                }
                mFrameOffsets = newOffsets;
                mFrameLens = newLens;
                mFrameGains = newGains;
                mMaxFrames = newMaxFrames;
            }

            stream.skip(frameLen - 12);
            pos += frameLen;
            offset = 0;
        }

        // We're done reading the file, do some postprocessing
        if (mNumFrames > 0)
            mAvgBitRate = mBitrateSum / mNumFrames;
        else
            mAvgBitRate = 0;
    }

    public void WriteFile(File outputFile, int startFrame, int numFrames) throws IOException {
                int i;
                outputFile.createNewFile();
                FileInputStream in = new FileInputStream(this.mInputFile);
                FileOutputStream out = new FileOutputStream(outputFile);
                int maxFrameLen = 0;
                for (i = 0; i < numFrames; i++) {
                    if (this.mFrameLens[startFrame + i] > maxFrameLen) {
                        maxFrameLen = this.mFrameLens[startFrame + i];
                    }
                }
                byte[] buffer = new byte[maxFrameLen];
                int pos = 0;
                for (i = 0; i < numFrames; i++) {
                    int skip = this.mFrameOffsets[startFrame + i] - pos;
                    int len = this.mFrameLens[startFrame + i];
                    if (skip > 0) {
                        in.skip((long) skip);
                        pos += skip;
                    }
                    in.read(buffer, 0, len);
                    out.write(buffer, 0, len);
                    pos += len;
                }
                in.close();
                out.close();
            }
        }
