package com.mulesoft.services.tools.validation.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that represents a XML attribute.
 *
 */
@XmlRootElement(name="attribute")
public class Attribute {

    private String name;
    private String value;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    @XmlAttribute
    public void setValue(String value) {
        this.value = value;
    }
}
