package TestsForTrello;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.util.List;

public class FirstRun {

    String trelloKey = "31d08ad7b936719994c3ed9dfb42d0c7"; // Key & Token for getting access to the trello
    String trelloToken = "7e62e8474906d93eac62e3fc4136e0392c4003c216c148cc16aa85ee7c679163";
    String trelloURI = "https://api.trello.com";
    
    String trelloIdList = "60c1bf199a1aee89c496978d";

    String newCardID; // The ID of a new created card
    String cardName = "Cat and Dog 333333333333333333333333"; // The name of a new created card
    
    String cardComment = "The comments are added by ReastAssured"; // Comments, that added to the card

    
    public static Response doGetRequest(String endpoint) { // extracting(parsing) the JASON response 
        RestAssured.defaultParser = Parser.JSON;

        return
            given().
                  headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
            when().
                  get(endpoint).
            then().
                  contentType(ContentType.JSON).extract().response();
    }
    
    
	@Test // create a new  card at the existing dashBoard (trelloIdList)
	public void testCreatNewCard() { 

			
		given().
		      body("").
		when().
		      post(trelloURI +"/1/cards?key="+ trelloKey +"&token="+ trelloToken +"&idList="+ trelloIdList +"&name="+ cardName).
		then().
		      statusCode(200);
		
	} 

	   @Test // get a list of the cards at at the existing dashBoard (trelloIdList)
		public void getCardsList( ) { 
			
   	        given().
			       get(trelloURI+"/1/lists/"+trelloIdList+"/cards?key="+trelloKey+"&token="+trelloToken).		
			then().
			      statusCode(200);
//			      log().all();
		   
	    	Response response = doGetRequest(trelloURI+"/1/lists/"+trelloIdList+"/cards?key="+trelloKey+"&token="+trelloToken);
	    	
	    	String listsOfCards = response.jsonPath().getString("id");
	    	System.out.println(listsOfCards);
	    	
	    	List<String> jsonResponse = response.jsonPath().getList("$");
	    	int qnCards = jsonResponse.size();// quantity of the Cards at the dashBoard  
	    	System.out.println(qnCards);
	    	qnCards--;
	    	
	    	newCardID = response.jsonPath().getString("id["+qnCards+"]");
	    	System.out.println(newCardID);   	
		}

		@Test // add comments to the existing Card 
		public void addComentsToTheCard() { 
			given().
			       body("").
			 when().
			       post(trelloURI +"/1/cards/60c216fcf3427f75600de16d/actions/comments?key="+ trelloKey +"&token="+ trelloToken +"&text="+ cardComment).
			 then().
			       statusCode(200);  
		}		
}
	
	

