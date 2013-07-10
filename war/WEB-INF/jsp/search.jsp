<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet"	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<script src="http://www.appelsiini.net/download/jquery.jeditable.mini.js"></script>

<html>
<head>

<link type="text/css" rel="stylesheet" href="/static/style.css" />

<title>Search</title>

<script>

var entryToRemove = {suggestionDetailBean: []} ;

function removeEntry(tagList , url , date , time){
	
	entryToRemove.suggestionDetailBean.push({
		tagList : tagList,
		url : url,
		date : date,
		time : time
	})
	 
alert('updateEntry '+tagList+','+url+','+date+','+time);	

$.ajax({
	url : "/servlets/deleteentry",
	type : 'post',
	dataType : 'json',
	data : ({
		json : JSON.stringify(entryToRemove)
	}),
	success : function(jsonResponse) {
		alert('response is '+jsonResponse);
	}
	
	});
}//end removeEntry
	
$(function() {
	$("#rounded-corner tbody").append("<tr><td id='loadingImage' colspan='6' style='display: none; text-align: center'><img src='/static/table-images/indicator.gif' /></td></tr>");
	$("#loadingImage").show();
	
$.ajax({
url : "/servlets/getsuggestions?term=all-all-all",
type : 'GET',
success : function(jsonResponseObjectOuter) {

	$("#rounded-corner tbody").empty();
	
$.each(jsonResponseObjectOuter,function() {
$.each(this,function(k,jsonResponseObjectInner) {

$("#rounded-corner tbody").append("<tr><td>"+ jsonResponseObjectInner.summary+ "</td><td><a href='"+jsonResponseObjectInner.url+"'>"+ jsonResponseObjectInner.url+ "</a></td><td>"+ jsonResponseObjectInner.date+ "</td><td>"+ jsonResponseObjectInner.time+ "</td><td class='click' id='"+jsonResponseObjectInner.tagList+","+jsonResponseObjectInner.url+","+jsonResponseObjectInner.date+","+jsonResponseObjectInner.time+","+jsonResponseObjectInner.summary+"'>"+ jsonResponseObjectInner.tagList+ "</td> <td> <div id='removeRowDiv"+k+"'>Remove</div> </td> </tr>");

console.log(jsonResponseObjectInner.url);
$('#removeRowDiv'+k).on("click", function(){
	$(this).parent().parent().remove();
removeEntry(jsonResponseObjectInner.tagList , jsonResponseObjectInner.url , jsonResponseObjectInner.date , jsonResponseObjectInner.time);
});

$(".click").editable("/servlets/updatetaglist",{
	id : 'elementid',
	name : 'newvalue',
	indicator : "<img src='/static/table-images/indicator.gif'>",
	tooltip : "Click to edit...",
	style : "inherit",
	callback : function(value, settings) {
	console.log(this);
	console.log(value);
	console.log(settings);
	} //end function(value, settings) {
	}); //end $(".click").editable("/servlets/updatetaglist",{


});
});

//    return false; // avoid to execute the actual submit of the form.

}
});
//});

$(".click").on("click", onTableCellEdit);
function onTableCellEdit(event) {

alert('here');
//$("tr.row-selected td:eq(0)").trigger("edit");
}

$.ajax({
url : '/servlets/getusertags',
type : 'GET',
success : function(userTagsJson) {

$.each(userTagsJson, function() {
$.each(this, function(k, userTagsJson) {
$("#userTags").append("<div style=\"float: left; text-align: left;\">"+ userTagsJson.tagName + "|</div>");
console.log(userTagsJson.tagName);
});
});

}
})

$("#eventFired").keyup(
function() {

var val = $("#eventFired").val(), len = val.length - 1;

if (val[len] == ' ') {
	
	$("#rounded-corner tbody").empty();
	$("#rounded-corner tbody").append("<tr><td id='loadingImage' colspan='6' style='display: none; text-align: center'><img src='/static/table-images/indicator.gif' /></td></tr>");
	$("#loadingImage").show();
	

	
xhr = $.ajax({
url : "/servlets/getsuggestions?term="+ $("#eventFired").val(),
type : 'GET',
success : function(jsonResponseObjectOuter) {
	
	$("#rounded-corner tbody").empty();
$.each(jsonResponseObjectOuter,function() {
$.each(this, function(k,jsonResponseObjectInner) {
console.log(jsonResponseObjectInner.url);

$("#rounded-corner tbody").append("<tr><td>"+ jsonResponseObjectInner.summary+ "</td><td><a href='"+jsonResponseObjectInner.url+"'>"+ jsonResponseObjectInner.url+ "</a></td><td>"+ jsonResponseObjectInner.date+ "</td><td>"+ jsonResponseObjectInner.time+ "</td><td class='click' id='"+jsonResponseObjectInner.tagList+","+jsonResponseObjectInner.url+","+jsonResponseObjectInner.date+","+jsonResponseObjectInner.time+","+jsonResponseObjectInner.summary+"'>"+ jsonResponseObjectInner.tagList+ "</td> <td> <div id='removeRowDiv"+k+"'>Remove</div> </td> </tr>");

$('#removeRowDiv'+k).on("click", function(){
	$(this).parent().parent().remove();
removeEntry(jsonResponseObjectInner.tagList , jsonResponseObjectInner.url , jsonResponseObjectInner.date , jsonResponseObjectInner.time);
});

$(".click").editable("/servlets/updatetaglist",
{
id : 'elementid',
name : 'newvalue',
indicator : "<img src='/static/table-images/indicator.gif'>",
tooltip : "Click to edit...",
style : "inherit",
callback : function(
value,
settings) {
console.log(this);
console.log(value);
console.log(settings);
}
});

});
});

console.log(jsonResponseObjectOuter);

}
})
}
});

function addRow(){
}
		
}); //end $(function() {
</script>
</head>
<body>
<%-- <br>
<br> 
center>
<h1>Lets GoGoFindIt :)</h1>
<br>
<form:form method="get" modelAttribute="searchobj"
action="/servlets/redirect">


<table>

<tr>
<td><form:input path="param" id="eventFired" size="35" /></td>
<td><form:input type="submit" path="searchButton"
value="Google" /></td>
<td><form:input type="submit" path="searchButton"
value="Duck Duck Go" /></td>
<td><form:input type="submit" path="searchButton"
value="Wikipedia" /></td>
<td><input type="submit" value="Ranked" /></td>
</tr>
</table>
</center>

</form:form> --%>
<br>
<center>
<input type="text" id="eventFired" size="35" />

<!-- <input type="text" id="eventFired" size="15" />
-->
<!--   <fieldset>

<legend>Suggestions</legend>

<div id ="suggestionsDiv"> -->

<div id ="tableDiv">
<table id="rounded-corner">
<thead>
<tr>
<th scope="col" class="rounded-company">Summary</th>
<th scope="col" class="rounded-q1">URL</th>
<th scope="col" class="rounded-q2">Date</th>
<th scope="col" class="rounded-q3">Time</th>
<th scope="col" class="rounded-q3">Tag(s)</th>
<th scope="col" class="rounded-q4">Remove</th>
</tr>
</thead>

<tbody>
<tr>
<td id="loadingImage" colspan="6" style="display: none; text-align: center">
<img src='/static/table-images/indicator.gif' />
</td>
</tr>
</tbody>

<tfoot>
<tr>
<td colspan="5" class="rounded-foot-left"><em></em></td>
<td class="rounded-foot-right">&nbsp;</td>
</tr>
</tfoot>

</table>
</div>

<!--             <div class="row">
<label for="first-field">Tag</label>
<span><input type="text" id="first-field" size="15" /></span>
</div>
<div class="row">
<label for="second-field">URL</label>
<span><input type="text" id="second-field" size="10" /></span>
</div>
<div class="row">
<label for="third-field">Date</label>
<span><input type="text" id="third-field" size="5" /></span>
</div>
<div class="row">
<label for="third-field">Time</label>
<span><input type="text" id="third-field" size="5" /></span>
</div> -->
<!--    </div>
</fieldset> -->

</center>

<!--   <button type="button" id="getAllButton">Get All Suggestions</button> -->

<div id="userTags"></div>

</body>
</html>