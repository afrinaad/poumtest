//Afrina Adlyna - Universiti Malaya 2017
//--------------------------------------


//This servlet file is to read data for Scientific Name chosen in viewData.jsp
//Workflow is as shown below : 
//1. Read owl file into a graph model
//2. Get d.scname from viewData.jsp
//3. Run SPARQL query
//4. Get result into HashMap array
//7. Pass servlet to detailData.jsp


//For any enquiries, please send and email to Afrina Adlyna
//afrina@um.edu.my

//------------------------------------------------------------------------------

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import poum.getJSON;

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
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class detailQ
 */
@WebServlet("/detailQ")
public class detailQ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String scname, sampleid, coverage, gps, pltype, pllength, plwidth, 
					plarr, plvein, plmar, pltip, plbase, plshape, plsurf, treeheight,
					fruitcolor, barkcolor, barksurf, petalnum, flowercolor, intype = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public detailQ() {
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
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		//System.out.println("podb : " + podb);
		
		//get queryscname from d.scname in viewData.jsp
		String queryscname = "";
		queryscname = request.getParameter("scname");
		
		//System.out.println("queryscname - " + queryscname);
		
		//start SPARQL query
		queryStr.append("PREFIX podb" + ": <" + "http://103.18.1.10:8080/plantdb/ontology/plantont#" + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + ">");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + ">");
		
		String queryRequest = "";

		queryRequest = 
				" SELECT * WHERE {" +
					  "?sub podb:hasSample ?psample. 			" +
					  "?psample podb:sampleId ?sampleid. 			" +
					  "?psample podb:isSpecies ?species. 			" +
					  "?species podb:scientificName ?query. 			" +
					  "?psample podb:livesIn ?dist. 			" +
					  "?dist podb:geoSpatialCoordinates ?gps. 			" +
					  "?dist podb:geoSpatialCoverage ?coverage. 			" +
					  
					  "optional {      			" +
					  "?psample podb:consistOf ?parts. 			" +
					  "?parts podb:isA ?pl.  			" +
					  "?pl podb:leafType ?pltype.  			" +
					  "?pl podb:leafLength ?pllength.			 			" +	
					  "?pl podb:leafWidth ?plwidth.				 			" +
					  "?pl podb:leafArrangement ?plarr.		 			" +	
					  "?pl podb:leafVenation ?plvein.		 			" +		
					  "?pl podb:leafMargin ?plmar.			 			" +		
					  "?pl podb:leafTip ?pltip.				 			" +	
					  "?pl podb:leafBase ?plbase.			 			" +		
					  "?pl podb:leafShape ?plshape.			 			" +	
					  "?pl podb:leafSurface ?plsurf.  			" +
					  

					  "optional {      			" +
					  "?parts podb:isA ?whole. 			" +
					  "?whole podb:treeHeight ?treeheight. 			}" +
					  

					  "optional {      			" +
					  "?parts podb:isA ?fruit. 			" +
					  "?fruit podb:fruitColor ?fruitcolor.  			}" +
					  

					  "optional {      			" +
					  "?parts podb:isA ?bark. 			" +
					  "?bark podb:barkColor ?barkcolor. 			" +
					  "?bark podb:barkSurface ?barksurf. 			}" +
					  

					  "optional {      			" +
					  "?parts podb:isA ?flower. 			" +
					  "?flower podb:petalNumber ?petalnum. 			" +
					  "?flower podb:flowerColor ?flowercolor. 			" +
					  "?flower podb:inflorescenceType ?intype. 			}" +
					  "} 			" +
					  
					  "FILTER (?query = \"" + queryscname +"\"). 			" +
					  "} ORDER by ?sampleid			";
			
		queryStr.append(queryRequest);		

		int i = 0;
		
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, podb);
		
		//create HashMap<Integer, HashMap<String,String>>
		HashMap<Integer, HashMap<String,String>> array = new HashMap<>();
		//create HashMap<String,String> resultmap
		HashMap<String,String> resultmap = new HashMap<>();
		
		try {
		
			ResultSet rq = qexec.execSelect();
			
			//print out result in console
			ResultSetFormatter.out(System.out, rq, query);
			
			//iterate result from SPARQL query
			while (rq.hasNext()) {
				QuerySolution soln = rq.nextSolution();
				resultmap = new HashMap<>();
		
				sampleid = soln.getLiteral("?sampleid").getLexicalForm();
				coverage = soln.getLiteral("?coverage").getLexicalForm();
				gps = soln.getLiteral("?gps").getLexicalForm();
				
				//put result into HashMap resultmap
				resultmap.put("sampleid", sampleid);
				resultmap.put("coverage",coverage);
				resultmap.put("gps",gps);
			
				//if result has leaf characteristics, add into HashMap resultmap
				if (soln.getResource("?pl") != null) {
					System.out.println("LEAF");
					pltype = soln.getLiteral("?pltype").getLexicalForm();
					pllength = soln.getLiteral("?pllength").getLexicalForm();
					plwidth = soln.getLiteral("?plwidth").getLexicalForm();
					plarr = soln.getLiteral("?plarr").getLexicalForm();
					plvein = soln.getLiteral("?plvein").getLexicalForm();
					plmar = soln.getLiteral("?plmar").getLexicalForm();
					pltip = soln.getLiteral("?pltip").getLexicalForm();
					plbase = soln.getLiteral("?plbase").getLexicalForm();
					plshape = soln.getLiteral("?plshape").getLexicalForm();
					plsurf = soln.getLiteral("?plsurf").getLexicalForm();
					
					resultmap.put("pleaf", "pleaf");
					resultmap.put("pltype",pltype);
					resultmap.put("pllength",pllength);
					resultmap.put("plwidth",plwidth);
					resultmap.put("plarr",plarr);
					resultmap.put("plvein",plvein);
					resultmap.put("plmar",plmar);
					resultmap.put("pltip",pltip);
					resultmap.put("plbase",plbase);
					resultmap.put("plshape",plshape);
					resultmap.put("plsurf",plsurf);
				
				}
		
				//if result has whole tree characteristics, add into HashMap resultmap
				if (soln.getResource("?whole") != null) {
					System.out.println("WHOLE");
					treeheight = soln.getLiteral("?treeheight").getLexicalForm();
					
					resultmap.put("ptree", "ptree");
					resultmap.put("treeheight",treeheight);
				
				}
				
				//if result has fruit characteristics, add into HashMap resultmap
				if (soln.getResource("?fruit") != null) {
					System.out.println("FRUIT");
					fruitcolor = soln.getLiteral("?fruitcolor").getLexicalForm();

					resultmap.put("pfruit", "pfruit");
					resultmap.put("fruitcolor",fruitcolor);
				
				}
				
				//if result has bark characteristics, add into HashMap resultmap
				if (soln.getResource("?bark") != null) {
					System.out.println("BARK");
					barkcolor = soln.getLiteral("?barkcolor").getLexicalForm();
					barksurf = soln.getLiteral("?barksurf").getLexicalForm();

					resultmap.put("pbark", "pbark");
					resultmap.put("barkcolor",barkcolor);
					resultmap.put("barksurf",barksurf);
			
				}
				
				//if result has flower characteristics, add into HashMap resultmap
				if (soln.getResource("?flower") != null) {
					System.out.println("FLOWER");
					petalnum = soln.getLiteral("?petalnum").getLexicalForm();
					flowercolor = soln.getLiteral("?flowercolor").getLexicalForm();
					intype = soln.getLiteral("?intype").getLexicalForm();

					resultmap.put("pflower", "pflower");
					resultmap.put("petalnum",petalnum);
					resultmap.put("flowercolor",flowercolor);
					resultmap.put("intype",intype);

				}
				
				//add HashMap resultmap into HashMap array
				array.put(i,resultmap);
				i++;
			
			}
		}
			
		finally {

				qexec.close();
		}
		
		//change string queryscname into string imgfile
		String imgfile = getFileName(queryscname);
		
		//pass string imgfile, queryscname and HashMap array to detailData.jsp
		request.setAttribute("imgfile", imgfile);
		request.setAttribute("array", array);
		request.setAttribute("queryscname", queryscname);
		
		String nextJSP = "/detailData.jsp";
		//ServletContext context = getServletContext();
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response); 
	
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
