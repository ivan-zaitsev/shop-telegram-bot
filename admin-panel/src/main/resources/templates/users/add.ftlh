<#import "../macros/home.ftlh" as h>

<@h.home "User adding">

  <form method="post" action="users/create">
    <table class="table table-striped table-bordered" cellspacing="0" width="100%">
      <tr>
        <th>Name</th>
        <td>
          <input class="form-control" name="name" value="<#if user??>${user.name}</#if>">
          <#if nameError??><div style="color:red;">${nameError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Username</th>
        <td>
          <input class="form-control" name="username" value="<#if user??>${user.username}</#if>">
          <#if usernameError??><div style="color:red;">${usernameError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Password</th>
        <td>
          <input class="form-control" name="password" value="<#if user??>${user.password}</#if>">
          <#if passwordError??><div style="color:red;">${passwordError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Role</th>
        <td>
          <select class="form-control" name="role">
            <#list roles as role>
              <option value="${role.name()}" <#if user?? && user.role.name() == role>selected</#if>>${role.value}</option>
            </#list>
          </select>
        </td>
      </tr>
    </table>
    
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-success ml-1" style="float:right;" type="submit" value="Create">
  </form>

  <a class="btn btn-info ml-1" style="float:right;" href="users">Back</a>

</@h.home>
