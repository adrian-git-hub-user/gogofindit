<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <title></title>

  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script>
  $(document).ready(function () {
	  
	 /*  ${searchParam} */

/* 	 alert(${isGoogle});  */
	 
	 if('${searchButton}' == 'Google'){
		 window.location.replace("http://www.google.com/search?q=<c:out value='${searchParam}' />");
	 }
	 else if('${searchButton}' == 'Duck Duck Go'){
		 
		 window.location.replace("http://www.duckduckgo.com/?q=<c:out value='${searchParam}' />");
		 // window.focus();
		  
		// window.location.open("http://www.duckduckgo.com/?q=<c:out value='${searchParam}' />");
	 }
	 else if('${searchButton}' == 'Wikipedia'){
		 
		 window.location.replace("http://en.wikipedia.org/wiki/<c:out value='${searchParam}' />");
		//  window.focus();
		  
		 //window.location.replace("http://en.wikipedia.org/wiki/<c:out value='${searchParam}' />");
	 }

	});
  </script>

</head>

</html>