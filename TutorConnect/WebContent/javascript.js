
	//Function to set cookie
	function setCookie(cname,cvalue,exdays) {
		var d = new Date();
		d.setTime(d.getTime()+(exdays*24*60*60*1000));
		var expires = "expires="+d.toGMTString();
		document.cookie = cname + "=" + cvalue + "; " + expires;
	}
	
	//Function to get cookie based on the key
	function getCookie(cname) {
		var name = cname + "=";
		var ca = document.cookie.split(';');
		
		for(var i=0; i<ca.length; i++) {
			var c = ca[i].trim();
		  	if (c.indexOf(name)==0) return c.substring(name.length,c.length);
		}
		return "";
	}
	
	//Function used to delete a cookie
	function deleteCookie() {
		document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
	}
	
	//ajax call to the servlet
	$.ajax({
		  url: 'Servlet',
		  cache: false
	}).done(function( html ) {
  		$( "#content" ).append( html );
	});
	
	//Function to refresh the home screen
	function refreshHome(){
		$('#container').show();
     	$('#addButton').show();
     	$('#myEvents').show();
     	$('#deleteButton').show();
     	$('#addForm').hide();
 	   	$('#deleteForm').hide();
     	$('#loginContent').hide();
     	$('#createLoginContent').hide();
     	$('#eventBar').show();
    	$('#searchContainer').show();
         var home="home";
          
        $.get('Servlet',{page:home},function(responseText) { 
             $('#content').html(responseText);  
             
         });	
	}
	
	//jQuery function that runs on loading the web page
    $(document).ready(function() {   
    	
	   	$('#addForm').hide();
	   	$('#deleteForm').hide();
	   	$('#loginContent').hide();
	   	$('#createLoginContent').hide();
	   	
	   	//Get request to the servlet to get events for home page
	   	$.get('Servlet', "page", function(responseText) { 
	   		$('#content').html(responseText);  
	   	});
    	
    	if(getCookie("username")){
    		$('#welcometext').text("Logout");
    	} else{
    		$('#welcometext').text("Login");
    	}
    			//Click of home button
                $('#homebutton').click(function(event) { 
                	refreshHome();
                });
                
                //Click of welcome text
                $('#welcometext').click(function(event) {
                	var text = $('#welcometext').text();
                	if($.trim(text) == 'Login'){
	                	$('#container').hide();
	                	$('#createLoginContent').hide();
	                	$('#loginContent').show().css("#loginContent");
                	} else if($.trim(text) == 'Logout'){
                		var answer = confirm('Are you sure you want to logout?');
                		if (answer){
							deleteCookie();
							$('#welcometext').text("Login");
							
							var home="home";
		                	$.get('Servlet',{page:home},function(responseText) { 
		                        $('#content').html(responseText);
		                    });
                		}
                	}
                	
                });
                //Click of about link
                $('#about').click(function(event) { 
                	
                	$('#eventBar').hide();
                	$('#searchContainer').hide();
                    var home="about";
                    /*  alert("inside login click event");  */
                 $.get('Servlet',{page:home},function(responseText) { 
                        $('#content').html(responseText);  
                        //alert(responseText);
                    });
                });
                
                //Click of contact us link
                $('#contact').click(function(event) { 
                	$('#eventBar').hide();
                	$('#searchContainer').hide();
                    var home="contact";
                    /*  alert("inside login click event");  */
                 $.get('Servlet',{page:home},function(responseText) { 
                        $('#content').html(responseText);  
                        //alert(responseText);
                    });
                });
                
                //Click of the add event link
                $('#addButton').click(function(event) {  
                	if(getCookie("username") != ""){
                		$('#addForm').show();
                		$('#deleteForm').hide();
                	} else{
                		alert("You must be logged in to add an Event");
                	}
          		});
                
                //Click of the myevents link
                $('#myEvents').click(function(event) {  
                	var home = "myEvents";
                	if(getCookie("username") != ""){
		            	$('#addForm').hide();
		        		$('#deleteForm').hide();
		            	$.get('Servlet',{page:home},function(responseText) { 
		            		$('#content').html(responseText);  
		                });
                	} else{
                		alert("Login to view your events");
                	}
          		});
                
                //Click of the delete event link
                $('#deleteButton').click(function(event) {
                	//alert("delete button clicked");
                	if(getCookie("username") != ""){
                		$('#deleteForm').show();
                		$('#addForm').hide();
                	} else{
                		alert("You must be logged in to delete an Event");
                	}
          		});
                
                //click of the add new user link
                $('#newUserLink').click(function(event) {  
                	$('#createLoginContent').show();
                	$('#loginContent').hide();
                    });
                
                //Click of the return to login page link
                $('#returnToLogin').click(function(event){
                	$('#loginContent').show();
                	$('#createLoginContent').hide();
                });
                
                //Submit of the search form
                $('form[name="searchBox"]').submit(function(event) { 
                	event.preventDefault();
                	var search = $.trim($('#search').val());
                	if(search == ''){
           				alert("Enter a keyword to search events");
                	} else{
	                	
	                	
	                	$.post('SearchServlet', {search : search},
	             			   function(responseText) {
		             			response = responseText;
		             			if(response == ''){
		             				response = "<span>Search did not return any matching results !</span>";
		             				$('#content').html(response);
		             			} else{
	             			   	 	$('#content').html(response);
		             			}
	             			   });
                	}
                	
               });
                
                //Submit of the add event form
                $('form[name="eventForm"]').submit(function(event){
                	//event.preventDefault();
                	var title = $("#Title").val();
                	var name =  getCookie("username");
                	var location =  $("#Location").val();
                	var description =  $("#Description").val();
                	var day =  $.trim($("#Day").val());
                	var time =  $.trim($("#Time").val());
                	var string = name.concat("#!", location, "#!", description, "#!", day, " ", time, "#!", title);
                	var response;
                	
                	if(title === "" || name === "" || location === "" || day === "" || time === "" || description === ""){
    					alert("All fields are required");  
                		return;         		
                	} else{
                	
	                	$.post('Servlet', { CreatorName : string},
	                			   function(responseText) {
	                			     response = responseText;
	                			   });
                	}
                    
                  });
                
                //Submit of the delete event form
                $('form[name="deleteEventForm"]').submit(function(event){
                	event.preventDefault();
                	var deleteID = $.trim($('#deleteID').val());
                	var response;               	
                	
                		$.post('DeleteServlet', { deleteID : deleteID},
                			function(responseText) {
                				                		
                				response = responseText;
                				if(response == "success"){
                					alert("Event Deleted");
                				} else {
                					alert("Don't have access to delete event");
                				}
                				
                			});
                	 
                	//code for refreshing my events after delete
                		var home = "myEvents";
	                	$('#addForm').hide();
		        		$('#deleteForm').hide();
		            	$.get('Servlet',{page:home},function(responseText) { 
		            		$('#content').html(responseText);  
		                });
                	//code for refreshing my events ends here
                });
                
                //Submit of the login form
                $('form[name="loginForm"]').submit(function(event){
                	event.preventDefault();
                	var username =  $("#Username").val();
                	var password =  $("#Password").val();
              
                	var string = username.concat("#!", password);
                	                	                	
                	$.post('LoginServlet', { Username : string},
                			   function(response) {
                			     if(response == "success"){
                			    	 $('#welcometext').text("Logout");
                			    	 setCookie("username", username, 1);
                			    	 alert("You are now logged in as " + getCookie("username"));
                			    	 refreshHome();
                			    	 
                			     } else{
                			    	 alert("login failed" + responseText);
                			     }
                			     response = responseText;
                			   });
                                   		
                  });
                
                //Submit of the add a new user form
                $('form[name="createLoginForm"]').submit(function(event){
                	event.preventDefault();
                	var username =  $("#createUsername").val();
                	var password =  $("#createPassword").val();
              
                	var string = username.concat("#!", password);
                	var response;
                	
                	$.post('NewUserServlet', { createUsername : string},
                			   function(response) {
                			     if(response == "success"){
                			    	 alert("successfully created an account");
                			     } else {
                			    	 alert("Account creation failed");
                			     }
                			     response = responseText;
                			   });
                   
                  });
            });