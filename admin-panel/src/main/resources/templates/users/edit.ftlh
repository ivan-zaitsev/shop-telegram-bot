<#import "../macros/home.ftlh" as h>

<@h.home "User editing">

  <#if Error??>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>${Error}
    </div>
  </#if>

  <#if user??>
    <form method="post" action="users/update">
      <div class="form-group row">
        <label class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="id" value="${user.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Name:</label>
        <div class="col-sm-10">
          <input class="form-control" name="name" value="${user.name}">
          <#if nameError??><div class="text-warning">${nameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Username:</label>
        <div class="col-sm-10">
          <input class="form-control" name="username" value="${user.username}">
          <#if usernameError??><div class="text-warning">${usernameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Password:</label>
        <div class="col-sm-10">
          <input class="form-control" name="password">
          <#if passwordError??><div class="text-warning">${passwordError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Role:</label>
        <div class="col-sm-10">
          <select class="form-control" name="role">
            <#list user.role.values() as role>
              <option value="${role.name()}" <#if user.role.name() == role>selected</#if>>${role.value}</option>
            </#list>
          </select>
        </div>
      </div>

      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-success" type="submit">Update</button>
    </form>

    <form method="get" action="users">
      <button class="btn btn-info" type="submit">Back</button>
    </form>

    <form method="post" action="users/delete">
      <input type="hidden" name="id" value="${user.id?c}">
      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-danger" type="submit">Delete</button>
    </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This user does not exist!
    </div>
  </#if>

</@h.home>
