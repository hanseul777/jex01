<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/header.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">Register Page</h1>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Dashboard v1</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <!-- left column -->
                <div class="col-md-12">
                    <!-- general form elements -->
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Board Register</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                        <form id="form1" action="/board/register" method="post">
                            <div class="card-body">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">Title</label>
                                    <input type="text" name="title" class="form-control" id="exampleInputEmail1" placeholder="Enter title">
                                </div>
                                <div class="form-group">
                                <label for="exampleInputEmail2">Writer</label>
                                <input type="text" name="writer" class="form-control" id="exampleInputEmail2" placeholder="Enter writer">
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <!-- textarea -->
                                        <div class="form-group">
                                            <label>Textarea</label>
                                            <textarea name="content" class="form-control" rows="3" placeholder="Enter ..."></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /.card-body -->

                            <div class="temp"></div>

                            <div class="card-footer">
                                <button type="submit" id="submitBtn" class="btn btn-primary">Submit</button>
                            </div>
                        </form>

                        <style>
                            .uploadResult{
                                display: flex;
                                justify-content: center;
                                flex-direction: row;
                            }
                        </style>

                        <label for="exampleInputFile">File input</label>
                        <div class="input-group">
                            <div class="custom-file">
                                <input type="file" name="uploadFiles" class="custom-file-input" id="exampleInputFile" multiple>
                                <label class="custom-file-label" for="exampleInputFile">Choose file</label>
                            </div>
                            <div class="input-group-append">
                                <span class="input-group-text" id="uploadBtn">Upload</span>
                            </div>
                        </div>
                        <div class="uploadResult">

                        </div>

                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="../includes/footer.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>


<script>
    const uploadResultDiv = document.querySelector(".uploadResult")

    document.querySelector("#uploadBtn").addEventListener("click",(e) => {

        const formData = new FormData()
        const fileInput = document.querySelector("input[name='uploadFiles']")

        for(let i = 0; i < fileInput.files.length; i++){
            formData.append("uploadFiles",fileInput.files[i]) //"uploadFiles" 이름중요
        }

        // console.dir(fileInput)
        console.log(formData)

        const headerObj = {headers:{'Content-Type': 'multipart/form-data'}} // 헤더정보(파일업로드할 때 헤더정보를 같이 보내줘야 함)

        axios.post("/upload",formData, headerObj).then((response) => {
            const arr = response.data
            console.log(arr)
            // return arr

            let str = ""
            for(let i = 0; i <arr.length; i++){
                const {uuid, fileName, uploadPath, image, thumbnail, fileLink} = {...arr[i]}

                if(image){
                    str += `<div data-uuid='\${uuid}' data-filename='\${fileName}' data-uploadpath='\${uploadPath}' data-image='\${image}'><img src='/viewFile?file=\${thumbnail}'/><span>\${fileName}</span>
                            <button onclick="javascript:removeFile('\${fileLink}',this)">X</button></div>`
                }else {
                    str += `<div data-uuid='\${uuid}'data-filename='\${fileName}' data-uploadpath='\${uploadPath}' data-image='\${image}'><a href='/downFile?file=\${fileLink}'>\${fileName}</a>
                            <button onclick="javascript:removeFile('\${fileLink}',this)">X</button></div>`
                }
            }
            //기존을 유지하면서 str을 추가한다
            uploadResultDiv.innerHTML += str
        })

    },false)

    function removeFile(fileLink,ele){
        console.log(fileLink)
        axios.post("/removeFile",{fileName:fileLink}).then(response => {
            const targetDiv = ele.parentElement
            targetDiv.remove()
        })
    }

    document.querySelector("#submitBtn").addEventListener("click",(e)=>{
        e.stopPropagation()
        e.preventDefault() //submit자동으로 일어나지 않도록 막아준다
        //현재 화면에 있는 파일 정보를 hidden태그들로 변화시켜준다.
        const form1 = document.querySelector("#form1")
        const fileDivArr = uploadResultDiv.querySelectorAll("div") //배열의 크기 = 첨부파일의 크기

        if(!fileDivArr){
            form1.submit()
            return; // 없으면 submit만 날려주고 끝난다.
        }

        let str=""
        for(let i = 0; i < fileDivArr.length; i++){
            const target = fileDivArr[i]
            const uuid = target.getAttribute("data-uuid")//uuid 찾아주기
            const fileName = target.getAttribute("data-filename")
            const uploadPath = target.getAttribute("data-uploadpath")
            const image = target.getAttribute("data-image") //위쪽 if문에서 다 찾아놨음

            str += `<input type='hidden' name='files[\${i}].uuid' value='\${uuid}'>`
            str += `<input type='hidden' name='files[\${i}].fileName' value='\${fileName}'>`
            str += `<input type='hidden' name='files[\${i}].uploadPath' value='\${uploadPath}'>`
            str += `<input type='hidden' name='files[\${i}].image' value='\${image}'>` // hidden태그로 만들어주기
        }

        document.querySelector(".temp").innerHTML += str
        form1.submit() //등록작업 끝 -> submit누르면
        //form을 submit해준다.
    },false)

</script>

</body>
</html>