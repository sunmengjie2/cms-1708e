<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>   
    
<link href="/resource/bootstrap-4.3.1/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.4.1.min.js" ></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>

	<link rel="stylesheet" href="/resource/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="/resource/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="/resource/kindeditor/plugins/code/prettify.js"></script>
	<script charset="utf-8" src="/resource/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="/resource/kindeditor/lang/zh-CN.js"></script>

	
	    
<form name="articleform"  id="articlesform">
  
  <div class="form-group">
    <label for="title">标题</label>
    <input type="text" class="form-control" id="title" name="title"  placeholder="请输入文章标题">
  </div>
  
  <div class="form-group">
    <label for="channel">栏目</label>
    <select class="form-control" id="channel" name="channelId">
      <option value="0">请选择</option>
      <c:forEach items="${channels}" var="cat">
      		<option value="${cat.id}">${cat.name}</option>
      </c:forEach>
      
      
    </select>
  </div>
  
  <div class="form-group">
    <label for="category">分类</label>
    <select class="form-control" id="category" name="categoryId">
    </select>
  </div>
  
 <div class="form-group">
    <label for="exampleFormControlFile1">文章图片</label>
    <input type="file" class="form-control-file" id="file" name="file">
  </div>
  
  <div class="form-group">
    <label for="content1">文章内容</label>
    <textarea name="content1" id="contentId" cols="200" rows="200" style="width:700px;height:200px;visibility:hidden;"></textarea> 
    
  </div>
  
  <div class="form-group">
  	<input type="button" class="btn btn-primary mb-2" value="提交" onclick="readyTxt()">
  </div> 
</form>
<script>
	$("#channel").change(function(){	
		console.log("选中的数据是 " + $("#channel").val())
		$.post("/user/getCategoris",{cid:$("#channel").val()},
				function(data){
					$("#category").empty();
					for ( var i in data) {
						$("#category").append("<option value='"+ data[i].id+"'>"+data[i].name+"</option>")	
					}
		})
	})
	
	
	 $(document).ready( function(){
		 
		KindEditor.ready(function(K) {
			//    textarea[name="content1"]
			editor = K.create('#contentId', {
			cssPath : '/resource/kindeditor/plugins/code/prettify.css',
			//uploadJson : '/resource/kindeditor/jsp/upload_json.jsp',
			//uploadJson:'/file/upload', //
			uploadJson:'/file/uploads.do',
			fileManagerJson:'/file/manager',
			//fileManagerJson : '/resource/kindeditor/jsp/file_manager_json.jsp',
			allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
      
	 }); 
		
	
	  
	  function readyTxt(){
		  alert(editor.html());
		  
		//  var formdata = new FormData($("#articleform"))
		// 生成formData  异步提交的数据包含附件  
		  var formData = new FormData($( "#articlesform" )[0]);
		  
		console.log("11111111")
		
		   // 把文章内容存放到formData 中
		  formData.set("content",editor.html());
		console.log("222222222222")
		 
		  $.ajax({url:"postArticles",
			  dataType:"json",
			  data:formData,
			  // 让jQuery 不要再提交数据之前进行处理
			  processData : false,
			  // 提交的数据不能加消息头
			  contentType : false,
			  // 提交的方式 
			  type:"post",
			  // 成功后的回调函数
			  success:function(data){
				  //$("#wordspace").load("/user/articles")
				  //  
				  wordspace($("#postLink"),"articles")
			  }
			  })
		  
	  }
		
</script>
