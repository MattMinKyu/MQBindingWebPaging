// data call.
let strDateTime;
let endDateTime;

function createDatePickerTwo(datePicker1, datePicker2){
	strDateTime = datePicker1;
	endDateTime = datePicker2;

	/**
	 * dateTimePicker 시작일자 Call.
	 * @author : mattmk
	**/
	strDateTime.datetimepicker({
		dateFormat:'yy-mm-dd',
		minDate: new Date('2022-06-30'),
		maxDate: new Date('2099-12-31'),
		monthNamesShort:[ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		dayNamesMin:[ '일', '월', '화', '수', '목', '금', '토' ],
		changeMonth:true,
		changeYear:true,
		showMonthAfterYear:true,
		closeText:'선택',
		// timepicker 설정
		//timeFormat:'HH:mm',
		timeFormat:'HH',
		controlType:'select',
		oneLine:true,
		onSelect: function (selectedDateTime){
			var selectDate = strDateTime.datetimepicker('getDate');

			endDateTime.datetimepicker('option', 'minDate', selectDate);
			endDateTime.datetimepicker('option', 'minDateTime', selectDate);
		}
	});

	/**
	 * dateTimePicker 종료일자 Call.
	 * @author : mattmk
	**/
	endDateTime.datetimepicker({
		dateFormat:'yy-mm-dd',
		minDate: new Date('2022-06-30'),
		maxDate: new Date('2099-12-31'),
		monthNamesShort:[ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월' ],
		dayNamesMin:[ '일', '월', '화', '수', '목', '금', '토' ],
		changeMonth:true,
		changeYear:true,
		showMonthAfterYear:true,
		closeText:'선택',
		// timepicker 설정
		//timeFormat:'HH:mm',
		timeFormat:'HH',
		controlType:'select',
		oneLine:true,
		onSelect: function (selectedDateTime){
			var selectDate = endDateTime.datetimepicker('getDate');

			if(strDateTime.datetimepicker('getDate') >= selectDate){
				strDateTime.datetimepicker('setDate', selectDate);

				//strDateTime.datetimepicker('option', 'minDate', selectDate);
				//strDateTime.datetimepicker('option', 'minDateTime', selectDate);
			}

			//strDateTime.datetimepicker('option', 'maxDate', selectDate);
			//strDateTime.datetimepicker('option', 'maxDateTime', selectDate);
		}
	});

	// 현재날짜, 시간으로 셋팅.
	strDateTime.datetimepicker('setDate', new Date());
    endDateTime.datetimepicker('setDate', new Date());
}


/**
 * 현재 날짜로 셋팅.
 * 파라미터가 존재하면, 시간/분 셋팅.
 * @author : mattmk
**/
function datePickerTimeSet(targetPicker, hourParam){
	var strToday = new Date();

	if(hourParam != ''){
		strToday.setHours(hourParam);
	}

	targetPicker.datetimepicker('setDate', strToday);
}

/**
 * 현재 날짜로 셋팅 TEST FUNCTION.
 * 파라미터가 존재하면, 시간/분 셋팅.
 * @author : mattmk
**/
function datePickerTimeSetTestFun(targetPicker, hourParam, minParam, dayParam){
	var strToday = new Date();

	if(dayParam != ''){
		strToday.setDate('01');
	}

	if(hourParam != ''){
		strToday.setHours(hourParam);
	}

	if(minParam != ''){
		strToday.setMinutes(minParam);
	}

	targetPicker.datetimepicker('setDate', strToday);
}

/**
 * 현재 날짜로 셋팅 TEST FUNCTION.
 * 파라미터가 존재하면, 시간/분 셋팅.
 * @author : mattmk
**/
function datePickerHourSetTestFun(targetPicker, hourParam, dayParam){
	var strToday = new Date();

	if(dayParam != ''){
		strToday.setDate('01');
	}

	if(hourParam != ''){
		strToday.setHours(hourParam);
	}

	targetPicker.datetimepicker('setDate', strToday);
}
