package com.toast.xml;

import java.util.ArrayList;

import org.w3c.dom.NodeList;

@SuppressWarnings("serial")
public class XmlNodeList extends ArrayList<XmlNode>
{
   public XmlNodeList(NodeList nodeList)
   {
      this.nodeList = nodeList;
      
      for (int i = 0; i < nodeList.getLength(); i++)
      {
         add(new XmlNode(nodeList.item(i)));
      }
   }
   
   @Deprecated
   public int getLength()
   {
      return (nodeList.getLength());
   }
   
   @Deprecated
   public XmlNode item(
      int index)
   {
      return (new XmlNode(nodeList.item(index)));
   }
   
   private NodeList nodeList;
}
