package com.toast.xml;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
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

import com.toast.xml.exception.XmlFormatException;
import com.toast.xml.exception.XPathExpressionException;

public class XmlNode
{
   //
   // XPath queries
   //

   /** A constant specifying an XPath query for retrieving all attributes nodes of an XML node. */
   public static final String ALL_ATTRIBUTE_NODES = "./@*";
   
   /** A constant specifying an XPath query for retrieving all direct child nodes an XML node. */
   public static final String ALL_DIRECT_CHILD_NODES = "*";
   
   public XmlNode(Node node)
   {
      this.node = node;
   }
   
   public String getName()
   {
      return (node.getNodeName());
   }
   
   public boolean hasAttribute(String name)
   {
      NamedNodeMap attributes = node.getAttributes();
      
      return ((attributes != null) &&
              (attributes.getNamedItem(name) != null));      
   }
      
   /*
   public String getAttribute(String name) throws XmlFormatException
   {
      String value = null;
      
      if (hasAttribute(name) == true)
      {
         value = node.getAttributes().getNamedItem(name).getNodeValue();
      }
      else
      {
         throw (new XmlFormatException(
                   String.format("Failed to find expected attribute \"%s\" in node:\n%s", name, node.toString())));
      }
      
      return (value);
   }
   */
   
   public XmlNode getAttribute(String name) throws XmlFormatException
   {
      // Validate the input.
      if (name == null)
      {
         throw (new IllegalArgumentException("Null name specified."));
      }
      
      if (hasAttribute(name) == false)
      {
         throw (new XmlFormatException(
                   String.format("Expected attribute [%s] in node: \n%s", name, this)));
      }
      else if (node.getAttributes().getNamedItem(name).getNodeValue() == null)
      {
         throw (new XmlFormatException(
                   String.format("Expected value for attribute [%s] in node: \n%s", name, this)));         
      }      
      
      return (new XmlNode(node.getAttributes().getNamedItem(name)));
   }

   /*
   public void setAttribute(String name, String value)
   {
      ((Element)node).setAttribute(name, value);
   }
   */
   
   public <T> void setAttribute(String name, T value)
   {
      // Validate the input.
      if (name == null)
      {
         throw (new IllegalArgumentException("Null name specified."));
      }
      else if (value == null)
      {
         throw (new IllegalArgumentException("Null value specified."));         
      }
      
      ((Element)node).setAttribute(name, value.toString());
   }
   
   public String getValue() throws XmlFormatException
   {
      if (node.getTextContent() == null)
      {
         throw (new XmlFormatException(String.format("Unexpected null value for node: \n%s", this)));            
      }
      
      return (node.getTextContent());
   }

   /*
   public void setValue(
      String value)
   {
      node.setTextContent(value);
   }
   */
   
   public <T> void setValue(T value)
   {
      // Validate the input.
      if (value == null)
      {
         throw (new IllegalArgumentException("Null value specified."));
      }
      
      node.setTextContent(value.toString());     
   }
   
   public int getIntValue() throws XmlFormatException
   {
      int value = 0;
      
      try
      {
         value = Integer.parseInt(getValue());
      }
      catch (NumberFormatException e)
      {
         throw (new XmlFormatException(String.format("Integer value expected in node: \n%s", this))); 
      }
      
      return (value);
   }   

   
   public double getDoubleValue() throws XmlFormatException
   {
      double value = 0;
      
      try
      {
         value = java.lang.Double.parseDouble(getValue());
      }
      catch (NumberFormatException e)
      {
         throw (new XmlFormatException(String.format("Double value expected in node: \n%s", this))); 
      }
      
      return (value);
   }
   
   public float getFloatValue() throws XmlFormatException
   {
      float value = 0;
      
      try
      {
         value = java.lang.Float.parseFloat(getValue());
      }
      catch (NumberFormatException e)
      {
         throw (new XmlFormatException(String.format("Float value expected in node: \n%s", this))); 
      }
      
      return (value);
   }
      
   public boolean getBoolValue() throws XmlFormatException
   {
      String value = getValue();
      
      if ((value.toLowerCase().contentEquals(Boolean.TRUE.toString()) == false) &&
          (value.toLowerCase().contentEquals(Boolean.FALSE.toString()) == false))
      {
         throw (new XmlFormatException(String.format("Boolean value expected in node: \n%s", this))); 
      }
      
      return (Boolean.parseBoolean(getValue()));
   }   
      
   public Character getCharacterValue() throws XmlFormatException
   {
      String value = getValue();
      
      if (value.length() != 1)
      {
         throw (new XmlFormatException(String.format("Character value expected in node: \n%s", this))); 
      }
      
      return(value.charAt(0));
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
   
   public boolean hasChild(String name)
   {
      // Note: We're choosing not to use the getNode() operation because XPath is relatively slow compared with
      //       simply looping through the direct children.
      
      // Validate the input.
      if (name == null)
      {
         throw (new IllegalArgumentException("Null name specified."));
      } 
      
      int count = 0;

      // Loop through all direct child nodes.
      Node tempNode = node.getFirstChild();
      while (tempNode != null)
      {
         // Look for a match.
         if (tempNode.getNodeName().contentEquals(name))
         {
           // Keep track of how many children we find.  We only expect one.
            count++;
         }
      
         // Next!
         tempNode = tempNode.getNextSibling();
      }

      return (count == 1);
   }   
      
   public XmlNode getChild(String name) throws XmlFormatException
   {
      // Note: We're choosing not to use the getNode() operation because XPath is relatively slow compared with
      //       simply looping through the direct children.
      
      // Validate the input.
      if (name == null)
      {
         throw (new IllegalArgumentException("Null name specified."));
      }           
      
      // Initialize our return value.
      Node foundNode = null;
      int count = 0;

      // Loop through all direct child nodes.
      Node tempNode = node.getFirstChild();
      while (tempNode != null)
      {
         // Look for a match.
         if (tempNode.getNodeName().contentEquals(name))
         {
            // Save off the first child that matches.
            if (foundNode == null)
            {
               foundNode = tempNode;
            }
            
           // Keep track of how many children we find.  We only expect one.
            count++;
         }
      
         // Next!
         tempNode = tempNode.getNextSibling();
      }
      
      // Verify we only encountered one match.
      if (count != 1)
      {
         throw (new XmlFormatException(String.format("Expected one \"%s\" node.  Found %d in node: \n%s", 
                                       name,
                                       count,
                                       toString())));         
      }

      return (new XmlNode(foundNode));
   }   
   
   
   public XmlNode appendChild(String name)
   {
      // Validate the input.
      if (name == null)
      {
         throw (new IllegalArgumentException("Null name specified."));
      }  
      
      Node childNode = node.getOwnerDocument().createElement(name);
      node.appendChild(childNode);
      
      return (new XmlNode(childNode));
   }   
   
   /*
   public XmlNode appendChild(String name, String value)
   {
      Node childNode = node.getOwnerDocument().createElement(name);
      childNode.setTextContent(value);
      node.appendChild(childNode);
      
      return (new XmlNode(childNode));
   }
   */
   
   public <T> XmlNode appendChild(String name, T value)
   {
      XmlNode node = appendChild(name);
      node.setValue(value.toString());

      return (node);      
   }
   
   /**
    * This operation retrieves a <code>XmlNodeList</code> containing all direct children of the node.
    * 
    * @return A <code>XmlNodeList</code> containing all direct child nodes.
    * <code>Null</code> is returned if the query was made on an invalid node.
    */     
   public XmlNodeList getChildren()
   {
      XmlNodeList childNodes = null;
      
      try
      {
         // Use an XPath expression to find all direct child nodes.
         // TODO: Try implementing without XPath for speed improvement.
         childNodes = getNodes(ALL_DIRECT_CHILD_NODES);
      }
      catch (XPathExpressionException e)
      {
         throw (new RuntimeException("Programmer error in XPath expression"));
      }      

      return (childNodes);
   }   
   
   public XmlNodeList getChildren(String name)
   {
      XmlNodeList childNodes = null;
      
      try
      {
         // Use an XPath expression to find all direct child nodes with the specified name.
         // TODO: Try implementing without XPath for speed improvement.
         childNodes = getNodes(name);         
      }
      catch (XPathExpressionException e)
      {
         throw (new RuntimeException("Programmer error in XPath expression"));
      }

      return (childNodes);
   }
      
   public XmlNodeList getNodes(String xpathString) throws com.toast.xml.exception.XPathExpressionException
   {
      // Initialize the return value.
      XmlNodeList nodeList = null;
      NodeList matchingNodes = null;
      
      // Create the components required for parsing an XPath expression.
      XPathFactory xPathFactory = XPathFactory.newInstance();
      XPath xPath = xPathFactory.newXPath();
      XPathExpression xPathExpression = null;
      
      try
      {
         // Create the Xpath expression, or retrieve it from the cache.
         if (xpathExpressionCache.containsKey(xpathString))
         {
            xPathExpression = xpathExpressionCache.get(xpathString);
         }
         else
         {
            xPathExpression = xPath.compile(xpathString);
            xpathExpressionCache.put(xpathString, xPathExpression);
         }
        
         // Execute the XPath query.
         matchingNodes = (NodeList)xPathExpression.evaluate(node, XPathConstants.NODESET);        
         
         // Wrap the result in an XmlNodeList.
         nodeList = new XmlNodeList(matchingNodes);
      }
      catch (javax.xml.xpath.XPathExpressionException e)
      {
         throw (new XPathExpressionException(String.format("Invalid XPath expression [%s]", xpathString), e));
      }
      
      return (nodeList);
   }
   
   @Override
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
      catch (TransformerException e)
      {
         logger.log(Level.WARNING, "Failed to convert node to string.");
      }
      
      return (stringWriter.toString());
   }
   
   private final static Logger logger = Logger.getLogger(XmlNode.class.getName());
   
   static HashMap<String, XPathExpression> xpathExpressionCache = new HashMap<String, XPathExpression>();
   
   private Node node;
}

      
