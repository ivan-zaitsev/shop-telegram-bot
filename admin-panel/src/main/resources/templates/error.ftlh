<#import "macros/home.ftlh" as h>
<#include "macros/security.ftlh">

<@h.home "Error">

  <div class="alert alert-danger" role="alert">
    <h4 class="alert-heading">${error} — ${status}</h4>
    <hr><div>${message}</div>
  </div>

</@h.home>
