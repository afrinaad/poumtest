<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*" %> 
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
	font-weight: bold;
}

i {
font-style:italic;
}

table {
    border-collapse: collapse;
    margin-left: 60px;
}

table, td, th {
    border: 1px solid black;
}

th, td, tr {
	padding: 5px 0px 5px 5px;
}

img {
	width : 50%;
	height : 50%;
}

</style>
</head>
<body>
 <% HashMap<Integer, HashMap<String,String>> array = (HashMap<Integer, HashMap<String,String>>)request.getAttribute("array");
	
	String imgfile = (String)request.getAttribute("imgfile");
 %>
 
 <h2>Information for samples of plant <i> <%=request.getAttribute("queryscname")%> </i></h2>
 
 
<% 
	for (int k = 0;  k < array.size(); k++) {
	HashMap<String,String> inmap = array.get(k);
	
%>

<table width = "50%" border="1">
  <tr>
    <th width = "20%" align="left" bgcolor="#ede176"><b>Sample ID</b></th>
    <th align="left" bgcolor="#ede176"><b><%=inmap.get("sampleid")%></b></th>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#ede176"><b>Location in UM</b></td>
  </tr>
  <tr>
    <td>Location</td>
    <td><%=inmap.get("coverage")%></td>
  </tr>
  <tr>
    <td>GPS Coordinates</td>
    <td><%=inmap.get("gps")%>5</td>
  </tr>
  <% if(k == 0) { %>
  <tr>
    <td colspan="2" bgcolor="#ede176"><b>Plant Characteristics</b></td>
  </tr>
  <% } %>
  <tr>
  <% if (inmap.get("ptree") != null) { %>
    <td colspan="2" bgcolor="#ede176"><b>Whole Tree</b></td>
  </tr>
  <tr>
    <td>Tree height</td>
    <td><%=inmap.get("treeheight")%></td>
  </tr>
   <tr>
    <td style="line-height: 100%;">Sample image</td>
    <td><img src="images/psimg/<%=imgfile%>-W001.jpg"></td>
  </tr>
  <% } 
   if (inmap.get("pbark") != null) {
  %>
  <tr>
    <td colspan="2" bgcolor="#ede176"><b>Bark</b></td>
  </tr>
  <tr>
    <td>Color</td>
    <td><%=inmap.get("barkcolor")%></td>
  </tr>
  <tr>
    <td>Bark surface</td>
    <td><%=inmap.get("barksurf")%></td>
  </tr>
  <tr>
    <td style="line-height: 100%;">Sample image</td>
    <td><img src="images/psimg/<%=imgfile%>-B001.jpg"></td>
  </tr>
  <% }
   if (inmap.get("pleaf") != null) {
   %>
  <tr>
    <td colspan="2" bgcolor="#ede176"><b>Leaf</b></td>
  </tr>
  <tr>
    <td>Type</td>
    <td><%=inmap.get("pltype")%></td>
  </tr>
  <tr>
    <td>Length</td>
    <td><%=inmap.get("pllength")%></td>
  </tr>
  <tr>
    <td>Width</td>
    <td><%=inmap.get("plwidth")%></td>
  </tr>
  <tr>
    <td>Arrangement</td>
    <td><%=inmap.get("plarr")%></td>
  </tr>
  <tr>
    <td>Venation</td>
    <td><%=inmap.get("plvein")%></td>
  </tr>
  <tr>
    <td>Margin</td>
    <td><%=inmap.get("plmar")%></td>
  </tr>
  <tr>
    <td>Tip</td>
    <td><%=inmap.get("pltip")%></td>
  </tr>
  <tr>
    <td>Base</td>
    <td><%=inmap.get("plbase")%></td>
  </tr>
  <tr>
    <td>Shape</td>
    <td><%=inmap.get("plshape")%></td>
  </tr>
  <tr>
    <td>Surface</td>
    <td><%=inmap.get("plsurface")%></td>
  </tr>
  <tr>
    <td style="line-height: 100%;">Sample image(s)</td>
    <td><img src="images/psimg/<%=imgfile%>-L001-FF.jpg" onerror="this.style.display='none'"><br/><br/><br/><br/><img src="images/psimg/<%=imgfile%>-L001-NF.jpg" onError="this.style.display='none'"></td>
  </tr>
  <% }
   if (inmap.get("pfruit") != null) {
   %>
  <tr>
    <td colspan="2" bgcolor="#ede176"><b>Fruit</b></td>
  </tr>
  <tr>
    <td>Color</td>
    <td><%=inmap.get("fruitcolor")%></td>
  </tr>
  <tr>
  <td style="line-height: 100%;">Sample image</td>
    <td><img src="images/psimg/<%=imgfile%>-T001.jpg"></td>
  </tr>
  <% }
   if (inmap.get("pflower") != null) {
  %>
  <tr>
    <td colspan="2" bgcolor="#ede176"><b>Flower</b></td>
  </tr>
  <tr>
    <td>Petal number</td>
    <td><%=inmap.get("petalnum")%></td>
  </tr>
  <tr>
    <td>Color</td>
    <td><%=inmap.get("flowercolor")%></td>
  </tr>
  <tr>
    <td>Inflorescence type</td>
    <td><%=inmap.get("intype")%></td>
  </tr>
  <tr>
  <td style="line-height: 100%;">Sample image</td>
    <td><img src="images/psimg/<%=imgfile%>-R001.jpg"></td>
  </tr>
  <% }
   %>
</table>
<br><br><br>
<% } %>
</body>
</html>