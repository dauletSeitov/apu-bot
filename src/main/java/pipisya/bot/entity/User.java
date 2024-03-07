package pipisya.bot.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class User {

    @Id
    private Long userId;
    private Integer length;
    private LocalDate lastPlayedTime;
    private String name;
    private String login;
}
