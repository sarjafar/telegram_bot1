import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class BotClass extends TelegramLongPollingBot {

    private String sendText;
    private List<KeyboardRow> buttonList = new ArrayList<>();
    private KeyboardRow keyboardButtonList = new KeyboardRow();


    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            List<Buttons> buttons = new ArrayList<>();

            switch (message.getText()) {
                case "/start":
                    sendText = "Чем могу помочь?";
                    buttons.clear();
                    buttons.add(Buttons.DESCR);
                    buttons.add(Buttons.PHOTOS);
                    buttons.add(Buttons.VIDEOS);
                    break;
                case "/descr":
                    sendText = "Опишите подробно Вашу проблему";
                    buttons.clear();
                    buttons.add(Buttons.DESCR);
                    buttons.add(Buttons.PHOTOS);
                    buttons.add(Buttons.VIDEOS);
                    break;
                case "/screenshots":
                    sendText = "Если есть возможностьб отправьте снимки с экрана где видна беспокоящая Вас проблема";
                    buttons.clear();
                    buttons.add(Buttons.DESCR);
                    buttons.add(Buttons.PHOTOS);
                    buttons.add(Buttons.VIDEOS);
                    break;
                case "/screencasts":
                    sendText = "Если есть возможность, отправьте запись с экрана где видна беспокоящая Вас проблема";
                    buttons.clear();
                    buttons.add(Buttons.DESCR);
                    buttons.add(Buttons.PHOTOS);
                    buttons.add(Buttons.VIDEOS);
                    break;
                default:
                    sendText = "Неправильная комманда";
                    buttons.clear();
                    buttons.add(Buttons.DESCR);
                    buttons.add(Buttons.PHOTOS);
                    buttons.add(Buttons.VIDEOS);
            }
            buttonList = addToButtonList(buttons);
            sendMsg(message, sendText, buttons);
        }

    }

    private List<KeyboardRow> addToButtonList(List<Buttons> _buttons) {
        List<KeyboardRow> _buttonList = new ArrayList<>();
        KeyboardRow _keyboardRow = new KeyboardRow();
        for(Buttons button:_buttons) {
            _keyboardRow.clear();
            _keyboardRow.add(new KeyboardButton(button.toString()));
            _buttonList.add(_keyboardRow);
        }
        return _buttonList;
    }

    public void sendMsg(Message message, String text, List<Buttons> buttons) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {

            setButtons(sendMessage, buttons);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage, List<Buttons> buttons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        for(Buttons button:buttons) {
            keyboardFirstRow.add(new KeyboardButton(button.toString()));
            keyboardRowList.add(keyboardFirstRow);
        }

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

//        replyKeyboardMarkup.setKeyboard(buttonList);
    }

    public String getBotUsername() {
        return "ttett_bot";
    }

    public String getBotToken() {
        return "737023411:AAF50iiyAR8KSu6TKwLsYcKv6ppXFDu_iPE";
    }
}
