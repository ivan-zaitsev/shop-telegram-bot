<#import "../macros/home.ftlh" as h>
<#import "../macros/table.ftlh" as t>

<@h.home "Orders list">

  <@t.table "orders" />
  <table id="orders" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Id</th>
        <th>Client</th>
        <th>Date</th>
        <th>State</th>
        <th>Amount</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <#list orders as order>
        <tr>
          <td>${order.id?c}</td>
          <td>${order.client.name!} (<a target="_blank" href="clients/edit/${order.client.id?c}">view</a>)</td>
          <td>${formatDateTime(order.createdDate, 'dd.MM.yyyy HH:mm')}</td>
          <td>${order.state.value}</td>
          <td>${order.amount} $</td>
          <td>
            <a class="btn btn-xs btn-info command-edit" href="orders/edit/${order.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
