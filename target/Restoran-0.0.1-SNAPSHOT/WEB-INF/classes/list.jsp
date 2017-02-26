<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        
<title>Greetings : View all</title>
</head>
<body>
<script>
$(document).ready(function() {
	$.getJSON( "api/users", function( data ) {
		  var items = [];
		  $.each( data, function( key, val ) {
		    items.push( "<tr><td id='" + val.id + "'>" + val.id + "</td>"+
		    		"<td id='" + val.id + "'>" + val.username + "</td>"+
		    		"<td id='" + val.id + "'>" + val.password + "</td></tr>");
		    console.log(val);
		  });
		  console.log(items);
		  });	  
});
</script>
	<div id="greetings">
		<table>
			<tr>
				<th>ID</th>
				<th>username</th>
				<th>password</th>
				<th></th>
			</tr>
		</table>
	</div>	
</body>
</html>
