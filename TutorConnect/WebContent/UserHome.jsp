<!DOCTYPE html>
<html>
<head>
<% String loginName = "Dummy";

%>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="Container.css">
<title>UserHome</title>
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
	
	<div class="about">
	 about
	</div>

</div>

</body>
</html>