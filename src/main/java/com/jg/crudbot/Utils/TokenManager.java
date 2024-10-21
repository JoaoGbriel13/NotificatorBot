package com.jg.crudbot.Utils;

import io.github.cdimascio.dotenv.Dotenv;

public class TokenManager {
    private static final Dotenv dotenv = Dotenv.load();
    public static String discordToken(){
        return dotenv.get("discord.token");
    }
    public static String googleToken(){
        return dotenv.get("google.api");
    }
    public static String twilioApi(){ return dotenv.get("twilio.api"); }
    public static String twilioSid(){ return dotenv.get("twilio.sid"); }
}
