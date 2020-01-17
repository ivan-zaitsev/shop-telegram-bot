<#import "macros/home.ftlh" as h>

<@h.home "Authorization">

  <#if RequestParameters.error?? >
    <div class="alert alert-warning" role="alert">
      Invalid username or password.
    </div>
  </#if>

  <#if RequestParameters.logout?? >
    <div class="alert alert-success" role="alert">
      You are logged out.
    </div>
  </#if>

  <form action="login" method="post">
    <div class="form-group row">
      <label class="col-sm-2 col-form-label">Username:</label>
      <div class="col-sm-6">
        <input type="text" class="form-control" name="username"/>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">Password:</label>
      <div class="col-sm-6">
        <input type="password" class="form-control" name="password"/>
      </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div class="form-group row">
        <div class="col-sm-6">
            <button type="submit" class="btn btn-primary">Sign In</button>
        </div>
    </div>
  </form>

</@h.home>
