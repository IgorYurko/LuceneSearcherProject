<#include "../freemarker/tags/jspTagLibs.ftl"/>
<#include "../freemarker/macros/layoutMacros.ftl"/>
<@tilesx.useAttribute id="titleName" name="title" classname="java.lang.String"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><@title titleName/></title>
    <link rel="stylesheet" href="<@spring.url value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>"/>
<@css/>
</head>
<body>

<@tiles.insertAttribute name="header"/>

<@tiles.insertAttribute name="content"/>

<@tiles.insertAttribute name="footer"/>

<script src="<@spring.url value="/webjars/jquery/2.2.0/jquery.min.js"/>"></script>
<script src="<@spring.url value="/webjars/jquery-ui/1.11.4/jquery-ui.min.js"/>"></script>
<@js/>
</body>
</html>