package com.doubledeltas.minecollector.yaml;

import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class DeclOrderingPropertyUtils extends PropertyUtils {
    @Override
    public Set<Property> getProperties(Class<?> type, BeanAccess bAccess) {
        // impl has replaced to memorize the order of the declaration, instead of its field name.
        Set<Property> properties = new LinkedHashSet<>();

        Collection<Property> props = getPropertiesMap(type, bAccess).values();
        for (Property property : props) {
            if (property.isReadable() && (isAllowReadOnlyProperties() || property.isWritable())) {
                properties.add(property);
            }
        }
        return properties;
    }
}
