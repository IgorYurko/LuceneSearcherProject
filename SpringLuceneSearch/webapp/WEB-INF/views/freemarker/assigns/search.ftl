<#include "../functions/index-searcher.ftl"/>

<#assign placeholder>
    <@spring.message code="search.input.placeholder.text"/>
</#assign>

<#assign button>
    <@spring.message code="search.button.text"/>
</#assign>

<#assign action>
    <@spring.url value="/search"/>
</#assign>

<#assign err>
    <@spring.message code="search.formComponent.q"/>
</#assign>