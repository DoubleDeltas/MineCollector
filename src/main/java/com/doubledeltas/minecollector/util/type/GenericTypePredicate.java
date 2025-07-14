package com.doubledeltas.minecollector.util.type;

import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Predicate;

public class GenericTypePredicate implements Predicate<Type> {
    private final @Nonnull Predicate<Type> rawTypeQuery;
    private final Predicate<Type>[] paramQueries;
    private final int paramCount;

    @SafeVarargs
    public GenericTypePredicate(@Nonnull Predicate<Type> rawTypeQuery, Predicate<Type>... paramQueries) {
        this.rawTypeQuery = rawTypeQuery;
        this.paramQueries = paramQueries;
        this.paramCount = paramQueries.length;
    }

    @Override
    public boolean test(Type type) {
        Type rawType = (type instanceof ParameterizedType p) ? p.getRawType() : type;
        if (!rawTypeQuery.test(rawType))
            return false;

        if (type instanceof ParameterizedType pType) {
            Type[] typeArgs = pType.getActualTypeArguments();
            int typeArgsCount = typeArgs.length;
            if (paramCount != typeArgsCount)
                return false;
            for (int i=0; i<typeArgsCount; i++) {
                if (!paramQueries[i].test(typeArgs[i]))
                    return false;
            }
            return true;
        }
        else {
            return paramCount == 0;
        }
    }
}