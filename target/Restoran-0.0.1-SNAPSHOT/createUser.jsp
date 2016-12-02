<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Create User</title>
</head>
<body>
	<div id="createUser">
		<form id="formUser" action="api/users" method="post">
			<fieldset>
				<input type="text" path="id" />
				<label path="username">Username </label>
				<input type="text" path="username" />
				<label path="password">password </label>
				<input  type="password" path="password" />
			 </fieldset>
			<p>
				<button type="submit">Submit</button>
			</p>
		</form>
	</div>
</body>
</html>