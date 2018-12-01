package bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.undeground.Event;
import ru.undeground.Queue;
import ru.undeground.storage.RuntimeStorageManager;

public class MyAmazingBot extends TelegramLongPollingBot {
    private RuntimeStorageManager manager = new RuntimeStorageManager();

    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10_000);
                        System.out.println("Такт");
                        if (queue != null) {
                            for (int i = 0; i < queue.getParticipatingUsers().size(); i++) {
                                System.out.println("send - " + queue.getParticipatingUsers().get(i));
                                SendMessage message = new SendMessage().setChatId(Long.valueOf(queue.getParticipatingUsers().get(i)));
                                message.setText("Time = " + 10 * i);
                                execute(message);
                            }
                            if (queue.getParticipatingUsers().size() > 0)
                                queue.getParticipatingUsers().remove(0);
                        }

                    } catch (InterruptedException | TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageTextWithSlash = update.getMessage().getText();

            String chatId = update.getMessage().getChatId().toString();
            if (messageTextWithSlash.charAt(0) == '/') {
                SendMessage outMessage = new SendMessage().setChatId(update.getMessage().getChatId());

                String messagesText = messageTextWithSlash.substring(1);
                String[] splitMessageText = messagesText.split(" ");

                String resultText = "";
                switch (splitMessageText[0]) {
                    case "createEvent":
                        resultText += registerEvent(chatId, splitMessageText[1]);
                        break;
                    case "createQueue":
                        resultText += registerQueue(chatId, splitMessageText[1]);
                        break;
                    case "enterQueue":
                        resultText += enterQueue(update.getMessage().getChatId().toString());
                        break;
                    case "enterEvent":
                        resultText += "You enter in HackDay event";
                        break;
                }

                outMessage.setText(resultText);
                try {
                    execute(outMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String enterQueue(String chatIdUser) {
        queue.getParticipatingUsers().add(chatIdUser);
        System.out.println(chatIdUser);
        return "You was added in Coffee queue";
    }

    private String registerEvent(String chatId, String eventName) {
        Event event = new Event();

        event.setChatId(chatId);
        event.setEventName(eventName);

        manager.getEventStorage().createEvent(event);

        return "Added event " + event.getEventName();
    }

    private String registerQueue(String chatId, String queueName) {
        Queue queue = new Queue();

        Event event = findEvent(chatId);

        queue.setQueueName(queueName);
//        queue.setEventId(event.getEventId());


        return "Queue coffee was create";
    }

    private Event findEvent(String chatId){
        Event event = null;
        for (Event e : manager.getEventStorage().getAllEvents()) {
            if (chatId.equals(e.getChatId())) {
                event = e;
                break;
            }
        }
        return event;
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