package Selenium_Recorder;

import java.io.IOException;

public class ScreenRecorder {

    private Process process;

    public void startRecording(String outputFilePath) throws IOException {
        String command = String.format("ffmpeg -y -video_size 1920x1080 -framerate 25 -f gdigrab -i :0.0 %s", outputFilePath);

        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.redirectErrorStream(true);
        process = processBuilder.start();
        System.out.println("Recording started...");
    }

    public void stopRecording() {
        if (process != null) {
            process.destroy();
            System.out.println("Recording stopped.");
        }
    }
}
