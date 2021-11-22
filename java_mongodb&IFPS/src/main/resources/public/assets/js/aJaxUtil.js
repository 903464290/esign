/**
 * ajax封装方法2018年12月19号
 */
function ajaxPostJson(url,param,calback){
	$.ajax({
            type: "POST",
            dataType: "json",
            url: url ,
            data: param,
            success: function (result) {
            	result=result;
            	calback(true,result);
            },
            error : function() {
            	result=null;
            	calback(false,result);
            }
        });
	
}

function ajaxGetJson(url,param,calback){
	$.ajax({
            type: "GET",
            dataType: "json",
            url: url ,
            data: param,
            success: function (result) {
            	result=result;
            	calback(true,result);
            },
            error : function() {
            	result=null;
            	calback(false,result);
            }
        });
	
}