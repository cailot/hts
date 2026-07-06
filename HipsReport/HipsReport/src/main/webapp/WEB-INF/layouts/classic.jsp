<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon-32x32.png"/>
<link href="${pageContext.request.contextPath}/css/jquery-ui.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.min.css">

<link href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" rel="stylesheet" />
<!--[if IE 8]><link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-ie8buttonfix.css"><![endif]-->
<link href="${pageContext.request.contextPath}/css/hips.report.css" rel="stylesheet" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Chart.min.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Chart.min.js"></script>

<script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hips.report.js"></script>


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
      <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->
	
	<style>
	html,body{
		height:100%
	}
	.flex-fill{
		flex:1;
	}
	
	</style>
	
</head>
<body>
	<div class="container-fluid d-flex h-100 flex-column">
		<div class="row">
			<tiles:insertAttribute name="header" />
		</div>
		<div class="row dhhs-color" style="display: flex; justify-content: space-between;">
			<tiles:insertAttribute name="menu" />
		</div>
		<div class="row justify-content-center align-items-center">		
			<tiles:insertAttribute name="body" />
		</div>
		<footer class="mt-auto">
			<div class="row dhhs-color" style="padding: 15px 20px;">
				This web site is managed and authorised by the Department of Health & Human Services, State Government of Victoria, Australia&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &copy;&nbsp;Copyright State of Victoria
				2017 - <%=new java.util.Date().getYear() + 1900%>
			</div>
		</footer>
	</div>
</body>
</html>