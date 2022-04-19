// Call the dataTables jQuery plugin
$(document).ready(function() {
  //$('#dataTable').DataTable();
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
       console.log(this.DataTable().page.info())
      }
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
});
