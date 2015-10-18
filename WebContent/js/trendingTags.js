/**
 * 
 */

theModule.controller('trendingTagsCtrl', function($scope, $http, $rootScope){
   $scope.params = {
	     	   
   }
   $scope.feedList = {}
   
	   window.navigator.geolocation.getCurrentPosition(function(position) {
	       $scope.$apply(function() {
	      	 $scope.params.gpsLatitudeNow = position.coords.latitude;
	      	 $scope.params.gpsLongitudeNow = position.coords.longitude;
		   	  $http.post('api/getTrendingHashTags', $scope.params).success(function(data, status, headers, config) {
				  $scope.trendingTags = data
			  }).error(function(data, status, headers, config) {
			  });
	       });
	   }, function(error) {
	       alert(error);
	   });
}).directive('mapArea', function() {
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	scope.$watch( function(){
	    	var mapOptions = {
	    	          center: new google.maps.LatLng(scope.params.gpsLatitudeNow, scope.params.gpsLongitudeNow),
	    	          zoom: 10,
	    	          mapTypeId: google.maps.MapTypeId.ROADMAP
	    	        }
	    	var map = new google.maps.Map(element[0],mapOptions);
	    	var marker = new google.maps.Marker({
	            map: map,
	            position: new google.maps.LatLng(scope.params.gpsLatitudeNow, scope.params.gpsLongitudeNow),
	            title: 'Posted Location'
	        });
	    	});
	    }
	}
}).directive('inputLocation', function() {
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	var mapElement = document.getElementsByClassName("mapArea");
	    	scope.$watch( function(){
	    	element.bind('blur', function() {
	    		var address = element.val();
	    		var geocoder = new google.maps.Geocoder();
	    	    geocoder.geocode( { 'address': address}, function(results, status) {
	    	      if (status == google.maps.GeocoderStatus.OK) {
	    	    	  scope.feed.gpsLatitude = results[0].geometry.location.k;
	    	    	  scope.feed.gpsLongitude = results[0].geometry.location.D;
	    	    	  var mapOptions = {
	    	    			  center: new google.maps.LatLng(scope.params.gpsLatitudeNow, scope.params.gpsLongitudeNow),
	    	    	          zoom: 10,
	    	    	          mapTypeId: google.maps.MapTypeId.ROADMAP
	    	    	        }
	    	    	var map = new google.maps.Map(mapElement[0],mapOptions);
	    	        map.setCenter(results[0].geometry.location);
	    	        var marker = new google.maps.Marker({
	    	            map: map,
	    	            position: new google.maps.LatLng(scope.params.gpsLatitudeNow, scope.params.gpsLongitudeNow),
	    	        });
	    	      } else {
	    	        console.log("Geocode was not successful for the following reason: " + status);
	    	      }
	        });
	    	});
	    	});
	    }
	}
})
