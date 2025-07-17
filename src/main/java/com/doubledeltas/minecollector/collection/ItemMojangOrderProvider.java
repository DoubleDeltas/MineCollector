package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.util.ReflectionUtil;
import com.doubledeltas.minecollector.util.type.GenericTypePredicate;
import static com.doubledeltas.minecollector.util.type.TypePredicates.*;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Material;

import java.lang.reflect.Field;
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
        for (Object item : getItemRegistry())
            mcKeymap.put(item.toString(), i++);

        order = new HashMap<>();
        for (Material material : Material.values()) {
            String itemKey = material.getKey().toString();
            if (!mcKeymap.containsKey(itemKey))
                continue;
            order.put(material, mcKeymap.get(itemKey));
        }

        for (Material key : order.keySet()) {
            if (order.get(key) == null)
                System.out.println(key);
        }

        mcKeymap.clear();
        ready = true;
    }

    public boolean isValid(Material material) {
        if (!ready) return false;
        return order.containsKey(material);
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

    @SneakyThrows(IllegalAccessException.class)
    private Iterable<?> getItemRegistry() {
        Field fItemRegistry = ReflectionUtil.findFieldByType(
                "net.minecraft.core.registries.BuiltInRegistries",
                new GenericTypePredicate(
                        subOf(Iterable.class),
                        fullName("net.minecraft.world.item.Item")
                )
        ).orElseThrow();
        return (Iterable<?>) fItemRegistry.get(null); // static field
    }
}
