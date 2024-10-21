package com.jg.crudbot.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.jg.crudbot.Model.Driver;
import com.jg.crudbot.Utils.ConvertTime;
import lombok.NoArgsConstructor;
import org.json.JSONException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class SheetService {
    private static final String CRUD_SHEET_ID = "1nypHhdBkK4bMdyz7rfgWIAbIRC9vyGY8qzTd46MkcK8";
    private static final String SCHEDULE_SHEET_ID = "1okFhRP-cn8ebIaMGm32_m_mnAEHTBR48S_6AhbYvtOU";
    private static final String RANGE = "Sheet1!A1:F1";

    public static boolean save(Driver driver) throws GeneralSecurityException, IOException {
        Sheets sheets = GoogleService.getSheetsService();
        if (checkRegister(driver.getIracingId())){
            return false;
        }
        ValueRange valueRange = new ValueRange().setValues(
                List.of(Arrays.asList(driver.getNome(),driver.getData(), driver.getEmail(), driver.getEndereco(),
                        driver.getIracingId(), driver.getTamanhoCamisa()))
        );
        AppendValuesResponse appendValuesResponse = sheets.spreadsheets().values().append(
                CRUD_SHEET_ID, RANGE, valueRange).setValueInputOption("RAW").execute();
        return true;
    }
    private static boolean checkRegister(String irID) throws GeneralSecurityException, IOException {
        Sheets sheets = GoogleService.getSheetsService();
        ValueRange values = sheets.spreadsheets().values().get(
                CRUD_SHEET_ID, "Sheet1!E:E"
        ).execute();
        List<List<Object>> response = values.getValues();
        if (values.isEmpty()){
            System.out.println("Nada encontrado");
        }
        for (List<Object> row: response){
            if (!row.isEmpty() && row.get(0).equals(irID)){
                return true;
            }
        }
        return false;
    }
    public void testTimes() throws GeneralSecurityException, IOException, JSONException {
        Sheets sheets = GoogleService.getSheetsService();
        ValueRange startTimes = sheets.spreadsheets().values().get(
                SCHEDULE_SHEET_ID, "Schedule!AC5:AC21"
        ).execute();
        List<List<Object>> startTimesList = startTimes.getValues();
        for (List<Object> row : startTimesList){
            LocalTime queried = ConvertTime.convert(row.toString().replace("[","").replace("]",""));
            LocalTime after = queried.plusMinutes(30);
            if (LocalTime.now().isBefore(after)){
                TwilioService.sendMessage(queried);
            }
        }
    }
}
