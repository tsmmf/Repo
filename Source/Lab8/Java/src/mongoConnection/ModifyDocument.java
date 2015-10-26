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
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Servlet implementation class ModifyDocument
 */
@WebServlet("/ModifyDocument")
public class ModifyDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyDocument() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		response.setHeader("Access-Control-Allow-Origin", "*");	
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
		doPut(request,response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");		
		System.out.println("Inside the put servlet");
		
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		
		while((line = reader.readLine())!= null){
		 
			buffer.append(line);
		}
		String data = buffer.toString();
				
		JSONObject params = (JSONObject)JSON.parse(data);
		BasicDBObject user1 = new BasicDBObject(params);
		
		String name = (String) params.get("username");
		String email = (String) params.get("email");
		String oldpass = (String) params.get("oldpass");
		String newpass = (String) params.get("newpass");
		
		MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds035014.mongolab.com:35014/sampledb");
		MongoClient client = new MongoClient(uri);
		
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("sample");
		
		BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("email", email);
	    whereQuery.put("password", oldpass);
	    DBCursor cursor = users.find(whereQuery);	    
	    cursor = users.find(whereQuery);
        if (cursor.hasNext()){
        	
        	BasicDBObject newDocument = new BasicDBObject();  
    		newDocument.put("name", name);  
    		newDocument.put("email", email);  
    		newDocument.put("password", newpass); 
    		users.update(new BasicDBObject().append("email", email), newDocument);
        }
        else {
        	System.out.println("Wrong");
        }	    
	}
}
