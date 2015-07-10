package com.toast.xml.exception;

@SuppressWarnings("serial")
public class XmlSerializeException extends Exception
{
   public XmlSerializeException()
   {
      super();
   }
   
   public XmlSerializeException(Exception e)
   {
      super(e);
   }
}
