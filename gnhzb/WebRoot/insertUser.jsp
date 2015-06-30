<html>
<head>
	<script type="text/javascript">
	function post() {
	var xmlhttp=false;
	if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
		try {
			xmlhttp = new XMLHttpRequest();
		} catch (e) {
			xmlhttp=false;
		}
	}
	if (!xmlhttp && window.createRequest) {
		try {
			xmlhttp = window.createRequest();
		} catch (e) {
			xmlhttp=false;
		}
	}
	var dest = document.getElementById('destination').value;
	xmlhttp.open("POST", dest, true);
	xmlhttp.onreadystatechange=function() {
    if (xmlhttp.readyState==4) {
    document.getElementById("result").innerHTML = xmlhttp.responseText;
    		}
	}
	xmlhttp.setRequestHeader("Man", "POST " + dest + " HTTP/1.1")
	xmlhttp.setRequestHeader("MessageType", "CALL")
	xmlhttp.setRequestHeader("Content-Type", "text/xml");
	var texta = document.getElementById('texta');
	var soapmess = texta.value;
	xmlhttp.send(soapmess);
	}
	</script>
</head>
<body>
hi there
<form id="forma">
<textarea id="texta"  rows="10" cols="80">
<?xml version='1.0' encoding='UTF-8'?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
 xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance"   xmlns:xsd="http://www.w3.org/1999/XMLSchema">
 <SOAP-ENV:Body>
 <Insert>
  <userId>2005221104210066</userId>
  <userName>Leon Cao</userName>
  <userEmail>caohongliang2@163.com</userEmail>
  <userAge>23</userAge>
</Insert>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
</textarea>
<p>
<input type="text" id="destination" size="50" value="http://localhost:8080/caltks/services/UserManager"></input>
<p>
<input type="button" onclick="post()" name="submit" value="Submit"></input>
</form>
<div id="container"></div>
Result:
<hr>
<div id="result">
</div>
<hr>
<div id="soap">
</div>
</body>
</html>
