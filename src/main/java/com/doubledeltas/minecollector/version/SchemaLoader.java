package com.doubledeltas.minecollector.version;

import com.doubledeltas.minecollector.yaml.MultiVersionYaml;
import lombok.RequiredArgsConstructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@RequiredArgsConstructor
public abstract class SchemaLoader<T, S extends Schema<T>> {
    private final MultiVersionYaml yaml;
    private final VersionSchemaTable<S> schemaTable;
    private final VersionUpdaterChain<S> updaterChain;

    /**
     * 현재 로딩중인 스키마. {@code load()}가 실행된 후 값을 가집니다.
     */
    protected S schema = null;
    /**
     * 현재 로딩중인 스키마의 버전. {@code load()}가 실행된 후 값을 가집니다.
     */
    protected Version<?> schemaVersion;

    public T load(File path) throws SchemaLoadingException {
        try (FileReader fileReader = new FileReader(path)) {
            return load(fileReader);
        } catch (IOException e) {
            throw new SchemaLoadingException("Could not load file", e);
        }
    }

    public T load(Reader reader) throws SchemaLoadingException {
        try (reader) {
            schema = yaml.load(reader, schemaTable);
        } catch (YAMLException e) {
            throw new SchemaLoadingException("Could not parse YAML", e);
        } catch (IOException e) {
            throw new SchemaLoadingException("Could not load content", e);
        }

        schemaVersion = schemaTable.getVersion(schema);
        Version<?> latestTargetVersion = schemaTable.getLatestVersion();

        int versionComparison = schemaTable.getVersionManager().compareVersions(schemaVersion, latestTargetVersion);

        if (versionComparison < 0) {
            onOlderVersionDetected();
        }
        else if (versionComparison > 0) {
            onNewerVersionDetected();
        }

        return convert(schema);
    }

    @SuppressWarnings("unchecked")
    private T convert(S schema) {
        return ((CurrentSchema<T>) schema).convert();
    }

    public void onOlderVersionDetected() {
        // no-op
    }
    public void onNewerVersionDetected() {
        // no-op
    }

    // UTIL METHOD

    protected void updateToLatest() {
        schema = updaterChain.updateToLatest(schema, schemaVersion);
    }
}
