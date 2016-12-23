<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registracija</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	function submita() {
		var a = document.getElementById("password").value;
		var b = document.getElementById("repeatedPassword").value;
		if (a === b) {
			var a = $('#registerGost');
			console.log(a);
			var o = {};
			var a = a.serializeArray();
			console.log(a);
			$.each(a, function() {
				if (o[this.name] !== undefined) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			console.log(a);
			json = JSON.stringify(o);
			console.log(json);
			$.ajax({
				type : "POST",
				url : "api/guestRegistration",
				data : json,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					alert(data);
					console.log(data);
				},
				failure : function(errMsg) {
					alert(errMsg);
				}
			});

		}
	}

	function validate() {

	}
</script>
</head>
<body>
	<form id="registerGost" onsubmit='submita(); return false;'>
		<fieldset>
			<label path="username">Username </label> <input type="text"
				id="username" name="username" /> <label path="email">Email </label>
			<input type="text" id="email" name="email" /> <label path="password">Password
			</label> <input type="password" id="password" name="password" /> <label
				path="repeatedPassword">Ponovite pasword </label> <input
				type="password" id="repeatedPassword" name="repeatedPassword" />
		</fieldset>
		<p>
			<input type="submit" value="Register">
		</p>
	</form>
</body>
</html>