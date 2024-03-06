package pipisya.bot.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import pipisya.bot.bot.TelegramBotOutput;
import pipisya.bot.entity.User;
import pipisya.bot.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class DickService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TelegramBotOutput botOutput;

    @Value("${bot.increase.length}")
    int increaseLength;
    @Value("${bot.decrease.length}")
    int decreaseLength;


    private final String decreasedMessageTemplate = """
            %s, твой писюн сократился на %d см.
            Теперь он равен %d см.
            """;

    private final String increasedMessageTemplate = """
            %s, твой писюн вырос на %d см.
            Теперь он равен %d см.
            """;

    private final String alreadyPlayedTemplate = """
            %s, ты уже играл.
            Сейчас он равен %d см.
            """;

    private final String youAreOnPlace = """
            Ты занимаешь %d место в топе
            Следующая попытка завтра!
            """;


    @SneakyThrows
    public void topDick(Message message) {
        List<User> users = userRepository.findAll(Sort.by("length"));
        StringBuilder sb = new StringBuilder("Топ 10 игроков\n\n");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            sb.append(i + 1).append("|").append(user.getName()).append(" - ").append(user.getLength()).append("cm\n");
        }
        SendMessage msg = new SendMessage();
        msg.setChatId(message.getChat().getId());
        msg.setText(sb.toString());
        botOutput.execute(msg);
    }

    @SneakyThrows
    public void dickPlay(Message message) {

        User user = userRepository.findById(message.getChat().getId()).orElseGet(() -> {
            User newUser = new User();
            newUser.setChatId(message.getChat().getId());
            newUser.setUserId(message.getFrom().getId());
            newUser.setLength(0);
            newUser.setLastPlayedTime(LocalDate.MIN);
            newUser.setName(message.getFrom().getFirstName());
            newUser.setLogin(message.getFrom().getUserName());
            return newUser;
        });
        String response;
        if (user.getLastPlayedTime().isBefore(LocalDate.now())) {
            int length = 0;
            if (increase()) {
                int random = random(1, increaseLength);
                length = user.getLength() + random;
                response = String.format(increasedMessageTemplate, user.getName(), random, length);
            } else {
                int random = random(1, decreaseLength);
                length = user.getLength() - random;
                response = String.format(decreasedMessageTemplate, user.getName(), random, length);
            }
            user.setLength(length);
            user.setLastPlayedTime(LocalDate.now());
            userRepository.save(user);

        } else {
            response = String.format(alreadyPlayedTemplate, user.getName(), user.getLength());
        }

        List<User> users = userRepository.findAll(Sort.by("length"));
        for (int i = 0; i < users.size(); i++) {
            User item = users.get(i);
            if (item.getChatId().equals(message.getChat().getId())) {
                response += String.format(youAreOnPlace, i + 1);
                break;
            }
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(response);
        sendMessage.setChatId(message.getChat().getId());
        botOutput.execute(sendMessage);
    }

    private boolean increase() {
        int increaseProbability = 70; // change this to set the chance of b being true from 0% to 100%
        Random rand = new Random();
        int randomInt = rand.nextInt(100);
        return (randomInt < increaseProbability); // this will be "true" ~prob% of the time
    }

    private int random(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }


    public static void main(String[] args) {

        int t = 0;
        int f = 0;
        for (int i = 0; i < 100; i++) {

            int prob = 100; // change this to set the chance of b being true from 0% to 100%
            Random rand = new Random();
            int randomInt = rand.nextInt(100);
            boolean b = (randomInt < prob); // this will be "true" ~prob% of the time
            if (b) {
                t++;
            } else {
                f++;
            }

        }
        System.out.println("t = " + t);
        System.out.println("f = " + f);
    }
}