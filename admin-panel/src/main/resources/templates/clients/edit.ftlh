<#import "../macros/home.ftlh" as h>

<@h.home "Client editing">

  <#if client??>
    <form method="post" action="clients/update">
      <div class="form-group row">
        <label class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="id" value="${client.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Chat id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="chatId" value="${client.chatId?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Name:</label>
        <div class="col-sm-10">
          <input class="form-control" name="name" value="${client.name!}">
          <#if nameError??><div class="text-warning">${nameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Phone number:</label>
        <div class="col-sm-10">
          <input class="form-control" name="phoneNumber" value="${client.phoneNumber!}">
          <#if phoneNumberError??><div class="text-warning">${phoneNumberError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">City:</label>
        <div class="col-sm-10">
          <input class="form-control" name="city" value="${client.city!}">
          <#if cityError??><div class="text-warning">${cityError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Address:</label>
        <div class="col-sm-10">
          <input class="form-control" name="address" value="${client.address!}">
          <#if addressError??><div class="text-warning">${addressError}</div></#if>
        </div>
      </div>

      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-success" type="submit">Update</button>
    </form>

    <form method="get" action="orders">
      <button class="btn btn-info" type="submit">Back</button>
    </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This client does not exist!
    </div>
  </#if>

</@h.home>
