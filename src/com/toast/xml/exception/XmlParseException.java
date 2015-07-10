package com.toast.xml.exception;

@SuppressWarnings("serial")
public class XmlParseException extends Exception
{
   public XmlParseException()
   {
      super();
   }
   
   public XmlParseException(Exception e)
   {
      super(e);
   }
}
