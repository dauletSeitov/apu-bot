package pipisya.bot.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Setting {

    @Id
    private String key;
    private String value;

}
