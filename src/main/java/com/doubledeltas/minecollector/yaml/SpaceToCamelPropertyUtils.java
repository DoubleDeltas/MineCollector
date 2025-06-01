package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.util.StringUtil;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

class SpaceToCamelPropertyUtils extends PropertyUtils {

    /**
     * @link PropertyUtils#getProperty(Class, String)}를 하이재킹하여 카멜 케이스를 인식시키도록 합니다.
     */
    @Override
    public Property getProperty(Class<? extends Object> type, String name, BeanAccess bAccess) {
        return super.getProperty(type, StringUtil.getCamelCased(name), bAccess);
    }
}