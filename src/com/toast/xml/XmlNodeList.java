package com.toast.xml;

import org.w3c.dom.NodeList;

public class XmlNodeList
{
   public XmlNodeList(
      NodeList initNodeList)
   {
      nodeList = initNodeList;
   }
   
   
   public int getLength()
   {
      return (nodeList.getLength());
   }
   
   
   public XmlNode item(
      int index)
   {
      return (new XmlNode(nodeList.item(index)));
   }
   
   private NodeList nodeList;
}
