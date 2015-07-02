package com.toast.xml;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlNode
{
   // Constructor.
   public XmlNode(
      Node initNode)
   {
      node = initNode;
   }
   
   
   public String getName()
   {
      return (node.getNodeName());
   }
   
   
   public boolean hasAttribute(
      String name)
   {
      NamedNodeMap attributes = node.getAttributes();
      
      return ((attributes != null) &&
              (attributes.getNamedItem(name) != null));      
   }
   
   
   public String getAttribute(
      String name)
   {
      String value = null;
      
      if (hasAttribute(name) == true)
      {
         value = node.getAttributes().getNamedItem(name).getNodeValue();
      }
      
      return (value);
   }
   
   
   public void setAttribute(
      String name,
      String value)
   {
      ((Element)node).setAttribute(name, value);
   }
   
   public <T> void setAttribute(String name, T value)
   {
      ((Element)node).setAttribute(name, value.toString());
   }
   
   public String getValue()
   {
      return (node.getTextContent());
   }
   
   
   public void setValue(
      String value)
   {
      node.setTextContent(value);
   }
   
   public <T> void setValue(T value)
   {
      node.setTextContent(value.toString());     
   }
   
   public int getIntValue()
   {
      
      return (Integer.parseInt(getValue()));
   }   

   
   public double getDoubleValue()
   {
      
      return (java.lang.Double.parseDouble(getValue()));
   }
   
   
   public boolean getBoolValue()
   {
      return (Boolean.parseBoolean(getValue()));
   }   
   
   
   public Character getCharacterValue()
   {
      return(getValue().charAt(0));
   }    

   
   /*
   public boolean hasChild(
      String name)
   {
      boolean returnStatus = false;
      
      if (node.getNodeType() == Node.ELEMENT_NODE)
      {
         // Retrieve all child nodes with the specified name.
         NodeList childNodes = ((Element)node).getElementsByTagName(name);
         
         returnStatus = (childNodes.getLength() == 1);
      }
      
      return (returnStatus);
   }   
   
   
   public XmlNode getChild(
      String name)
   {
      Node childNode = null;
      
      if (node.getNodeType() != Node.ELEMENT_NODE)
      {
         logger.log(
               Level.WARNING, 
               "Invalid call on a non-Element node.");           
      }
      else
      {
         // Retrieve all child nodes with the specified name.
         NodeList childNodes = ((Element)node).getElementsByTagName(name);
         
         // We're only expecting to find one.
         if (childNodes.getLength() != 1)
         {
            logger.log(
                  Level.WARNING, 
                  String.format(
                     "Expected one \"%s\" node.  Found %d.", 
                     name,
                     childNodes.getLength()));  
         }
         else
         {
            childNode = childNodes.item(0);
         }
      }  // end if (node() != Node.ELEMENT_NODE)
      
      return (new XmlNode(childNode));
   }
   */
   
   public boolean hasChild(
      String name)
   {
      XmlNodeList childNodes = getNodes("./" + name);
      return (childNodes.getLength() == 1);
   }   
      
      
   public XmlNode getChild(
      String name)
   {
      XmlNode childNode = null;
      
      XmlNodeList childNodes = getNodes("./" + name);

      // We're expecting to find one, and only one.
      if (childNodes.getLength() != 1)
      {
         logger.log(
               Level.WARNING, 
               String.format(
                  "Expected one \"%s\" node.  Found %d.", 
                  name,
                  childNodes.getLength()));  
      }
      else
      {
         childNode = childNodes.item(0);
      }

      return (childNode);
   }   
   
   
   public XmlNode appendChild(
      String name)
   {
      Node childNode = node.getOwnerDocument().createElement(name);
      node.appendChild(childNode);
      
      return (new XmlNode(childNode));
   }   
   
   
   public XmlNode appendChild(
      String name,
      String value)
   {
      Node childNode = node.getOwnerDocument().createElement(name);
      childNode.setTextContent(value);
      node.appendChild(childNode);
      
      return (new XmlNode(childNode));
   }
   
   public <T> XmlNode appendChild(String name, T value)
   {
      XmlNode node = appendChild(name);
      node.setValue(value.toString());

      return (node);      
   }
   
   public XmlNodeList getChildren(
      String name)
   {
      NodeList childNodes = null;
      
      if (node.getNodeType() != Node.ELEMENT_NODE)
      {
         logger.log(
               Level.WARNING, 
               "Invalid call on a non-Element node.");           
      }
      else
      {
         // Retrieve all child nodes with the specified name.
         childNodes = ((Element)node).getElementsByTagName(name);
         
      }  // end if (node.getNodeType() != Node.ELEMENT_NODE)
      
      return (new XmlNodeList(childNodes));
   }
   
   
   public XmlNodeList getNodes(
      String xpathString)
   {
      NodeList matchingNodes = null;
      
      XPathFactory xPathFactory = XPathFactory.newInstance();
      XPath xPath = xPathFactory.newXPath();
      XPathExpression xPathExpression = null;
      
      try
      {
         xPathExpression = xPath.compile(xpathString);
      }
      catch (Exception e)
      {
         logger.log(Level.WARNING,                   
                    String.format(
                    "Exception!  Failed to compile xPath expression: %s", 
                    xpathString));  
      }
      
      try
      {
         matchingNodes = 
            (NodeList)xPathExpression.evaluate(node, XPathConstants.NODESET);         
      }
      catch (Exception e)
      {
         logger.log(Level.WARNING,                   
                    String.format(
                    "Exception!  Failed to evaluate xPath expression: %s", 
                    xpathString));  
      }
      
      return (new XmlNodeList(matchingNodes));
   }
   
   
   public String toString()
   {
      StringWriter stringWriter = new StringWriter();
      
      try
      {
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         transformerFactory.setAttribute("indent-number", new Integer(3));
         Transformer transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.transform(new DOMSource(node), new StreamResult(stringWriter));
      }
      catch (Exception e)
      {
         logger.log(Level.WARNING, "Failed to convert node to string.");
      }
      
      return (stringWriter.toString());
   }
   
   private final static Logger logger = Logger.getLogger(XmlNode.class.getName());  
   
   private Node node;
}

      
