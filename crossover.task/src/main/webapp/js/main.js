function renderInsurance(insurance) {
	showErrorMessage({}, true);
	$('.editForm').toggle(false);
	$('#driverSearch').val('');
	var content = $('.content');
	if (content) {
		var tbody = content.find('#driversList');
		tbody.empty();

		insurance.drivers.forEach(function(_) {
			var driver = _;
			var bdate = new Date(driver.birthDate);
			var age = moment(bdate).fromNow(true);

			var row = $('<tr>');
			row.append($('<td>').text(driver.name), $('<td>').text(
					getTime(bdate)), $('<td>').text(age), $('<td>').text(
					driver.gender), $('<td>').text(driver.category), $('<td>')
					.append(
							$('<div class="inline control edit"/>').on(
									'click',
									function(e) {
										renderEditDriver($.extend(true, {},
												driver));
									})), $('<td>').append(
					($('<div class="inline control remove"/>').on('click',
							function(e) {
								removeDriver(driver);
							}))));
			tbody.append(row);

		});
	}
}

function renderEditDriver(d, addresses) {
	showErrorMessage({}, true);

	$('.editForm').find('#_name').val(d.name);
	$('.editForm').find('#_birthDate').val(getTime(d.birthDate));
	$('.editForm').find('#_age').val(moment(d.birthDate).fromNow(true));
	$('.editForm').find(
			'input[name="gender"][value="' + d.gender.toLowerCase() + '"]')
			.prop('checked', true);
	$('.editForm').find('#_cat').val(d.category);

	$('.editForm').find('#_save').off('click').on('click', function() {

		d.name = $('#_name').val();
		d.birthDate = parseTime($('#_birthDatedate').val());
		d.gender = $('input[name="gender"]:checked').val().toUpperCase();
		d.category = $('#_cat').val();
		console.log(d);
		$.ajax({
			type : "POST",
			url : './rest/updatedriver',
			data : d
		}).done(function() {
			console.log("updated")
			showInsurance();
		}).fail(function(e) {
			console.log("not updated")
			showErrorMessage(e);
			showInsurance();
		});
	});
	$('.editForm').toggle(true);
}

function removeDriver(driver) {
	$.ajax({
		url : './rest/delfromins?id=' + driver.id
	}).done(function(data) {
		console.log("removed")
		showInsurance();
	}).fail(function(e) {
		console.log("not removed")
		showInsurance();
		showErrorMessage(e);
	});
}

function addDriver(driver) {
	$.ajax({
		url : './rest/addtoins?id=' + driver.id
	}).done(function(data) {
		console.log("added")
		showInsurance();
	}).fail(function(e) {
		console.log("not added")
		showInsurance();
		showErrorMessage(e);
	});
}

function showErrorMessage(e, hide) {
	if (!hide) {
		$('#ajaxred').text(e.responseText);
	} else {
		$('#ajaxred').empty();
	}

}

function showInsurance() {
	$.ajax({
		type : 'GET',
		url : './rest/insurance',
	}).done(function(data) {
		renderInsurance(data);
	}).fail(function(e) {
		showErrorMessage(e);
	});
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

	$.getJSON('./rest/getcats', {}, function(data) {
		data.forEach(function(cat) {
			$('#_cat').append($('<option>').val(cat).text(cat));
		});
	});
	$('.editForm #_birthDate').datetimepicker({
		format : 'd.m.Y',
		timepicker : false,
		onSelectDate : function(ct, $i) {
			$('#_age').val(moment(ct).fromNow(true));
		}
	});
	$('input#driverSearch').autocomplete({
		lookup : function(query, done) {
			// Do ajax call or lookup locally, when done,
			// call the callback and pass your results:
			$.getJSON('./rest/getdrivers?search=' + query, {}, function(data) {
				var map = $.map(data, function(d) {
					return {
						value : d.name,
						data : d
					}
				});
				done({
					suggestions : map
				});
			});
		},
		onSelect : function(suggestion) {
			addDriver(suggestion.data);
		}
	});
	showInsurance();

});