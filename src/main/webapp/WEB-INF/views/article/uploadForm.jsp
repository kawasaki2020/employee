<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo List</title>
<style type="text/css">
.strike {
    text-decoration: line-through;
}
</style>
</head>
<body>
    <h1>Todo List</h1>
    <div id="todoForm">
        <!-- (1) -->
        <t:messagesPanel />

        <!-- (2) -->
<form:form
  action="${pageContext.request.contextPath}/upload" method="post"
  modelAttribute="fileUploadForm" enctype="multipart/form-data">
  <form:errors path="*" element="div" cssClass="error-message-list" />
  <table>
    <tr>
      <th width="35%">File to upload</th>
      <td width="65%">
        <form:input type="file" path="file" />
        <form:errors path="file" />
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><form:button>Upload</form:button></td>
    </tr>
  </table>
</form:form>
    </div>
    <hr />
</body>
</html>