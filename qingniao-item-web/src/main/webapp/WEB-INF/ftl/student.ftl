<html>
	<head>
		<title>Freemarker Pojo Demo</title>
	</head>
	<body>
		<label>学号:</label>${student.id}</br>
		<label>姓名:</label>${student.name}</br>
		<label>年龄:</label>${student.age}</br>
		<table border="1">
			<tr>
				<th>序号</th>
				<th>学号</th>
				<th>姓名</th>
				<th>年龄</th>
			</tr>
			<#list studentList as student>
			<#if student_index%2==0>
			<tr bgcolor="green">
			<#else>
			<tr bgcolor="yellow">
			</#if>
				<td>${student_index}</td>
				<td>${student.id}</td>
				<td>${student.name}</td>
				<td>${student.age}</td>
			</tr>
			</#list>
		</table>
		<label>日期显示/date</label>:${date?date}</br>
		<label>日期显示/time</label>:${date?time}</br>
		<label>日期显示/datetime</label>:${date?datetime}</br>
		<label>null值处理</label>:${aaa!}</br>
		<#include "hello.ftl"/>
	</body>
</html>