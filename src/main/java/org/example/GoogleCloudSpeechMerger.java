package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
在这个示例代码中，我们使用FFmpeg的命令行选项将原始视频文件和合成的英文语音文件合并到一个新的文件中。-c:v copy选项指示FFmpeg复制视频流而不进行重新编码，
以避免降低视频质量。-map选项指示FFmpeg将视频流映射到输出文件中的第一个视频流，并将英文语音流映射到输出文件中的第一个音频流。-shortest选项指示FFmpeg
在视频流或音频流结束时停止输出，以确保输出文件与原始视频文件的长度相同。
请注意，此示例代码假定你已经安装了FFmpeg并将其添加到了系统路径中。如果你的系统上没有安装FFmpeg，你需要先安装它或手动指定其可执行文件的路径。此外，为了使代码更加健壮，你可能需要添加一些错误处理和边缘情况的处理。


在这个方案中，我们并没有编写代码将原始中文语音从视频中去除，而是使用了 FFmpeg 的 an 选项来禁用原始音频流的编码和复制。这样做的目的是确保只有翻译后的英文
语音被包含在输出视频中，而原始中文语音不会出现。
如果你想要在视频中去除原始中文语音，可以使用 FFmpeg 的音频过滤器来实现。具体来说，你可以使用-af选项指定一个音频过滤器图形，以在输出中从视频流中去除音频。
以下是一个简单的示例：
 String[] cmd = new String[] {
    "ffmpeg",
    "-i", inputVideo,
    "-c:v", "copy",
    "-af", "an",
    outputVideo
};
 */


public class GoogleCloudSpeechMerger {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 读取原始视频文件和合成的英文语音文件
        Path videoFilePath = Paths.get("path/to/video.mp4");
        Path englishAudioFilePath = Paths.get("path/to/english/audio.wav");
        // 创建输出文件路径
        Path outputFilePath = Paths.get("path/to/output/video-synced.mp4");

        // 构建 FFmpeg 命令
        String[] command = {
                "ffmpeg",
                "-i", videoFilePath.toString(),
                "-i", englishAudioFilePath.toString(),
                "-c:v", "copy",
                "-map", "0:v:0",
                "-map", "1:a:0",
                "-shortest",
                outputFilePath.toString()
        };

        // 启动 FFmpeg 进程并等待进程结束
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.inheritIO().start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Video and audio synced successfully!");
        } else {
            System.err.println("Failed to sync video and audio!");
        }
    }
}
