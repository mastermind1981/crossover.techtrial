function showErrorMessage(e, hide) {
	if (!hide) {
		$('#ajaxred').text(e.responseText);
	} else {
		$('#ajaxred').empty();
	}

}

function getTime(date) {
	return moment(date).format('DD.MM.YYYY');
}

function parseTime(strDate) {
	return moment(strDate, 'DD.MM.YYYY').valueOf();
}
$(document).ready(function() {
	$.ajaxSetup({
		dataType : 'json',
		contentType : 'application/json',
		processData : false,
		traditional : false,
		beforeSend : function(request, settings) {
			if (settings.type == 'POST') {
				settings.data = JSON.stringify(settings.data);
			} else {
				delete settings.data;
			}
		}
	});
	
});