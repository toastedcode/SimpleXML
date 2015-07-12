package com.toast.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.toast.xml.exception.XmlFormatException;
import com.toast.xml.exception.XmlParseException;
import com.toast.xml.exception.XmlSerializeException;

public class XmlDocumentTest
{
   @Test
   public void testXmlDocument()
   {
      XmlDocument document = new XmlDocument();
      
      assertTrue(document.getRootNode() == null);
   }

   @Test
   public void testCreateRootNode()
   {
      XmlDocument document = new XmlDocument();
      
      document.createRootNode("root");
      
      assertTrue(document.getRootNode() != null);
      assertTrue(document.getRootNode().getName().equals("root"));
   }

   @Test
   public void testParse_Valid() throws XmlParseException, XmlFormatException
   {
      String xmlString = "<root id=\"root\"><node id=\"node\"></node></root>";
      
      XmlDocument document = new XmlDocument();
      
      document.parse(xmlString);
      
      assertTrue(document.getRootNode() != null);
      assertTrue(document.getRootNode().getName().equals("root"));
      assertTrue(document.getRootNode().getAttribute("id").equals("root"));
      assertTrue(document.getRootNode().hasChild("node") == true);
      assertTrue(document.getRootNode().getChild("node").getAttribute("id").equals("node"));
   }
   
   @Test(expected=XmlParseException.class)
   public void testParse_ParseException() throws XmlParseException, XmlFormatException
   {
      String xmlString = "<root><node></root>";
      
      XmlDocument document = new XmlDocument();
      
      document.parse(xmlString);
   }
   
   @Test(expected=XmlFormatException.class)
   public void testParse_FormatException() throws XmlParseException, XmlFormatException
   {
      String xmlString = "<root><node/></root>";
      
      XmlDocument document = new XmlDocument();
      
      document.parse(xmlString);
      
      document.getRootNode().getChild("invalid");
   }

   @Test
   public void testLoad() throws IOException, XmlParseException, XmlFormatException
   {
      XmlDocument document = loadTestDocument();
      
      assertTrue(document.getRootNode().getName().equals("animals"));
      assertTrue(document.getRootNode().getChild("dog").getAttribute("name").equals("Ginger"));
      assertTrue(document.getRootNode().getChild("cat").getAttribute("name").equals("Polly"));
      assertTrue(document.getRootNode().getChild("hamster").getAttribute("name").equals("Gizmo"));
   }

   @Test
   public void testSave() throws XmlParseException, IOException, XmlSerializeException
   {
      String xmlString = "<cars><duster/><tempo/><vibe/></cars>";
      
      XmlDocument document = new XmlDocument();
      
      document.parse(xmlString);
      
      String pathString = getClass().getResource("/resources").getFile();
      pathString = new File(pathString).getAbsolutePath();
      Path path = Paths.get(pathString);
      
      document.save(path);
   }

   @Test
   public void testToString() throws IOException, XmlParseException
   {
      XmlDocument document = loadTestDocument();
      
      System.out.printf(document.toString());
   }
   
   private XmlDocument loadTestDocument() throws IOException, XmlParseException
   {
      String pathString = getClass().getResource("/resources/animals.xml").getFile();
      pathString = new File(pathString).getAbsolutePath();
      Path path = Paths.get(pathString);
      
      XmlDocument document = new XmlDocument();
      document.load(path);
      
      return (document);
   }
}
