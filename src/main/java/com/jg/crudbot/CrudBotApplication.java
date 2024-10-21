package com.jg.crudbot;

import com.jg.crudbot.Service.SheetService;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
public class CrudBotApplication {

	public static void main(String[] args) throws GeneralSecurityException, IOException, JSONException {
		SheetService sheetService = new SheetService();
		sheetService.testTimes();
		SpringApplication.run(CrudBotApplication.class, args);
	}

}
