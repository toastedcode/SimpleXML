package com.toast.xml;

public interface Serializable
{
   public String getNodeName();
   
   public XmlNode serialize(XmlNode node);
   
   public void deserialize(XmlNode node);
}
