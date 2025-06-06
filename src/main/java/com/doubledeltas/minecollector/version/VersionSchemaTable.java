package com.doubledeltas.minecollector.version;

import lombok.Getter;

import java.lang.reflect.Modifier;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class VersionSchemaTable<T> {
    @Getter
    protected final VersionManager versionManager;

    @Getter
    protected final Class<? extends T> metadataType;
    protected final BiFunction<VersionSchemaTable<T>, T, Version<?>> versionMapper;

    protected final NavigableMap<Version<?>, Class<? extends T>> versionMap;

    public VersionSchemaTable(
            VersionManager versionManager,
            Class<? extends T> metadataType,
            Function<T, Version<?>> versionMapper
    ) {
        this(versionManager, metadataType, (self, type) -> versionMapper.apply(type));
    }

    public VersionSchemaTable(
            VersionManager versionManager,
            Class<? extends T> metadataType,
            BiFunction<VersionSchemaTable<T>, T, Version<?>> versionMapper
    ) {
        if (Modifier.isAbstract(metadataType.getModifiers()))   // if metadataType is interface or abstract
            throw new IllegalArgumentException("metadata type must be concrete class");
        this.versionManager = versionManager;
        this.metadataType = metadataType;
        this.versionMapper = versionMapper;

        this.versionMap = new TreeMap<>(versionManager::compareVersions);
    }

    public void registerSchema(String ver, Class<? extends T> schemaType) {
        registerSchema(versionManager.parse(ver), schemaType);
    }

    public void registerSchema(Version<?> ver, Class<? extends T> schemaType) {
        versionMap.put(ver, schemaType);
    }

    public Class<? extends T> getSchemaType(Version<?> ver) {
        return versionMap.get(ver);
    }

    public Version<?> getVersion(T schema) {
        return versionMapper.apply(this, schema);
    }

    public Version<?> getNearestOlderVersion(Version<?> ver) {
        try {
            return versionMap.floorKey(ver);
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    public Version<?> getLatestVersion() {
        return versionMap.lastKey();
    }
}
