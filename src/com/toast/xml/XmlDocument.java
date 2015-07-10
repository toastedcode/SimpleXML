package com.toast.xml;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.toast.xml.exception.XmlParseException;
import com.toast.xml.exception.XmlSerializeException;

public class XmlDocument
{
   public XmlDocument()
   {
      document = null;
   }
      
   public XmlNode createRootNode(String name)
   {
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
      }
      catch (ParserConfigurationException e)
      {
         // TODO: Logging
         System.out.println("Exception!  Failed to create root node.");              
      }
      
      return (new XmlNode((Node)document.getDocumentElement()));
   }
   
   public XmlNode getRootNode()
   {
      XmlNode node = null;
      
      if (document != null)
      {
         node = new XmlNode((Node)document.getDocumentElement());
      }
      
      return (node);
   }
   
   public void parse(String xmlString) throws XmlParseException
   {
      try
      {
         DocumentBuilder db = null;
         
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
   
         db = dbf.newDocumentBuilder();
      
         document = db.parse(new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8)));
      }
      catch (IOException | ParserConfigurationException | SAXException e)
      {
         throw (new XmlParseException(e));
      }
   }
   
   /*
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
   
   public boolean load(
      InputStream inputStream)
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
            document = db.parse(inputStream);
         }
         catch  (Exception e)
         {
            logger.log(Level.WARNING, "Exception!  Failed to parse the XML document.");  
         }
      }
      
      return (document != null);      
   }
   */
   
   public void load(Path path) throws IOException, XmlParseException
   {
      DocumentBuilder db = null;
      
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      try
      {
         db = dbf.newDocumentBuilder();
         
         document = db.parse(path.toFile());
      }
      catch (ParserConfigurationException | SAXException e)
      {
         throw (new XmlParseException(e));
      }
   }
   
   /*
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
   */
   
   public void save(Path path) throws IOException, XmlSerializeException
   {
      try
      {
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         
         transformerFactory.setAttribute("indent-number", new Integer(3));
         Transformer transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
   
         StreamResult result = new StreamResult(new FileWriter(path.toFile()));
         DOMSource source = new DOMSource(document);
         transformer.transform(source, result);
      }
      catch (TransformerException e)
      {
         throw (new XmlSerializeException(e));         
      }
   }
   
   public String toString()
   {
      return (getRootNode().toString());
   }
   
   private Document document;
}
