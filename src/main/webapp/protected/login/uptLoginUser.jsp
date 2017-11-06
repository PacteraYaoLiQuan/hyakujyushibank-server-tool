<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="container">
    <div class="block-header">
        <h2>ログインユーザ更新</h2>
    </div>

    <div class="card">
        <div class="card-body card-padding">
            <div class="row">
				<table class="input-group-addon">
					<tr style="height:40px;">
						<td style="width:150px;">ログインID:</td>
						<td><input type="text" name=""></td>
					</tr>
					<tr style="height:40px;">
						<td style="width:150px;">パスワード:</td>
						<td><input type="password" name=""></td>
					</tr>
					<tr style="height:40px;">
						<td style="width:150px;">パスワード再確認:</td>
						<td><input type="password" name=""></td>
					</tr>
					<tr style="height:40px;">
						<td style="width:150px;">権限:</td>
						<td>
							<select chosen 
	                            class="w-100">
	                            <option value="受付">role_user</option>
	                            <option value="処理中">role_admin</option>
	                        </select>
						</td>
					</tr>
					<tr  style="height:50px;">
						<td colspan="2"></td>
					</tr>
					<tr>
						<td colspan="2"><button  class="btn bgm-lightblue">更新</button></td>
					</tr>
				</table>
            </div>
        </div>

    </div>

</div>