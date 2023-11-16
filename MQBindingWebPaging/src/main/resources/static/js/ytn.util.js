/**
	util
	@author : mattmk
*/

var clickCnt=0;
var targetCnt=0;

/**
 * 공통 Cookie 삭제.
 * @author : mattmk
 */
 function cookieDelAll(){
	$.removeCookie('leftMenuDept');
}

/**
 * 로그아웃 이벤트 함수.
 * @author : mattmk
 */
$('#logoutBtn').on('click', function () {
	// 공통 Cookie 삭제함수 호출.
	cookieDelAll();
});

/*
function leftMenuSet(){

	$('.nav-link').on('click', function() {
		mktestvar = $(this);
		alert("mktest");
		alert($(this).index());
		//alert($(this).data('leftmenuidx'));
	});
}
*/

/**
 * leftMenuDept Cookie Setting
 * @author : mattmk
*/
 var $leftMenuIdx = $('.nav-item').on('click', function() {
 	var idx = $leftMenuIdx.index(this);
 	$.cookie('leftMenuDept',idx);
 });

 /**
 * 시간, 분 2자리 변환.
 * @author : mattmk
 **/
  function dateFormatTime(paramDateTime) {
	return paramDateTime >= 10 ? paramDateTime.toString() : '0' + paramDateTime.toString();
}

/**
 * Server DateTime Format Convert.
 * @author : mattmk
 **/
function getServerFormatDate(paramDate, targetType) {
	if(paramDate == ''){
		return '';
	}
	
	var year = paramDate.getFullYear().toString();
	var month = dateFormatTime(paramDate.getMonth()+1);
	var day = dateFormatTime(paramDate.getDate());
	var hour = dateFormatTime(paramDate.getHours());

	if(targetType == 'str'){
		hour += '0000000';
	}else if(targetType == 'end'){
		hour += '5999999';
	}


	return year+month+day+hour;
}

/**
 * Left Menu Index Active.
 * @author : mattmk
 */
function activeLeftMenuIdx(paramIdx){
	$('.nav-item').eq(paramIdx).addClass('active');
}

/**
 * Login Click.
 * @author : mattmk
 */
function loginClick(){
	location.replace('/ytn/member/login');
}

/**
 * Login Ajax Call.
 * @author : mattmk
 */
function loginChk(userIdTemp, pwdTemp){
	if(userIdTemp != '' && pwdTemp != ''){
		$.ajax({
			type: "POST",
			url: "/ytn/member/auth/login", 
			data: {
					userId : userIdTemp,
					userPwd : pwdTemp
			},  
			dataType : "json",
			success : function(data){
				if(data == 700){
					alert('아이디 또는 패스워드가 일치하지 않습니다.');
					return false;
				}
	
				location.replace('/ytn/captions/list');
			},
			error:function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				alert("관리자에게 문의해 주세요.");
			}
		});
	}
}

/**
 * logout Click.
 * @author : mattmk
 */
 function logoutClick(){
	$.ajax({
		type: "POST",
		url: "/ytn/member/auth/logout", 
		data: {},  
		dataType : "json",
		success : function(data){
			if(data){
				alert("로그아웃 되었습니다.");
				location.replace('/ytn/captions/list');
			}else{
				alert("관리자에게 문의해 주세요.");
			}
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			alert("관리자에게 문의해 주세요.");
		}
	});
}

/**
 * registerUser Click.
 * @author : mattmk
 */
 function registerUserClick(){
	location.replace('/ytn/member/userRegister');
}

/**
 * userRegisterChk Click.
 * @author : mattmk
 */
 function userRegisterChk(userIdTemp, pwdTemp, userNameTemp){
	if(userIdTemp != '' && pwdTemp != '' && userNameTemp != ''){
		$.ajax({
			type: "POST",
			url: "/ytn/member/auth/register", 
			data: {
					userId : userIdTemp,
					userPwd : pwdTemp,
					userName : userNameTemp
			},  
			dataType : "json",
			success : function(data){
				if(data == 0){
					alert("관리자에게 문의해 주세요.");
					return false;
				}else if(data == 600){
					alert("이미 사용중인 ID 입니다.");
					return false;
				}
				
				alert("회원등록이 완료 되었습니다.");
				loginClick()
			},
			error:function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				alert("관리자에게 문의해 주세요.");
			}
		});
	}
}

/**
  * downloadTxtFile Click.
  * @author : mattmk
 */
function downloadTxtFileClick(startDate, endDate, searchText){
	if(clickCnt > 0){
		alert('로딩 중 입니다.');
		return false;
	}

	if(startDate != '' && endDate != ''){
		$.ajax({
			type: "POST",
			url: "/ytn/captions/data/getExcelData", 
			data: {
					strDateTime : startDate,
					endDateTime : endDate,
					searchText : searchText
			},  
			dataType : "json",
			beforeSend: function () {
				clickCnt++;
				var width = 0;
				var height = 0;
				var left = 0;
				var top = 0;
			 
			    width = 150;
				height = 150;
				top = ( $(window).height() - height ) / 2 + $(window).scrollTop();
				left = ( $(window).width() - width ) / 2 + $(window).scrollLeft();
			 
			    if($("#div_ajax_load_image").length != 0) {
					$("#div_ajax_load_image").css({"top": top+"px","left": left+"px"});
					$("#div_ajax_load_image").show();
				}else {
					$('body').append('<div id="div_ajax_load_image" style="position:absolute; top:' + top + 'px; left:' + left + 'px; width:' + width + 'px; height:' + height + 'px; z-index:9999; background:#f0f0f0; filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="/img/ajax_loader6.gif" style="width:150px; height:150px;"></div>');
				}
			},
			success : function(data){
				if(data == ''){
					alert('해당 데이터가 없습니다.');
					ajaxLoadImageDefult();
					return false;
				}

				downloadTemplate('속기사_'+startDate.substring(0,startDate.length-7)+'_'+endDate.substring(0,endDate.length-7), data);
			},
			complete: function () {
				setTimeout(function() {
					ajaxLoadImageDefult();
					clickCnt=0;
				  }, 3000);
			},
			error:function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				ajaxLoadImageDefult();
				alert("관리자에게 문의해 주세요.");
			}
		});
	}else{
		alert('시작일자, 종료일자를 선택해 주세요.');
	}
}

//Download HTML Template Source
function downloadTemplate(filename, text) {
    let element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();

    document.body.removeChild(element);
}


/**
 * DataMoveAndDel Target Count.
 * @author : mattmk
 */
function totalTargetCnt(){
	$.ajax({
			type: "POST",
			url: "/ytn/captions/data/dataListMoveCnt", 
			data: {
			},
			async: false,
			dataType : "json",
			success : function(data){
				console.log('data ---->' , data);
				targetCnt = data.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			},
			error:function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				targetCnt=0;
				alert("관리자에게 문의해 주세요.");
			}
		});
}



/**
 * DataMoveAndDel Click.
 * @author : mattmk
 */
function dataMoveAndDelClick(){
	totalTargetCnt();

	var result = confirm('데이터 이관을 진행 하시겠습니까? \n' + '총' + targetCnt + ' 건');

	if(clickCnt > 0){
		alert('로딩 중 입니다.');
		return false;
	}

	if(targetCnt == 0){
		alert('이관할 데이터가 없습니다.');
		return false;
	}

	if(result){
		$.ajax({
			type: "POST",
			url: "/ytn/captions/data/dataListMove", 
			data: {
			},  
			dataType : "json",
			beforeSend: function () {
				clickCnt++;
				var width = 0;
				var height = 0;
				var left = 0;
				var top = 0;
			 
			    width = 150;
				height = 150;
				top = ( $(window).height() - height ) / 2 + $(window).scrollTop();
				left = ( $(window).width() - width ) / 2 + $(window).scrollLeft();
			 
			    if($("#div_ajax_load_image").length != 0) {
					$("#div_ajax_load_image").css({"top": top+"px","left": left+"px"});
					$("#div_ajax_load_image").show();
				}else {
					$('body').append('<div id="div_ajax_load_image" style="position:absolute; top:' + top + 'px; left:' + left + 'px; width:' + width + 'px; height:' + height + 'px; z-index:9999; background:#f0f0f0; filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="/img/ajax_loader6.gif" style="width:150px; height:150px;"></div>');
				}
			},
			success : function(data){

				if(data == ""){
					alert("잘못된 접근 입니다.");
					ajaxLoadImageDefult();
					return false;
				}else if(data.resultCode != '200'){
					alert(data.resultMessage);
					ajaxLoadImageDefult();
					return false;
				}else{
					alert(data.resultMessage);
					location.reload();
				}
			},
			complete: function () {
				setTimeout(function() {
					ajaxLoadImageDefult();
				  }, 20000);
			},
			error:function(request,status,error){
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				alert("관리자에게 문의해 주세요.");
			}
		});
	}
}

/**
 * beforeDataInsert Click.
 * @author : mattmk
 */
function beforeDataInsertClick(){
	var popup = window.open("/ytn/captions/captionBeforeDataInsert", "viewer", "width=1500, height=100");
}


function ajaxLoadImageDefult(){
	$("#div_ajax_load_image").hide();
	clickCnt=0;
}