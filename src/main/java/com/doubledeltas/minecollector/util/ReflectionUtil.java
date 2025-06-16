package com.doubledeltas.minecollector.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@UtilityClass
public final class ReflectionUtil {

    /**
     * 객체의 Getter를 연쇄 적용해 객체를 탐색합니다.
     * <p>예를 들어, {@code traverseGetters(a, "someObj.someBool")}를 호출하면 {@code a.getSomeObj().isSomeBool()}를 찾습니다.
     * 현재 탐색중인 객체가 {@code boolean} 타입이라면 getter의 접두사는 {@code is}, 아니라면 {@code get}으로 적용합니다.</p>
     * @param root 탐색을 시작할 객체
     * @param graph '.'으로 연쇄되는 프로퍼티 이름들의 연결
     * @param type 탐색의 결과로 반환되는 객체의 예상 타입
     * @return 탐색의 종점에 존재하는 객체
     * @throws NoSuchFieldException     필드가 존재하지 않음
     * @throws NoSuchMethodException    get&lt;필드&gt;() 또는 is&lt;필드&gt;() 메소드가 존재하지 않음
     * @throws IllegalAccessException   메소드가 public으로 공개되지 않음
     */
    @SuppressWarnings("unchecked")
    public static <T> T traverseGetters(Object root, String graph, Class<T> type)
            throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, ClassCastException
    {
        return (T) traverseGetters(root, graph);
    }

    /**
     * 객체의 Getter를 연쇄 적용해 객체를 탐색합니다.
     * <p>예를 들어, {@code traverseGetters(a, "someObj.someBool")}를 호출하면 {@code a.getSomeObj().isSomeBool()}를 찾습니다.
     * getter의 접두사는 "get"으로 먼저 찾고, 없으면 "is"로 찾습니다.</p>
     * @param root 탐색을 시작할 객체
     * @param graph '.'으로 연쇄되는 프로퍼티 이름들의 연결
     * @return 탐색의 종점에 존재하는 객체
     * @throws IllegalStateException    메소드가 존재하지 않음
     * @throws IllegalAccessException   메소드가 public으로 공개되지 않음
     */
    public static Object traverseGetters(Object root, String graph)
            throws IllegalStateException, IllegalAccessException
    {
        String[] fieldNames = graph.split("\\.");
        Object curObj = root;
        for (String fieldName : fieldNames) {
            Class<?> curType = curObj.getClass();
            if (Map.class.isAssignableFrom(curType)) {
                curObj = findMapValue((Map<?, ?>) curObj, fieldName);
            }
            else {
                Method getter = GetterFinder.find(curType, fieldName);
                try {
                    curObj = getter.invoke(curObj);
                } catch (InvocationTargetException ex) {
                    // do nothing
                }
            }
        }
        return curObj;
    }

    private static Object findMapValue(Map<?, ?> map, String keyName) {
        for (Map.Entry<?, ?> entry: map.entrySet()) {
            Object key = entry.getKey();
            if (keyName.equals(key.toString()))
                return entry.getValue();
        }
        return null;
    }

    private static final class GetterFinder {
        private static final String[] PREFIX_CANDIDATES = new String[] {"get", "is", "has", null};

        public static Method find(Class<?> type, String fieldName) {
            return find(type, fieldName, 0);
        }

        private static Method find(Class<?> type, String fieldName, int curIndex) {
            try {
                if (curIndex >= PREFIX_CANDIDATES.length)
                    throw new IllegalStateException("No getter found for field: " + fieldName + " in " + type);
                String prefix = PREFIX_CANDIDATES[curIndex];
                return type.getMethod(getterName(prefix, fieldName));
            } catch (NoSuchMethodException ex) {
                return find(type, fieldName, curIndex + 1);
            }
        }

        private static String getterName(String prefix, String fieldName) {
            if (prefix == null)
                return fieldName;
            return prefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        }
    }
}
