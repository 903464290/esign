layui.define(['jquery'], function (exports) {
    var $ = layui.jquery;
    var tool = {
        /**
         * Remove the value of the array
         * @param arr   Array
         * @param val   Deleted value
         */
        removeByValue: function (arr, val) {
            for (var i = 0; i < arr.length; i++) {
                if (arr[i] == val) {
                    arr.splice(i, 1);
                    break;
                }
            }
        },
        /**
         * Escape character to prevent xss
         * @param str
         * @returns {string}
         */
        stringEncode:function (str){
            var div=document.createElement('div');
            if(div.innerText){
                div.innerText=str;
            }else{
                div.textContent=str;//Support firefox
            }
            return div.innerHTML;
            }
        };

    exports('tool', tool);
});  