package com.doubledeltas.minecollector.collection;

import com.doubledeltas.minecollector.util.ReflectionUtil;
import com.doubledeltas.minecollector.util.type.GenericTypePredicate;
import static com.doubledeltas.minecollector.util.type.TypePredicates.*;

import com.google.common.base.Preconditions;
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
public class MaterialMojangOrderProvider {
    private Map<Material, Integer> order;

    @Getter
    private boolean ready = false;

    public void load() {
        Map<String, Integer> mcKeymap = new HashMap<>();
        int i = 0;
        for (Object item : getItemRegistry())
            mcKeymap.put(item.toString(), i++);
        mcKeymap.clear();

        order = new HashMap<>();
        for (Material material : Material.values()) {
            order.put(material, mcKeymap.get(material.getKey().toString()));
        }
        ready = true;
    }

    public int getOrder(Material material) {
        Preconditions.checkArgument(ready, "Order map is not loaded!");
        return order.get(material);
    }

    public Comparator<Material> getComparator() {
        Preconditions.checkArgument(ready, "Order map is not loaded!");
        return (a, b) -> Integer.compare(order.get(a), order.get(b));
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
        System.out.println(fItemRegistry.getName());
        return (Iterable<?>) fItemRegistry.get(null); // static field
    }
}
