<#import "../../common/home.ftlh" as h>
<#import "../../common/table.ftlh" as t>

<@h.home "Orders list">

  <@t.table "orders" />
  <table id="orders" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>Id</th>
        <th>Client</th>
        <th>Date</th>
        <th>Status</th>
        <th>Amount</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <#list orders as order>
        <tr>
          <td>${order.id?c}</td>
          <td>${order.client.name!} (<a target="_blank" href="clients/edit/${order.client.id?c}">view</a>)</td>
          <td>${order.createdDate.format('dd.MM.yyyy HH:mm')}</td>
          <td>${order.status.value}</td>
          <td>${order.amount} $</td>
          <td>
            <a class="btn btn-xs btn-info" href="orders/edit/${order.id?c}">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </a>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

</@h.home>
