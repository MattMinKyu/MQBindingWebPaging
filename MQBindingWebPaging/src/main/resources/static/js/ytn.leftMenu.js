/*
	LeftMenu
*/

$(function(){
	setTimeout(() => leftMenuSelector(tabType), 100);
});

//


/**
	LeftMenu Selector
	Bold 처리.
	@Author : Mattmk
 */
function leftMenuSelector(tabType){
	 if(tabType == 'A'){
		$('#typeB').removeClass('active');
		$('#typeA').addClass('active');
	}else if(tabType == 'B'){
		$('#typeA').removeClass('active');
		$('#typeB').addClass('active');
	}else{
		$('#typeB').removeClass('active');
		$('#typeA').addClass('active');
	}
}