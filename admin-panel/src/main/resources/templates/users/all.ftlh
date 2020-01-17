<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<@h.home "User list">

  <@t.table "users" />
  <center class="mb-2"><a class="btn btn-sm btn-info" href="users/add">Add user</a></center>

  <table id="users" class="table table-striped table-bordered mb-5" cellspacing="0" width="100%">
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
          <td>${user.id}</td>
          <td>${user.name}</td>
          <td>${user.username}</td>
          <td>${user.role.value}</td>
          <td>
            <div>
              <a class="btn btn-xs btn-info command-edit" href="users/edit/${user.id?c}" style="float:left;">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </a>
            </div>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
