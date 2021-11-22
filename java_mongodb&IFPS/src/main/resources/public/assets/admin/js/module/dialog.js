layui.define(['jquery', 'layer'], function (exports) {
        var $ = layui.jquery;
        var layer = layui.layer;
        var dialog = {
            confirm: function (jsonData) {
                layer.confirm(jsonData.message, {
                    btn: ['OK', 'Cancel'],
                    shade: [0.1, '#fff']
                }, function () {
                    jsonData.success && jsonData.success();
                }, function () {
                    jsonData.cancel && jsonData.cancel();
                });
            },
            page: function (title, url, w, h) {
                if (title == null || title == '') {
                    title = false;
                }
                ;
                if (url == null || url == '') {
                    url = "404.html";
                }
                ;
                if (w == null || w == '') {
                    w = '700px';
                }
                ;
                if (h == null || h == '') {
                    h = '350px';
                }
                ;
                var index = layer.open({
                    type: 2,
                    title: title,
                    area: [w, h],
                    fixed: false,
                    maxmin: true,
                    content: url
                });
            },
            /**
			 * hint
			 * @param title
			 * @param obj
			 */
			tips: function(title, obj) {
				layer.tips(title, obj, {
					tips: [1, '#444c63'],
					time: 1000
				});
			}
        };
        exports('dialog', dialog);
    }
);
