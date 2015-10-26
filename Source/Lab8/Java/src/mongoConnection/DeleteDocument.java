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
 * Servlet implementation class DeleteDocument
 */
@WebServlet("/DeleteDocument")
public class DeleteDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDocument() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		System.out.println("Delete servlet called");
		
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		
		while((line = reader.readLine())!= null){
		 
			buffer.append(line);
		}
		String data = buffer.toString();
				
		JSONObject params = (JSONObject)JSON.parse(data);
		BasicDBObject user1 = new BasicDBObject(params);
		
		String name = (String) params.get("name");
		String email = (String) params.get("email");
		
		System.out.println("Username : " + name);
		System.out.println("Email : " + email);
		
		MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds035014.mongolab.com:35014/sampledb");
		MongoClient client = new MongoClient(uri);
		
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("sample");
		
		BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("email", email);
	    whereQuery.put("name", name);
	    DBCursor cursor = users.find(whereQuery);	    
	    //cursor = users.find(whereQuery);
        if (cursor.hasNext()){
        	
        	users.remove(whereQuery);
        }
        else {
        	System.out.println("Wrong");
        }	   
	}
}
