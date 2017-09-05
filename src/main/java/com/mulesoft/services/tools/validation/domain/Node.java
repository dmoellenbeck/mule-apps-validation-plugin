package com.mulesoft.services.tools.validation.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that represents a XML Node.
 *
 */
@XmlRootElement(name="node")
public class Node {

    private String namespace;
    private String name;

    /**
     * @return namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    @XmlElement
    public void setNamespace(String namespace) {
        this.namespace = namespace;
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
}
