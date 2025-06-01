package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.util.StringUtil;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 값이 null인 프로퍼티를 무시하고, 대신 주입된 객체로부터 값을 가져와 객체를 구성하는 Constructor 입니다.
 */
public class SchematicConstructor extends Constructor {
    private final Object root;

    public SchematicConstructor(Object root, LoaderOptions loaderOptions) {
        super(root.getClass(), loaderOptions);
        addTypeDescription(new TypeDescription(root.getClass()));
        this.root = root;

        // replace mapping scalar strategy
        this.yamlClassConstructors.put(NodeId.mapping, new ConstructMapping());
    }

    @Override
    protected Object constructObjectNoCheck(Node node) {
        return super.constructObjectNoCheck(node);
    }

    protected class ConstructMapping extends Constructor.ConstructMapping {
        @Override
        protected Object constructJavaBean2ndStep(MappingNode node, Object object) {
            MappingNode newNode = preconstructNullScalarNodes(root, node);
            return super.constructJavaBean2ndStep(newNode, object);
        }

        private MappingNode preconstructNullScalarNodes(Object cur, MappingNode node) {
            List<NodeTuple> newNodeValue = new ArrayList<>();

            List<NodeTuple> nodeValue = node.getValue();
            for (NodeTuple tuple : nodeValue) {
                ScalarNode sKeyNode = (ScalarNode) tuple.getKeyNode();
                Node valueNode = tuple.getValueNode();
                String rawKey = sKeyNode.getValue();
                String camelCasedKey = StringUtil.getCamelCased(rawKey);

                if (valueNode instanceof MappingNode mValueNode) {
                    Node newValueNode = preconstructNullScalarNodes(mValueNode);
                    newNodeValue.add(new NodeTuple(sKeyNode, newValueNode));
                }
                else if (valueNode instanceof ScalarNode sValueNode) {
                    if (sValueNode.getTag() == Tag.NULL)
                }

                System.out.println(tuple);
            }

            return new MappingNode(node.getTag(), newNodeValue, node.getFlowStyle());
        }
    }
}
