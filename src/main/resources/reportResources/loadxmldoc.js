function loadXMLDoc(xmfFile) 
{
try //Internet Explorer
  {
  xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
  xmlDoc.async=false;
  xmlDoc.load(xmfFile); 
   return(xmlDoc);
  }
catch(e)
  {
    try //Firefox, Mozilla, Opera, etc.
       {
           xmlDoc=document.implementation.createDocument("","",null);
		   xmlDoc.async=false;
	       xmlDoc.load(xmfFile);
		   return(xmlDoc);
	   }
	catch(e)
	  {
		try
		{
			xmlObj = new XMLHttpRequest(); 
			xmlObj.open("GET", xmfFile, false);
			xmlObj.send(null);
			xmlDoc = xmlhttp.responseXML.documentElement;
		   return(xmlDoc);
		}
		catch(e)
		{
			 error=e.message; 
			 return(null);
		}
		
	  }  
 }
}