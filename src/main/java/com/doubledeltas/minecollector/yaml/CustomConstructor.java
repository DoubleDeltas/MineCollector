package com.doubledeltas.minecollector.yaml;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.*;

import java.util.HashMap;
import java.util.Map;

/**
 * YAML 스칼라 노드 타입을 추가할 수 있는 {@link Constructor} 입니다.
 */
public class CustomConstructor extends Constructor {
    protected final Map<Tag, Object> defaultValues = new HashMap<>();

    public CustomConstructor(Class<?> rootType, LoaderOptions loaderOptions) {
        super(rootType, loaderOptions);

        // replace ConstructMapping with customized one.
        yamlClassConstructors.put(NodeId.mapping, new CustomConstructor.ConstructMapping());
    }

    /**
     * Register YAML serializer, and return this, supporting method chaining.
     * @param clazz type token to serialize
     * @param serializer serializer
     * @return this object.
     * @param <T> type to serialize
     */
    public <T> CustomConstructor registerSerializer(Class<T> clazz, YamlSerializer<? extends T> serializer) {
        registerSerializerUnsafe(clazz, serializer);
        return this;
    }

    /**
     * Register YAML serializers in given map, and return this, supporting method chaining.
     * @param serializerMap Map containing {@link Class} tokens as the key and {@link YamlSerializer}s as the value.
     *                      Each serializers must be able to serialize from the matching type.
     * @return this object.
     */
    public CustomConstructor registerSerializers(Map<Class<?>, YamlSerializer<?>> serializerMap) {
        for (Map.Entry<Class<?>, YamlSerializer<?>> serializerEntry : serializerMap.entrySet()) {
            Class<?>            clazz       = serializerEntry.getKey();
            YamlSerializer<?>   serializer  = serializerEntry.getValue();
            registerSerializerUnsafe(clazz, serializer);
        }
        return this;
    }

    @Override
    protected Object constructObjectNoCheck(Node node) {
        if (!(node instanceof ScalarNode scalarNode))
            return super.constructObjectNoCheck(node);

        Object obj = preconstructScalarNode(scalarNode);
        if (obj != null)
            return obj;
        else
            return super.constructObjectNoCheck(node);
    }

    protected class ConstructMapping extends Constructor.ConstructMapping {
        @Override
        protected Object constructJavaBean2ndStep(MappingNode node, Object object) {
            System.out.println("node ====");
            System.out.println(node);
            System.out.println("obj ====");
            System.out.println(object);
            return super.constructJavaBean2ndStep(node, object);
        }
    }

    protected void registerSerializerUnsafe(Class<?> clazz, YamlSerializer<?> serializer) {
        putTagUnsafe(clazz, serializer);
        defaultValues.put(new Tag(clazz), serializer.defaultValue());
    }

    protected void putTagUnsafe(Class<?> clazz, YamlSerializer<?> serializer) {
        this.yamlConstructors.put(
                new Tag(clazz),
                new AbstractConstruct() {
                    @Override
                    public Object construct(Node node) {
                        String value = ((ScalarNode) node).getValue();
                        return serializer.deserialize(value);
                    }
                }
        );
    }

    protected Object preconstructScalarNode(ScalarNode scalarNode) {
        // traversing type hierarchy, when matching Construct found, construct with it.
        for (Class<?> curType = scalarNode.getType(); curType != null; curType = curType.getSuperclass()) {
            Tag curTag = new Tag(curType);
            if (!yamlConstructors.containsKey(curTag))
                continue;
            return yamlConstructors.get(curTag).construct(scalarNode);
        }
        return null;    // no matching Constructor found.
    }
}
