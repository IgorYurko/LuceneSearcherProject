<#include "../tags/jspTagLibs.ftl"/>

<#function showError>

    <#if (errors??)>
        <#if errors?seq_contains("q") || errors?seq_contains("depth")>
            <#assign eQ>
                <#compress>
                    <div class="alert alert-warning" role="alert">
                        ${err}
                    </div>
                </#compress>
            </#assign>
        </#if>
    </#if>

    <#return eQ!''>

</#function>