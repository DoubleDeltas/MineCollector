package com.doubledeltas.minecollector.config;

import com.doubledeltas.minecollector.config.chapter.AnnouncementChapter;
import com.doubledeltas.minecollector.config.chapter.DBChapter;
import com.doubledeltas.minecollector.config.chapter.GameChapter;
import com.doubledeltas.minecollector.config.chapter.ScoringChapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigValidator {
    private Map<String, Object> target;
    private List<String> causes = new ArrayList<>();
    private Config result = null;

    /**
     * config map을 검증합니다. 객체 생성과 동시에 검증합니다.
     * @param target 검증할 config map
     */
    public ConfigValidator(Map<String, Object> target) {
        this.target = target;

        validateConfig();

        if (causes.size() == 0) {
            result = new Config(
                    (boolean) target.get("enabled"),
                    ScoringChapter.convert((Map<String, Object>) target.get("scoring")),
                    AnnouncementChapter.convert((Map<String, Object>) target.get("announcement")),
                    GameChapter.convert((Map<String, Object>) target.get("game")),
                    DBChapter.convert((Map<String, Object>) target.get("db"))
            );
        }
    }

    private void validateConfig() {
        Object oEnabled = target.get("enabled");

        if (oEnabled == null)
            causes.add("'enabled' 값이 누락되었습니다.");
        else if (!(oEnabled instanceof Boolean))
            causes.add("'enabled' 값은 \"true\" 또는 \"false\"여야 합니다.");

        validateScoringChapter();
        validateAnnouncementChapter();
        validateGameChapter();
        validateDBChapter();
    }

    private void validateScoringChapter() {
        Object oScoring = target.get("scoring");

        if (oScoring == null) {
            causes.add("'scoring' 값이 누락되었습니다.");
            return;
        }
        if (!(oScoring instanceof Map mScoring)) {
            causes.add("'scoring' 값은 여러 값으로 이루어져야합니다.");
            return;
        }
        if (!mScoring.containsKey("collection enabled"))
            causes.add("'scoring/collection enabled' 값이 없습니다.");
        else if (!(mScoring.get("collection enabled") instanceof Boolean))
            causes.add("'scoring/collection enabled' 값은 \"true\" 또는 \"false\"여야 합니다.");

    }

    private void validateAnnouncementChapter() {

    }

    private void validateGameChapter() {

    }

    private void validateDBChapter() {

    }

    /**
     * config가 유효한 지를 확인합니다.
     * @return
     */
    public boolean isValid() {
        return causes.size() == 0;
    }

    /**
     * config validation에 실패한 이유들을 가져옵니다.
     * @return
     */
    public List<String> getCauses() {
        return causes;
    }

    /**
     * 검증을 마친 config 객체를 가져옵니다.
     * @return
     */
    public Config getResult() {
        return result;
    }
}
