package com.mulesoft.services.tools.validation.rule;

import static com.mulesoft.services.tools.validation.report.MessageConstants.*;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 * Common methods to obtain rules and nodes.
 *
 */
public class RulesUtil {
    private static List<Element> nodes = new ArrayList<>();

    /**
     * Returns first level nodes
     *
     * @param document
     * @return list of elements
     */
    public static List<Element> getRootNodes(Document document) {

        document.getRootElement().getChildren().forEach(e -> getChildrenNode(e) );
        return nodes;
    }

    /**
     * Add the node to the list
     *
     * @param element
     */
    private static void getChildrenNode(Element element) {
        nodes.add(element);
        if( element.getChildren().size() > 0 )
            element.getChildren().forEach(e -> getChildrenNode(e));
    }

}
