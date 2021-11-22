layui.define(function(exports){
    var formate = {
        /**
         * Get the current date function
         * Pass in a timestamp, if not, it will be the current time
         * Note: If it is a uinx timestamp, remember to multiply it by 1000, for example, the timestamp obtained by the php function time() must be multiplied by 1000
         * @type String timestamp The timestamp format to be converted 1469504554276
         * @returns {String} Date format: 2016-07-26 10:55:38
         */
        ge_time_format:function(timestamp){
            if(timestamp){
                var date = new Date(timestamp*1000);
            }else{
                var date = new Date();
            }
            Y = date.getFullYear(),
                m = date.getMonth()+1,
                d = date.getDate(),
                H = date.getHours(),
                i = date.getMinutes(),
                s = date.getSeconds();
            if(m<10){
                m = '0'+m;
            }
            if(d<10){
                d = '0'+d;
            }
            if(H<10){
                H = '0'+H;
            }
            if(i<10){
                i = '0'+i;
            }
            if(s<10){
                s = '0'+s;
            }
            var t = Y+'-'+m+'-'+d+' '+H+':'+i+':'+s;
            return t;
        },
        /**
         * Date function converted to timestamp format
         * Pass in a date and time format, if you don't pass in, get the current time
         * @type String strtime date and time format to be converted 2016-07-26 10:55:38
         * @return {String} timestamp format: 1469504554276
         */
        get_unix_time_stamp:function (strtime) {
            if(strtime){
                var date = new Date(strtime);
            }else{
                var date = new Date();
            }
            time1 = date.getTime();   //Will be accurate to the millisecond---length is 13 digits
            //time2 = date.valueOf(); //Will be accurate to the millisecond---length is 13 digits
            //time3 = Date.parse(date); //Only accurate to the second, milliseconds will be replaced by 0 --- length is 10 digits
            return time1;
        }
    };

    exports('formate', formate);
});



