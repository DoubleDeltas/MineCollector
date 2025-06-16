package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.lang.LangManager;
import com.doubledeltas.minecollector.version.Version;
import com.doubledeltas.minecollector.version.VersionUpdater;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.util.Locale;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data @AllArgsConstructor @SuperBuilder
public class McolConfigSchema1_3_0 extends McolConfigSchemaUnlabeled implements CurrentMcolConfigSchema {
    @Builder.Default
    private String configVersion = "1.3.0";
    @Builder.Default
    private String lang = LangManager.DEFAULT_LANG;

    /**
     * Default schema
     */
    public McolConfigSchema1_3_0() {
        super();
        this.configVersion = MineCollector.getInstance().getDescription().getVersion();
        this.lang = Locale.getDefault() == Locale.KOREA ? "ko_KR" : LangManager.DEFAULT_LANG;
    }

    @Override
    public void validate() throws InvalidConfigException {
        super.validate();

        try {
            MineCollector.getInstance().getVersionManager().parse(configVersion);
        } catch (IllegalArgumentException ex) {
            throw new InvalidConfigException("version이 잘못되었습니다!", ex);
        }
    }

    @Override
    public McolConfig convert() throws InvalidConfigException {
        validate();
        return McolConfig.builder()
                .enabled(isEnabled())
                .lang(getLang())
                .scoring(McolConfig.Scoring.builder()
                        .collectionEnabled(getScoring().isCollectionEnabled())
                        .collectionScore(getScoring().getCollectionScore())
                        .stackEnabled(getScoring().isStackEnabled())
                        .stackMultiple(getScoring().getStackMultiple())
                        .stackScore(getScoring().getStackScore())
                        .advancementEnabled(getScoring().isAdvancementEnabled())
                        .advancementScores(getScoring().getAdvancementScores())
                        .build())
                .announcement(McolConfig.Announcement.builder()
                        .collection(getAnnouncement().getCollection())
                        .highLevelReached(getAnnouncement().getHighLevelReached())
                        .highLevelMinimum(getAnnouncement().getHighLevelMinimum())
                        .advancement(getAnnouncement().getAdvancement())
                        .build())
                .game(McolConfig.Game.builder()
                        .hideUnknownCollection(getGame().isHideUnknownCollection())
                        .respawnEnderegg(getGame().isRespawnEnderegg())
                        .build())
                .db(McolConfig.DB.builder()
                        .autosavePeriod(Duration.ofMinutes(getDb().getAutosavePeriod()))
                        .autosaveLogging(getDb().isAutosaveLogging())
                        .build())
                .configVersion(Version.parse(getConfigVersion()))
                .build();
    }

    public static final class Updater implements VersionUpdater<McolConfigSchemaUnlabeled, McolConfigSchema1_3_0> {
        @Override
        public McolConfigSchema1_3_0 update(McolConfigSchemaUnlabeled source) {
            return McolConfigSchema1_3_0.builder()
                    .enabled(source.isEnabled())
                    .scoring(source.getScoring())
                    .announcement(source.getAnnouncement())
                    .game(source.getGame())
                    .db(source.getDb())
                    .build();
        }
    }
}
