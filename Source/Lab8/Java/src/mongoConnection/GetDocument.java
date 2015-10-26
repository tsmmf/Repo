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
import com.mongodb.WriteResult;

/**
 * Servlet implementation class GetDocument
 */
@WebServlet("/GetDocument")
public class GetDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDocument() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		response.setHeader("Access-Control-Allow-Origin", "*");	
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		
		String name = request.getParameter("name");
		String pass = request.getParameter("password");	
	
		JSONObject params = new JSONObject();
		params.put("name", name);
	    params.put("password", pass);
	    
		BasicDBObject user1 = new BasicDBObject(params);
		
		for (Object key : params.keySet().toArray()){
			user1.put(key.toString(), params.get(key));
		}
		
		System.out.println(user1);
		
		MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds035014.mongolab.com:35014/sampledb");
		MongoClient client = new MongoClient(uri);
		
		DB db = client.getDB(uri.getDatabase());		
		DBCollection collect = db.getCollection("sample");
		
		BasicDBObject query = new BasicDBObject();
		query.put("name", name);
		query.put("password", pass);
		
		DBCursor docs = collect.find(query);
		response.getWriter().write(docs.toArray().toString());
	}
}
