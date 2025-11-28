package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.util.ReflectionUtil;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Material;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Mojang이 NMS 레지스트리에 Item을 저장하는 순서를 가져와 Material의 Comparator를 구성합니다.
 */
public class ItemMojangOrderProvider {
    private Map<Material, Integer> order;

    @Getter
    private boolean ready = false;

    public void load() {
        Map<String, Integer> mcKeymap = new HashMap<>();
        int i = 0;
        for (Object item : getItemRegistry()) {
            mcKeymap.put(item.toString(), i++);
        }

        order = new HashMap<>();
        for (Material material : Material.values()) {
            String itemKey = material.getKey().toString();
            if (!mcKeymap.containsKey(itemKey))
                continue;
            order.put(material, mcKeymap.get(itemKey));
        }

        mcKeymap.clear();
        ready = true;
    }

    public int getOrder(Material material) {
        Preconditions.checkArgument(ready, "Order map is not loaded!");
        Preconditions.checkArgument(order.containsKey(material), "Not valid material, The material must be item!");
        return order.get(material);
    }

    /**
     * get Material's order, with default value when an invalid material is given.
     * @param material material
     * @return Material's order, with default value when an invalid material is given.
     */
    private int getTotalOrder(Material material) {
        Preconditions.checkArgument(ready, "Order map is not loaded!");
        return order.getOrDefault(material, Integer.MAX_VALUE);
    }

    public Comparator<Material> getComparator() {
        Preconditions.checkArgument(ready, "Order map is not loaded!");
        return (a, b) -> Integer.compare(getTotalOrder(a), getTotalOrder(b));
    }

    @SneakyThrows({
            ClassNotFoundException.class,
            IllegalAccessException.class
    })
    private Iterable<?> getItemRegistry() {
        Class<?> cBuiltInRegistries = Class.forName("net.minecraft.core.registries.BuiltInRegistries");
        Field fItemRegistry = Arrays.stream(cBuiltInRegistries.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .filter(field -> {
                    Type gType = field.getGenericType();
                    if (!(gType instanceof ParameterizedType pType))
                        return false;

                    Type rawType = pType.getRawType();
                    Type[] genericTypes = pType.getActualTypeArguments();

                    if (!(rawType instanceof Class<?> rClass))
                        return false;

                    return Iterable.class.isAssignableFrom(rClass)
                            && genericTypes.length == 1
                            && "net.minecraft.world.item.Item".equals(genericTypes[0].getTypeName());
                })
                .findFirst().orElseThrow();

        fItemRegistry.setAccessible(true);

        return (Iterable<?>) fItemRegistry.get(null); // static field
    }
}