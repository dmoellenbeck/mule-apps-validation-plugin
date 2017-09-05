package com.mulesoft.services.tools.validation;

import com.mulesoft.services.tools.validation.domain.Rule;
import com.mulesoft.services.tools.validation.domain.Rules;
import com.mulesoft.services.tools.validation.rule.RulesUtil;
import com.mulesoft.services.tools.validation.rule.RulesValidator;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;
import org.jdom2.Element;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Executor of the Rules Validation Process.
 *
 */
public class ValidationExecutor {

    private ArrayList<String> rulesPath;
    private ArrayList<String> files;
    private ArrayList<Rule> rules = new ArrayList<>();
    private List<Element> nodes = new ArrayList<>();

    /**
     * Executes the Validation process.
     *
     * @return overallStatus
     * @throws Exception
     */
    public boolean execute() throws Exception {

        parseRules();
        parseNodes();

        rules.forEach(rule -> RulesValidator.validateRuleAgainstNodes(nodes, rule));

        return RulesValidator.overallStatus;
    }

    private void parseNodes() throws Exception{

        for(String file: files) {
            nodes.addAll(RulesUtil.getRootNodes(generateDoc(file)));
        }

    }

    /**
     * Unmarshal process to obtain Java Objects from the rules xmls.
     *
     * @throws Exception
     */
    private void parseRules() throws Exception {

        String rulesFileName = "";

        try {

            for(String rulePath : rulesPath) {
                File ruleFile = new File(rulePath);
                rulesFileName = ruleFile.getName();
                InputStream file = new FileInputStream(ruleFile);
                JAXBContext context = JAXBContext.newInstance(Rules.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Rules rules = (Rules) unmarshaller.unmarshal(file);
                this.rules.addAll(rules.getRules());
            }

        } catch (Exception ex) {

            StringBuilder errorMessage = new StringBuilder();
            errorMessage
                    .append("Failed to parse Rules file ")
                    .append(rulesFileName)
                    .append(". ")
                    .append(ex.getMessage());

            throw new Exception(errorMessage.toString());
        }

    }

    /**
     * Generates a DOM Document based on the inputStream xml.
     *
     * @param filePath
     * @return document
     * @throws Exception
     */
    public Document generateDoc(String filePath) throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        File file = new File(filePath);
        return saxBuilder.build(file);
    }

    /**
     *
     * @param rulesPath the rulesPath to set
     */
    public void setRulesPath(ArrayList<String> rulesPath) {
        this.rulesPath = rulesPath;
    }

    /**
     *
     * @param files the files to set
     */
    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }
}
