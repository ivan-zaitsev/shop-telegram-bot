<#import "../../common/home.ftlh" as h>

<@h.home "Category editing">

  <#if category??>
    <form method="post" action="categories/update">
      <div class="form-group row">
        <label class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="id" value="${category.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Name:</label>
        <div class="col-sm-10">
          <input class="form-control" name="name" value="${category.name!}">
          <#if nameError??><div class="text-warning">${nameError}</div></#if>
        </div>
      </div>

      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-success" type="submit">Update</button>
    </form>

    <form method="get" action="categories">
      <button class="btn btn-info" type="submit">Back</button>
    </form>

    <form method="post" action="categories/delete">
      <input type="hidden" name="id" value="${category.id?c}">
      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-danger" type="submit">Delete</button>
    </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This category does not exist!
    </div>
  </#if>

</@h.home>
