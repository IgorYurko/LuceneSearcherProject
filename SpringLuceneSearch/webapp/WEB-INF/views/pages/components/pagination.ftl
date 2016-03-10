<#include "../../freemarker/tags/jspTagLibs.ftl"/>

<div id="pagination" class="container">

<#if count gt 1>
    <ul class="pagination">
        <#list 1..count as item>
            <#if item != getCurrent()>
                <li><a href="${action}/${item}">${item}</a></li>
            </#if>
            <#if item == getCurrent()>
                <li class="active current"><span>${item}<span class="sr-only">(current)</span></span></li>
            </#if>
        </#list>
    </ul>
    <div id="show">
        <a href="#" id="viewMore" class="btn btn-default">
            <span class="glyphicon glyphicon-refresh"></span>
            <@spring.message code="search.div.viewMore.text"/>
        </a>
    </div>
</#if>

</div>