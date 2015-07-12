package com.toast.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.toast.xml.exception.XPathExpressionException;
import com.toast.xml.exception.XmlFormatException;
import com.toast.xml.exception.XmlParseException;

public class XmlNodeListTest
{
   @Test
   public void testGetNodes() throws IOException, XmlParseException
   {
      try
      {
         XmlDocument document = loadTestDocument();
         
         XmlNodeList childNodes = document.getRootNode().getNodes("*");
      
         assertTrue(childNodes.size() == 4);
         
         System.out.println("*** getNodes(\"*\") ***");
         
         int index = 0;
         for (XmlNode node : childNodes)
         {
            System.out.format("child[%d] = %s\n", index, node.getName());
            index++;
         }
         
         assertTrue(index == 4);
      }
      catch (XPathExpressionException e) 
      {
         throw (new RuntimeException("Programmer error!  Bad XPath construction."));
      }
   }
   
   @Test
   public void testGetChildren() throws IOException, XmlParseException, XmlFormatException
   {
      XmlDocument document = loadTestDocument();
      
      XmlNodeList childNodes = document.getRootNode().getChildren("dog");
      
      assertTrue(childNodes.size() == 2);
      
      System.out.println("*** getChildren(\"dogs\") ***");
      
      int index = 0;
      for (XmlNode node : childNodes)
      {
         System.out.format("child[%d] = %s\n", index, node.getAttribute("name"));
         index++;
      }
      
      assertTrue(index == 2);
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
