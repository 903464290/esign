<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Signature</title>

<script src="js/jquery-3.1.1.min.js"></script>
<!--[if lt IE 9]>
	<script src="jSignature/flashcanvas.js"></script>
<![endif]-->
<script src="jSignature/jSignature.min.js"></script>
<script src="js/layer/layer.js"></script>
<style type="text/css"> 
    .zx11{
        border: 1px;
        border-color: #3333FF;
    }
    #signatureparent {
        color:black;
        /*background-color:darkgrey;  背景顏色*/
		background-color:#9BCDFF;  /*背景顏色*/
    }
    #signature {
        border: 2px dotted black;
    }
</style>
<script>
    $(document).ready(function () {
        var arguments = {
            width: '100%',
            height: '300px',
            cssclass: 'zx11',
			"decor-color": "transparent",//去除默认画布上那条横线
            lineWidth: '3'  //筆畫面粗細
        };
        $("#signature").jSignature(arguments);
        $('#clear').click(function () {
            $("#signature").jSignature("reset");
        });
        $('#save').click(function () {
			if( $("#signature").jSignature('getData', 'native').length === 0){
                layer.msg('please sign first',{icon:5});
            	return false;
            }
			var datapair = $("#signature").jSignature("getData", "image");
			var src_data = 'data:' + datapair[0] + "," + datapair[1];			
			var i = new Image();
            i.src = 'data:' + datapair[0] + "," + datapair[1];
            i.image = datapair[1];
            layer.confirm("please check and comfirm your signature<br><img src=" + i.src + " height='100px' width='180px' />", {
                btn: ['confirm', 'cancel'] //按钮
            },function () {  //確認後進入後臺
				$.ajax({
					type:'post',
					url:'./save.php',
					data:{src_data:src_data},
					async:false,
					success:function(data){
						if(data){
							layer.msg('succeed',{icon:6});
							//alert(data);
						}else{
							layer.msg('fail to sign, please try again',{icon:5});
						}
					}
				});
            }, function () {
                layer.msg('cancel succeed');
            });
        });
    });
</script>

</head>

<body>

<div class="container">
    <div id="signatureparent">		
        <div id="signature"></div>
    </div>
    <br>
    <button type="button" class="btn btn-primary btn-block" id="save">Confirm</button>
    <button type="button" class="btn btn-default btn-block" id="clear">Clear</button><br>
    <a href="sign.html">Save </a> 
</div>
</body>
</html>
