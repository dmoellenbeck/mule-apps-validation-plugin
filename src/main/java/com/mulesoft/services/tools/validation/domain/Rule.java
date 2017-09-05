package com.mulesoft.services.tools.validation.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that represents a Rule.
 *
 */
@XmlRootElement(name="rule")
public class Rule {

    private String id;
    private String name;
    private String type;
    private Node node;
    private List<Attribute> attributes = new ArrayList<>();
    private Boolean inclusive;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return node
     */
    public Node getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    @XmlElement
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * @return true/false
     */
    public Boolean getInclusive() {
        return inclusive;
    }

    /**
     * @param inclusive, sets if the rule is inclusive or not
     */
    @XmlElement
    public void setInclusive(Boolean inclusive) {
        this.inclusive = inclusive;
    }

    /**
     * @return attributes
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    @XmlElementWrapper(name="attributes")
    @XmlElement(name="attribute")
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
