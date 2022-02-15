<#import "../../../common/table.ftlh" as t>

<div class="mt-5">
    <h1>Order items</h1>
    <@t.table "items" />
    <table id="items" class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>Name</th>
            <th>Quantity</th>
            <th>Product price</th>
            <th>Total price</th>
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
                <td>${item.totalPrice} $</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
