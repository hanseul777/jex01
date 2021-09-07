<%--
  Created by IntelliJ IDEA.
  User: hanseul
  Date: 2021/09/07
  Time: 2:41 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <input type="file" name="uploadFiles" multiple><button id="uploadBtn">UPLOAD</button>

  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>


  <script>
    document.querySelector("#uploadBtn").addEventListener("click",(e) => {

        const formData = new FormData()
        const fileInput = document.querySelector("input[name='uploadFiles']")

        for(let i = 0; i < fileInput.files.length; i++){
            formData.append("uploadFiles",fileInput.files[i]) //"uploadFiles" 이름중요
        }

        // console.dir(fileInput)
        console.log(formData)

        const headerObj = {headers:{'Content-Type': 'multipart/form-data'}} // 헤더정보(파일업로드할 때 헤더정보를 같이 보내줘야 함)

        axios.post("/upload",formData, headerObj)
    },false)

</script>
</body>
</html>
