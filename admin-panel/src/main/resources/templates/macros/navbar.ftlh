<#include "security.ftlh">

<nav class="navbar navbar-default">
  <div class="container-fluid">

    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed"
              data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Admin panel</a>
    </div>

    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li><a href="clients"><i class="fa fa-users mr-1"></i>Clients</a></li>
        <li><a href="categories"><i class="fa fa-folder-open mr-1"></i>Categories</a></li>
        <li><a href="products"><i class="fa fa-archive mr-1"></i>Products</a></li>
        <li><a href="orders"><i class="fa fa-cart-arrow-down mr-1"></i>Orders</a></li>
        <li><a href="broadcasts/add"><i class="fa fa-comment mr-1"></i>Broadcasts</a></li>

        <#if isAdmin>
          <li><a href="messages"><i class="fa fa-envelope mr-1"></i>Messages</a></li>
          <li><a href="users"><i class="fa fa-user-secret mr-1"></i>Users</a></li>
        </#if>
      </ul>

      <div class="navbar-text pull-right">
        <form action="logout" method="post">
          <input type="hidden" name="_csrf" value="${_csrf.token}" />
          <input type="submit" class="btn btn-xs btn-warning" value="Log out"/>
        </form>
      </div>

      <div class="navbar-text pull-right">
        <i>${username}</i>
      </div>
    </div>

  </div>
</nav>
