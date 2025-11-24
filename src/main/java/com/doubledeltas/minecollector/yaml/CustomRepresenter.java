package com.doubledeltas.minecollector.yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CustomRepresenter extends Representer {
    CustomRepresenter(DumperOptions dumperOptions) {
        super(dumperOptions);

        this.representers.put(UUID.class, new RepresentRawUuid());
        this.representers.put(LocalDateTime.class, new RepresentLocalDateTime());
    }

    /**
     * represent a JavaBean as a Map of their properties.
     * @param properties JavaBean getters
     * @param javaBean instance for Node
     */
    @Override
    protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
        Map<String, Object> values = new LinkedHashMap<>();
        for (Property property : properties) {
            try {
                values.put(property.getName(), property.get(javaBean));
            } catch (Exception ignored) {}
        }
        return (MappingNode) representMapping(Tag.MAP, values, DumperOptions.FlowStyle.AUTO);
    }

    private class RepresentLocalDateTime implements Represent {
        @Override
        public Node representData(Object data) {
            LocalDateTime ldt = (LocalDateTime) data;
            String value = ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return CustomRepresenter.this.representScalar(Tag.TIMESTAMP, value);
        }
    }

    private class RepresentRawUuid implements Represent {
        @Override
        public Node representData(Object data) {
            return representScalar(Tag.STR, data.toString());
        }
    }
}