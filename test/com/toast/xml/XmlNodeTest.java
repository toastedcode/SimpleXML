package com.toast.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.toast.xml.exception.XmlFormatException;
import com.toast.xml.exception.XmlParseException;

public class XmlNodeTest
{
   @Test
   public void testGetName() throws IOException, XmlParseException, XmlFormatException
   {
      XmlDocument document = new XmlDocument();
      
      document.createRootNode("animals");
      
      assertTrue(document.getRootNode().getName().contentEquals("animals"));
   }

   @Test
   public void testHasAttribute()
   {
      XmlDocument document = new XmlDocument();
      
      document.createRootNode("book");
      
      document.getRootNode().setAttribute("author",  "jtost");
      
      assertTrue(document.getRootNode().hasAttribute("author"));
      assertFalse(document.getRootNode().hasAttribute("illustrator"));
   }

   @Test
   public void testGetAttribute() throws XmlFormatException
   {
      XmlDocument document = new XmlDocument();
      
      document.createRootNode("book");
      
      document.getRootNode().setAttribute("author",  "jtost");
      
      XmlNode attribute = document.getRootNode().getAttribute("author");
      
      assertTrue(attribute != null);
      assertTrue(attribute.getValue().contentEquals("jtost"));
   }

   @Test
   public void testGetValue() throws XmlFormatException
   {
      XmlDocument document = new XmlDocument();
      
      XmlNode node = document.createRootNode("animals").appendChild("dog");
      
      node.appendChild("name").setValue("Sparky");
      assertTrue(node.hasChild("name"));
      assertTrue(node.getChild("name").getValue().contentEquals("Sparky"));
      
      node.appendChild("age").setValue(5);
      assertTrue(node.hasChild("age"));
      assertTrue(node.getChild("age").getIntValue() == 5);
      
      node.appendChild("weight").setValue(new Double(25.6));
      assertTrue(node.hasChild("weight"));
      assertTrue(node.getChild("weight").getDoubleValue() == 25.6);
      
      node.appendChild("height").setValue(new Float(3.1));
      assertTrue(node.hasChild("height"));
      assertTrue(node.getChild("height").getFloatValue() == 3.1);
      
      node.appendChild("isNeutered").setValue(true);
      assertTrue(node.hasChild("isNeutered"));
      assertTrue(node.getChild("isNeutered").getBoolValue() == true);
      
      node.appendChild("grade").setValue('A');
      assertTrue(node.hasChild("grade"));
      assertTrue(node.getChild("grade").getCharacterValue().equals('A'));
   }

   @Test
   public void testHasChild()
   {
      XmlDocument document = new XmlDocument();
      
      document.createRootNode("animals").appendChild("dog");
      
      assertTrue(document.getRootNode().hasChild("dog"));
      assertFalse(document.getRootNode().hasChild("cat"));
   }

   @Test
   public void testGetChild_Valid() throws XmlFormatException
   {
      XmlDocument document = new XmlDocument();
      
      document.createRootNode("animals").appendChild("dog");
      
      assertTrue(document.getRootNode().getChild("dog") != null);
   }
   
   @Test(expected=XmlFormatException.class)
   public void testGetChild_NotFound() throws XmlFormatException
   {
      XmlDocument document = new XmlDocument();
      
      assertTrue(document.getRootNode().getChild("dog") == null);
   }
   
   @Test(expected=XmlFormatException.class)
   public void testGetChild_MoreThanOne() throws XmlFormatException
   {
      XmlDocument document = new XmlDocument();
      
      XmlNode root = document.createRootNode("animals");
      
      root.appendChild("dog");
      root.appendChild("dog");
      
      assertTrue(document.getRootNode().getChild("dog") == null);
   }

   @Test
   public void testAppendChildStringT()
   {
      fail("Not yet implemented");
   }

   @Test
   public void testGetChildren()
   {
      XmlDocument document = new XmlDocument();
      
      XmlNode root = document.createRootNode("animals");
      
      XmlNodeList children = root.getChildren();
      
      assertTrue(children.size() == 0);
      
      root.appendChild("dog");
      root.appendChild("dog");
      
      children = root.getChildren();
      
      assertTrue(children.size() == 2);
   }

   @Test
   public void testGetChildrenString()
   {
      fail("Not yet implemented");
   }

   @Test
   public void testGetNodes()
   {
      XmlDocument document = new XmlDocument();
      
      XmlNode root = document.createRootNode("animals");
      
      XmlNodeList children = root.getChildren("dog");
      
      assertTrue(children.size() == 0);
      
      root.appendChild("dog");
      root.appendChild("dog");
      root.appendChild("cat");
      
      children = root.getChildren("dog");
      
      assertTrue(children.size() == 2);
   }

   @Test
   public void testToString()
   {
      fail("Not yet implemented");
   }

   private XmlDocument loadTestDocument(String filename) throws IOException, XmlParseException
   {
      String pathString = getClass().getResource("/resources/" + filename).getFile();
      pathString = new File(pathString).getAbsolutePath();
      Path path = Paths.get(pathString);
      
      XmlDocument document = new XmlDocument();
      document.load(path);
      
      return (document);
   }
}
