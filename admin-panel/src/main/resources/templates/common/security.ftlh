<#if SPRING_SECURITY_CONTEXT??>
  <#assign
    isLoggedIn = true
    user = SPRING_SECURITY_CONTEXT.authentication.principal
  >
<#else>
  <#assign
    isLoggedIn = false
  >
</#if>
