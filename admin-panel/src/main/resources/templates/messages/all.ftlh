<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<@h.home "Messages list">

  <@t.table "messages" "asc" />
  <table id="messages" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Id</th>
        <th>Description</th>
        <th>Text</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <#list messages as message>
        <tr>
          <td>${message.id?c}</td>
          <td>${message.description!}</td>
          <td>${message.text}</td>
          <td>
            <a class="btn btn-xs btn-info command-edit" href="messages/edit/${message.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
