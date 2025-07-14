package com.doubledeltas.minecollector.util.type;

import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Predicate;

@UtilityClass
public final class TypePredicates {
    public static Predicate<Type> ANY = type -> true;

    public static Predicate<Type> fullName(@Nonnull String fullName) {
        return type -> fullName.equals(type.getTypeName());
    }

    public static Predicate<Type> simpleName(@Nonnull String simpleName) {
        return type -> {
            if (!(type instanceof Class<?> clazz))
                return simpleName.equals(type.getTypeName());
            return simpleName.equals(clazz.getSimpleName());
        };
    }

    public static Predicate<Type> is(@Nonnull Type targetType) {
        return targetType::equals;
    }

    public static Predicate<Type> superOf(Class<?>... subTypes) {
        return type -> {
            if (!(type instanceof Class<?> clazz))
                return false;
            return Arrays.stream(subTypes).allMatch(clazz::isAssignableFrom);
        };
    }

    public static Predicate<Type> subOf(Class<?>... superTypes) {
        return type -> {
            if (!(type instanceof Class<?> clazz))
                return false;
            return Arrays.stream(superTypes).allMatch(superType -> superType.isAssignableFrom(clazz));
        };
    }
}
