<#include "../../freemarker/assigns/result.ftl"/>

<div class="container">
    <div id="pages" class="row col-lg-8">
        <table class="table table-hover">
            <#list results as result>
                    <tr>
                        <td class="clickable">
                            <b>${highlightString(result.highlightTitle)}</b><br/>
                            <a href="${result.url}">${result.url}</a><br/><br/>
                            <p>${highlightString(result.highlightArticle)}</p>
                        </td>
                    </tr>
            </#list>
        </table>
    </div>
    <div class="row col-lg-4 naw">
    </div>
</div>

<#include "../components/pagination.ftl"/>
