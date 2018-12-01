package bot;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.undeground.Event;
import ru.undeground.Queue;
import ru.undeground.location.Location;
import ru.undeground.location.LocationAPI;
import ru.undeground.storage.RuntimeStorageManager;

public class MyAmazingBot extends TelegramLongPollingBot {

  private RuntimeStorageManager manager = new RuntimeStorageManager();
  private Map<String, List<Location>> userLocationChoice = new HashMap<>();

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
        String eventName;
        switch (splitMessageText[0]) {
          case "createEvent":
            eventName = splitMessageText[1];
            String locationName = splitMessageText.length > 2 ? splitMessageText[2] : null;
            resultText += registerEvent(chatId, eventName, locationName);

            if (locationName != null) {
              List<Location> locations = LocationAPI.getLocations(locationName);
              for (int i = 0; i < locations.size() && i < 5; ++i) {
                Location location = locations.get(i);
                resultText += (i + ": " + location.getName() + "\n" + location.getViewLink() + "\n"
                    + "\n==========\n");
              }

              resultText += "Please pick some location by typing location index\n";
            }

            break;

          case "pickLocation":
            resultText += "Congratulations, location for creating event was successfully picked!";
            break;
          case "createQueue":
            resultText += registerQueue(splitMessageText[1], splitMessageText[2],
                splitMessageText[3]);
            break;
          case "enterQueue":
            resultText += enterQueue(chatId, splitMessageText[1], splitMessageText[2]);
            break;
          case "getEvents":
            resultText += manager.getEventStorage().getAllEvents().stream()
                .map(event -> event.getEventName() + "\n")
                .collect(Collectors.joining());
            break;
          case "getQueues":
            eventName = splitMessageText[1];
            List<Queue> eventQueues = manager
                .getQueueStorage()
                .getEventQueues(eventName);

            if (eventQueues.isEmpty()) {
              resultText += "No queues were found in the event \"" + eventName + "\"";
            } else {
              resultText += "Queues in the event \"" + eventName + "\":\n";
              for (Queue queue : eventQueues) {
                resultText += (queue.getQueueName() + ": " + queue.getParticipatingUsers().size()
                    + " people inside\n");
              }
              resultText += "==========";
            }
            break;
          case "findLocations":
            List<Location> locations = LocationAPI.getLocations(splitMessageText[1]);

            if (locations.isEmpty()) {
              resultText += "No locations with name \"" + splitMessageText[1] + "\" were found";
            } else {
              resultText += "Found locations:\n";
              for (int i = 0; i < locations.size(); ++i) {
                Location location = locations.get(i);
                resultText += (i + ": " + location.getName() + "\n" + location.getViewLink() + "\n"
                    + "\n==========\n");
              }

              resultText += "Please choose some location by typing location index\n";
              userLocationChoice.put(chatId, locations);
            }
            break;

          case "getEventsByLocation":
            Location chosenLocation = userLocationChoice.get(chatId)
                .get(Integer.parseInt(splitMessageText[1]));
            resultText += manager.getEventStorage()
                .getEventsByLocation(chosenLocation.getViewLink())
                .stream()
                .map(event -> event.getEventName() + "\n")
                .collect(Collectors.joining());
            if (resultText.isEmpty()) {
              resultText += "No events were found in \"" + chosenLocation.getName() + '\"';
            }
            break;
          case "queueInfo":
            resultText +=
                findQueue(splitMessageText[1], splitMessageText[2]).getParticipatingUsers().size() +
                    " people left";
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

  private String enterQueue(String chatId, String eventName, String queueName) {
    Queue queue = findQueue(eventName, queueName);

    queue.getParticipatingUsers().add(chatId);

    return "You was successfully added to the queue \"" + queueName + "\"";
  }

  private String registerEvent(String chatId, String eventName, String location) {
    Event event = new Event();

    event.setChatId(chatId);
    event.setEventName(eventName);

    event.setGeoLocation(LocationAPI.getLocations(location));

    manager.getEventStorage().createEvent(event);

    return "New event \"" + event.getEventName() + "\" was successfully created in \"" + location
        + "\"";
  }

  private String registerQueue(String eventName, String time, String queueName) {
    Queue queue = new Queue();

    Event event = findEvent(eventName);

    queue.setQueueName(queueName);
    queue.setEventName(event.getEventName());

    manager.getQueueStorage().createQueue(queue);

    createThread(event.getEventName(), time, queueName);

    return "New queue \"" + queueName + "\" was successfully created in the event \"" + event
        .getEventName() + "\"";
  }

  private void createThread(String eventName, String time, String queueName) {
    Thread thread = new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(Integer.parseInt(time) * 1000);
          Queue queue = findQueue(eventName, queueName);
          if (queue != null) {
            for (int i = 0; i < queue.getParticipatingUsers().size(); i++) {
              String chatIdUser = queue.getParticipatingUsers().get(i);
              SendMessage message = new SendMessage().setChatId(chatIdUser);
              if (i == 0) {
                message.setText("It's your turn!");
              } else {
                message.setText(
                    "Expected waiting time is " + Integer.parseInt(time) * i + " seconds.\n" +
                        "You are " + i + (i == 1 ? "st" : (i == 2 ? "nd" : (i == 3 ? "rd" : "th")))
                        + " in the queue\n");
              }
              execute(message);
            }
            if (queue.getParticipatingUsers().size() > 0) {
              queue.getParticipatingUsers().remove(0);
            }
          }

        } catch (InterruptedException | TelegramApiException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();

  }

  private Event findEvent(String eventName) {
    Event event = null;
    for (Event e : manager.getEventStorage().getAllEvents()) {
      if (eventName.equals(e.getEventName())) {
        event = e;
        break;
      }
    }
    return event;
  }

  private Queue findQueue(String eventName, String queueName) {
    Queue queue = null;
    for (Queue q : manager.getQueueStorage().getEventQueues(eventName)) {
      if (queueName.equals(q.getQueueName())) {
        queue = q;
        break;
      }
    }
    return queue;
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