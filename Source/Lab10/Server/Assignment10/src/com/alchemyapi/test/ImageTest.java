
package com.alchemyapi.test;

import com.alchemyapi.api.*;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.net.URLEncoder;

class ImageTest {
	public static void main(String[] args)
			throws IOException, SAXException,
			ParserConfigurationException, XPathExpressionException
	{
		
		String apiKey = "e446c91871eee0986fc72beef24971425917e373";
		// Create an AlchemyAPI object.
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString(apiKey);

		// Extract image for a web URL.
	//	Document doc = alchemyObj.URLGetImage("http://www.techcrunch.com/");
	//	System.out.println(getStringFromDocument(doc));

		String imageURL = "http://www.mascotdesign.com/_dev/images/famous-cartoon-character-mickey-mouse.png";
		//String imageURL = "https://pbs.twimg.com/profile_images/665351551100153856/yQVO5Lyw.jpg";
		
		Document doc = alchemyObj.URLGetRankedImageKeywords(imageURL);
		System.out.println(getStringFromDocument(doc));
            
		//byte[] imageByteArray = readFile("data/cat.jpg");

		//AlchemyAPI_ImageParams imageParams = new AlchemyAPI_ImageParams();
		//imageParams.setImage(imageByteArray);
		//imageParams.setImagePostMode(AlchemyAPI_ImageParams.RAW);
		//doc = alchemyObj.ImageGetRankedImageKeywords(imageParams);
		//System.out.println(getStringFromDocument(doc));
	}

	// utility function
	private static byte[] readFile(String file) throws IOException {
		// Open file
		RandomAccessFile f = new RandomAccessFile(new File(file), "r");
		try {
			// Get and check length
			long longlength = f.length();
			int length = (int) longlength;
			if (length != longlength)
				throw new IOException("File size >= 2 GB");
			// Read file and return data
			byte[] data = new byte[length];
			f.readFully(data);
			return data;
		} finally {
			f.close();
		}
	}

	// utility method
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
