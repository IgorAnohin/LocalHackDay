package bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageTextWithSlash = update.getMessage().getText();
            if (messageTextWithSlash.charAt(0) == '/') {
                SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId());

                String messagesText = messageTextWithSlash.substring(1);
                String resultText = "";
                switch (messagesText.split(" ")[0]) {
                    case "help":
                        resultText += "To register an event: \\registerEvent [name event] [description event]\n" +
                                "Choose queue [name]\n";
                        break;
                    case "createEvent":
                        resultText += registerEvent(messagesText.split(" ")[1], messagesText.split(" ")[2]);
                        break;
                    case "createQueue":
                        resultText += registerQueue(messagesText.split(" ")[1], messagesText.split(" ")[2]);
                        break;
                    case "text":
                        resultText += update.getMessage().getGroupchatCreated();
                        break;

                }

                message.setText(resultText);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String registerEvent(String nameEvent, String descriptionEvent) {
        //todo
        return "newEvent";
    }

    private String registerQueue(String s, String s1) {
        //todo
        return "newQueue";
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return "SovietEmulatorBot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "603227695:AAGgjP-Ly6tDSoM5kWCkBvbCMNt5GjMqwnU";
    }
}