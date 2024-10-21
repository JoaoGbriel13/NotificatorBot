package com.jg.crudbot.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.jg.crudbot.Utils.TokenManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleService {
    private static final String APP_NAME = "Bot Discord";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String token = TokenManager.googleToken();

    public static Sheets getSheetsService() throws GeneralSecurityException, IOException {
        GoogleCredentials credentials;
        try(FileInputStream serviceAccount = new FileInputStream(token)) {
            credentials = GoogleCredentials.fromStream(serviceAccount)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpRequestInitializer requestInitializer =new HttpCredentialsAdapter(credentials);
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, requestInitializer).setApplicationName(APP_NAME).build();
    }
    public static Drive getDriveService() throws IOException, GeneralSecurityException {
        GoogleCredentials credentials;
        try(FileInputStream serviceAccount = new FileInputStream(token)) {
            credentials = GoogleCredentials.fromStream(serviceAccount)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                requestInitializer).setApplicationName(APP_NAME).build();
    }
}
