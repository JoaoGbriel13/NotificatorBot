package com.jg.crudbot.Service;

import com.jg.crudbot.Model.Driver;
import com.jg.crudbot.Utils.TokenManager;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@Service
public class Bot extends ListenerAdapter {
    private final String token = TokenManager.discordToken();
    private static final Map<User, RegistrationSession> activeSessions = new HashMap<>();

    @PostConstruct
    public void discordBot() throws LoginException {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        jdaBuilder.addEventListeners(new Bot()).enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES);
        jdaBuilder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        User user = message.getAuthor();
        TextChannel channel = event.getChannel().asTextChannel();

        if (user.isBot()) {
            return;
        }
        if (message.getContentRaw().toLowerCase().startsWith("!register")){
            activeSessions.put(user, new RegistrationSession(user));
        }
        RegistrationSession session = activeSessions.get(user);
        if (session != null){
            try {
                session.processResponse(message, event);
            } catch (IOException | GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class RegistrationSession {
        private final User user;
        private int step = 0;
        private final Driver driver = new Driver();
        public RegistrationSession(User user) {
            this.user = user;
        }

        public void processResponse(Message message, MessageReceivedEvent event) throws GeneralSecurityException, IOException {
            if (!message.getAuthor().equals(user)) {
                return;
            }

            String response = message.getContentRaw();

            switch (step) {
                case 0:
                    event.getChannel().sendMessage("Registro iniciado, qual o seu nome?").queue();
                    step++;
                    break;
                case 1:
                    driver.setNome(response);
                    event.getChannel().sendMessage("Qual é a sua data de nascimento?").queue();
                    step++;
                    break;
                case 2:
                    driver.setData(response);
                    event.getChannel().sendMessage("Qual é o seu email?").queue();
                    step++;
                    break;
                case 3:
                    driver.setEmail(response);
                    event.getChannel().sendMessage("Qual é o seu Iracing ID?").queue();
                    step++;
                    break;
                case 4:
                    driver.setIracingId(response);
                    event.getChannel().sendMessage("Qual o seu tamanho de camisa?").queue();
                    step++;
                    break;
                case 5:
                    driver.setTamanhoCamisa(response);
                    event.getChannel().sendMessage("Qual o seu endereço?").queue();
                    step++;
                    break;
                case 6:
                    driver.setEndereco(response);
                    if (SheetService.save(driver)) {
                        String result = "Registro completo:\n" +
                                "Nome: " + driver.getNome() + "\n" +
                                "Data de Nascimento: " + driver.getData() + "\n" +
                                "Email: " + driver.getEmail() + "\n" +
                                "Endereço: " + driver.getEndereco() + "\n" +
                                "Iracing ID: " + driver.getIracingId() + "\n" +
                                "Tamanho camisa: " + driver.getTamanhoCamisa();
                        event.getChannel().sendMessage(result).queue();
                    }else{
                        event.getChannel().sendMessage("O usuario já está cadastrado na planilha").queue();
                    }
                    activeSessions.remove(user);
                    break;
            }
        }
    }
}
