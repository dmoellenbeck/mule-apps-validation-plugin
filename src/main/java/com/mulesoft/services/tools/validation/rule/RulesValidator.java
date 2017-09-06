package com.mulesoft.services.tools.validation.rule;

import com.mulesoft.services.tools.validation.domain.Rule;
import org.jdom2.Element;

import java.util.List;
import java.util.stream.Collectors;

import static com.mulesoft.services.tools.validation.report.MessageConstants.*;
import static com.mulesoft.services.tools.validation.report.ReportBuilder.*;

/**
 * Rules Validator
 *
 */
public class RulesValidator {
    public static boolean overallStatus = Boolean.TRUE;

    /**
     * Validates a rule against the nodes (elements of each flow of the Mule App).
     *
     * @param nodes
     * @param rule
     */
    public static void validateRuleAgainstNodes(List<Element> nodes, Rule rule) {
        List<Element> resultNodes = nodes.stream().filter(element -> element.getNamespacePrefix().equals(rule.getNode().getNamespace()) &&
                element.getName().equals(rule.getNode().getName())).collect(Collectors.toList());

        if (rule.getInclusive())
            validateInclusiveRule(rule, resultNodes);
        else
            validateExclusiveRule(rule, resultNodes);
    }

    /**
     * Validates an inclusive rule.
     *
     * @param rule
     * @param nodes
     */
    private static void validateInclusiveRule(Rule rule, List<Element> nodes) {
        if(nodes.size() > 0) {
            if (rule.getAttributes().isEmpty())
                buildNoDetailsMessage(rule, PASS);
            else {
                rule.getAttributes().forEach(attr -> {
                    nodes.forEach(node -> {

                        if (attr.getName().equals(NAME)) {
                            if(node.getAttributeValue(attr.getName()).matches(attr.getValue())) {
                                buildNamingMessage(rule, node.getAttributeValue(attr.getName()), attr, PASS);
                            } else {
                                overallStatus = Boolean.FALSE;
                                buildNamingMessage(rule, node.getAttributeValue(attr.getName()), attr, FAIL);
                            }
                        } else {
                            if(attr.getValue().equals(node.getAttributeValue(attr.getName()))) {
                                buildMessage(rule,attr, REQUIRED_ATTRIBUTE, PASS);
                            } else {
                                overallStatus = Boolean.FALSE;
                                buildMessage(rule, attr, REQUIRED_ATTRIBUTE, FAIL);
                            }
                        }
                    });
                });
            }
        } else {
            overallStatus = Boolean.FALSE;
            buildNoDetailsMessage(rule, FAIL);
        }
    }

    /**
     * Validates an exclusive rule.
     *
     * @param rule
     * @param nodes
     */
    private static void validateExclusiveRule(Rule rule, List<Element> nodes) {

        if(nodes.isEmpty()) {
            if (rule.getAttributes().isEmpty())
                buildNoDetailsMessage(rule, PASS);
        } else {
            if (rule.getAttributes().isEmpty()) {
                overallStatus = Boolean.FALSE;
                buildNoDetailsMessage(rule, FAIL);
            } else {
                rule.getAttributes().forEach(attr -> {
                    nodes.forEach(node -> {
                        if(attr.getValue().equals(node.getAttributeValue(attr.getName()))) {
                            overallStatus = Boolean.FALSE;
                            buildMessage(rule, attr, NOT_ALLOWED_ATTRIBUTE, FAIL);
                        } else {
                            buildMessage(rule,attr, NOT_ALLOWED_ATTRIBUTE, PASS);
                        }
                    });
                });
            }
        }
    }
}
