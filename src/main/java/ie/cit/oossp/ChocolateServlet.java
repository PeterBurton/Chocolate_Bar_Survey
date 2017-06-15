package ie.cit.oossp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class AnimalsServlet
 */
@WebServlet("/ChocolateServlet")
public class ChocolateServlet extends HttpServlet {

	private static final long serialVersionUID = -4628573099634298659L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output;
		//get choice parameters into Strings
		String choice1 = request.getParameter("choice1");
		String choice2 = request.getParameter("choice2");
		//make strings utf-8 compliant
		String encodedChoice1 = URLEncoder.encode(choice1, "UTF-8");
		String encodedChoice2 = URLEncoder.encode(choice2, "UTF-8");
		//see if the cookies already exist
		Cookie c1= getCookie(request, encodedChoice1);
		Cookie c2= getCookie(request, encodedChoice2);
		if (c1 != null){//if cookie exists
			//get score from cookie, change to integer, and add weighted score
			//weighted score is hardcoded as 2 for 1st choice and 1 for second choice
			Integer i = Integer.parseInt(c1.getValue()) + 2;
			//set value back to string, and set in cookie
			c1.setValue(i.toString());
			c1.setMaxAge(60*60*24);//set max age to 24 hours
			response.addCookie(c1);
		}
		else{//if cookie doesn't exist create new cookie
			c1 = new Cookie(encodedChoice1, "2");
			c1.setMaxAge(60*60*24); 
			response.addCookie(c1); 
		}

		if (c2 != null){
			Integer i = Integer.parseInt(c1.getValue()) + 1;
			c2.setValue(i.toString());
			c2.setMaxAge(60*60*24);
			response.addCookie(c2);
		}
		else{
			c2 = new Cookie(encodedChoice2, "1");
			c2.setMaxAge(60*60*24);
			response.addCookie(c2);
		}

		output = response.getWriter();

		// send HTML page to client informing them of their choices
		output.println("<HTML><HEAD><TITLE>");
		output.println("Thank You");
		output.println("</TITLE><LINK REL='stylesheet' HREF='/chocoStyle.css' TYPE='text/css'>"
				+ "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></HEAD><BODY>");
		output.println("<H1>Thank you for participating</H1>");
		output.println("<P>You Selected: ");
		output.println(choice1);
		output.println(" as your first choice.</P>");
		output.println("<P>You Selected: ");
		output.println(choice2);
		output.println(" as your second choice.</P>");
		output.println("</BODY></HTML>");
		output.close(); // close stream

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output;
		Cookie cookies[];
		Integer barTotal = 0;
		Integer ingTotal = 0;
		//2 hashmaps, 1 for storing the results of most popular bar, 1 for storing popularity of ingredients
		HashMap<String, Integer> barResults = new HashMap<String, Integer>();
		HashMap<String, Integer> ingredientResults = new HashMap<String, Integer>();
		cookies = request.getCookies();

		response.setContentType("text/html");
		output = response.getWriter();


		//Check if there's any cookies
		if (cookies != null && !cookies[0].getName().equals("JSESSIONID")){
			for(int i = 0 ; i < cookies.length ; i++){
				if (!cookies[i].getName().equals("JSESSIONID")){
					//use URLDecoder to remove %20 from the string, and put back spaces
					String barName = URLDecoder.decode(cookies[i].getName(), "UTF-8");
					//split string into an array of its component strings with white space as the delimiter
					String[] arr = barName.split(" ");
					//If the hashmap already contains a key with this name
					if (barResults.containsKey(barName)){
						try{
						Integer value = Integer.parseInt(cookies[i].getValue());//parse the integer value from cookie string
						//put new value into hashmap at this key, and add value from the cookie to what's already there
						barResults.put(barName, barResults.get(barName) + value);
						//ingredientResults always uses the values at arr[3] and arr[5], as these are the ingredients in the sentence
						ingredientResults.put(arr[3], ingredientResults.get(arr[3]) + value);
						ingredientResults.put(arr[5], ingredientResults.get(arr[5]) + value);
						}catch(NumberFormatException e ){
							//This is here to skip over number format exception that was happening in Bluemix
						}
					}
					else{//add new entry to the hashmap,as above but simpler, as you don't have to retrieve the key and value
						try{
						Integer value = Integer.parseInt(cookies[i].getValue());
						barResults.put(barName, value);
						ingredientResults.put(arr[3], value);
						ingredientResults.put(arr[5], value);
						}catch(NumberFormatException e ){
							//This is here to skip over number format exception that was happening in Bluemix
						}

					}
				}
			}
			//send both hashmaps to sortByValue method
			HashMap <String,Integer>barSorted = (HashMap<String, Integer>) sortByValue(barResults);
			HashMap <String,Integer>ingredientSorted = (HashMap<String, Integer>) sortByValue(ingredientResults);
			
			//Work out the total of all scores in the 2 hashmaps, this will be used to calculate percentages
			for (Entry<String, Integer> entry : barSorted.entrySet()){
				barTotal += entry.getValue();
			}
			for (Entry<String, Integer> entry : ingredientSorted.entrySet()){
				ingTotal += entry.getValue();
			}
			//specify the formatting for the output
			DecimalFormat twoDigits = new DecimalFormat("#0.00");
			output.println("<HTML><HEAD><TITLE>");
			output.println("Survey Results");
			output.println("</TITLE><LINK REL='stylesheet' HREF='/chocoStyle.css' TYPE='text/css'>"
					+ "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></HEAD><BODY>");
			output.println("<H1>Survey Results</H1>");
			output.println("<H1>The most popular bars in order</H1>");
			for (Entry<String, Integer> entry : barSorted.entrySet()) {
				//Cast Integer to float and work out percentage to 2 decimal places
				output.println("<P>" + entry.getKey() + " : " + twoDigits.format((float)entry.getValue()/barTotal*100) +"%</P>");
			}
			
			output.println("<H1>The most popular ingredients in order</H1>");
			for (Entry<String, Integer> entry : ingredientSorted.entrySet()) {
				//Cast Integer to float and work out percentage to 2 decimal places
				output.println("<P>" + entry.getKey() + " : " + twoDigits.format((float)entry.getValue()/ingTotal*100) +"%</P>");
			}
			output.println("<BR></BODY></HTML>");
			output.close(); // close stream
		}
		else {
			//print message that there's no cookies yet
			output.println("<HTML><HEAD><TITLE>");
			output.println("Survey Results");
			output.println("</TITLE><LINK REL='stylesheet' HREF='/chocoStyle.css' TYPE='text/css'>"
					+ "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></HEAD><BODY>");
			output.println("<H1>There are no results yet</H1>");
			output.println("<P>You have not selected any chocolate bars or the cookies have expired.</P>");
			output.println("<BR></BODY></HTML>");
			output.close(); // close stream
		}



	}
	/*This method is used to check if a cookie with the given name already exists.
	 *If it exists it returns the cookie, if not it returns null. */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}

		return null;
	}
	/*This method is used to sort the given HashMap by its value, not key,
	 * In our case it's sorting the score*/
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}


}




