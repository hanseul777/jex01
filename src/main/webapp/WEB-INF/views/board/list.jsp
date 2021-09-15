<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/header.jsp"%>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">List Page</h1>
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
            <!-- Main row -->
            <div class="row">
                <!-- Left col -->
                <section class="col-lg-12">
                    <!-- TO DO List -->
                    <div class="card">
                        <div class="card-header">
                            <h3 class="card-title">Bordered Table</h3>
                            <sec:authorize access="isAuthenticated()">
                            <button><a href="/board/register">register</a> </button>
                            </sec:authorize>
                        </div>
                        <!-- /.card-header -->
                        <div class="card-body">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th style="width: 20px">BNO</th>
                                    <th>TITLE</th>
                                    <th>WRITER</th>
                                    <th>REGDATE</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${dtoList}" var="dto">
                                    <tr>
                                        <td><c:out value="${dto.bno}"></c:out></td>
                                        <td><a href="javascript:moveRead(${dto.bno})"><c:out value="${dto.title}"></c:out></a></td>
                                        <td><c:out value="${dto.writer}"></c:out></td>
                                        <td><c:out value="${dto.regDate}"></c:out></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <form action="/board/list" method="get">
                                <input type="hidden" name="page" value="1"> <!--검색버튼 누르면 무조건 1페이지로 보냄 -->
                                <input type="hidden" name="size" value="${pageMaker.size}">
                            <div class="col-sm-3">
                                <!-- select -->
                                <div class="form-group">
                                    <label>Search</label>
                                    <select name="type" class="custom-select"><!--type -->
                                        <option value="">----</option> <!--검색조건 없을 떄 -->
                                        <option value="T" ${pageRequestDTO.type=="T"?"selected":""}>제목</option>
                                        <option value="TC" ${pageRequestDTO.type=="TC"?"selected":""}>제목내용</option>
                                        <option value="C" ${pageRequestDTO.type=="C"?"selected":""}>내용</option>
                                        <option value="TCW" ${pageRequestDTO.type=="TCW"?"selected":""}>제목 내용 작성자</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-sm-9">
                                <div class="input-group input-group-sm">
                                    <input type="text" class="form-control" name="keyword" value="${pageRequestDTO.keyword}"> <!--keyword-->
                                    <span class="input-group-append"><button type="submit" class="btn btn-info btn-flat">Go!</button></span>
                                </div>
                            </div><!--버튼누리면 name=type과 name=keyword가 같이 날아가야한다. -->
                            </form>

                        </div>
                        <!-- /.card-body -->


                        <div class="card-footer clearfix">
                            <ul class="pagination pagination-sm m-0 float-right">

                                <c:if test="${pageMaker.prev}">
                                    <li class="page-item"><a class="page-link" href="javascript:movePage(${pageMaker.start -1})"> << </a></li>
                                </c:if>

                                <c:forEach begin="${pageMaker.start}" end="${pageMaker.end}" var="num">
                                    <li class="page-item ${pageMaker.page == num?'active':''}"><a class="page-link" href="javascript:movePage(${num})">${num}</a></li>
                                </c:forEach>

                                <c:if test="${pageMaker.next}">
                                    <li class="page-item"><a class="page-link" href="javascript:movePage(${pageMaker.end +1})"> >> </a></li>
                                </c:if>

                            </ul>
                        </div>
                    </div>
                    <!-- /.card -->
                </section>
                <!-- /.Left col -->
            </div>
            <!-- /.row (main row) -->
        </div><!-- /.container-fluid -->
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<div class="modal fade" id="modal-sm">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Small Modal</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>One fine body&hellip;</p>
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<form id="actionForm" action="/board/list" method="get">
    <input type="hidden" name="page" value="${pageMaker.page}">
    <input type="hidden" name="size" value="${pageMaker.size}">

    <c:if test="${pageRequestDTO.type != null}">
        <input type="hidden" name="type" value="${pageRequestDTO.type}">
        <input type="hidden" name="keyword" value="${pageRequestDTO.keyword}">
    </c:if>
</form>

<%@include file="../includes/footer.jsp"%>

<script>

    const actionForm = document.querySelector("#actionForm") //actionForm에서 page와 size를 찾아줘야 함. -> css selector사용

    //값이 있으면 const result = bno값 들어감
    //값이 없으면 const result = ''
    const result = '${result}'

    if(result && result !== ''){ //result를 받아오고 result가 공백문자가 아닐 때 (register를 실행했을 때)
        $('#modal-sm').modal('show') //모달창을 띄우는 코드(jquery코드 -> 가능하면 안쓰려고함 -> 바꾸려면 어떻게?)
        window.history.replaceState(null,'','/board/list') //뒤로가기했을 때 모달창이 다시 보이지 않도록하는 코드
    }

    function movePage(pageNum){
        //a태그 자체가 목적이 이동인데 기본기능을 다 막아줘야 한다. -> 파라미터에 event추가
        // event.preventDefault()
        // event.stopPropagation()
        // alert(pageNum) //페이지넘버 확인용
        //만들고 위에 올라가서 a태그에 movePage걸어줌

        // const pageEle = actionForm.querySelector("input[name='page']")//css selector
        // console.log(pageEle) //console창에서 9번을 눌러도 value는 설정한 값(3)으로 나오는지 확인

        actionForm.querySelector("input[name='page']").setAttribute("value",pageNum)
        //클린한 페이지번호 값으로 바꾼다 -> read에서도 기존의 페이지값을 가지고 있게한다.
        //actionForm안에 페이지와 사이즈값을 가지고 있음

        actionForm.submit() //실행

    }

    function moveRead(bno){

        actionForm.setAttribute("action","/board/read")
        actionForm.innerHTML += `<input type='hidden' name='bno' value='\${bno}'>` //총 세개의 파라미터가 전송되어야 한다.

        actionForm.submit() //실행 -> 일단 하지말고 element창에서 확인 후에 실행하기!
    }

</script>

</body>
</html>