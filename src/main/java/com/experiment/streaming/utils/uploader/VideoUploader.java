package com.experiment.streaming.utils.uploader;

import com.experiment.streaming.utils.ContentUtils;
import com.experiment.streaming.utils.PathUtils;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VideoUploader extends Uploader<VideoUploader> {
    private String contentName;
    private Path convertedFilePath;
    private Path convertedFileShortPath;
    private FFmpegProbeResult probeResult;

    private static FFprobe ffprobe;
    private static FFmpeg ffmpeg;

    private VideoUploader(MultipartFile multipartFile){
        this.file = ContentUtils.convertToFile(multipartFile);
        this.contentName = ContentUtils.generateRandomName(multipartFile.getContentType());
        this.convertedFilePath = Paths.get(PathUtils.UPLOAD_CONVERTED_VIDEO_DIR + "." + contentName);
        this.convertedFileShortPath = Paths.get(PathUtils.UPLOAD_CONVERTED_VIDEO_SHORT_DIR + "." + contentName);
        this.uploadPath = Paths.get(PathUtils.UPLOAD_RAW_VIDEO_DIR + "." + contentName);
        try {
            ffmpeg = new FFmpeg(PathUtils.FFMPEG_PATH);
            ffprobe = new FFprobe(PathUtils.FFPROBE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static VideoUploader getInstance(MultipartFile file) throws IOException {
        return new VideoUploader(file);
    }

    @Override
    public void upload() throws IOException{
        ContentUtils.copyContentToServer(file, uploadPath);
        convert();
        delete();
    }

    private void convert() throws IOException {
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(getProbeResult())     // Filename, or a FFmpegProbeResult
                .overrideOutputFiles(true) // Override the output if it exists
                .addOutput(PathUtils.UPLOAD_CONVERTED_VIDEO_DIR + "." + contentName)   // Filename for the destination
                .setFormat("mp4")        // Format is inferred from filename, or can be set
                .setTargetSize(250_000)  // Aim for a 250KB file
                .disableSubtitle()       // No subtiles
                .setAudioChannels(1)         // Mono audio
                .setAudioCodec("aac")        // using the aac codec
                .setAudioSampleRate(48_000)  // at 48KHz
                .setAudioBitRate(32768)      // at 32 kbit/s
                .setVideoCodec("libx264")     // Video using x264
                .setVideoFrameRate(24, 1)     // at 24 frames per second
                .setVideoResolution(640, 480) // at 640x480 resolution
                .setStrict(FFmpegBuilder.Strict.NORMAL)// Allow FFmpeg to use experimental specs
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(getFFmpeg(), getFFprobe());
        // Run a one-pass encode
        executor.createJob(builder).run();
    }

    private void delete() throws IOException {
        Files.delete(uploadPath);
    }

    public FFmpegProbeResult getProbeResult() throws IOException {
        if (probeResult != null)
            return probeResult;
        if (Files.exists(uploadPath)) {
            this.probeResult = getFFprobe().probe(uploadPath.toString());
            return this.probeResult;
        }
        return null;
    }

    public static FFmpeg getFFmpeg() {
        return ffmpeg;
    }

    public static FFprobe getFFprobe() {
        return ffprobe;
    }

    public String getContentName() {
        return contentName;
    }

    public Path getConvertedFilePath() {
        return convertedFilePath;
    }

    public Path getConvertedFileShortPath() {
        return convertedFileShortPath;
    }

}
