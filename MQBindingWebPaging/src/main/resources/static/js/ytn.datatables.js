/**
 * Get Data Ajax.
 * @author : mattmk
**/
 function getDataAjax(dataTableName){

	var table = dataTableName.dataTable({
		//반응형 설정
		responsive: true,
		scrollY: "950px",
		//scrollCollapse: true,
		//검색란
		searching: false,
		//페이지 당 글 개수 설정
		pageLength: 50,
		//페이징 표시 설정
		paging: true,
		bPaginate: true,
		//show entries
		bLengthChange: true,
		lengthMenu : [ [ 10, 30, 50, 100], [ 10, 30, 50, 100]],
		bAutoWidth: true,
		processing: true,
		//글 순서 설정
		ordering: false,
		bServerSide: true,
		serverSide: true,
		ajax : {
			"type"   : "POST",
			"url"    : "/ytn/captions/data/getList",
			data : function(d) {
				// 페이지 번호, DataTable().page.info().page은 0임
	            d.page =dataTableName.DataTable().page.info().page + 1;
				// 페이지 사이즈, 한 페이지에 몇개의 row인지
	            d.pageSize =dataTableName.DataTable().page.len();
				d.searchText = $('#searchText').val();
				d.strDateTime = getServerFormatDate(strDateTime.datetimepicker('getDate'), 'str');
				d.endDateTime = getServerFormatDate(endDateTime.datetimepicker('getDate'), 'end');

				// 정렬조건 컬럼명
	            //d.orderBy = orderColumn[$('#table').DataTable().order()[0][0]];
				// 오름 또는 
	            //d.orderCondition = $('#table').DataTable().order()[0][1];
	        },
		},
		columns : [
			{data: "rowIdx"},
			{data: "context",
				render: function (data, type, row, meta) {
					if(authBoolean){
						return '<a href="javascript:void(0);" onclick="editPopupShow(`'+meta.row+'`)">' + data + '</a>';
					}

					return data;
				},
			},
			{data: "mqInsDate"},
			{data: "updUserid"},
			{data: "updDate"},
			{data: "captionIdx"}
		],
		"columnDefs":[
			{
				"targets":[0],
				"searchable":false,
				"orderable": false
			},
			{
				"targets":[5],
				"searchable":false,
				"visible":false
			}
		],
		oLanguage: {
			//sSearch: "내용 검색: ",
			sInfo: "_START_ to _END_ / 전체 데이터 수: _TOTAL_개",
			sLengthMenu: "_MENU_ 개씩 보기",
			sZeroRecords: '해당 데이터가 없습니다.',
			sProcessing: '조회중 ...',
			oPaginate:{
				sNext: '다음페이지',
				sPrevious: '이전페이지'
			}
		}
	});
}


/**
* reload function
* @author : mattmk
**/
function reloadDataTable(targetDataTableParam){
	var table = targetDataTableParam.DataTable();
	table.ajax.reload();
}
