<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NCCU Time Table - Login</title>
<style type="text/css">
:root{
	--color-bg: #3d405b;
	--color-red: #e07a5f;
	--color-ivory: #f4f1de;
	--color-yellow: #f2cc8f;
	--font-e: "noto Sans TC", "Optima";
	--font-c: "noto Sans TC";
}
body {
	background-color: #3d405b;
	margin: 0;
	font-family: var(--font-e);
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}
.logo {
	margin: 100px auto 0px;
}
#block {
	background-color: var(--color-yellow);
	width: 340px;
	height: 80px;
	margin: 40px auto;
}
#year {
	text-align: center;
}
#title {
	text-align: center;
	position: absolute;
	width: 340px;
	height: 160px;
	font-size: 60px;
	color: white;
	line-height: 1.3em;
}
input {
	margin: 20px;
	color: var(--color-ivory);
	font-size: 14px;
}
#studentID, #password {
	padding: 8px 10px;
	border: 2px solid var(--color-yellow);
	border-radius: 10px;
	width: 350px;
	line-height: 20px;
	background-color: var(--color-bg);
	padding-left: 12px;
	letter-spacing: 2px;
}
#btn_login {
	width: 120px;
	height: 28px;
	line-height: 28px;
	text-align: center;
	background-color: #4E5273;
	border-radius: 6px;
	border: 0;
	margin-left: 148px;
	margin-right: 148px;
}
#studentID:focus {
	outline: none;
	border: 2px solid var(--color-ivory);
}
#password:focus {
	outline: none;
	border: 2px solid var(--color-ivory);
}
#btn_login:focus {
	outline: none;
}
</style>
<script type="text/javascript">
	if(${invalid_login}) {
		alert("Invalid ID or password");
	}
</script>
</head>
<body>
	<div class="logo">
		<div id="title">NCCU<br>Time Table</div>
		<div id="block"></div>
		<div id="year">SINCE 2021</div>
	</div>
	<form name="login" action='login' method='post'>
		<div>
			<input type='text' name='studentID' placeholder='Student ID' id='studentID' value='${studentID}'/><br>
			<input type='text' name='password' placeholder='Password' id='password' value='${password}'/><br>
			<input type="submit" id="btn_login" value="Log In"/>
		</div>
	</form>
</body>
</html>