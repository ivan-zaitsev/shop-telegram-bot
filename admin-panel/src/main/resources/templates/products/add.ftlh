<#import "../macros/home.ftlh" as h>
<#import "../macros/fileinput.ftlh" as f>

<@h.home "Product adding">

  <@f.fileinput "photo", "" />
  <link href="static/css/bootstrap-select.css" rel="stylesheet" />
  <script src="static/js/bootstrap-select.js"></script>

  <form method="post" action="products/create" enctype="multipart/form-data">
    <table class="table table-striped table-bordered" cellspacing="0" width="100%">
      <tr>
        <th>Photo</th>
        <td><input class="form-control" id="photo" type="file" name="photo" required></td>
      </tr>
      <tr>
        <th>Name</th>
        <td>
          <input class="form-control" name="name" value="<#if product??>${product.name!}</#if>">
          <#if nameError??><div style="color:red;">${nameError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Description</th>
        <td>
          <textarea class="form-control" name="description"><#if product??>${product.description!}</#if></textarea>
          <#if descriptionError??><div style="color:red;">${descriptionError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Category</th>
        <td>
          <select class="form-control selectpicker" name="category" data-live-search="true">
            <#list categories as category>
              <option value="${category.id?c}"
                      <#if product?? && category == product.category>selected</#if>>${category.name}</option>
            </#list>
          </select>
        </td>
      </tr>
      <tr>
        <th>Price</th>
        <td>
          <input class="form-control" type="number" name="price"
                 value="<#if product?? && product.price??>${product.price?c}</#if>">
          <#if priceError??><div style="color:red;">${priceError}</div></#if>
        </td>
      </tr>
    </table>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-success ml-1" style="float:right;" type="submit" value="Create">
  </form>

  <a class="btn btn-info ml-1" style="float:right;" href="products">Back</a>

</@h.home>
