//Afrina Adlyna - Universiti Malaya 2017
//--------------------------------------

//This java file is to create string with JSON format
//Different optdata have different style of JSON format

//For any enquiries, please send and email to Afrina Adlyna
//afrina@um.edu.my

//------------------------------------------------------------------------------

package poum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class getJSON {
	
	public String writeJSON(String optdata, HashMap<Integer, HashMap<String,String>> fr) {
		
		//create array arrgenus
		List<String> arrgenus = new ArrayList<String>();
		
		//add genus name from HashMap fr to array arrgenus
		for (int k = 0; k < fr.size(); k++) {
			HashMap<String, String> map = fr.get(k);
			arrgenus.add(map.get("genus"));
		}
		
		List<String> arrgenus2 = arrgenus.stream().distinct().collect(Collectors.toList());
		
		int index = 1;					//used for creating id element in JSON
		String finalJson = "" ;			//used for final JSON string
		String tempJson = "";			//used as temporary JSON string
		
		//if optdata is Scientific Name, write JSON-format string for Scientific Name
		if(optdata.equals("Scientific Name")) {
			for (int loop = 0; loop < fr.size(); loop++) {
				
				//get inner map of HashMap fr into HashMap innermap
				HashMap<String,String> innermap = fr.get(loop);
					
				//start writing JSON-format string tempJson
					tempJson = 
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("scname") + "\", \"con\": \"Scientific Name\", \"size:\": 2000, \"children\": [" +
										
								"{ \"id\":\"" + index++ + "\", \"name\": \"General Information\",\"size\": 2000, \"children\":[" +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plantname") + "\",\"con\": \"Common Name\", \"size\": 2000 }," +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("soil") + "\",\"con\": \"Soil\", \"size\": 2000 }," +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("wateruse") + "\",\"con\": \"Water Usage\", \"size\": 2000 }]}," +
								
								"{ \"id\":\"" + index++ + "\", \"name\": \"Taxon Rank\",\"size\": 2000, \"children\":[" +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("family") + "\",\"con\": \"Family\", \"size\": 2000 }," +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("genus") + "\",\"con\": \"Genus\", \"size\": 2000 }," +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("order") + "\",\"con\": \"Order\", \"size\": 2000 }]}," +
									
								"{ \"id\":\"" + index++ + "\", \"name\": \"Sample\", \"scname\":\"" + innermap.get("scname") + "\", \"size\": 2000, \"children\":[" +
								"{ \"id\":\"" + index++ + "\", \"name\":\"Sample details\",\"con\": \"Detail\", \"url\":\"detailQ?scname=" + innermap.get("scname") + "\", \"size\": 2000 }," +
								
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"imgfile\":\"" + innermap.get("imgfile") + "\", \"size\": 2000, \"children\":[" +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("coverage") + "\",\"con\": \"Location\", \"size\": 2000 }," +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("gps") + "\",\"con\": \"GPS\", \"size\": 2000 }," +
									
									"{ \"id\":\"" + index++ + "\", \"name\": \"Leaf\", \"size\": 2000, \"children\":[" +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plarr") + "\",\"con\": \"Arrangement\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plbase") + "\",\"con\": \"Base\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pllength") + "\",\"con\": \"Length\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plmar") + "\",\"con\": \"Margin\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plshape") + "\",\"con\": \"Shape\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plsurf") + "\",\"con\": \"Surface\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pltype") + "\",\"con\": \"Type\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pltip") + "\",\"con\": \"Tip\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plvein") + "\",\"con\": \"Vein\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plwidth") + "\",\"con\": \"Width\", \"size\": 2000 }" +
										"]	}	]	},";
								
								loop++;
								
								innermap = fr.get(loop);
								
								tempJson = tempJson + 
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"size\": 2000, \"children\":[" +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("coverage") + "\", \"con\": \"Location\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("gps") + "\",\"con\": \"GPS\", \"size\": 2000 }]},";
								
								loop++;
								
								innermap = fr.get(loop);
								
								tempJson = tempJson + 
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"size\": 2000, \"children\":[" +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("coverage") + "\",\"con\": \"Location\", \"size\": 2000 }," +
										"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("gps") + "\",\"con\": \"GPS\", \"size\": 2000 }]}]}]}";

				}
				//pass string tempJson to string finalJson
				finalJson = finalJson + tempJson;
			}

		//if optdata is Family Name, write JSON-format string for Family Name
		else if (optdata.equals("Family Name")) {

			//create HashMap<String, List<Integer>> genlist
			HashMap<String, List<Integer>> genlist = new HashMap<>();
			
			//get nth element of genus name from HashMap fr to HashMap genlist 
			for (int w = 0; w < arrgenus2.size(); w++) {
				List<Integer> num = new ArrayList<Integer>();
				
				String tempg = arrgenus2.get(w);
				
				for (int z = 0; z < fr.size(); z = z + 3) {
					
					HashMap<String,String> genmap = fr.get(z);
					
					if (tempg.equals(genmap.get("genus"))) {
						num.add(z);
					}
				}
				genlist.put(tempg, num);
			}
			
			//write the main parent node
			HashMap<String,String> mainmap = fr.get(0);
			String famjson = "{ \"id\":\"" + index++ + "\", \"name\":\"" + mainmap.get("family") + "\", \"con\": \"Family\", \"size:\": 2000, \"children\": [";
			finalJson = finalJson + famjson;
			
			String genjson = "";
			
			//start writing JSON string based on genus name
			for (String genkey : genlist.keySet()) {
				genjson = genjson + "{ \"id\":\"" + index++ + "\", \"name\":\"" + genkey + "\",\"con\": \"Genus\", \"size\": 2000, \"children\": [";
				
				List<Integer> numvalue = genlist.get(genkey);
				
				//write JSON for genus children node 
				for (int loop=0; loop < numvalue.size(); loop++) {
					
					int num = numvalue.get(loop);
					
					HashMap<String,String> innermap = fr.get(num);
					
					tempJson = 
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("scname") + "\", \"con\": \"Scientific Name\",\"size:\": 2000, \"children\": [" +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plantname") + "\",\"con\": \"Common Name\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("wateruse") + "\",\"con\": \"Water Usage\", \"size\": 2000 }," +
							
							"{ \"id\":\"" + index++ + "\", \"name\": \"Sample\", \"scname\":\"" + innermap.get("scname") + "\", \"size\": 2000, \"children\":[" +
							"{ \"id\":\"" + index++ + "\", \"name\":\"Sample details\",\"con\": \"Detail\", \"url\":\"detailQ?scname=" + innermap.get("scname") + "\", \"size\": 2000 }," +
							
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"imgfile\":\"" + innermap.get("imgfile") + "\", \"size\": 2000, \"children\":[" +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("coverage") + "\",\"con\": \"Location\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("gps") + "\",\"con\": \"GPS\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\": \"Leaf\",\"size\": 2000, \"children\":[" +
							
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plarr") + "\",\"con\": \"Arrangement\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plbase") + "\",\"con\": \"Base\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pllength") + "\",\"con\": \"Length\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plmar") + "\",\"con\": \"Margin\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plshape") + "\",\"con\": \"Shape\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plsurf") + "\",\"con\": \"Surface\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pltype") + "\",\"con\": \"Type\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pltip") + "\",\"con\": \"Tip\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plvein") + "\",\"con\": \"Vein\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plwidth") + "\",\"con\": \"Width\", \"size\": 2000 }" +
							"]	}	]	},";
			
							innermap = fr.get(num+1);
							
							tempJson = tempJson + "{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"size\": 2000, \"children\":[" +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("coverage") + "\",\"con\": \"Location\", \"size\": 2000 }," +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("gps") + "\",\"con\": \"GPS\", \"size\": 2000 }]},";
						
							innermap = fr.get(num+2);
							
							tempJson = tempJson + "{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"size\": 2000, \"children\":[" +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("coverage") + "\",\"con\": \"Location\", \"size\": 2000 }," +
									"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("gps") + "\",\"con\": \"GPS\", \"size\": 2000 }]}]}]}";
					
							if(loop+1 < numvalue.size()) { 
								genjson = genjson + tempJson + ",";
								System.out.println("IN");
							}
							
							else {
								genjson = genjson + tempJson + "]},";
								System.out.println("OUT");
							}
				}
		
			}
			
			//remove excess }] from temporary JSON string genjson
			finalJson = famjson + genjson.substring(0, genjson.length()-1) + "]}";
			
		}

		//if optdata is Location, write JSON-format string for Location
		else if (optdata.equals("Location")) {
			
			//write the main parent node
			HashMap<String,String> mainmap = fr.get(0);
			String locjson = "{ \"id\":\"" + index++ + "\", \"name\":\"" + mainmap.get("coverage") + "\", \"con\": \"Coverage\", \"size:\": 2000, \"children\": [";
			finalJson = finalJson + locjson;

			//start writing children node
			for (int loop = 0; loop < fr.size(); loop++) {
				
				HashMap<String,String> innermap = fr.get(loop);
				
				tempJson =
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("scname") + "\", \"con\": \"Scientific Name\", \"size:\": 2000, \"children\": [" +
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plantname") + "\",\"con\": \"Common Name\", \"size\": 2000 }," +
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("soil") + "\",\"con\": \"Soil\", \"size\": 2000 }," +
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("wateruse") + "\",\"con\": \"Water Usage\", \"size\": 2000 }," +
						
						"{ \"id\":\"" + index++ + "\", \"name\": \"Taxon Rank\",\"size\": 2000, \"children\":[" +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("family") + "\",\"con\": \"Family\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("genus") + "\",\"con\": \"Genus\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("order") + "\",\"con\": \"Order\", \"size\": 2000 }]}," +
						
						"{ \"id\":\"" + index++ + "\", \"name\": \"Sample\", \"scname\":\"" + innermap.get("scname") + "\",\"size\": 2000, \"children\":[" +
						"{ \"id\":\"" + index++ + "\", \"name\":\"Sample details\",\"con\": \"Detail\", \"url\":\"detailQ?scname=" + innermap.get("scname") + "\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"imgfile\":\"" + innermap.get("imgfile") + "\", \"size\": 2000, \"children\":[" +
							"{ \"id\":\"" + index++ + "\", \"name\": \"Leaf\",\"size\": 2000, \"children\":[" +
							
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plarr") + "\",\"con\": \"Arrangement\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plbase") + "\",\"con\": \"Base\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pllength") + "\",\"con\": \"Length\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plmar") + "\",\"con\": \"Margin\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plshape") + "\",\"con\": \"Shape\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plsurf") + "\",\"con\": \"Surface\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pltype") + "\",\"con\": \"Type\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pltip") + "\",\"con\": \"Tip\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plvein") + "\",\"con\": \"Vein\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plwidth") + "\",\"con\": \"Width\", \"size\": 2000 }" +
								"]	}	]	},";
				
				loop++;
				
				innermap = fr.get(loop);
				
				tempJson = tempJson + "{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"size\": 2000 },";
			
				loop++;
				
				innermap = fr.get(loop);
				
				tempJson = tempJson + "{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"size\": 2000 }]}]}";
				
				//if loop is not the last nth element, add comma to string finalJson
				if (loop != (fr.size()-1)) {
					finalJson = finalJson + tempJson + ",";
				}
				
				//if loop is the last nth element, ends string finalJson with }]
				else {
					finalJson = finalJson + tempJson + "] }" ;
				}
			}
			
		}

		//if optdata is Water Usage, write JSON-format string for Water Usage
		else if (optdata.equals("Water Usage")) {
			
			//write the main parent node
			HashMap<String,String> mainmap = fr.get(0);
			String locjson = "{ \"id\":\"" + index++ + "\", \"name\":\"" + mainmap.get("wateruse") + "\", \"con\": \"Water Usage\", \"size:\": 2000, \"children\": [";
			finalJson = finalJson + locjson;

			//start writing children node
			for (int loop = 0; loop < fr.size(); loop++) {
				
				HashMap<String,String> innermap = fr.get(loop);
				
				tempJson =
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("scname") + "\", \"con\": \"Scientific Name\", \"size:\": 2000, \"children\": [" +
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plantname") + "\",\"con\": \"Common Name\", \"size\": 2000 }," +
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("soil") + "\",\"con\": \"Soil\", \"size\": 2000 }," +					
						
						"{ \"id\":\"" + index++ + "\", \"name\": \"Taxon Rank\",\"size\": 2000, \"children\":[" +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("family") + "\",\"con\": \"Family\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("genus") + "\",\"con\": \"Genus\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("order") + "\",\"con\": \"Order\", \"size\": 2000 }]}," +
						
						"{ \"id\":\"" + index++ + "\", \"name\": \"Sample\", \"scname\":\"" + innermap.get("scname") + "\", \"size\": 2000, \"children\":[" +
						"{ \"id\":\"" + index++ + "\", \"name\":\"Sample details\",\"con\": \"Detail\", \"url\":\"detailQ?scname=" + innermap.get("scname") + "\", \"size\": 2000 }," +
						
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"imgfile\":\"" + innermap.get("imgfile") + "\", \"size\": 2000, \"children\":[" +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("coverage") + "\",\"con\": \"Location\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("gps") + "\",\"con\": \"GPS\", \"size\": 2000 }," +
							"{ \"id\":\"" + index++ + "\", \"name\": \"Leaf\",\"size\": 2000, \"children\":[" +
							
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plarr") + "\",\"con\": \"Arrangement\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plbase") + "\",\"con\": \"Base\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pllength") + "\",\"con\": \"Length\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plmar") + "\",\"con\": \"Margin\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plshape") + "\",\"con\": \"Shape\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plsurf") + "\",\"con\": \"Surface\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pltype") + "\",\"con\": \"Type\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("pltip") + "\",\"con\": \"Tip\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plvein") + "\",\"con\": \"Vein\", \"size\": 2000 }," +
								"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("plwidth") + "\",\"con\": \"Width\", \"size\": 2000 }" +
								"]	}	]	},";
				
				loop++;
				
				innermap = fr.get(loop);
				
				tempJson = tempJson + 
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"size\": 2000, \"children\":[" +
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("coverage") + "\", \"con\": \"Location\", \"size\": 2000 }," +
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("gps") + "\",\"con\": \"GPS\", \"size\": 2000 }]},";
						
				loop++;
				
				innermap = fr.get(loop);
				
				tempJson = tempJson + "{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("id") + "\", \"con\": \"Sample ID\", \"size\": 2000, \"children\":[" +
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("coverage") + "\",\"con\": \"Location\", \"size\": 2000 }," +
						"{ \"id\":\"" + index++ + "\", \"name\":\"" + innermap.get("gps") + "\",\"con\": \"GPS\", \"size\": 2000 }]}]}]}";

				//if loop is not the last nth element, add comma to string finalJson
				if (loop != (fr.size()-1)) {
					finalJson = finalJson + tempJson + ",";
				}

				//if loop is the last nth element, ends string finalJson with }]
				else {
					finalJson = finalJson + tempJson + "] }" ;
				}
			}
			
		}
		
		//return string finalJson 
		return finalJson;
	}
	
}
