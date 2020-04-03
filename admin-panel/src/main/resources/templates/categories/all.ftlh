<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<@h.home "Category list">

  <center class="mb-4">
    <a class="btn btn-sm btn-info" href="categories/add">Add category</a>
  </center>

  <@t.table "categories" />
  <table id="categories" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <#list categories as category>
        <tr>
          <td>${category.id?c}</td>
          <td>${category.name!}</td>
          <td>
            <a class="btn btn-xs btn-info command-edit" href="categories/edit/${category.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
