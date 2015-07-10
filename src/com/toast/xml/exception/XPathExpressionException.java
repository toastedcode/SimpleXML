package com.toast.xml.exception;

/**
 * The <code>XPathExpressionException</code> class represents an exception that may occur when a malformed XPath
 * expression is used to query an XML document.
 * 
 * @author jtost
 */ 
@SuppressWarnings("serial")
public class XPathExpressionException extends Exception
{
   /**
    * Constructor
    * 
    * @param e An exception to wrap.
    */   
   public XPathExpressionException(
      Exception e)
   {
      super(e);
   }
   
   /**
    * Constructor
    * 
    * @param message A message to include inside the exception.
    */     
   public XPathExpressionException(String message)
   {
      super(message);
   }   
   
   /**
    * Constructor
    * 
    * @param message A message to include inside the exception.
    * @param e       An exception to wrap.
    */      
   public XPathExpressionException(String message, Exception e)
   
   {
      super(message, e);
   }   
}