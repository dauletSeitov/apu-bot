package pipisya.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pipisya.bot.entity.Setting;
import pipisya.bot.repository.SettingRepository;

import java.util.Optional;

@Service
public class SettingService {

    public final static String CHECK_ALREADY_PLAYED = "CHECK_ALREADY_PLAYED";
    @Autowired
    private SettingRepository settingRepository;

    public boolean getBooleanValue(String key) {
        Optional<Setting> stringOpt = settingRepository.findSetting(key);
        return stringOpt.map(Setting::getValue).filter(Boolean::parseBoolean).isPresent();
    }

    public void setBooleanValue(String key, boolean val) {
        Setting setting = settingRepository.findSetting(key).orElseGet(Setting::new);
        setting.setKey(key);
        setting.setValue(String.valueOf(val));
        settingRepository.save(setting);
    }
}


