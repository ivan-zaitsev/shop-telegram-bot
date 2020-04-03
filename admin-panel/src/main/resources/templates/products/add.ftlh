<#import "../macros/home.ftlh" as h>
<#import "../macros/fileinput.ftlh" as f>

<@h.home "Product adding">

  <@f.fileinput "photo", "" />
  <link href="static/css/bootstrap-select.css" rel="stylesheet">
  <script src="static/js/bootstrap-select.js"></script>

  <form method="post" action="products/create" enctype="multipart/form-data">
    <div class="form-group row">
      <label class="col-sm-2">Photo:</label>
      <div class="col-sm-10">
        <input class="form-control" id="photo" type="file" name="photo" required>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2">Name:</label>
      <div class="col-sm-10">
        <input class="form-control" name="name" value="<#if product??>${product.name!}</#if>">
        <#if nameError??><div class="text-warning">${nameError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2">Description:</label>
      <div class="col-sm-10">
        <textarea class="form-control" name="description" rows="4"><#if product??>${product.description!}</#if></textarea>
        <#if descriptionError??><div class="text-warning">${descriptionError}</div></#if>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2">Category:</label>
      <div class="col-sm-10">
        <select class="form-control selectpicker" name="category" data-live-search="true" data-size="4">
          <#list categories as category>
            <option value="${category.id?c}"
                    <#if product?? && category == product.category>selected</#if>>${category.name}</option>
          </#list>
        </select>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2">Price:</label>
      <div class="col-sm-10">
        <input class="form-control" type="number" name="price" step="any"
               value="<#if product?? && product.price??>${product.price?string["0.00;; decimalSeparator='.'"]}</#if>">
        <#if priceError??><div class="text-warning">${priceError}</div></#if>
      </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button class="btn btn-success" type="submit">Create</button>
  </form>

  <form method="get" action="products">
    <button class="btn btn-info" type="submit">Back</button>
  </form>

</@h.home>
