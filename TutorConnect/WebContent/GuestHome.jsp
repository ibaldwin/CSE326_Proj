<!DOCTYPE html>
<html>
<head>
<% String loginName = "Dummy";

%>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="Container.css">
<title>GuestHome</title>
</head>
<body>

<div class="containerDiv"> 

	<div class="headerDiv" >
		<a href="GuestHome.jsp" class= "homeButton">TutorConnect</a>
		<span class="dummy">
			<span class="dummyText">
			<%= loginName %>
			</span>
		</span>
		<br></br>
		<a href="About.jsp" class=subHeaderDiv>About</a>
		<a href="Contact.jsp" class=subHeaderDiv>ContactUs</a>
	</div>
	
	<hr class= "hline">
	</hr>
	
	<div class="eventCal">
	 	<div class="events">
	 		<div class="search">
	 			Type to search events
	 		</div>
	 		
	 		<div class="eventList">
	 			Events go here
	 		</div>
	 		
	 		
	 	</div>
	 	
	 	<div class="calendar">
	 		<div class="gapi">
	 			GAPI goes here
	 		</div>
	 		
	 	</div>
	</div>

</div>

</body>
</html>