<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/chocoStyle.css" type="text/css">
<title>Choco Survey!</title>
</head>
<body>
	<FORM METHOD="POST" ACTION="ChocolateServlet">
		<h1>What is your favorite chocolate bar?</h1>
		<p>Pick your first and second choice of chocolate bar, and press submit.</p>
		<p>Second choice is given half the weighting of first choice.</p>
		<table>
			<thead id="grad1">
				<tr>
					<th>Flavour</th>
					<th>First Choice</th>
					<th>Second Choice</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Dark chocolate with hazelnut and nougat</td>
					<td><INPUT TYPE=radio NAME=choice1 VALUE="Dark chocolate with hazelnut and nougat" ></td>
					<td><INPUT TYPE=radio NAME=choice2 VALUE="Dark chocolate with hazelnut and nougat" ></td>	
									
				</tr>
				<tr>
					<td>White chocolate with raspberry and caramel</td>
					<td><INPUT TYPE=radio NAME=choice1 VALUE="White chocolate with raspberry and caramel"></td>
					<td><INPUT TYPE=radio NAME=choice2 VALUE="White chocolate with raspberry and caramel"></td>
				</tr>
				<tr>
					<td>Milk chocolate with caramel and peanut</td>
					<td><INPUT TYPE=radio NAME=choice1 VALUE="Milk chocolate with caramel and peanut"></td>
					<td><INPUT TYPE=radio NAME=choice2 VALUE="Milk chocolate with caramel and peanut"></td>					
				</tr>
				<tr>
					<td>Dark chocolate with nougat and orange</td>
					<td><INPUT TYPE=radio NAME=choice1 VALUE="Dark chocolate with nougat and orange"></td>
					<td><INPUT TYPE=radio NAME=choice2 VALUE="Dark chocolate with nougat and orange"></td>					
				</tr>
				<tr>
					<td>White chocolate with orange and hazelnut</td>
					<td><INPUT TYPE=radio NAME=choice1 VALUE="White chocolate with orange and hazelnut"></td>
					<td><INPUT TYPE=radio NAME=choice2 VALUE="White chocolate with orange and hazelnut"></td>					
				</tr>
				<tr>
					<td>Milk chocolate with peanut and raspberry</td>
					<td><INPUT TYPE=radio NAME=choice1 VALUE="Milk chocolate with peanut and raspberry"></td>
					<td><INPUT TYPE=radio NAME=choice2 VALUE="Milk chocolate with peanut and raspberry"></td>
				</tr>
			</tbody>
		</table>
		<BR><INPUT TYPE=submit VALUE="Submit" > <INPUT TYPE=reset>
	</FORM>
	<BR>
	Go to the results page using this <a href="/Results.jsp" target="_blank">web page</a>
</body>
</html>

