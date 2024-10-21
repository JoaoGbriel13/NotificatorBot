package com.jg.crudbot.Service;

import com.google.gson.JsonObject;
import com.jg.crudbot.Utils.TokenManager;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.time.LocalTime;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class TwilioService {
    public static void sendMessage(LocalTime time) throws JSONException {
        Twilio.init(TokenManager.twilioSid(), TokenManager.twilioApi());
        String to = "whatsapp:+558173265467";
        String from = "whatsapp:+14155238886";
        Message message = Message.creator(
                new PhoneNumber(to), new PhoneNumber(from), "test"
        )       .setContentSid("HX541ccab143a44e963a8c62b8f66ed06d")
                .setContentVariables(
                        new JSONObject(new HashMap<String, Object>() {
                            {
                                put("1", time);
                            }
                        }).toString())
                .create();
        System.out.println("Mensagem enviada com sucesso");
    }
}
