<#import "../macros/home.ftlh" as h>

<@h.home "Order editing">

  <#if order??>
    <form method="post" action="orders/update">
      <div class="form-group row">
        <label class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="id" value="${order.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Client:</label>
        <div class="col-sm-10">
          <input type="hidden" name="client" value="${order.client.id?c}">
          ${order.client.name!} (<a target="_blank" href="clients/edit/${order.client.id?c}">view</a>)
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Date:</label>
        <div class="col-sm-10">
          <input class="form-control" type="datetime-local" name="createdDate" value="${order.createdDate!}" readonly>
          <#if createdDateError??><div class="text-warning">${createdDateError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">State:</label>
        <div class="col-sm-10">
          <select class="form-control" name="status">
            <#list order.status.values() as status>
              <option value="${status.name()}" <#if order.status.name() == status>selected</#if>>${status.value}</option>
            </#list>
          </select>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Amount:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="amount" step="any"
                 value="<#if order.amount??>${order.amount?string["0.00;; decimalSeparator='.'"]}</#if>">
          <#if amountError??><div class="text-warning">${amountError}</div></#if>
        </div>
      </div>

      <div class="mt-5">
        <h1>Order items</h1>
        <@t.table "items" />
        <table id="items" class="table table-striped table-bordered" cellspacing="0" width="100%">
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
      <button class="btn btn-success" type="submit">Update</button>
    </form>

    <form method="get" action="orders">
      <button class="btn btn-info" type="submit">Back</button>
    </form>

    <form method="post" action="orders/delete">
      <input type="hidden" name="id" value="${order.id?c}">
      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-danger" type="submit">Delete</button>
    </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This order does not exist!
    </div>
  </#if>

</@h.home>
