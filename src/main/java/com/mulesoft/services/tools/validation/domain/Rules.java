package com.mulesoft.services.tools.validation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Rules Wrapper
 *
 */
@XmlRootElement(name="rules")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rules {

    @XmlElement(name="rule")
    private List<Rule> rules = null;

    /**
     * @return rules
     */
    public List<Rule> getRules() {
        return rules;
    }

    /**
     * @param rules the rules to set
     */
    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
