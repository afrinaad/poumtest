//Afrina Adlyna - Universiti Malaya 2017
//--------------------------------------


//This servlet file is to read query from user and transform the result
//into JSON file
//Workflow is as shown below : 
//1. Read owl file into a graph model
//2. Get user query parameters optdata and dataquery1/2/3
//3. If optdata is Scientific Name or Family Name, preprocess dataquery1
//3. Run SPARQL query
//4. Iterate result into a HashMap fr
//5. If HashMap fr is 0 (no result), pass parameters optdata and dataquery1/2/3 to noResult.jsp
//6. If HashMap fr is not 0 (have result), serialized HashMap fr into JSON-format string answerJSON.
//   Pass string answerJSON and HashMap fr to viewData.jsp


//------------------------------------------------------------------------------

import poum.getJSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

//servlet
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

//Jena
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;

import com.hp.hpl.jena.query.ResultSetFormatter;
//Jackson
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet("/queryD")
public class queryD extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String imgfile, id, plantname, scname, family, order, gps, coverage, wateruse,
							genus, soil, pltype,plarr,plbase,pllength,plmar,plshape,plsurf,pltip,plvein,plwidth = null;
       

    public queryD() {
        super();
        // TODO Auto-generated constructor stub
    }

    //FUNCTION - change scientific name into image filename
	public static String getFileName(String scName) {

		String imgFile = "";
		//split string scientific name by words
		String[] temp = scName.split("\\s");
		
		//substring the first 3 letters and change to uppercase
		for (int m = 0; m < temp.length; m++) {
			temp[m] = temp[m].substring(0, 3);
			temp[m] = temp[m].toUpperCase();
		}
		
		//combine strings and add letter O
		imgFile = temp[0] + temp[1] + "O";
			
		return imgFile;
		
	}
	
	//FUNCTION - preprocess string dataquery1
	public static String processQuery(String dataquery1) {
		
		//remove any whitespace
		String tempdata = dataquery1.replaceAll("( +)"," ").trim();

		//change first letter into uppercase
        char[] value = tempdata.toCharArray();
        value[0] = Character.toUpperCase(value[0]);
        
        return new String(value);
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		Model modeltmp = null;
		OntModel podb = null;
		
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
		
		podb = ModelFactory.createOntologyModel(spec,modeltmp);
		
		StringBuffer queryStr = new StringBuffer();
		
		//get poum.owl file from 10.3.204.173 server
		InputStream in = FileManager.get().open("http://103.18.1.10:8080/plantviz/ont/poum.owl");
		
		//get poum.owl file from localhost server
		//InputStream in = FileManager.get().open("http://localhost:8080/plantviz/ont/poum.owl");
		
		//get poum.owl from computer (if server is not available)
		//InputStream in = FileManager.get().open("C:/Users/User/workspace/poum/ontology/poum.owl");

		//read owl file
		podb.read(in, "http://103.18.1.10:8080/plantdb/ontology/plantont");
		
		System.out.println("podb : " + podb);
		
		//get query from query.jsp
		String dataquery1 = "";
		dataquery1 = request.getParameter("dataquery1");
		
		String dataquery2 = "";
		dataquery2 = request.getParameter("dataquery2");
		
		String dataquery3 = "";
		dataquery3 = request.getParameter("dataquery3");
		
		//get option from query.jsp
		String optdata = "";
		optdata = request.getParameter("optdata");
		
		//if optdata is Scientific Name or Family Name, preprocess string dataquery1
		if (optdata.equals("Scientific Name") || optdata.equals("Family Name") ) {
			dataquery1 = processQuery(dataquery1);
		}
		
		//start SPARQL query
		queryStr.append("PREFIX podb" + ": <" + "http://103.18.1.10:8080/plantdb/ontology/plantont#" + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + ">");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + ">");
		
		String queryRequest = "";

			queryRequest = 
				" SELECT ?id ?plantname ?scname ?gname ?fname ?oname ?gps ?coverage ?wateruse ?habtype ?soil ?pltype ?plarr ?plbase ?pllength ?plmar ?plshape ?plsurf ?pltip ?plvein ?plwidth WHERE { " +
					  "?subs podb:hasSample ?sub."		+
					  "?sub podb:sampleId ?id."		+ 		 			
					  "?sub podb:isSpecies ?species."		+  				
					  "?species podb:plantName ?plantname."		+ 		
					  "?species podb:scientificName ?scname."		+ 		
				      "?species podb:isBelongTo ?genus."		+ 	
					  "?genus podb:rankGenus ?gname."		+
				      "?genus podb:isBelongTo ?family."		+ 			
				      "?family podb:rankFamily ?fname."		+
				      "?family podb:isBelongTo ?order."		+
				      "?order podb:rankOrder ?oname."		+
				      "?sub podb:livesIn ?plantdist."		+ 					
				      "?plantdist podb:geoSpatialCoordinates ?gps."		+ 		
				      "?plantdist podb:geoSpatialCoverage ?coverage."		+

					    "?species podb:hasHabitat ?planthab."		+ 			
					    "?planthab podb:waterUsage ?wateruse."		+
					    
					 "optional {"		+
			
					    "?planthab podb:habitatType ?habtype."		+ 		
					    "?planthab podb:typeOfSoil ?soil."		+
					  
					    "?sub podb:consistOf ?parts."		+  
					    "?parts podb:isA ?pl."		+ 
					    "?pl podb:leafType ?pltype."		+ 
					    "?pl podb:leafLength ?pllength."		+				
					    "?pl podb:leafWidth ?plwidth."		+				
					    "?pl podb:leafArrangement ?plarr."		+			
					    "?pl podb:leafVenation ?plvein."		+				
					    "?pl podb:leafMargin ?plmar."		+					
					    "?pl podb:leafTip ?pltip."		+					
					    "?pl podb:leafBase ?plbase."		+					
					    "?pl podb:leafShape ?plshape."		+				
					    "?pl podb:leafSurface ?plsurf." +
					    "FILTER regex(str(?pl), \"Leaf\") }";

					    if (optdata.equals("Scientific Name")) {
							queryRequest = queryRequest + 
									"FILTER (?scname = \"" + dataquery1 + "\")";
						    }
					    
					    if (optdata.equals("Family Name")) {
							queryRequest = queryRequest + 
									"FILTER (?fname = \"" + dataquery1 + "\")";
						    }
					    
					    if (optdata.equals("Location")) {
							queryRequest = queryRequest + 
									"FILTER (?coverage = \"" + dataquery2 + "\")";
						    }
					    
					    if (optdata.equals("Water Usage")) {
							queryRequest = queryRequest + 
									"FILTER (?wateruse = \"" + dataquery3 + "\")";
						    }
					    
					    queryRequest = queryRequest + 
					    		"} ORDER BY ?id "; 

		
		queryStr.append(queryRequest);
		
		//System.out.println(queryRequest);
		
		Query query = QueryFactory.create(queryStr.toString());
		
		QueryExecution qexec = QueryExecutionFactory.create(query, podb);
		
		//create HashMap<String,String> result for result iteration
		HashMap<String,String> result = new HashMap<>();
		//create HashMap<Integer, HashMap<String,String>> fr for all SPARQL result
		HashMap<Integer, HashMap<String,String>> fr = new HashMap<>();

		int i = 0;
		try {
			
			ResultSet rq = qexec.execSelect();
			
			//print out result XML in console
			//ResultSetFormatter.outputAsXML(System.out, rq);
			
			//iterate result from SPARQL query
			while (rq.hasNext()) {
				
				result = new HashMap<>();
				
				QuerySolution soln = rq.next();
					
				id = soln.getLiteral("?id").getLexicalForm();
				plantname = soln.getLiteral("?plantname").getLexicalForm();
				scname = soln.getLiteral("?scname").getLexicalForm();
				genus = soln.getLiteral("?gname").getLexicalForm();
				family = soln.getLiteral("?fname").getLexicalForm();
				order = soln.getLiteral("?oname").getLexicalForm();
				gps = soln.getLiteral("?gps").getLexicalForm();
				coverage = soln.getLiteral("?coverage").getLexicalForm();
			
				//change string scientific name into string imgfile
				imgfile = getFileName(scname);
				
				//put result into HashMap result
				result.put("id",id);
				result.put("plantname",plantname);
				result.put("scname",scname);
				result.put("imgfile", imgfile);
				result.put("genus",genus);
				result.put("family",family);
				result.put("order", order);
				result.put("gps",gps);
				result.put("coverage",coverage);
				
				//if result with leaf characteristics, put result into HashMap result
				if (soln.getLiteral("?pltype") != null) {
					
					wateruse = soln.getLiteral("?wateruse").getLexicalForm();
					soil = soln.getLiteral("?soil").getLexicalForm();
					pltype = soln.getLiteral("?pltype").getLexicalForm();
					plarr = soln.getLiteral("?plarr").getLexicalForm();
					plbase = soln.getLiteral("?plbase").getLexicalForm();
					pllength = soln.getLiteral("?pllength").getLexicalForm();
					plmar = soln.getLiteral("?plmar").getLexicalForm();
					plshape = soln.getLiteral("?plshape").getLexicalForm();
					plsurf = soln.getLiteral("?plsurf").getLexicalForm();
					pltip = soln.getLiteral("?pltip").getLexicalForm();
					plvein = soln.getLiteral("?plvein").getLexicalForm();
					plwidth = soln.getLiteral("?plwidth").getLexicalForm();
				
					result.put("wateruse",wateruse);
					result.put("soil",soil);
					result.put("pltype",pltype);
					result.put("plarr",plarr);
					result.put("plbase",plbase);
					result.put("pllength",pllength);
					result.put("plmar",plmar);
					result.put("plshape",plshape);
					result.put("plsurf",plsurf);
					result.put("pltip",pltip);
					result.put("plvein",plvein);
					result.put("plwidth",plwidth);
				}

				else {
					System.out.println("");
				}				
				
				//add HashMap result into HashMap fr
				fr.put(i,result);
				i++;
			}
			
		}
		
		finally {

			qexec.close();
		}
		
		String nextJSP = "";
		
		//if size HashMap fr is 0, pass optdata and dataquery1/2/3 to noResult.jsp
		if (fr.size() == 0) {

			request.setAttribute("optdata", optdata);
			
			if (optdata.equals("Scientific Name") || optdata.equals("Family Name")) {
				request.setAttribute("dataquery", dataquery1);
			}
			
			else if (optdata.equals("Location")){
				request.setAttribute("dataquery", dataquery2);
			}
			
			else {
				request.setAttribute("dataquery", dataquery3);
			}
			
			nextJSP = "/noResult.jsp";
		}
		
		//if size HashMap fr is not 0, serialize HashMap fr into JSON-format string answerJSON
		else {
					
			getJSON getjson = new getJSON();
			String answerJSON = getjson.writeJSON(optdata, fr);
			
			//print out string answerJSON in console
			//System.out.println("answerJSON - " + answerJSON);
			
			//pass query parameter and keyword
			request.setAttribute("optdata", optdata);
			if (optdata.equals("Scientific Name") || optdata.equals("Family Name")) {
				request.setAttribute("dataquery", dataquery1);
			}
			
			else if (optdata.equals("Location")){
				request.setAttribute("dataquery", dataquery2);
			}
			
			else {
				request.setAttribute("dataquery", dataquery3);
			}
			
			//pass HashMap fr and string answerJSON to viewData.jsp
			request.setAttribute("fr", fr);
			request.setAttribute("answerJSON", answerJSON);
			nextJSP = "/viewData.jsp";
		}
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response); 
		
	}

}
