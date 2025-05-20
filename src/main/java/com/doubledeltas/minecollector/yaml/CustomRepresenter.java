package com.doubledeltas.minecollector.yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import java.util.Map;

/**
 * YAML 스칼라 노드 타입을 추가할 수 있는 {@link Representer} 입니다.
 */
public class CustomRepresenter extends Representer {
    public CustomRepresenter(DumperOptions dumperOptions) {
        super(dumperOptions);
    }

    /**
     * Register YAML serializer, and return this, supporting method chaining.
     * @param clazz type token to serialize
     * @param serializer serializer
     * @return this object.
     * @param <T> type to serialize
     */
    public <T> CustomRepresenter registerSerializer(Class<T> clazz, YamlSerializer<? extends T> serializer) {
        registerSerializerUnsafe(clazz, serializer);
        return this;
    }

    /**
     * Register YAML serializers in given map, and return this, supporting method chaining.
     * @param serializerMap Map containing {@link Class} tokens as the key and {@link YamlSerializer}s as the value.
     *                      Each serializers must be able to deserialize to the matching type.
     * @return this object.
     */
    public CustomRepresenter registerSerializers(Map<Class<?>, YamlSerializer<?>> serializerMap) {
        for (Map.Entry<Class<?>, YamlSerializer<?>> scalarizerEntry : serializerMap.entrySet()) {
            Class<?> clazz = scalarizerEntry.getKey();
            YamlSerializer<?> serializer = scalarizerEntry.getValue();
            registerSerializerUnsafe(clazz, serializer);
        }
        return this;
    }

    private void registerSerializerUnsafe(Class<?> clazz, YamlSerializer<?> serializer) {
        this.representers.put(
                clazz,
                new Represent() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public Node representData(Object data) {
                        YamlSerializer<Object> oSerializer = (YamlSerializer<Object>) serializer;
                        return representScalar(new Tag(clazz), oSerializer.serialize(data));
                    }
                }
        );
    }
}
