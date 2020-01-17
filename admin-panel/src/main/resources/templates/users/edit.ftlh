<#import "../macros/home.ftlh" as h>

<@h.home "User editing">

  <#if Error??>
    <center>
      <div class="alert alert-danger" role="alert">
        <h4 class="alert-heading">Error</h4>
        <hr>${Error}
      </div>
    </center>
  </#if>

  <#if user??>
  <form method="post" action="users/update">
    <table class="table table-striped table-bordered" cellspacing="0" width="100%">
      <input type="hidden" name="id" value="${user.id?c}">
      <tr>
        <th>Name</th>
        <td>
          <input class="form-control" name="name" value="${user.name}">
          <#if nameError??><div style="color:red;">${nameError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Username</th>
        <td>
          <input class="form-control" name="username" value="${user.username}">
          <#if usernameError??><div style="color:red;">${usernameError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Password</th>
        <td>
          <input class="form-control" name="password">
          <#if passwordError??><div style="color:red;">${passwordError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Role</th>
        <td>
          <select class="form-control" name="role">
            <#list user.role.values() as role>
              <option value="${role.name()}" <#if user.role.name() == role>selected</#if>>${role.value}</option>
            </#list>
          </select>
        </td>
      </tr>
    </table>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-success ml-1" style="float:right;" type="submit" value="Update">
  </form>

  <a class="btn btn-info ml-1" style="float:right;" href="users">Back</a>

  <form method="post" action="users/delete">
    <input type="hidden" name="id" value="${user.id?c}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-danger" style="float:right;" type="submit" value="Delete">
  </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This user does not exist!
    </div>
  </#if>

</@h.home>
