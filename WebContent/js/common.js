// for footer links in modal showing broken links.
$(document).ready(function(){
	setTimeout(function(){
		$("#myModal").click(function(evt){
			if(evt.target.className.indexOf('close')>-1)	return;
			$(".fade .close").click();
		});

		$( ".footer a" ).click(function() {
			$('#myModal').modal('show');		
		});
	},1000);
});

var theModule = angular.module('theModule', ['ui.bootstrap']);

theModule.run(function($rootScope) {

	$rootScope.cities = ['Chennai', 'Mumbai', 'Bangalore'];
	$rootScope.searchDistances = [5, 10, 15, 20 , 25, 50];
});