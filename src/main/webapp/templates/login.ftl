<#import "/tags/util.ftl" as util>
<html>
<head>
<title>Login Page</title>
<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body>
	<#if error??>
		<div class="errorblock">
			${Session.SPRING_SECURITY_LAST_EXCEPTION}

		</div>
	</#if>
	
	<form action="<@util.url "/j_spring_security_check" ""/>" method="POST" name="" class="form">
		<div class="form-header">
			<h2 class="title">Přihlášení do systému</h2>
		</div>
		<div class="form-content">
			<div class="form-content-inner">

				<div class="message message-error">
					<p>Vyplňte správně všechny povinné položky</p>
				</div>				
			
				<p>
					<label for="" class="label error">Uživatelské jméno:</label><br>
					<span class="inp-fix inp-fix-error">
						<input type="text" name="j_username" id="" class="inp-text">
					</span>
				</p>
				<p>
					<label for="" class="label error">Heslo:</label><br>
					<span class="inp-fix inp-fix-error">
						<input type="password" name="j_password" id="" class="inp-text">
					</span>
				</p>
				<p class="right">
					<button class="btn btn-big btn-blue" name="" type="submit">
						<span>Přihlásit se</span>
					</button>
				</p>
			</div>
		</div>
	</form>
</body>
</html>