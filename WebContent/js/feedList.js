/**
 * 
 */

theModule.controller('feedListCtrl', function($scope, $http, $rootScope){

   $scope.feedList = {}

	  $http.get('api/getFeeds').success(function(data, status, headers, config) {
		  $scope.feedList = angular.copy(data);
		  console.log($scope.feedList);
	  }).error(function(data, status, headers, config) {
	  });

}).directive('mapArea', function() {
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	var mapOptions = {
	    	          center: new google.maps.LatLng(scope.feed.gpsLatitude, scope.feed.gpsLongitude),
	    	          zoom: 10,
	    	          mapTypeId: google.maps.MapTypeId.ROADMAP
	    	        }
	    	var map = new google.maps.Map(element[0],mapOptions);
	    	var marker = new google.maps.Marker({
	            map: map,
	            position: new google.maps.LatLng(scope.feed.gpsLatitude, scope.feed.gpsLongitude),
	            title: 'Posted Location'
	        });
	    }
	}
});
