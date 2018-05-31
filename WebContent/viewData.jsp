<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:xlink="http://www.w3.org/1999/xlink">
	<head>
		<title>Plant Ontology | PlantViz</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<!--5grid--><script src="css/5grid/viewport.js"></script><!--[if lt IE 9]><script src="css/5grid/ie.js"></script><![endif]--><link rel="stylesheet" href="css/5grid/core.css" />
		<link rel="stylesheet" href="css/style.css" />
		<!--[if IE 9]><link rel="stylesheet" href="css/style-ie9.css" /><![endif]-->
<style>

#legend {
	float: left;
	padding: 5px 50px 50px 5px ;
	height: 495px;
	width:  200px;
	border: 1px solid black;
	align: left;
}

b {
	font-weight : bold;
}

svg {
	display: block;
	border: 1px solid black;
}

.node circle {
  cursor: pointer;
}

.node text {
  font: 12px sans-serif;
  pointer-events: none;
  text-anchor: middle;
}

line.link {
  fill: none;
  stroke: #adadad;
  stroke-width: 2px;
}

div.tooltip {   
  position: absolute;           
  text-align: center;           
  width: 400px;          
  padding: 4px;             
  font: 12px sans-serif;        
  background: lightsteelblue; 
  border-radius: 4px;           
  pointer-events: none;         
}

ul {
  display: inline-block;
  margin: 0 15px;
  list-style-type: decimal;
}

newdiv.tooltip {
  position: absolute;    
  text-align: center;       
  width: 50px;  
  height:30px;               
  font: 12px sans-serif;
  border-radius: 4px;        
  background: #fffaa8;  
}

.tooltip:hover {
    visibility: visible;
}

img {
border: 2px solid #fff;
width:185px;
height:175px;
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

				<div id="result">
					<h2>RESULT</h2>
	<p>Result for <b> <%=request.getAttribute("optdata") %> : <%=request.getAttribute("dataquery") %> </b><br/></p>
	<div id="legend">
	<b>LEGEND</b> <br>
	<font size = "3"><font size="5" color="#ff006a">&bull;</font> Parent node <br/> <font size="5.5" color="#ff9642">&bull;</font> Expandable node <br/> <font size="5" color="#004bff">&bull;</font> Children node</font><br>
<br/>
<b>VISUALIZATION FEATURES</b>
<font size="3"><ul>
	<li>Hover on the node to view node's label</li><br/>
	<li>Hover on the node to highlight links between node</li><br/>
	<li>Click on the expandable node to extend network graph or children node to shrink network graph</li><br/>
	<li>Hover on the the node with sample ID to view thumbnail images of selected plant sample</li><br/>
	<li>Click on the "Sample" node to view full information of the plant sample</li><br/>
	</ul>
	
<br/>Please visit 'How To' page to learn the visualization features in PlantViz
</font>
</div>
					<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
					<script src="//d3js.org/d3.v3.min.js"></script>
					<script>
					var parentschildren=[];
					var nodes2 = [];
					var linkedByIndex = {};

					var width = 750,
					height = 550,
					root;
					
					//set up force layout
					var force = d3.layout.force()
					.linkDistance(100)
					.charge(-350)
					.gravity(.05)
					.size([width, height])
					.on("tick", tick);

					//append SVG to div result
					var svg = d3.select("#result").append("svg")
					.attr("width", width)
					.attr("height", height)
					//allow zoom in/out in the graph
					.call(d3.behavior.zoom().on("zoom", function () {
    					svg.attr("transform", "translate(" + d3.event.translate + ")" + " scale(" + d3.event.scale + ")")
  					}))
  					//disable zoom in when double click on the graph
  					.on("dblclick.zoom", null) 
  					.append("g");

					//append div and newdiv to body
					var div = d3.select("body").append("div")   
					.attr("class", "tooltip")               
					.style("opacity", 0);

					var newdiv = d3.select("body").append("newdiv")   
					.attr("class", "tooltip")               
					.style("opacity", 0);
					
					//create links and nodes of SVGs (but without coordinates yet)
					var link = svg.selectAll(".link"),
					node = svg.selectAll(".node");

					//get string answerJson from servlet queryD.java
					getJson();

					flatten(root); 	//to set ids
					setParents(root, null);
					collapseAll(root);
					root.children = root._children;
					root._children = null;
					update();		//update nodes and links in force layout graph

					function update() {
					var nodes = flatten(root),
					  links = d3.layout.tree().links(nodes);
					
					// Restart the force layout.
					force
					  .nodes(nodes)
					  .links(links)
					  .start();

					// Update links.
					link = link.data(links, function(d) { return d.target.id; });

					link.exit().remove();

					link.enter().insert("line", ".node")
					  .attr("class", "link");

					// Update nodes.
					node = node.data(nodes, function(d) { return d.id; });

					//Show node "con" when hover at children node
				    node.append("svg:title")
				  		.text(function(d) { return d.con});
					
					node.exit().remove();

					//give attributes to nodes
					var nodeEnter = node.enter().append("g")
					  .attr("class", "node")
					  .on("click", click)
					  .on("mouseover", mouseover)
					  .on("mouseout", mouseout)
					  .call(force.drag);

					//append circle to all nodes
					nodeEnter.append("circle")
					  .attr("r", function(d) { return Math.sqrt(d.size) / 10 || 4.5; });

					//append text to all nodes based on name in string answerJSON
					nodeEnter.append("text")
					.attr("dx", 0)
					.attr("dy", 20)
					.text(function(d) { return d.name });
					
					//fill circle with color
					node.select("circle")
					  .style("fill", color);
					}
					
					//get string answerJSON from servlet queryD.java
					function getJson() {
						var answer = '${answerJSON}';					
						var json = JSON.parse(answer);			//parse string answer as json
							
						  root = json;
						  update();
					}
					
					//generate coordinates to links of SVG
					function tick() {
					link.attr("x1", function(d) { return d.source.x; })
					  .attr("y1", function(d) { return d.source.y; })
					  .attr("x2", function(d) { return d.target.x; })
					  .attr("y2", function(d) { return d.target.y; });

					node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
					}

					//fill circle with color
					function color(d) {
					  return d._children ? "#ff9642" // collapsed package
					      : d.children ? "#ff006a" // expanded package
					      : "#004bff"; // leaf node
						}

					//check if nodes are neighbours or not (connected or not)
					function neighboring(a, b) {
					    return a.index == b.index || linkedByIndex[a.index + "," + b.index];
					}

					//Toggle children on click.
					function click(d) {
					// if d.url is in the node, open new window with new link 
					if (d.url != null) {
						var url = d.url
						window.open(url);
					}
						
					if (d3.event.defaultPrevented) return; // ignore drag
					if (d.children) {
					    d._children = d.children;
					    d.children = null;
					} else {
					  if (d._parent){
					      d._parent.children.forEach(function(e){
					          if (e != d){
					              collapseAll(e);
					          }
					      });
					  }
					d.children = d._children;
					d._children = null;
					}
					update();
					}

					//highlight neighbouring nodes
					function mouseover(d) {
						
						 // marks the clicked node
					    d3.selectAll(".link").transition().duration(500)
					    .style("stroke-width", function(o) {
					   	  return neighboring(d, o) || neighboring(o, d) ? "3px" : "2px";
					    }).style("stroke", function(o) {
					      return o.target === d || o.source === d ? "#9600cd" : "#adadad";
					    });
						 
						 //if d.imgfile is in the node element, provide preview to image
						if(d.imgfile != null) {
							
							div.transition()
							  	.duration(200)
							  	.style("opacity", .9);
							
							var string =
								  "<img src=\"images/psimg/" + d.imgfile + "-W001.jpg\" onError =\"this.style.display=\'none\'\">&nbsp;&nbsp;"+
								  "<img src=\"images/psimg/" + d.imgfile + "-B001.jpg\" onError =\"this.style.display=\'none\'\">&nbsp;&nbsp;"+
							  	  "<img src=\"images/psimg/" + d.imgfile + "-L001-FF.jpg\" onError =\"this.style.display=\'none\'\">&nbsp;&nbsp;" +
							  	  "<img src=\"images/psimg/" + d.imgfile + "-T001.jpg\" onError =\"this.style.display=\'none\'\">&nbsp;&nbsp;";
							  				      		  	  
							  div.html(string)
					          .style("left", (d3.event.pageX) + "px")     
					          .style("top", (d3.event.pageY)  + "px")
					          .style("font-color", "white");
						  }
						
					}

					//change link attribute to normal, remove div for image
					function mouseout(d) {

					    d3.selectAll(".link").transition().duration(500)
					    .style("stroke-width", 1)
					    .style("stroke", "#adadad")
					    .style("opacity", 1);
					    
					    div.transition()
						  	 .duration(150)
						  	 .style("opacity",0);
					 
					}
					
					//disable for all node to expand at the same
					function collapseAll(d){
						if (d.children){
						    d.children.forEach(collapseAll);
						    d._children = d.children;
						    d.children = null;
						}
						
						else if (d._children){
						    d._children.forEach(collapseAll);
						}
					}

					//Returns a list of all nodes under the root
					function flatten(root) {
					var nodes = [], i = 0;

						function recurse(node) {
							if (node.children) node.children.forEach(recurse);
							if (!node.id) node.id = ++i;
							nodes.push(node);
						}
						
						recurse(root);
						return nodes;
					}

					//set parents node
					function setParents(d, p){
					d._parent = p;
					if (d.children) {
					  d.children.forEach(function(e){ setParents(e,d);});
					} else if (d._children) {
					  d._children.forEach(function(e){ setParents(e,d);});
					}
					}

					//disable initial animation 
					var k = 0;
					while ((force.alpha() > 1e-2) && (k < 150)) {
					    force.tick(),
					    k = k + 1;
					}

</script>
</div>	


</div><br/><br/><br/>


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