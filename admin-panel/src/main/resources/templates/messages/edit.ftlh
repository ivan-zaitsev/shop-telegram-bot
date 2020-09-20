<#import "../macros/home.ftlh" as h>

<@h.home "Message editing">

  <#if message??>
    <form method="post" action="messages/update">
      <div class="form-group row">
        <label class="col-sm-2">Id:</label>
        <div class="col-sm-10">
          <input class="form-control" type="number" name="id" value="${message.id?c}" readonly>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Name:</label>
        <div class="col-sm-10">
          <input class="form-control" name="name" value="${message.name!}" readonly>
          <#if messageNameError??><div class="text-warning">${messageNameError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Description:</label>
        <div class="col-sm-10">
          <input class="form-control" name="description" value="${message.description!}" readonly>
          <#if messageDescriptionError??><div class="text-warning">${messageDescriptionError}</div></#if>
        </div>
      </div>

      <div class="form-group row">
        <label class="col-sm-2">Text:</label>
        <div class="col-sm-10">
          <textarea class="form-control" name="text" rows="8">${message.text!}</textarea>
          <#if messageTextError??><div class="text-warning">${messageTextError}</div></#if>
        </div>
      </div>

      <input type="hidden" name="_csrf" value="${_csrf.token}">
      <button class="btn btn-success" type="submit">Update</button>
    </form>

    <form method="get" action="messages">
      <button class="btn btn-info" type="submit">Back</button>
    </form>

  <#else>
    <div class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Error</h4>
      <hr>This message does not exist!
    </div>
  </#if>

</@h.home>
