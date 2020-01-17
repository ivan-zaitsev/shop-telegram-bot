<#import "../macros/home.ftlh" as h>
<#import "../macros/fileinput.ftlh" as f>

<@h.home "Product editing">

  <#if product??>
  <@f.fileinput "photo", "${product.photoUrl!}" />
  <link href="static/css/bootstrap-select.css" rel="stylesheet" />
  <script src="static/js/bootstrap-select.js"></script>

  <form method="post" action="products/update" enctype="multipart/form-data">
    <table class="table table-striped table-bordered" cellspacing="0" width="100%">
      <tr>
        <th>Id</th>
        <td><input type="number" class="form-control" name="id" value="${product.id?c}" readonly></td>
      </tr>
      <tr>
        <th>Photo</th>
        <input type="hidden" name="photoUrl" value="${product.photoUrl!}">
        <td><input class="form-control" id="photo" type="file" name="photo"></td>
      </tr>
      <tr>
        <th>Name</th>
        <td>
          <input class="form-control" name="name" value="${product.name!}">
          <#if nameError??><div style="color:red;">${nameError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Description</th>
        <td>
          <textarea class="form-control" name="description">${product.description!}</textarea>
          <#if descriptionError??><div style="color:red;">${descriptionError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Category</th>
        <td>
          <select class="form-control selectpicker" name="category" data-live-search="true">
            <#if !product.category??><option selected>is absent</option></#if>
            <#list categories as category>
              <option value="${category.id?c}"
                      <#if product.category?? && category == product.category>selected</#if>>${category.name}</option>
            </#list>
          </select>
        </td>
      </tr>
      <tr>
        <th>Price</th>
        <td>
          <input class="form-control" type="number" name="price" value="<#if product.price??>${product.price?c}</#if>">
          <#if priceError??><div style="color:red;">${priceError}</div></#if>
        </td>
      </tr>
    </table>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-success ml-1" style="float:right;" type="submit" value="Update">
  </form>

  <a class="btn btn-info ml-1" style="float:right;" href="products">Back</a>

  <form method="post" action="products/delete">
    <input type="hidden" name="id" value="${product.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-danger" style="float:right;" type="submit" value="Delete">
  </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This product does not exist!
    </div>
  </#if>

</@h.home>
