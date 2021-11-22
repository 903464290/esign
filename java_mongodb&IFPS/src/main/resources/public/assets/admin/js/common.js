layui.config({
	base: '../../static/admin/js/module/'
}).extend({
	dialog: 'dialog',
});

layui.use(['form', 'jquery', 'laydate', 'layer', 'laypage', 'dialog',   'element'], function() {
	var form = layui.form(),
		layer = layui.layer,
		$ = layui.jquery,
		dialog = layui.dialog;
	//Get the name value of the current iframe
	var iframeObj = $(window.frameElement).attr('name');

	form.on('checkbox(allChoose)', function(data) {
		var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
		child.each(function(index, item) {
			item.checked = data.elem.checked;
		});
		form.render('checkbox');
	});
	//Render the form
	form.render();	
	//Top add
	$('.addBtn').click(function() {
		var url=$(this).attr('data-url');
		//Pass the iframeObj to the parent window and perform the operation to complete the refresh
		parent.page("Menu added", url, iframeObj, w = "700px", h = "620px");
		return false;

	}).mouseenter(function() {

		dialog.tips('add', '.addBtn');

	})
	//顶部排序
	$('.listOrderBtn').click(function() {
		var url=$(this).attr('data-url');
		dialog.confirm({
			message:'Are you OK to sort?',
			success:function(){
				layer.msg('OK')
			},
			cancel:function(){
				layer.msg('Cancel')
			}
		})
		return false;

	}).mouseenter(function() {

		dialog.tips('Batch sort', '.listOrderBtn');

	})	
	//顶部批量删除
	$('.delBtn').click(function() {
		var url=$(this).attr('data-url');
		dialog.confirm({
			message:'You are OK to delete the selected item',
			success:function(){
				layer.msg('deleted')
			},
			cancel:function(){
				layer.msg('cancelled')
			}
		})
		return false;

	}).mouseenter(function() {

		dialog.tips('batch deletion', '.delBtn');

	})	
	//List add
	$('#table-list').on('click', '.add-btn', function() {
		var url=$(this).attr('data-url');
		//Pass the iframeObj to the parent window
		parent.page("menu add", url, iframeObj, w = "700px", h = "620px");
		return false;
	})
	//List delete
	$('#table-list').on('click', '.del-btn', function() {
		var url=$(this).attr('data-url');
		var id = $(this).attr('data-id');
		dialog.confirm({
			message:'Are you OK to delete？',
			success:function(){
				layer.msg('OK')
			},
			cancel:function(){
				layer.msg('Cancel')
			}
		})
		return false;
	})
	//List jump
	$('#table-list,.tool-btn').on('click', '.go-btn', function() {
		var url=$(this).attr('data-url');
		var id = $(this).attr('data-id');
		window.location.href=url+"?id="+id;
		return false;
	})
	//编辑栏目
	$('#table-list').on('click', '.edit-btn', function() {
		var That = $(this);
		var id = That.attr('data-id');
		var url=That.attr('data-url');
		//Pass the iframeObj to the parent window
		parent.page("menu edit", url + "?id=" + id, iframeObj, w = "700px", h = "620px");
		return false;
	})
});

/**
 * Control the refresh operation of the iframe window
 */
var iframeObjName;

//Parent pop-up page
function page(title, url, obj, w, h) {
	if(title == null || title == '') {
		title = false;
	};
	if(url == null || url == '') {
		url = "404.html";
	};
	if(w == null || w == '') {
		w = '700px';
	};
	if(h == null || h == '') {
		h = '350px';
	};
	iframeObjName = obj;
	//If the mobile terminal, full screen display
	if(window.innerWidth <= 768) {
		var index = layer.open({
			type: 2,
			title: title,
			area: [320, h],
			fixed: false,
			content: url
		});
		layer.full(index);
	} else {
		var index = layer.open({
			type: 2,
			title: title,
			area: [w, h],
			fixed: false,
			content: url
		});
	}
}

/**
 * Refresh the subpage and close the pop-up window
 */
function refresh() {
	//According to the passed name value, get the child iframe window, perform refresh
	if(window.frames[iframeObjName]) {
		window.frames[iframeObjName].location.reload();

	} else {
		window.location.reload();
	}

	layer.closeAll();
}