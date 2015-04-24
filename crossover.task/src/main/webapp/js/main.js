function getTime(date) {
	return moment(date).format('DD.MM.YYYY');
}

function parseTime(strDate) {
	return moment(strDate, 'DD.MM.YYYY').valueOf();
}

function updateState() {
	$.getJSON('./rest/state', {}, function(data) {
		if (!data) {
			return;
		}
		$('#_fullname').text(data.userName);

		if (!data.examStarted) {
			showExamList();
		} else if (data.examActive) {
			showActiveExam();
		} else {
			showExamResults(data);
		}

	});
}

function showExamList() {
	$.getJSON('./rest/exams', {}, function(data) {
		$('#_examlist').empty();
		data.forEach(function(e) {
			var item = e;
			$('#_examlist').append(
					$('<tr>').append($('<td>').text('#' + item.id),
							$('<td>').text(item.name),
							$('<td>').append($('<button>').click(function() {
								showExamInfo(item);
							}).text('View'))));
		});
		$('div.subtitle-div:visible').toggle(false);
		$('div.subtitle-div#_exams').toggle(true);
	});
}

function showActiveExam() {
	$.getJSON('./rest/questions', {}, function(data) {
		renderQuestionList(data);
	})
}

function showExamResults(state) {
	var d = $('div.subtitle-div#_examresult');
	d.find('#_id').text(state.exam.id);
	d.find('#_name').text(state.exam.name);
	d.find('#_pass').text(state.exam.passScore);
	d.find('#_total').text(state.exam.totalScore);
	d.find('#_score').text(state.score);
	if (state.examTimedOut) {
		d.find('#_status').text('Timed out').removeClass('green').addClass(
				'red');
	} else {
		d.find('#_status').text('Complete').removeClass('red')
				.addClass('green');
	}
	var b = $('#_answerResults');
	b.empty();
	state.exam.questions.forEach(function(q) {
		var qAns = state.userAnswers[q.id];
		var td = $('<td>');
		var score = 0;
		qAns.forEach(function(a) {
			td.append(' ').append(
					$('<span>').text(a.text).addClass(
							!a.correct ? 'red' : 'green'));
			if (a.correct) {
				score++;
			}
		})
		b.append($('<tr>').append($('<td>').text(q.title), td,
				$('<td>').text(score)))

	})
	d.find('#_sumscore').text(state.score);
	;
	$('div.subtitle-div:visible').toggle(false);
	d.toggle(true);
}

function showExamInfo(e) {
	var exam = e;
	var d = $('div.subtitle-div#_examinfo');
	d.find('#_id').text(exam.id);
	d.find('#_name').text(exam.name);
	d.find('#_desc').text(exam.description);
	d.find('#_duration').text(exam.duration);
	d.find('#_pass').text(exam.passScore);
	d.find('#_total').text(exam.totalScore);
	d.find('#_start').off('click').click(function() {
		startExam(exam);
	});
	$('div.subtitle-div:visible').toggle(false);
	d.toggle(true);
}

function startExam(e) {
	var exam = e;
	$.getJSON('./rest/startexam?id=' + exam.id, {}, function(data) {
		renderQuestionList(data);
	});
}

function renderQuestionList(qList) {
	activateTimer();
	questions = qList;
	var sel = $('select#_questions');
	sel.empty();
	questions.forEach(function(item) {
		var q = item;
		sel.append($('<option>').text(q.title).val(q.id));
	});
	sel.off('change').change(function() {
		var id = $(this).val()
		showQuestion(id);
	})
	showQuestionObject(questions[0]);
}

function showQuestion(questionId) {
	var id = questionId;
	var q = null;
	if (!questions) {
		return;
	}
	questions.forEach(function(item) {
		if (q == null && item.id == id) {
			q = item;
		}
	});
	showQuestionObject(q);
}

function showQuestionObject(question) {
	var q = question;
	if (q == null) {
		return;
	}
	var id = q.id;
	var d = $('div.subtitle-div#_question');
	d.find('#_order').text(q.questionOrder + 1);
	d.find('#_title').text(q.title);
	d.find('#_desc').text(q.description);

	$.getJSON('./rest/answers?id=' + id, {}, function(data) {
		var b = $('#_optionsBody');
		b.empty();
		data.forEach(function(item) {
			var ans = item;
			b.append($('<tr>').addClass('option').append(
					$('<td>').append(
							$('<label>')
									.append(
											$('<input>').prop(
													'type',
													!q.multiAnswer ? 'radio'
															: 'checkbox').prop(
													'name', 'a').val(ans.id)
													.addClass('answer'),
											$('<span>').text(ans.text)))))
		});
	});

	$('#_postanswer').off('click').click(
			function() {
				var answers = []
				$('input.answer:checked').each(function(index, item) {
					answers.push(parseInt($(item).val()));
				});
				if (answers.length == 0) {
					alert("You should choose "
							+ (q.multiAnswer ? "at least " : "")
							+ "one answer option!");
				}
				$.post('./rest/postanswer?id=' + id, answers, function(data) {
					var sel = $('select#_questions');
					var option = sel.find('option[value="' + q.id + '"]');
					option.remove();
					if (!data || data < 0) {
						updateState();
					} else {
						sel.val(data);
						showQuestion(data);
					}
				})
			});

	$('div.subtitle-div:visible').toggle(false);
	d.toggle(true);
}

function activateTimer() {
	if (!timerId) {
		timerId = window.setInterval(function() {
			if (!timerId) {
				return;
			}
			$.getJSON('./rest/timeleft', {}, function(time) {
				$('#_timeleft').text(millisecondsToString(time));
				if (time <= 0) {
					deactivateTimer();
					updateState();
				}
			})
		}, 2000);
	}
}

function deactivateTimer() {
	if (!timerId) {
		return;
	}
	window.clearInterval(timerId);
	timerId = null;
}

function millisecondsToString(ms) {
	var seconds = Math.floor(ms / 1000)
	var numhours = Math.floor(seconds / 3600);
	var numminutes = Math.floor((seconds % 3600) / 60);
	var numseconds = (seconds % 3600) % 60;
	return "Time left: " + pad(numhours) + ":" + pad(numminutes) + ":"
			+ pad(numseconds);

}
function pad(num, size) {
	if (!size) {
		size = 2;
	}
	var s = num + "";
	while (s.length < size)
		s = "0" + s;
	return s;
}

var questions = [];
var timerId = null;

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

	$('button.backToExamList').click(function() {
		showExamList();
	})
	updateState();

});

/*
 * 
 * $('input.answer:checked').map(function (index, item){return
 * parseInt($(item).val())})
 * 
 */