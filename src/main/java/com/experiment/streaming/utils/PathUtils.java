package com.experiment.streaming.utils;

public class PathUtils {

    //VIDEO PATHS
    public static final String UPLOAD_RAW_VIDEO_DIR = getCurrentPath()+"static/uploads/raw/";
    public static final String UPLOAD_CONVERTED_VIDEO_SHORT_DIR = "uploads/converted/";
    public static final String UPLOAD_CONVERTED_VIDEO_DIR = getCurrentPath()+"static/uploads/converted/";

    //IMAGE PATHS
    public static final String UPLOAD_IMAGE_DIR = getCurrentPath()+"static/uploads/images/";
    public static final String UPLOAD_IMAGE_SHORT_DIR = "/uploads/images/";

    //FFMPEG PATHS
    public static final String FFMPEG_PATH = getCurrentPath()+"ffmpeg/bin/ffmpeg.exe";
    public static final String FFPROBE_PATH = getCurrentPath()+"ffmpeg/bin/ffprobe.exe";

    public static final String RESOURCES_STATIC = getCurrentPath()+ "src/main/resources/static/";

    public static String getCurrentPath(){
        return PathUtils.class.getResource("PathUtils.class").getPath().substring(1,PathUtils.class.getResource("PathUtils.class").getPath().indexOf("com")).replaceAll("%20"," ");
    }
}
