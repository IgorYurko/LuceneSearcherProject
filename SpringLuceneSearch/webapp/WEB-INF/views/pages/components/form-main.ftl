<#compress>
    <@form.form commandName="formComponent" action="${action!''}" method="POST" class="form-inline">
        <div class="form-group">
            <@form.input path="q" placeholder="${placeholder}" class="form-control"/>
            <#if depthList??>
                <@form.select path="depth" class="form-control">
                    <option selected><@spring.message code="index.select.text"/></option>
                    <#list 1..depthList as item>
                        <option value="${item}">${item}</option>
                    </#list>
                </@form.select>
            </#if>
            <@form.button type="submit" class="btn btn-primary">${button}</@form.button>
            ${showError()}
        </div>
    </@form.form>
</#compress>