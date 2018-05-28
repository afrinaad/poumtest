<!-- Afrina Adlyna - Universiti Malaya 2017
//--------------------------------------//

This jsp file is the main page of PlantViz GUI to get input from user.
The workflow is as shown below: 
1. Users choose options from Scientific Name, Family Name, Location, and Water Usage.
2. If users choose option Scientific Name or Family Name, users key in keyword that will be assigned to string 'dataquery1'.
3. If users choose option Location, or Water Usage, users choose one keyword from options given and that keyword will be assigned to string 'dataquery2' or 'dataquery3' respectively.
4. Users click on button Submit to pass inputs to queryD servlet or Reset to reset query form. 

-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Plant Ontology | PlantViz</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<meta http-equiv="cache-control" content="no-cache"> 
		<meta http-equiv="expires" content="0"> 
		<meta http-equiv="pragma" content="no-cache">
		<!--5grid--><script src="css/5grid/viewport.js"></script><!--[if lt IE 9]><script src="css/5grid/ie.js"></script><![endif]--><link rel="stylesheet" href="css/5grid/core.css" />
		<link rel="stylesheet" href="css/style.css" />
		<!--[if IE 9]><link rel="stylesheet" href="css/style-ie9.css" /><![endif]-->
	</head>
	<body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script>
	
	function resetQuery() {
	    document.getElementById("formquery").reset();
	    $("#opt1").hide();
	    $("#opt2").hide();
	    $("#opt3").hide();
	}

	$(function () { 
	$('#option').change(function(){
		
		var val = $(this).val();
		console.log(val);
		
	    if( val === "Scientific Name") {
	    $("#opt1").show();
	    $("#opt2").hide();
	    $("#opt3").hide();
	    }
	    
	    else if (val === "Family Name") {
		    $("#opt1").show();
		    $("#opt2").hide();
		    $("#opt3").hide();
		    }
	    
	    else if (val === "Location") {
	    	$("#opt1").hide();
	    	$("#opt2").show();
	    	$("#opt3").hide();
	    }
	    
	    else if (val === "Water Usage") {
	    	$("#opt1").hide();
		    $("#opt2").hide();
		    $("#opt3").show();
	    }
	    
	    else{
	    	$("#opt1").hide();
		    $("#opt2").hide();
		    $("#opt3").hide();
	    }
	});
	});
	
	</script>
	<!-- ********************************************************* -->
		<div id="header-wrapper">
			<div class="5grid">
				<div class="12u-first">
					
					<header id="header">
						<h1><a href="#">UM Plant Knowledge</a></h1>
						<nav>
							<a href="home.jsp">Home</a>
							<a href="query.jsp" class="current-page-item">PlantViz</a>
							<a href="howto.html">How To</a>
							<a href="aboutus.html">About Us</a>
						</nav>
					</header>
				
				</div>
			</div>
		</div>
		<div id="main">
			<div class="5grid">
				<div class="main-row">		
					<section class="left-content">
						<h2>QUERY</h2>
						<p style="line-height: 1.3">
						In PlantViz's query tool, users are able to search on any of parameters available from the drop-down menu. <br/>
						Choose any parameters, key in search keyword or choose from options given, then click <b>Search</b> to perform the query.
						</p><br/>
						
							<form id="formquery" action="queryD" method="post">
							<div>Search for : &nbsp;&nbsp; 
								
				       			<select id="option" class="enjoy-css" style="width:170px" name="optdata">				        
								<option>Choose option</option>
								<option value="Scientific Name">Scientific Name</option>						
								<option value="Family Name">Family Name</option>
					  			<option value="Location">Location</option>
					  			<option value="Water Usage">Water Usage</option>
							 	</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 	<input class="enjoy-css" type="submit" name="Search" value="Search">
							 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 	<a style="text-decoration: underline" onclick="resetQuery()">Reset</a><br/>
							 	<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 	
							 	<div id="opt1" style="display:none;">
							 	Keyword : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 	<input class="enjoy-css" style="width:120px" placeholder="Enter keyword" type="text" name="dataquery1">
							 	<br><br>
							 	<i>TIPS : </i><br>
							 		<ul style="list-style-type:disc">
							 			<li>Only capitalize the first word. Eg: Magnolia figo</li>
							 			<li>Remember to have space between words. Eg: Hura crepitans NOT Huracrepitans</li>
							 			<li>Do not put extra spaces at the beginning or at the end of the query.</li>							 			
							 		</ul>
							 	</div>
							 	<br/>
							 	<div id="opt2" style="display:none;">
							 	<input type="radio" name="dataquery2" value="DTC UM"> DTC UM<br>
							 	<input type="radio" name="dataquery2" value="Fakulti Kejuruteraan UM"> Fakulti Kejuruteraan UM<br>
							 	<input type="radio" name="dataquery2" value="Fakulti Perniagaan dan Perakaunan UM"> Fakulti Perniagaan dan Perakaunan UM<br>
							 	<input type="radio" name="dataquery2" value="Fakulti Sains UM"> Fakulti Sains UM<br>
							 	<input type="radio" name="dataquery2" value="Tasik Varsiti UM"> Tasik Varsiti UM<br>
							 	</div>
							 	<br/>
							 	<div id="opt3" style="display:none;">
							 	<input type="radio" name="dataquery3" value="Low"> Low<br>
							 	<input type="radio" name="dataquery3" value="Low to moderate"> Low to moderate<br>
							 	<input type="radio" name="dataquery3" value="Moderate"> Moderate<br>
							 	<input type="radio" name="dataquery3" value="Moderate to high"> Moderate to high<br>
							 	<input type="radio" name="dataquery3" value="High"> High<br>
							 	</div>
							 
						 	</div>
						 	</form>			 					
		 			</section>
		 		</div>
		 		<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
			</div>
		</div>		
		<div id="footer-wrapper">
			<div class="5grid">

				<div class="12u-first">

					<div id="copyright">
						This project is under Grant Num : RP038B-15AET</div>

				</div>
			</div>
		</div>
	<!-- ********************************************************* -->
	</body>
</html>