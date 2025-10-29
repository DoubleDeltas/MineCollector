package com.doubledeltas.minecollector.version;

import com.doubledeltas.minecollector.util.Holder;
import com.doubledeltas.minecollector.yaml.MultiVersionYaml;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class SchemaLoader<T, S extends Schema<T>> {
    private final MultiVersionYaml yaml;
    private final VersionSchemaTable<S> schemaTable;
    private final VersionUpdaterChain<S> updaterChain;

    private Consumer<ContextOlderVersionDetected>   actionOlderVersionDetected  = (ctx) -> {};
    private Consumer<ContextNewerVersionDetected>   actionNewerVersionDetected  = (ctx) -> {};
    private Consumer<ContextFinished>               actionFinished              = (ctx) -> {};

    public T load(File path) throws SchemaLoadingException {
        try (FileReader fileReader = new FileReader(path)) {
            return load(fileReader);
        } catch (IOException e) {
            throw new SchemaLoadingException("Could not load file", e);
        }
    }

    public T load(Reader reader) throws SchemaLoadingException {
        Holder<S> schemaHolder = new Holder<>(null);

        try (reader) {
            S schema = yaml.load(reader, schemaTable);
            schemaHolder.set(schema);
        } catch (YAMLException e) {
            throw new SchemaLoadingException("Could not parse YAML", e);
        } catch (IOException e) {
            throw new SchemaLoadingException("Could not load content", e);
        }

        Version<?> schemaVersion = schemaTable.getVersion(schemaHolder.get());
        Version<?> latestTargetVersion = schemaTable.getLatestVersion();
        int versionComparison = schemaTable.getVersionManager().compareVersions(schemaVersion, latestTargetVersion);

        if (versionComparison < 0) {
            var ctx = new ContextOlderVersionDetected(schemaVersion, latestTargetVersion, schemaHolder);
            actionOlderVersionDetected.accept(ctx);
        }
        else if (versionComparison > 0) {
            var ctx = new ContextNewerVersionDetected(schemaVersion, latestTargetVersion, schemaHolder);
            actionNewerVersionDetected.accept(ctx);
        }
        var ctx = new ContextFinished(schemaVersion, latestTargetVersion, schemaHolder);
        actionFinished.accept(ctx);

        S schema = schemaHolder.get();
        return convert(schema);
    }

    @SuppressWarnings("unchecked")
    private T convert(S schema) {
        return ((CurrentSchema<T>) schema).convert();
    }

    public SchemaLoader<T, S> onOlderVersionDetected(Consumer<ContextOlderVersionDetected> actionOlderVersionDetected) {
        this.actionOlderVersionDetected = actionOlderVersionDetected;
        return this;
    }

    public SchemaLoader<T, S> onNewerVersionDetected(Consumer<ContextNewerVersionDetected> actionNewerVersionDetected) {
        this.actionNewerVersionDetected = actionNewerVersionDetected;
        return this;
    }

    public SchemaLoader<T, S> onFinished(Consumer<ContextFinished> actionFinished) {
        this.actionFinished = actionFinished;
        return this;
    }

    @Getter @AllArgsConstructor
    private abstract class Context {
        protected Version<?> schemaVersion;
        protected Version<?> latestTargetVersion;
        protected Holder<S> schemaHolder;

        public MultiVersionYaml getYaml() {
            return SchemaLoader.this.yaml;
        }

        public VersionSchemaTable<S> getSchemaTable() {
            return SchemaLoader.this.schemaTable;
        }

        public VersionUpdaterChain<S> getUpdaterChain() {
            return SchemaLoader.this.updaterChain;
        }

        public S getSchema() {
            return schemaHolder.get();
        }
    }

    public class ContextOlderVersionDetected extends Context {
        private ContextOlderVersionDetected(Version<?> schemaVersion, Version<?> latestTargetVersion, Holder<S> schemaHolder) {
            super(schemaVersion, latestTargetVersion, schemaHolder);
        }

        public void updateToLatest() {
            S schema = schemaHolder.get();
            schema = updaterChain.updateToLatest(schema, schemaVersion);
            schemaHolder.set(schema);
        }
    }

    public class ContextNewerVersionDetected extends Context {
        private ContextNewerVersionDetected(Version<?> schemaVersion, Version<?> latestTargetVersion, Holder<S> schemaHolder) {
            super(schemaVersion, latestTargetVersion, schemaHolder);
        }
    }

    public class ContextFinished extends Context {
        public ContextFinished(Version<?> schemaVersion, Version<?> latestTargetVersion, Holder<S> schemaHolder) {
            super(schemaVersion, latestTargetVersion, schemaHolder);
        }
    }
}
