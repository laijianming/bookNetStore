function getXmlHttpRequest() {
	var xhr;

	try {
		xhr = new XMLHttpRequest();
	} catch (e) {
		try {
			xhr = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {

			try {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("�����������֧��ajax!");
				return false;
			}
		}
	}
	return xhr;
}
