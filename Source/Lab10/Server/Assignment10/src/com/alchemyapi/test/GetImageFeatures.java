package com.alchemyapi.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;
import com.ibm.json.java.JSONObject;

/**
 * Servlet implementation class GetImageFeatures
 */
@WebServlet("/GetImageFeatures")
public class GetImageFeatures extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetImageFeatures() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setHeader("Access-Control-Allow-Origin", "*");

		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;

		while((line = reader.readLine())!= null){

			buffer.append(line);
		}
		
		JSONObject jsonObj = JSONObject.parse(buffer.toString());
	
		String imageURL = (String) jsonObj.get("url");

		String apiKey = "e446c91871eee0986fc72beef24971425917e373";
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString(apiKey);
		
		Document doc = null;
		try {
			doc = alchemyObj.URLGetRankedImageKeywords(imageURL);
		} catch (XPathExpressionException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String type = "";
		if (getStringFromDocument(doc).contains("person")){
			type = "person";
		}
		else
			type = "cartoon";
		
		JSONObject obj = new JSONObject();
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
}
