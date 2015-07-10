package com.toast.xml.exception;

@SuppressWarnings("serial")
public class XmlFormatException extends Exception
{
   public XmlFormatException(String message)
   {
      super(message);
   }
   
   public XmlFormatException(Exception e)
   {
      super(e);
   }
}
