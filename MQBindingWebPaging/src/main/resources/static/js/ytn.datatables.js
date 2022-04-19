// Call the dataTables jQuery plugin
$(document).ready(function() {
    var columns = ["NO", "TITLE"];
            
            $('#userTable').dataTable({
                //반응형 설정
                responsive: true,
                //검색란
                searching: true,
                //페이지 당 글 개수 설정
                pageLength: 50,
                //페이징 표시 설정
                paging: true,
                bPaginate: true,
                bLengthChange: true,
                lengthMenu : [ [ 10, 30, 50, 100], [ 10, 30, 50, 100] ],
                bAutoWidth: false,
                processing: true,
                //글 순서 설정
                ordering: true,
                bServerSide: true,
                /*
                ajax : {
                    "url":"/mq/getList",
                    "type":"POST",
                    "data": function (d) {
                        d.userStatCd = "NR";
                    }
                },
                */
                sAjaxSource : "/mq/getList",
                sServerMethod: "POST",
                columns : [
                    {data: "no"},
                    {data: "title"}
                ]
            });

    /*
    alert('test');
  //이전에 만든 것 없애고 다시 그리기 위함.
  $("#dataTable").DataTable().destroy(); 
  $('#dataTable').DataTable({
    responsive: false,  //반응형 설정
    pageLength: 10,     //페이지 당 글 개수 설정
    autoWidth: false,
    destroy: true,
    processing: true,
    serverSide: false,
    searching: false,    //검색란 표시 설정
    ordering: true,      //글 순서 설정
    paging: true,        //페이징 표시 설정
    dom: "Blfrtip",      //버튼 dom 설정 l을 추가하면 pagelength 드롭다운 표시
    buttons: [
      {
        extend: "excel",
        text: "엑셀 다운로드",
        filename: function () {
          if ($("#file1").val() === "a1") {
            return "_"+$("#file1").val();
          } else {
            return "file2";
          }
        },

        customize: function (xlsx) {
        //엑셀 셀 커스텀
          var sheet = xlsx.xl.worksheets["sheet1.xml"];
          $("c[r=B2] t", sheet).text("custom text1");
          $("c[r=C2] t", sheet).text("custom text2");

        },
      },
    ],
    language: {
      emptyTable: "데이터가 없습니다.",
      lengthMenu: "페이지당 _MENU_ 개씩 보기",
      info: "현재 _START_ - _END_ / _TOTAL_건",
      infoEmpty: "데이터 없음",
      infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
      search: "",
      zeroRecords: "일치하는 데이터가 없습니다.",
      loadingRecords: "로딩중...",
      processing: "잠시만 기다려 주세요.",
      paginate: {
        next: "다음",
        previous: "이전",
      },
    },
    data: '',
    columns: [
      { data: "data1" },
      { data: "data2" },
      { data: "data3" },
      { data: "data4" },
    ],
    columnDefs: [
      {//0번컬럼 설정
        targets: 0,
        orderable : false,
        render: function (data) {
          if ($("#searchType").val() === "day") {
            return data + "시";
          } else {
            return data;
          }
        },
      },
      {//1번,2번컬럼 설정
        targets: [1,2],
        visible: false,
        orderable : false,
        render: function (data) {
            return data;      
        },
      },
      {//모든 컬럼 설정
        targets: "_all",
        render: function (data, type, full, meta) {
            console.log(data); // 데이터중 해당 열, 행에 들어갈 data
          console.log(type); 
          console.log(full); // 데이터중 해당 행에 들어갈 full data
          console.log(meta); // 해당 셀의 row, col 번호
        },
      },
    ],
  });
  */
  /*
  $('#dataTable').DataTable({
    // "scrollY" : 300,
    // "scrollCollapse" : true, // 일정높이 이상 시 Y스크롤 추가
    "paging" : true,
    "searching" : false,
    "info" : true,
    "autoWidth" : false,
    "responsive" : true,
    "lengthChange" : true,          // 페이지 조회 시 row를 변경할 것인지
    // "pageLength" : 15,           // lengthChange가 false인 경우 조회 row 수
    "lengthMenu" : [ 10, 20, 50 ],  // lengthChange가 true인 경우 선택할 수 있는 값 설정
    "ordering" : true,
    "columns" : [ {                 // 테이블에 맵핑할 리턴 파라미터명 순서
        "data" : "grpCd"
    }, {
        "data" : "grpNm"
    }, {
        "data" : "useYn"
    }, {
        "data" : "delYn"
    }, {
        "data" : "crtDt"
    } ],
    "processing" : true,
    "serverSide" : true,        // serverside 사용 여부
    "ajax" : {
        url : "/ajax/getPageList",
        data : function(d) {
      console.log('d ---> ', d);
            // d.page = $('#table').DataTable().page.info().page + 1;    // 페이지 번호, DataTable().page.info().page은 0임
            // d.pageSize = $('#table').DataTable().page.len();            // 페이지 사이즈, 한 페이지에 몇개의 row인지
            // d.orderBy = orderColumn[$('#table').DataTable().order()[0][0]];        // 정렬조건 컬럼명
            // d.orderCondition = $('#table').DataTable().order()[0][1];            // 오름 또는 
        },
    },
    "fnDrawCallback": function (oSettings) {
  console.log('oSettings ----> ', oSettings);
       console.log(this.DataTable().page.info());
      },
// "dom" : 'Bfrtip',        // 버튼 추가
// "buttons" : [
// "copy"
// , {
// extend : 'csv',
// charset : 'UTF-8',
// bom : true,
// }
// , "excel"
// , "pdf"
// , "print"
// , "colvis"
// ],
});
*/

});
