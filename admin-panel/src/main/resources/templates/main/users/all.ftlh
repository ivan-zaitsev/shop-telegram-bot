<#import "../../common/home.ftlh" as h>
<#import "../../common/table.ftlh" as t>

<@h.home "User list">

  <center class="mb-4">
    <a class="btn btn-sm btn-info" href="users/add">Add user</a>
  </center>

  <@t.table "users" />
  <table id="users" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Username</th>
        <th>Role</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <#list users as user>
        <tr>
          <td>${user.id?c}</td>
          <td>${user.name}</td>
          <td>${user.username}</td>
          <td>${user.role.value}</td>
          <td>
            <a class="btn btn-xs btn-info" href="users/edit/${user.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
