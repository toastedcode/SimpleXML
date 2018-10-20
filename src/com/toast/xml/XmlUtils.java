package com.toast.xml;

import java.awt.Color;

import com.toast.xml.XmlNode;
import com.toast.xml.exception.XmlFormatException;

public class XmlUtils
{
   public static int getInt(XmlNode node, String attribute, int defaultValue)
   {
      int value = defaultValue;
      
      try
      {
         value = node.getAttribute(attribute).getIntValue();         
      }
      catch (XmlFormatException e)
      {
         // No action.
      }
      
      return (value);
   }
   
   public static String getString(XmlNode node, String attribute, String defaultValue)
   {
      String value = defaultValue;

      try
      {
         value = node.getAttribute(attribute).getValue();    
      }
      catch (XmlFormatException e)
      {
         // No action.
      }
      
      return (value);
   }
   
   public static boolean getBool(XmlNode node, String attribute, boolean defaultValue)
   {
      boolean value = defaultValue;
      
      try
      {
         value = node.getAttribute(attribute).getBoolValue();   
      }
      catch (XmlFormatException e)
      {
         // No action.
      }
      
      return (value);
   }
   
   public static Color getColor(XmlNode node, String attribute, Color defaultValue)
   {
      Color value = defaultValue;
      
      try
      {
         if (node.hasAttribute(attribute))
         {
            value = Color.decode(node.getAttribute(attribute).getValue());  
         }
      }
      catch (NumberFormatException | XmlFormatException e)
      {
         value = null;
      }
      
      return (value);
   }
}
