<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div style="height: 750;">
	<h2 align="center">
		<table>
			<tr>
				<td width=5%></td>
				<td colspan="3">
					<ul
						style="list-style-type: none; margin-left: 0px; font-size: 13px; padding-top: 5px;">
						<li>
							系统已有注册用户
							<font color="blue">${totalUsers} </font> 人，当前在线
							<font color="blue">${onlineUsers} </font>人；
						</li>
						<li>
							系统已有知识
							<font color="blue">${totalkg }</font> 篇；
						</li>
						<li>
							您共登录了系统
							<font color="blue">${currentUserlg}</font> 次；
						</li>
						<li>
							您已经上传了
							<font color="blue">${currentUserKg}</font> 篇知识，知识平均质量为：
							<font color="blue">${currentUserkgs}</font> ；
						</li>
						<li>
							您的系统参与分为：
							<font color="blue">${user.totoalscore}</font>；您的系统贡献分为：
							<font color="blue">${user.contributionscore}</font>。
						</li>
					</ul>
					<br>
				</td>
				
			</tr>
			<tr align=left>
				<td width=5%></td>
				<td width=45%><%@ include file="chartKtypeXML.jsp"%></td>
				<td width="45%"><%@ include file="chartPKtypeXML.jsp"%></td>
				<td width=5%></td>
			</tr>
			<tr>
				<td width=5%></td>
				<td align="center" colspan="2">
					<%@ include file= "chartKtrendXML.jsp"%>
				</td>
				<td width=5%></td>
			</tr>

		</table>
	</h2>
</div>