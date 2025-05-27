package com.doubledeltas.minecollector.config.schema;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.InvalidConfigException;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.version.SemanticVersion;
import com.doubledeltas.minecollector.version.Version;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class McolConfigSchema1_3 extends McolConfigSchemaUnlabeled {
    private String configVersion = "1.3";

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
    protected McolConfig.McolConfigBuilder getConfigBuilder() {
        return super.getConfigBuilder()
                .configVersion(Version.parse(configVersion));
    }
}
