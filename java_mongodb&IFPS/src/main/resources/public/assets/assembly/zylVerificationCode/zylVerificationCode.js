$(function(){
	zylVerCode();
});



function zylVerCode(len){
    len = len || 4;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = $chars.length;
    var zylCode = '';
    for (i = 0; i < len; i++) {
        zylCode += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    $(".zylVerCode").html(zylCode);
}