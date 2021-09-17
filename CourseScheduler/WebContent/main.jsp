<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NCCU TIME TABLE</title>
<style type="text/css">
	:root{
		--color-bg: #3d405b;
		--color-table: #4E5273;
		--color-red: #e07a5f;
		--color-ivory: #f4f1de;
		--color-yellow: #f2cc8f;
		--font-e: "noto Sans TC", "Optima";
		--font-c: "noto Sans TC";
	}
	body{
		background-color: var(--color-bg);
		color: var(--color-ivory);
		margin:0;
		display: flex;
		flex-direction: column;
		font-family: var(--font-e);
		justify-content: center;
	}
	a {
		text-decoration: none;
	}
	.header {
		background-color: var(--color-bg);
		display: flex;
		justify-content: space-between;
		align-items: flex-end;
		position: fixed;
		top: 0;
		width: 100%;
		color: var(--color-yellow);
		/* border-bottom: solid var(--color-yellow); */
	}
	.header>div {
		display: flex;
	}
	.header-left>#title {
		font-size: 25px;
		margin: 10px 20px;
	}
	.header-left>#welcome {
		align-self: flex-end;
		font-size: 20px;
		color: white;
		margin: 10px 0;
	}
	.header-right>div {
		display: flex;
		align-items: center;
		flex-direction: column;
		font-size: 20px;
		margin: 10px 20px;
	}
	#about:hover {
		color: var(--color-red);
	}
	#about:after {
	 	content: '';
	    width: 0%;
	    height: 1px;
	    background-color: var(--color-red);
		/* position: absolute; */
		bottom: 0;
	    left: 0;
	    transition: all .3s linear;
	}
	#about:hover::after {
		width: 100%;
	}
	#logout {
		color: var(--color-yellow);
		display: flex;
		align-items: center;
		flex-direction: column;
	}
	#logout:hover {
		color: var(--color-red);
	}
	#logout:after {
	 	content: '';
	    width: 0%;
	    height: 1px;
	    background-color: var(--color-red);
		bottom: 0;
	    left: 0;
	    transition: all .3s linear;
	}
	#logout:hover::after {
		width: 100%;
	}
	
	.main {
		margin-top: 100px;
		display: flex;
		justify-content: center;
	}
	.scheduleTable {
		border: none;
		background-color: var(--color-table);
		margin-right: 150px;
		table-layout: fixed;
		border-collapse: collapse;
	}
	.scheduleTable th {
		width: 100px;
		height: 44px;
		color: #f4f1de;
	}
	.scheduleTable td {
		border: none;
		width: 100px;
		height: 44px;
		text-align: center;
	}
	#schedule-item {
		background-color: #ffd7ba;
		color: var(--color-bg);
	}
	
	
	.title {
		flex: auto;
		font-size: 32px;
		color: var(--color-yellow);
		margin-top: 30px;
	}
	#text-search{
		padding: 8px 10px;
		border: 2px solid var(--color-yellow);
		border-radius: 10px;
		width: 330px;
		line-height: 15px;
		background-color: var(--color-bg);
		background-position: 10px 7px;
		background-repeat: no-repeat;
		font-size: 12px;
		color: var(--color-ivory);
		letter-spacing: 2px;
	}
	#text-search:focus {
		outline: none;
		border: 2px solid var(--color-red);
	}
	.searchResult {
		flex: none;
		height: 185px;
		width: 350px;
		overflow-y: auto;
	    -webkit-overflow-scrolling: touch;
	    margin-top: 30px;
	}
	.searchTable {
		border: none;
		background-color: var(--color-table);
	}
	.searchTable td {
		width: 350px;
		border-bottom: 1px solid #f4f1de;
		height: 40px;
	}
	.followList {
		flex: none;
		height: 380px;
		width: 350px;
		overflow-y: auto;
	    -webkit-overflow-scrolling: touch;
	    margin-top: 20px;
	}
	.followTable {
		border: none;
		background-color: var(--color-table);
	}
	.followTable td {
		padding: 0;
		width: 350px;
		border-bottom: 1px solid #f4f1de;
		height: 45px;
	}
	.c-menu {
		display: flex;
		justify-content: space-between;
	}
	.c-menu-li {
		text-align: center;
		color: var(--color-yellow);
		background-color: var(--color-bg);
		margin: 6px 0;
		padding: 2px;
		border: 1px solid var(--color-yellow);
		height: 25px;
		width:100px;
		line-height: 24px;
	}
	.c-menu-li a {
		color: var(--color-yellow);
	}
	
	
	.unSelectedItem {
		margin: 100px 0;
		display: flex;
		justify-content: space-around;
		position: relative;
	}
	.unSelectedItem .title {
		position: absolute;
		top: -80px;
	}
	.bottomTitle {
		border-bottom: 1px solid var(--color-yellow);
		font-size: 24px;
		padding-bottom: 5px;
	    margin-bottom: 10px;
	}
	.bottomList {
		width: 150px;
		height: 400px;
		overflow-y: auto;
	    -webkit-overflow-scrolling: touch;
	}
	.bottomTable td {
		padding: 10px 0;
	}
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">

	// 衝堂提醒
	if(${conflict}) {
		alert("衝堂");
	}
	
	$(function() {
		//About us
		$('#about').click(function() {
			alert(' ----NCCU Time Table---- \n Hi! We are group 1 from DB Management Systems.');
		});
		//追蹤清單--顯示/隱藏選單
		$('.followTable').on('click', 'td', function() {
			if($('>.c-menu', this).is(':visible')) {
				$('>.c-menu', this).hide();
			} else {
				$('.c-menu').hide();
				$('>.c-menu', this).show();
			}
		});
		
		/**
		*	連結Servlet
		*/
		// search(未修課程--點擊加入搜尋欄並搜尋)
		$('.bottomTable').on('click', 'td', function() {
			$("#text-search").val($(this).html());
			document.forms["search"].submit();
		});
		// addFollowList(搜尋結果--加入追蹤清單)
		$('.searchTable').on('click', 'td', function() {
			$('#text-add-follow').val($(this).html());
			alert("加入追蹤清單");
			document.forms["addFollowlist"].submit();
		});
		// deleteFollowList(追蹤清單--取消追蹤)
		$('.followTable').on('click', '#c-del', function() {
			alert("取消追蹤");
			$("#text-delete-follow").val($(this).closest('td').children('label').html());
			document.forms["deleteFollowlist"].submit();
		});
		// addSchedule(追蹤清單--加入課表)
		$('.followTable').on('click', '#c-add', function() {
			alert("加入課表");
			$("#text-add-schedule").val($(this).closest('td').children('label').html());
			document.forms["addSchedule"].submit();
		});
		// deleteSchedule(課表--刪除課程)
		$('.scheduleTable').on('click', 'td', function() {
			alert("移除課程");
			$("#text-delete-schedule").val($('>label', this).html());
			document.forms["deleteSchedule"].submit();
		});
	});
</script>
</head>
<body>

	<!-- Header -->
	<div class="header">
		<div class="header-left">
			<div id="title">NCCU Time Table</div>
			<div id="welcome">Welcome! ${student.getName()}</div>
		</div>
		<div class="header-right">
			<div id='about'>About Us</div>
			<div><a href='logout' id='logout'>Log Out</a></div>
		</div>
	</div><!-- Header -->
	
	<!-- Main -->
	<div class="main">
		<!-- table: scheduleTable -->
		<table class="scheduleTable">
			<thead><tr>
					<th scope="col"></th>
					<th scope="col">Mon</th>
					<th scope="col">Tue</th>
					<th scope="col">Wed</th>
					<th scope="col">Thu</th>
					<th scope="col">Fri</th>
			</tr></thead>
			<c:forEach var="row" items="${student.getScheduleTable()}">
				<tr>
					<c:forEach var="col" items="${row}">
						<c:if test="${col.getDay().equals(\"Sun\")}">
							<th>${col.getHour()}</th>
						</c:if>
						<c:if test="${!col.getDay().equals(\"Sun\")}">
							<c:if test="${!col.getId().equals(\"\")}">
								<td id="schedule-item">
									${col.getCourse_name()}
									<label style="display: none">${col.getId()}</label>
								</td>
							</c:if>
							<c:if test="${col.getId().equals(\"\")}"><td></td></c:if>
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		<!-- form: deleteSchedule -->
		<form name='deleteSchedule' action='deleteSchedule' method='post'>
			<input type='text' id='text-delete-schedule' name="delete_schedule" value='${delete_schedule}' style="display: none"/>
		</form>
		
		<!-- 側邊欄 -->
		<div class="verticle">
		
			<!-- 搜尋欄 -->
			<div class="searchField">
				<!-- form: search -->
				<form name='search' action='search' method='post'>
					<input type='text' id='text-search' placeholder='Search...' name="keyword" value='${keyword}'/>
				</form>
			</div><!-- 搜尋欄 -->	
			
			<!-- 搜尋結果 -->
			<div class="searchResult">
				<!-- table: searchTable -->
				<table class="searchTable">
					<c:forEach var="section" items="${searches}">
						<tr><td id='search_result'><c:out value="${section.toString()}"/></td></tr>
					</c:forEach>
				</table>
				<!-- form: addFollowlist -->
				<form name='addFollowlist' action='addFollowlist' method='post'>
					<input type='text' id='text-add-follow' name="add_follow" value='${add_follow}' style="display: none"/>
				</form>
			</div><!-- 搜尋結果 -->
			
			<!-- 追蹤清單 -->
			<div class="title">追蹤清單</div>
			<div class="followList">
				<!-- table: followTable -->
				<table class="followTable">
					<c:forEach var="section" items="${student.getFollow_List()}">
						<tr><td>
							<label><c:out value="${section.toString()}"/></label><br>
							<label class="detail"><c:out value="${section.detail()}"/></label>
							<div class="c-menu" style="display: none">
								<p class="c-menu-li" id="c-add">加入課表</p>
								<p class="c-menu-li"><a href="${section.getUrl()}">課程大綱</a></p>
								<p class="c-menu-li" id="c-del">取消追蹤</p>
							</div>
						</td></tr>
					</c:forEach>
				</table>
				<!-- form: deleteFollowlist -->
				<form name='deleteFollowlist' action='deleteFollowlist' method='post'>
					<input type='text' id='text-delete-follow' name="delete_follow" value='${delete_follow}' style="display: none"/>
				</form>
				<!-- form: addSchedule -->
				<form name='addSchedule' action='addSchedule' method='post'>
					<input type='text' id='text-add-schedule' name="add_schedule" value='${add_schedule}' style="display: none"/>
				</form>
			</div><!-- 追蹤清單 -->
			
		</div><!-- 側邊欄 -->
	</div><!-- Main -->
	
	<!-- Bottom -->
	<div class="bottom">
	
		<!-- 未修課程 -->
		<div class="unSelectedItem">
			<!-- 本系必修 -->
			<div>
				<div class="title">未修課程</div>
				<div class="bottomTitle">本系必修</div>
				<div class="bottomList">
					<table class="bottomTable">
						<c:forEach var="course" items="${student.getUntakenMajor()}">
							<tr><td><c:out value="${course}"/></td></tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<!-- 雙主修 -->
			<div>
				<div class="bottomTitle">雙主修</div>
				<div class="bottomList">
					<table class="bottomTable">
						<c:forEach var="course" items="${student.getUntakenDoubleMajor()}">
							<tr><td><c:out value="${course}"/></td></tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<!-- 輔系 -->
			<div>
				<div class="bottomTitle">輔系</div>
				<div class="bottomList">
					<table class="bottomTable">
						<c:forEach var="course" items="${student.getUntakenMinor()}">
							<tr><td><c:out value="${course}"/></td></tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div><!-- 未修課程 -->
	
	</div><!-- Bottom -->

</body>
</html>