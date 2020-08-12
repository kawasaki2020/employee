<form:form
  action="${pageContext.request.contextPath}/upload" method="post"
  modelAttribute="fileUploadForm" enctype="multipart/form-data">
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