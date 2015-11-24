package com.alchemyapi.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;
import com.ibm.json.java.JSONObject;

/**
 * Servlet implementation class GetTextSentiment
 */
@WebServlet("/GetTextSentiment")
public class GetTextSentiment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTextSentiment() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");	
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		
        String apiKey = "e446c91871eee0986fc72beef24971425917e373";
        String text = request.getParameter("keytext");
        System.out.println("Text = " + text);
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString(apiKey);
		Document doc = null;
		try {
			doc = alchemyObj.TextGetTextSentiment(text);
		} catch (XPathExpressionException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String TEST_XML_STRING = getStringFromDocument(doc);
		//System.out.println(TEST_XML_STRING);
        
		DocumentBuilder db = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(TEST_XML_STRING));

	    Document doc1 = null;
		try {
			doc1 = db.parse(is);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    NodeList nodes = doc1.getElementsByTagName("docSentiment");

	    String score = "", type="";
		for (int i = 0; i < nodes.getLength(); i++) {
	      Element element = (Element) nodes.item(i);

	      NodeList name = element.getElementsByTagName("score");
	      Element line = (Element) name.item(0);
	      score = getCharacterDataFromElement(line);

	      NodeList title = element.getElementsByTagName("type");
	      line = (Element) title.item(0);
	      type =  getCharacterDataFromElement(line);
	   }
	    
	   // Creating a json object to return the result
	   JSONObject obj = new JSONObject();
	   obj.put("Score", score);
	   obj.put("Type", type);
	   
	   PrintWriter out = response.getWriter();
	   out.println(obj);
	}
	
	private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	 public static String getCharacterDataFromElement(Element e) {
		    Node child = e.getFirstChild();
		    if (child instanceof CharacterData) {
		    	
		      CharacterData cd = (CharacterData) child;
		      return cd.getData();
		    }
		    return "";
		  }

}
