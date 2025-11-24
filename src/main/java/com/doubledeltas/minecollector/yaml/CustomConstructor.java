package com.doubledeltas.minecollector.yaml;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.time.LocalDateTime;

public class CustomConstructor extends Constructor {
    CustomConstructor(LoaderOptions loaderOptions) {
        super(loaderOptions);
        this.yamlConstructors.put(Tag.TIMESTAMP, new ConstructLocalDateTime());

        this.addTypeDescription(new TypeDescription(LocalDateTime.class, Tag.TIMESTAMP) {
            @Override
            public Object newInstance(Node node) {
                return parseLocalDateTime(node);
            }
        });
    }

    private static class ConstructLocalDateTime extends AbstractConstruct {
        @Override
        public Object construct(Node node) {
            return parseLocalDateTime(node);
        }
    }

    private static LocalDateTime parseLocalDateTime(Node node) {
        ScalarNode scalarNode = (ScalarNode) node;
        return LocalDateTime.parse(scalarNode.getValue());
    }
}