<#import "common/home.ftlh" as h>
<#include "common/security.ftlh">

<@h.home "Error">

  <div class="alert alert-danger" role="alert">
    <h4 class="alert-heading">${error} — ${status}</h4>
    <hr><div>${message}</div>
  </div>

</@h.home>
