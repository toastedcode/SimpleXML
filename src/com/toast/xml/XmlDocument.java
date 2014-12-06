package com.toast.xml;

import java.io.FileWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XmlDocument
{
   // Constructor.
   public XmlDocument()
   {
      document = null;
   }
   
   
   public boolean createRootNode(
      String name)
   {
      boolean returnStatus = true;
      
      document = null;
      
      try
      {
         // Create a document builder.
         DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    
         // Create the document and root node.
         document = docBuilder.newDocument();
         Element rootElement = document.createElement(name);
         document.appendChild(rootElement);
         
         returnStatus = true;
      }
      catch (Exception e)
      {
         logger.log(Level.WARNING, "Exception!  Failed to create XML document.");              
      }
      
      return (returnStatus);
   }
   
   
   public boolean load(
      String filename)
   {
      DocumentBuilder db = null;
      
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      try
      {
         db = dbf.newDocumentBuilder();
      }
      catch (Exception e)
      {
         logger.log(Level.WARNING, "Exception!  Failed to get a new DocumentBuilder object.");    
      }
      
      if (db == null)
      {
         logger.log(Level.WARNING, "Failed to get a new DocumentBuilder object.");            
      }
      else
      {
         try
         {
            document = db.parse(filename);
         }
         catch  (Exception e)
         {
            logger.log(Level.WARNING, "Exception!  Failed to parse the XML document.");  
         }
      }
      
      return (document != null);      
   }
   
   
   public boolean save(
      String filename)
   {
      boolean returnStatus = false;
      
      try
      {
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         transformerFactory.setAttribute("indent-number", new Integer(3));
         Transformer transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
   
         StreamResult result = new StreamResult(new FileWriter(filename));
         DOMSource source = new DOMSource(document);
         transformer.transform(source, result);
         
         returnStatus = true;
      }
      catch (Exception e)
      {
         logger.log(Level.WARNING, "Exception!  Failed to save the XML document.");           
         System.out.println(e.toString());
      }
      
      return (returnStatus);
   }
      
   
   public XmlNode getRootNode()
   {
      return (new XmlNode((Node)document.getDocumentElement()));      
   }
   
   
   public String toString()
   {
      return (getRootNode().toString());
   }
   
   private final static Logger logger = Logger.getLogger(XmlDocument.class.getName());   
   
   private Document document;
}
