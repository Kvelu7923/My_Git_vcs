/*
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ChatGPTUploader {

    public static void main(String[] args) {
        String zipFilePath = "path_to_your_zip_file.zip";
        String extractedDir = "extracted_files/";
        String apiKey = "your_openai_api_key_here"; // Replace with your actual OpenAI API Key

        // Initialize the OpenAiService
        OpenAiService service = new OpenAiService(apiKey);

        // Unzip and process files
        unzipAndProcess(zipFilePath, extractedDir, service);
    }

    public static void unzipAndProcess(String zipFilePath, String extractedDir, OpenAiService service) {
        try {
            File dir = new File(extractedDir);
            if (!dir.exists()) dir.mkdirs();

            FileInputStream fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry zipEntry;

            while ((zipEntry = zis.getNextEntry()) != null) {
                String extractedFilePath = extractedDir + File.separator + zipEntry.getName();

                if (zipEntry.isDirectory()) {
                    new File(extractedFilePath).mkdirs();
                } else {
                    try (FileOutputStream fos = new FileOutputStream(extractedFilePath)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = zis.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }
                zis.closeEntry();
            }
            zis.close();
            fis.close();
            System.out.println("Extraction completed successfully.");

            // Process JSON and communicate with ChatGPT API
            processExtractedFiles(extractedDir, service);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processExtractedFiles(String extractedDir, OpenAiService service) {
        try {
            File dir = new File(extractedDir);

            if (dir.exists() && dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.getName().endsWith(".json")) {
                        System.out.println("Processing file: " + file.getName());
                        sendDataToChatGPT(file.getAbsolutePath(), service);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendDataToChatGPT(String filePath, OpenAiService service) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(jsonContent);

            if (jsonObject.has("messages")) {
                JSONArray messagesArray = jsonObject.getJSONArray("messages");
                StringBuilder inputData = new StringBuilder();

                for (int i = 0; i < messagesArray.length(); i++) {
                    JSONObject message = messagesArray.getJSONObject(i);
                    inputData.append(message.optString("content", "")).append("\n");
                }

                // Send data to ChatGPT via the OpenAI endpoint
                CompletionRequest completionRequest = CompletionRequest.builder()
                        .model("gpt-4")
                        .prompt(inputData.toString())
                        .maxTokens(200)
                        .build();

                CompletionResult result = service.createChatCompletion(completionRequest);
                System.out.println("Response from ChatGPT:\n" + result.getChoices().get(0).getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
