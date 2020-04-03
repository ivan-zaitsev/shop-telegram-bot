<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<@h.home "Product list">

  <center class="mb-4">
    <a class="btn btn-sm btn-info" href="products/add">Add product</a>
  </center>

  <@t.table "products" />
  <table id="products" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Id</th>
        <th>Photo</th>
        <th>Name</th>
        <th>Category</th>
        <th>Price</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <#list products as product>
        <tr>
          <td>${product.id?c}</td>
          <td><img src="${product.photoUrl}" width="50" height="50"></td>
          <td>${product.name}</td>
          <td><#if product.category??>${product.category.name}<#else>—</#if></td>
          <td>${product.price} $</td>
          <td>
            <a class="btn btn-xs btn-info command-edit" href="products/edit/${product.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
