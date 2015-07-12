package com.toast.xml;

import com.toast.xml.exception.XmlFormatException;

public interface Serializable
{
   public String getNodeName();
   
   public XmlNode serialize(XmlNode node);
   
   public void deserialize(XmlNode node) throws XmlFormatException;
}
