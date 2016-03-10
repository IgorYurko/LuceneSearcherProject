<#include "../functions/layout.ftl"/>

<#macro css>
    <#compress>

        <#assign themeStyles>
            <@spring.theme code="path.css"/>
        </#assign>

    ${getThemeStyleOrScrypt(themeStyles)}

    </#compress>
</#macro>

<#macro js>
    <#compress>

        <#assign themeScripts>
            <@spring.theme code="path.js"/>
        </#assign>

    ${getThemeStyleOrScrypt(themeScripts, "js")}

    </#compress>
</#macro>

<#macro title titleName>
    <@spring.message code=titleName/>
</#macro>