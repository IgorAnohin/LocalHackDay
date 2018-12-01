package bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.undeground.Event;
import ru.undeground.Queue;
import ru.undeground.storage.RuntimeEventStorage;

public class MyAmazingBot extends TelegramLongPollingBot {
    private RuntimeEventStorage eventStorage = new RuntimeEventStorage();

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
                        resultText += registerEvent(update.getMessage());
                        break;
                    case "createQueue":
                        resultText += registerQueue(message);
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

    private String registerEvent(Message message) {
        Event event = new Event();

        for (Event e : eventStorage.getAllEvents()) {
            if(e.getChatId().equals(message.getChatId().toString())){
                return "You have already created an event";
            }
        }

        event.setChatId(message.getChatId().toString());
        event.setEventName(message.getText().substring(1).split(" ")[1]);
        event.setEventDescriptions(message.getText().substring(1).split(" ")[2]);

        eventStorage.createEvent(event);
        return "newEvent : " + event.getEventId();
    }

    private String registerQueue(SendMessage message) {
        Queue queue = new Queue();

        Event event = null;
        for (Event e : eventStorage.getAllEvents()) {
            if (message.getChatId().equals(e.getChatId())) {
                event = e;
                break;
            }
        }

        queue.setEventId(event.getEventId());
        queue.setQueueName(message.getText().substring(1).split(" ")[1]);

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