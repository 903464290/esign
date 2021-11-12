//图片上传到浏览器并显示
function ProcessFile(e) {
    var file = document.getElementById('chooseImage0').files[0];
    console.log(file);
    var reader = new FileReader();
    if (file) {
        reader.onload = function (event) {
            var txt = event.target.result;
            $("#cropedBigImg0").attr('src', txt);//将图片base64字符串赋值给img的src
            //console.log(txt)//base64
        };
    }
    reader.readAsDataURL(file);
}

function contentLoaded() {
    document.getElementById('chooseImage0').addEventListener('change',
        ProcessFile, false);
}

// var model=document.getElementById('model');
// var input=model.querySelectorAll('input');
window.addEventListener("DOMContentLoaded", contentLoaded, false);
