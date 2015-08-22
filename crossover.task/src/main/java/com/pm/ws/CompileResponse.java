
package com.pm.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for compileResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="compileResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="exitCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="output" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compileResponse", propOrder = {
    "exitCode",
    "output"
})
public class CompileResponse {

    protected int exitCode;
    protected String output;

    /**
     * Gets the value of the exitCode property.
     * 
     */
    public int getExitCode() {
        return exitCode;
    }

    /**
     * Sets the value of the exitCode property.
     * 
     */
    public void setExitCode(int value) {
        this.exitCode = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutput(String value) {
        this.output = value;
    }

}
