<#import "../macros/home.ftlh" as h>

<@h.home "Order editing">

  <#if order??>
  <form method="post" action="orders/update">
    <table class="table table-striped table-bordered" cellspacing="0" width="100%">
      <tr>
        <th>Id</th>
        <td><input type="number" class="form-control" name="id" value="${order.id?c}" readonly></td>
      </tr>
      <tr>
        <th>Client</th>
        <input type="hidden" name="client" value="${order.client.id?c}">
        <td>${order.client.name!} (<a target="_blank" href="clients/edit/${order.client.id?c}">view</a>)<br></td>
      </tr>
      <tr>
        <th>Date</th>
        <td>
          <input class="form-control" type="datetime-local" name="createdDate" value="${order.createdDate!}">
          <#if createdDateError??><div style="color:red;">${createdDateError}</div></#if>
        </td>
      </tr>
      <tr>
        <th>State</th>
        <td>
          <select class="form-control" name="state">
            <#list orderStates as state>
              <option value="${state.id?c}" <#if state == order.state>selected</#if>>${state.state}</option>
            </#list>
          </select>
        </td>
      </tr>
      <tr>
        <th>Amount</th>
        <td>
          <input class="form-control" type="number" name="amount" value="<#if order.amount??>${order.amount?c}</#if>">
          <#if amountError??><div style="color:red;">${amountError}</div></#if>
        </td>
      </tr>
    </table>

    <div class="mt-5">
      <h1>Order items</h1>
      <table class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
          <tr>
            <th>Name</th>
            <th>Quantity</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          <#list order.items as item>
            <input type="hidden" name="items[${item?index}].id" value="${item.id?c}">
            <input type="hidden" name="items[${item?index}].order" value="${order.id?c}">
            <#if item.product??>
              <input type="hidden" name="items[${item?index}].product" value="${item.product.id?c}">
            </#if>
            <input type="hidden" name="items[${item?index}].quantity" value="${item.quantity?c}">
            <input type="hidden" name="items[${item?index}].productName" value="${item.productName}">
            <input type="hidden" name="items[${item?index}].productPrice" value="${item.productPrice?c}">
            <tr>
              <td>${item.productName}</td>
              <td>${item.quantity?c} pcs.</td>
              <td>${item.productPrice} $</td>
            </tr>
          </#list>
        </tbody>
      </table>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-success ml-1" style="float:right;" type="submit" value="Update">
  </form>

  <a class="btn btn-info ml-1" style="float:right;" href="orders">Back</a>

  <form method="post" action="orders/delete">
    <input type="hidden" name="id" value="${order.id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input class="btn btn-danger" style="float:right;" type="submit" value="Delete">
  </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This order does not exist!
    </div>
  </#if>

</@h.home>
