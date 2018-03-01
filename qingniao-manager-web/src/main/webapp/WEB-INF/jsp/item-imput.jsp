<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<a href="javascript:void(0)" class="easyui-linkbutton" onclick="impo()">添加商品到索引库</a>
<script type="text/javascript">
	function impo() {
		$.post("/item/import",null,function(data){
			if(data.status==200){
				$.messager.alert('提示','添加索引库成功');
			}
		});
	}
</script>