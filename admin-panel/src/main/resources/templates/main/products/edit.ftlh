<#import "../../common/home.ftlh" as h>
<#import "../../common/fileinput.ftlh" as f>

<@h.home "Product editing">

  <#if product??>
    <@f.fileinput "photo", "${product.photoUrl!}" />
    <link href="static/css/bootstrap-select.css" rel="stylesheet">
    <script src="static/js/bootstrap-select.js"></script>

    <form method="post" action="products/update" enctype="multipart/form-data">
      <div class="form-group row">
        <label class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="id" value="${product.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Photo:</label>
        <div class="col-sm-10">
          <input type="hidden" name="photoUrl" value="${product.photoUrl!}">
          <input class="form-control" id="photo" type="file" name="photo">
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Name:</label>
        <div class="col-sm-10">
          <input class="form-control" name="name" value="${product.name!}">
          <#if nameError??><div class="text-warning">${nameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Description:</label>
        <div class="col-sm-10">
          <textarea class="form-control" name="description" rows="4">${product.description!}</textarea>
          <#if descriptionError??><div class="text-warning">${descriptionError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Category:</label>
        <div class="col-sm-10">
          <select class="form-control selectpicker" name="category" data-live-search="true" data-size="4">
            <#if !product.category??><option selected>—</option></#if>
            <#list categories as category>
              <option value="${category.id?c}"
                      <#if product.category?? && category == product.category>selected</#if>>${category.name}</option>
            </#list>
          </select>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Price:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="price" value="<#if product.price??>${product.price?c}</#if>">
          <#if priceError??><div class="text-warning">${priceError}</div></#if>
        </div>
      </div>

      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-success" type="submit">Update</button>
    </form>

    <form method="get" action="products">
      <button class="btn btn-info" type="submit">Back</button>
    </form>

    <form method="post" action="products/delete">
      <input type="hidden" name="id" value="${product.id?c}">
      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-danger" type="submit">Delete</button>
    </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This product does not exist!
    </div>
  </#if>

</@h.home>
