<#include "../tags/jspTagLibs.ftl"/>

<#function highlightString str>

    <#assign start>
        <@spring.message code="result.function.highlightTitle.start"/>
    </#assign>

    <#assign finish>
        <@spring.message code="result.function.highlightTitle.finish"/>
    </#assign>

    <#assign date>
        <@spring.message code="result.function.highlightTitle.date"/>
    </#assign>

    <#return str?html
    ?replace(start, "<span class='firefly'>")
    ?replace(finish, "</span>")
    ?replace(date, "<span class='fireflyDate'>...</span>")>

</#function>

<#function getCurrent>
    <#assign current = request.getRequestURI()?remove_ending("/")?keep_after_last("/")/>

    <#if current?matches("[0-9]+")>
        <#return current?number>
    </#if>
    <#return 1>
</#function>