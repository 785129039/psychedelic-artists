<#import "/tags/security.ftl" as security>
<html>
<head>
<title>
	Titulek
</title>
</head>
<body>
Always allowed
<@security.authorize ["admin"]>
only admin can see this
</@security.authorize>
</body>
</html>