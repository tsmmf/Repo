package mongoConnection;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

/**
 * Servlet implementation class PutDocument
 */
@WebServlet("/PutDocument")
public class PutDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PutDocument() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
   /* protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	
    	response.setHeader("Access-Control-Allow-Origin", "*");
    	response.setHeader("Access-Control-Allow-Methods", "POST,GET");
    	
    	doPost(request, response);
    }*/
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		
		System.out.println("I have called the post servlet");
		
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		
		while((line = reader.readLine())!= null){
		 
			buffer.append(line);
		}
		String data = buffer.toString();
		System.out.println("Data ::: " + data);
		
		JSONObject params = (JSONObject)JSON.parse(data);
		BasicDBObject user1 = new BasicDBObject(params);
		
		for (Object key : params.keySet().toArray()){
			user1.put(key.toString(), params.get(key));
		}
		
		MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds035014.mongolab.com:35014/sampledb");
		MongoClient client = new MongoClient(uri);
		
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("sample");
		WriteResult result = users.insert(user1);		
		
		response.getWriter().write(result.toString());
		BasicDBObject newDocument = new BasicDBObject();  
		newDocument.put("name", "shedhta");  
		newDocument.put("email", "tarun.shedhani@vzw.com");  
		newDocument.put("password", "ShedhaniTarun"); 		
	}
}
