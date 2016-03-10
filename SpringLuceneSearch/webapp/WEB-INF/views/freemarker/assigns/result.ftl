<#include "../functions/result.ftl"/>

<#assign action_switcher>
    <@spring.url value="/search/switch"/>
</#assign>

<#assign action>
    <@spring.url value="/search"/>
</#assign>

<#assign placeholder>
    <@spring.message code="search.input.placeholder.text"/>
</#assign>

<#assign button>
    <@spring.message code="search.button.text"/>
</#assign>

<#assign t_button>
    <@spring.message code="result.button.date_1.text"/>
</#assign>

<#assign f_button>
    <@spring.message code="result.button.date_2.text"/>
</#assign>

<#assign err>
    <@spring.message code="search.formComponent.q"/>
</#assign>