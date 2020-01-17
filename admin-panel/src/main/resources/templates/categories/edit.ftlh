<#import "../macros/home.ftlh" as h>

<@h.home "Category editing">

  <#if category??>
  <form method="post" action="categories/update">
    <table class="table table-striped table-bordered" cellspacing="0" width="100%">
      <tr>
        <th>Id</th>
        <td><input type="number" class="form-control" name="id" value="${category.id?c}" readonly></td>
      </tr>
      <tr>
        <th>Name</th>
        <td>
          <input class="form-control" name="name" value="${category.name!}">
          <#if nameError??><div style="color:red;">${nameError}</div></#if>
        </td>
      </tr>
    </table>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-success ml-1" style="float:right;" type="submit" value="Update">
  </form>

  <a class="btn btn-info ml-1" style="float:right;" href="categories">Back</a>

  <form method="post" action="categories/delete">
    <input type="hidden" name="id" value="${category.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-danger" style="float:right;" type="submit" value="Delete">
  </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This category does not exist!
    </div>
  </#if>

</@h.home>
