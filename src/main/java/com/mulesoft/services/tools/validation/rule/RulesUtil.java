package com.mulesoft.services.tools.validation.rule;

import org.jdom2.Document;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Common methods to obtain rules and nodes.
 *
 */
public class RulesUtil {

    /**
     * Returns first level nodes
     *
     * @param document
     * @return list of elements
     */
    public static List<Element> getRootNodes(Document document) {
        List<Element> nodes = new ArrayList<>();
        document.getRootElement().getChildren().forEach(e -> getChildrenNode(e, nodes) );
        return nodes;
    }

    /**
     * Add the node to the list
     *
     * @param element
     */
    private static void getChildrenNode(Element element, List<Element> nodes) {
        nodes.add(element);
        if( element.getChildren().size() > 0 )
            element.getChildren().forEach(e -> getChildrenNode(e, nodes));
    }

}
