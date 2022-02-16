<#import "../../common/home.ftlh" as h>

<@h.home "User adding">

  <form method="post" action="users/create">
    <div class="form-group row">
      <label class="col-sm-2">Name:</label>
      <div class="col-sm-10">
        <input class="form-control" name="name" value="<#if user??>${user.name}</#if>">
        <#if nameError??><div class="text-warning">${nameError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2">Username:</label>
      <div class="col-sm-10">
        <input class="form-control" name="username" value="<#if user??>${user.username}</#if>">
        <#if usernameError??><div class="text-warning">${usernameError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2">Password:</label>
      <div class="col-sm-10">
        <input class="form-control" name="password" value="<#if user??>${user.password}</#if>">
        <#if passwordError??><div class="text-warning">${passwordError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2">Role:</label>
      <div class="col-sm-10">
        <select class="form-control" name="role">
          <#list roles as role>
            <option value="${role.name()}" <#if user?? && user.role.name() == role>selected</#if>>${role.value}</option>
          </#list>
        </select>
      </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button class="btn btn-success" type="submit">Create</button>
  </form>

  <form method="get" action="users">
    <button class="btn btn-info" type="submit">Back</button>
  </form>

</@h.home>
