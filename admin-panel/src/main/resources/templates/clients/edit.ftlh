<#import "../macros/home.ftlh" as h>

<@h.home "Client editing">

  <#if client??>
  <form method="post" action="clients/update">
    <table class="table table-striped table-bordered" cellspacing="0" width="100%">
      <tr>
        <th>Id</th>
        <td><input type="number" class="form-control" name="id" value="${client.id?c}" readonly></td>
      </tr>
      <tr>
        <th>Chat id</th>
        <td><input type="number" class="form-control" name="chatId" value="${client.chatId?c}" readonly></td>
      </tr>
      <tr>
        <th>Name</th>
        <td>
          <input class="form-control" name="name" value="${client.name!}">
          <#if nameError??><div style="color:red;">${nameError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Phone number</th>
        <td>
          <input class="form-control" name="phoneNumber" value="${client.phoneNumber!}">
          <#if phoneNumberError??><div style="color:red;">${phoneNumberError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>City</th>
        <td>
          <input class="form-control" name="city" value="${client.city!}">
          <#if cityError??><div style="color:red;">${cityError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>Address</th>
        <td>
          <input class="form-control" name="address" value="${client.address!}">
          <#if addressError??><div style="color:red;">${addressError}</div></#if>
        </td>
      </tr>
    </table>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-success ml-1" style="float:right;" type="submit" value="Update">
  </form>

  <a class="btn btn-info ml-1" style="float:right;" href="clients">Back</a>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This client does not exist!
    </div>
  </#if>

</@h.home>
