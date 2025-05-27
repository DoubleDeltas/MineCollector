package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.version.Version;
import com.doubledeltas.minecollector.version.VersionSchemaTable;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.scanner.Scanner;

import java.io.Reader;

public class MultiVersionYaml extends Yaml {
    private final BaseConstructor metaConstructor;

    public MultiVersionYaml(BaseConstructor constructor, Representer representer) {
        super(constructor, representer);

        this.metaConstructor = new Constructor(loadingConfig);
        metaConstructor.setPropertyUtils(this.constructor.getPropertyUtils());

        this.constructor.getPropertyUtils().setSkipMissingProperties(true);
    }

    public <T> T load(Reader reader, VersionSchemaTable<T> table) {
        return load(new StreamReader(reader), table);
    }

    @SuppressWarnings("unchecked")
    public <T> T load(StreamReader sreader, VersionSchemaTable<T> table) {
        Composer composer = new Composer(new ParserImpl(sreader, loadingConfig), resolver, loadingConfig);

        Node rootNode = composer.getSingleNode();
        CacheComposer rootNodeComposer = new CacheComposer(rootNode);

        metaConstructor.addTypeDescription(new TypeDescription(table.getMetadataType()));
        metaConstructor.setComposer(rootNodeComposer);
        T metadata = (T) metaConstructor.getSingleData(table.getMetadataType());

        System.out.println(metadata);
        Version<?> docVer = table.getVersion(metadata);
        Version<?> targetVer = table.getNearestOlderVersion(docVer);

        Class<? extends T> schemaType = table.getSchemaType(targetVer);
        constructor.addTypeDescription(new TypeDescription(schemaType));
        rootNodeComposer.setNodeType(schemaType);
        constructor.setComposer(rootNodeComposer);

        return (T) constructor.getSingleData(schemaType);
    }

    /**
     * node를 저장한 뒤 같은 노드를 반환하는 composer
     */
    private static class CacheComposer extends Composer {
        private final Node node;

        CacheComposer(Node node) {
            super(new ParserImpl((Scanner) null), new Resolver(), new LoaderOptions());
            this.node = node;
        }

        @Override
        public Node getSingleNode() {
            return node;
        }

        @Override
        public boolean checkNode() {
            return true;
        }

        @Override
        public Node getNode() {
            return getSingleNode();
        }

        public void setNodeType(Class<?> type) {
            node.setType(type);
        }
    }
}
