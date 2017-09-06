package com.mulesoft.services.tools.validation.report;

import com.mulesoft.services.tools.validation.domain.Attribute;
import com.mulesoft.services.tools.validation.domain.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mulesoft.services.tools.validation.report.MessageConstants.*;

/**
 * Report Builder.
 *
 */
public class ReportBuilder {
    public static Integer MUTEX = 1;
    public static ArrayList<String> lines = new ArrayList<>();

    /**
     * Filter lines from the report according the result.
     * @param result result of the validation rule execution ('PASS' or 'FAIL').
     * @return
     */
    public static List<String> getReportLines(String result) {
        return lines.stream().filter(line -> line.endsWith(result)).collect(Collectors.toList());
    }

    /**
     * Builds message without details.
     *
     * @param rule
     */
    public static void buildNoDetailsMessage(Rule rule, String result) {
        lines.add(rule.getId().concat(SPACE).concat(rule.getName()).concat(result));
    }

    /**
     * Builds message with details.
     *
     * @param rule
     * @param attribute
     * @param required
     * @param result
     */
    public static void buildMessage(Rule rule, Attribute attribute, String required, String result) {
        lines.add(rule.getId().concat(SPACE).concat(rule.getName()).concat(ATTRIBUTE).concat(attribute.getName())
                .concat(WITH_VALUE).concat(attribute.getValue()).concat(required).concat(result));
    }

    /**
     * Builds naming convention message with details.
     *
     * @param rule
     * @param name
     * @param attribute
     */
    public static void buildNamingMessage(Rule rule, String name, Attribute attribute, String result) {
        lines.add(rule.getId().concat(SPACE).concat(rule.getName()).concat(FLOW).concat(WITH_NAME).
                concat(SINGLE_QUOTE).concat(name).concat(SINGLE_QUOTE).concat(MATCHES).
                concat(SINGLE_QUOTE).concat(attribute.getValue()).concat(SINGLE_QUOTE).concat(result));
    }

}
