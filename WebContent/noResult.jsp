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
		<style>
		b {
			font-weight:bold;
		}
		</style>
	</head>
	<body>
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
					<div class="12u-first">
					<h2>NO RESULT</h2>
					<br/>
					<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
					<p>There is no record for <b> <%=request.getAttribute("optdata") %> : <%=request.getAttribute("dataquery") %> </b> in our database.<br/></p>
					<p>Click <a href="#" onclick="window.location='http://localhost:8080/poum/query.jsp'; return false;"><b>HERE</b> </a>to try again. DO NOT click button <b>Back</b> to start a new query.</p>
					
					</div>
					</div>
					</div>
		<div id="main">
			<div class="5grid">
				<div class="main-row">		
					<section class="left-content">
					</section>
					</div>
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