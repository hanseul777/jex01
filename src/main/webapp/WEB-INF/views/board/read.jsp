<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/header.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">Read Page</h1>
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
                            <h3 class="card-title">Board Read</h3>
                        </div>
                        <!-- /.card-header -->
                        <div class="card-body">
                            <div class="form-group">
                                <label for="exampleInputEmail0">BNO</label>
                                <input type="text" name="bno" class="form-control" id="exampleInputEmail0" placeholder="Enter title" value="<c:out value="${boardDTO.bno}"></c:out> " readonly>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">TITLE</label>
                                <input type="text" name="title" class="form-control" id="exampleInputEmail1" placeholder="Enter title" value="<c:out value="${boardDTO.title}"></c:out> " readonly>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail2">Writer</label>
                                <input type="text" name="writer" class="form-control" id="exampleInputEmail2" placeholder="Enter writer" value="<c:out value="${boardDTO.writer}"></c:out>" readonly>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <!-- textarea -->
                                    <div class="form-group">
                                        <label>Textarea</label>
                                        <textarea name="content" class="form-control" rows="3" disabled><c:out value="${boardDTO.content}"></c:out>
                                        </textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-default btnList">LIST</button>
                            <button type="button" class="btn btn-info btnMod">MODIFY</button>
                        </div>
                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
    </section>
</div>

<form id="actionForm" action="/board/list" method="get">
    <input type="hidden" name="page" value="${pageRequestDTO.page}">
    <input type="hidden" name="size" value="${pageRequestDTO.size}">

    <c:if test="${pageRequestDTO.type != null}">
        <input type="hidden" name="type" value="${pageRequestDTO.type}">
        <input type="hidden" name="keyword" value="${pageRequestDTO.keyword}">
    </c:if>
</form>
<%@include file="../includes/footer.jsp"%>

<script>
    const actionForm = document.querySelector("#actionForm")

    document.querySelector(".btnList").addEventListener("click",()=>{actionForm.submit()},false)

    document.querySelector(".btnMod").addEventListener("click",()=>{

        const bno = '${boardDTO.bno}'

        actionForm.setAttribute("action","/board/modify")
        actionForm.innerHTML += `<input type='hidden' name='bno' value='\${bno}'>`

        actionForm.submit()
    },false)

</script>

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="/resources/js/reply.js"></script>

<script>

    function after(result){
        console.log("after..................")
        console.log("result",result) // .js에서 fn(arr)으로 받아온 결과를 확인한다.
    }

    // console.log(doA()) //doA()호출했으니까 read.jsp에 있는 이게 먼저 출력하고 -> promise만 먼저 반환해줌 (thread.sleep을 찍어놔서 나중에 반환된 값이 나중에 찍힘)
    // doA().then(result => console.log(result)) // 제대로 결과값이 나오려면 결국 Then을 사용해야 한다.

    // doB(after) //위의 after함수를 객체로 받아서 (괄호없이) 파라미터로 전달

    // const reply = {bno:201, replyer:'user00', reply:'12314839471897'}
    // doC(reply).then(result => console.log(result))

    // doD(112).then(result => console.log(result))

    const reply = {rno:112, reply:"Update reply text........."} // 댓글 112번에 입력할 내용
    doE(reply).then(result => console.log(result))//위에 입력한 reply 호출


</script>
</body>
</html>