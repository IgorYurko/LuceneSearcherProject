<#include "../../freemarker/tags/jspTagLibs.ftl"/>
<@tilesx.useAttribute name="assign" classname="java.lang.String"/>
<#include "../../freemarker/assigns/${assign}.ftl"/>

<#compress>
<div class="container">
    <div class="row col-lg-1"></div>
    <div id="showcase" class="row col-lg-8">
        <img src="<@spring.url value="/resources/default/images/go.jpg"/>" width="500">
        <#include "../components/form-main.ftl"/>
    </div>
    <div class="row col-lg-2"></div>
</div>
</#compress>
