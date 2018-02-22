function clickableRows() {
	$('#contentTable').on( "click", "tr", function() {
			 $(this).children('td').children('input').prop('checked', true);
			  $('#contentTable tr').removeClass('selected');
			  $(this).toggleClass('selected');
	});
}