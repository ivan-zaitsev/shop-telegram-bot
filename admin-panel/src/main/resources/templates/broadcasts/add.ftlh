<#import "../macros/home.ftlh" as h>

<@h.home "Broadcast adding">

  <#if RequestParameters.send?? >
    <div class="alert alert-success" role="alert">
      The broadcast is added to the send queue.
    </div>
  </#if>

  <form method="post" action="broadcasts/send">
    <div class="form-group row">
      <label class="col-sm-2">Text:</label>
      <div class="col-sm-10">
        <textarea class="form-control" name="text" rows="8" maxlength="4096" required></textarea>
      </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button class="btn btn-success" type="submit">Send</button>
  </form>

</@h.home>
