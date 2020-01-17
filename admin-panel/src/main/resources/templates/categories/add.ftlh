<#import "../macros/home.ftlh" as h>

<@h.home "Category adding">

  <form method="post" action="categories/create">
    <table class="table table-striped table-bordered" cellspacing="0" width="100%">
      <tr>
        <th>Name</th>
        <td>
          <input class="form-control" name="name" value="<#if category??>${category.name!}</#if>">
          <#if nameError??><div style="color:red;">${nameError}</div></#if>
        </td>
      </tr>
    </table>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-success ml-1" style="float:right;" type="submit" value="Create">
  </form>

  <a class="btn btn-info ml-1" style="float:right;" href="categories">Back</a>

</@h.home>
