<#include "../../freemarker/tags/jspTagLibs.ftl"/>
<#include "../../freemarker/functions/index-searcher.ftl"/>
<#include "../../freemarker/assigns/result.ftl"/>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="<@spring.url value="/"/>"><img src=
               "<@spring.url value="/resources/default/images/new-google-logo-knockoff.png"/>" width="100px"></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav" id="main">
                <li class="active">
                    <div class="form-inline">
                    <#include "form-main.ftl"/>
                    </div>
                </li>
            </ul>
        <#include "../components/form-result-1.ftl"/>

        <#include "../components/form-result-2.ftl"/>
        </div>
    </div>
</nav>