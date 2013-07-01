function getParams() {
	var username = getCookie("projectx_user")
	var password = getCookie("projectx_password")
	if (username != null && username != "" && password != null
			&& password != "") {
		$("#username").attr("value", username)

		$("#password").attr("value", password)

		$('#remember_me').prop('checked', true);
	}
}

function setParams() {
	if (document.getElementById('remember_me').checked == 1) {
		setCookie("projectx_user", document.getElementById('username').value, 30);
		setCookie("projectx_password", document.getElementById('password').value, 30);
	} else {
		setCookie("projectx_user", "", 30);
		setCookie("projectx_password", "", 30);
	}
}

function getCookie(c_name) {
	var c_value = document.cookie;
	var c_start = c_value.indexOf(" " + c_name + "=");
	if (c_start == -1) {
		c_start = c_value.indexOf(c_name + "=");
	}
	if (c_start == -1) {
		c_value = null;
	} else {
		c_start = c_value.indexOf("=", c_start) + 1;
		var c_end = c_value.indexOf(";", c_start);
		if (c_end == -1) {
			c_end = c_value.length;
		}
		c_value = unescape(c_value.substring(c_start, c_end));
	}
	return c_value;
}

function setCookie(c_name, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value = escape(value)
			+ ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
	document.cookie = c_name + "=" + c_value;
}
