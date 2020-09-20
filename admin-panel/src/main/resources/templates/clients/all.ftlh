<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<@h.home "Client list">

  <@t.table "clients" />
  <table id="clients" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>City</th>
        <th>Active</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <#list clients as client>
        <tr>
          <td>${client.id?c}</td>
          <td>${client.name!}</td>
          <td>${client.city!}</td>
          <td><#if client.active>Yes<#else>No</#if></td>
          <td>
            <a class="btn btn-xs btn-info" href="clients/edit/${client.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
