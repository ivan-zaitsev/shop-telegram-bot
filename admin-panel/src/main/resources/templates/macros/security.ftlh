<#assign isSecutiryContextExists = Session.SPRING_SECURITY_CONTEXT??>

<#if isSecutiryContextExists>
  <#assign
    isLoggedIn = true
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    username = user.username
    isAdmin = user.admin
  >
<#else>
  <#assign
    isLoggedIn = false
    username = "unknown"
    isAdmin = false
  >
</#if>
