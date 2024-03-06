package pipisya.bot.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class User {

    @Id
    private Long chatId;
    private Long userId;
    private Integer length;
    private LocalDate lastPlayedTime;
    private String name;
    private String login;
}
