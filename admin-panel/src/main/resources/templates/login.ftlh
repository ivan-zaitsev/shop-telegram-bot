<#import "common/home.ftlh" as h>

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
      <label class="col-sm-2">Username:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" name="username">
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2">Password:</label>
      <div class="col-sm-10">
        <input type="password" class="form-control" name="password">
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2">Remember me:</label>
      <div class="col-sm-10">
        <input type="checkbox" name="remember-me" checked>
      </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" class="btn btn-primary">Sign In</button>
  </form>

</@h.home>
